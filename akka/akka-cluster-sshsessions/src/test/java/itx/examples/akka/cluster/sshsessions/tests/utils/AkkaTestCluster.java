/*
 * Copyright © 2017 Pantheon Technologies, s.r.o. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.examples.akka.cluster.sshsessions.tests.utils;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import itx.examples.akka.cluster.sshsessions.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;

/**
 * Created by juraj on 3/17/17.
 */
public class AkkaTestCluster {

    private static final Logger LOG = LoggerFactory.getLogger(AkkaTestCluster.class);

    private int clusterSize;
    private Map<Integer, ClusterObjectRegistry> clusterObjects = new ConcurrentHashMap<>();
    private boolean clusterStarted;

    public AkkaTestCluster(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public void startCluster(long timeoutWaitForLeader, TimeUnit timeUnit) {
        LOG.info("starting akka cluster ...");
        long duration = System.currentTimeMillis();
        ClusterStatusObserver clusterStatusObserver = new ClusterStatusObserver(clusterSize);
        ClusterStatusObserverActorCreator clusterStatusObserverActorCreator
                = new ClusterStatusObserverActorCreator(clusterStatusObserver);
        for (int i = 1; i <= clusterSize; i++) {
            final ActorSystem actorSystem;
            if (clusterSize == 1) {
                actorSystem = ActorSystem.create(
                        Utils.CLUSTER_NAME, ConfigFactory.load("single-node1"));
            } else {
                actorSystem = ActorSystem.create(
                        Utils.CLUSTER_NAME, ConfigFactory.load("node" + i));
            }
            actorSystem.actorOf(Props.create(clusterStatusObserverActorCreator), "test-cluster-satus-observer");
            clusterObjects.put(i, new ClusterObjectRegistry(i, actorSystem, clusterStatusObserver));
        }

        clusterStatusObserver.waitForAllMembersWithLeader(timeoutWaitForLeader, timeUnit);
        clusterStarted = true;
        duration = System.currentTimeMillis() - duration;
        LOG.info(MessageFormat.format("akka cluster started in {0} ms", duration));
    }

    public void stopCluster() throws Exception {
        LOG.info("cluster shutdown ...");
        long duration = System.currentTimeMillis();
        for (ClusterObjectRegistry clusterObjectRegistry: clusterObjects.values()) {
            clusterObjectRegistry.getActorSystem().terminate();
        }

        LOG.info("waiting for cluster to shutdown !");
        await().atMost(15, TimeUnit.SECONDS).until(() ->
                clusterObjects.values().stream().allMatch(c -> c.getActorSystem().isTerminated()));
        clusterStarted = false;
        duration = System.currentTimeMillis() - duration;
        LOG.info(MessageFormat.format("cluster stopped in {0} ms", duration));
    }

    public boolean isClusterStarted() {
        return clusterStarted;
    }

    public int getSize() {
        return clusterSize;
    }

    public ClusterObjectRegistry getClusterObjectRegistry(int ordinal) {
        return clusterObjects.get(ordinal);
    }

}

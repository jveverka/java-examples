package itx.examples.akka.cluster.sshsessions;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import itx.examples.akka.cluster.sshsessions.client.SshClientService;
import itx.examples.akka.cluster.sshsessions.client.SshClientServiceImpl;
import itx.examples.akka.cluster.sshsessions.cluster.SshClusterManager;
import itx.examples.akka.cluster.sshsessions.cluster.SshClusterManagerActorCreator;
import itx.examples.akka.cluster.sshsessions.sessions.SshSessionActorCreator;
import itx.examples.akka.cluster.sshsessions.sessions.SshSessionCreatorAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juraj on 3/18/17.
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private ActorSystem actorSystem;
    private ActorRef sshClusterManagerActor;
    private SshClientService sshClientService;

    public Application(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    public void init() {
        LOG.info("Application init ...");

        SshClusterManager sshClusterManager = new SshClusterManager();
        SshClusterManagerActorCreator sshClusterManagerActorCreator = new SshClusterManagerActorCreator(sshClusterManager);
        sshClusterManagerActor = actorSystem.actorOf(Props.create(sshClusterManagerActorCreator), Utils.CLUSTER_MANAGER_NAME);

        actorSystem.actorOf(Props.create(SshSessionCreatorAgent.class), Utils.SESSION_CREATOR_AGENT_NAME);

        sshClientService = new SshClientServiceImpl(actorSystem, sshClusterManager);

        LOG.info("Application done.");
    }

    public void destroy() {
        LOG.info("Application shutdown.");
        sshClusterManagerActor.tell(PoisonPill.getInstance(), null);
    }

    public SshClientService getSshClientService() {
        return sshClientService;
    }

    public static void main(String[] args) {
        LOG.info("Application start ...");
        String akkaConfigPath = System.getProperty("akka.config.path");
        String clusterName = System.getProperty("akka.cluster.name");
        LOG.info("akka.config.path=" + akkaConfigPath);
        LOG.info("akka.cluster.name=" + clusterName);
        ActorSystem actorSystem = ActorSystem.create(clusterName, ConfigFactory.load(akkaConfigPath));
        Application application = new Application(actorSystem);
        application.init();
        LOG.info("started.");
    }

}

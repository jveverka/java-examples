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
import itx.examples.akka.cluster.sshsessions.sessions.SshLocalManagerActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by juraj on 3/18/17.
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private ActorSystem actorSystem;
    private ActorRef sshClusterManagerActor;
    private ActorRef sshLocalManagerActor;
    private SshClientService sshClientService;

    public Application(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    public void init() {
        LOG.info("Application init ...");

        SshClusterManager sshClusterManager = new SshClusterManager();
        SshClusterManagerActorCreator sshClusterManagerActorCreator = new SshClusterManagerActorCreator(sshClusterManager);
        sshClusterManagerActor = actorSystem.actorOf(
                Props.create(sshClusterManagerActorCreator), Utils.CLUSTER_MANAGER_NAME);

        sshLocalManagerActor = actorSystem.actorOf(
                Props.create(SshLocalManagerActor.class), Utils.LOCAL_MANAGER_NAME);

        sshClientService = new SshClientServiceImpl(actorSystem, sshClusterManager);

        LOG.info("Application done.");
    }

    public void destroy() {
        LOG.info("Application shutdown.");
        sshClusterManagerActor.tell(PoisonPill.getInstance(), ActorRef.noSender());
        sshLocalManagerActor.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }

    public SshClientService getSshClientService() {
        return sshClientService;
    }

    public static void main(String[] args) {
        LOG.info("Application start ...");
        if (args.length < 1) {
            LOG.error("Error: expected first parameter /path/to/akka.conf file");
            System.exit(1);
        }
        File akkaConfigFile = new File(args[0]);
        if (!akkaConfigFile.exists()) {
            LOG.error("Error: config file not found " + args[0]);
            System.exit(1);
        }
        LOG.info("akkaConfigPath = " + args[0]);
        ActorSystem actorSystem = ActorSystem.create(Utils.CLUSTER_NAME, ConfigFactory.parseFile(akkaConfigFile));
        Application application = new Application(actorSystem);
        application.init();

        Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    public void run() {
                        LOG.info("shutting down ...");
                        application.destroy();
                        actorSystem.terminate();
                        LOG.info("bye.");
                    }
                }
        );

        LOG.info("started.");
    }

}

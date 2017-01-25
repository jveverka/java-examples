package itx.java.examples.enigma.thebomb;

import itx.java.examples.enigma.configuration.EnigmaConfiguration;
import itx.java.examples.enigma.configuration.EnigmaSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 24.1.2017.
 */
public class TheBomb {

    final private static Logger LOG = LoggerFactory.getLogger(TheBomb.class);

    private EnigmaConfiguration enigmaConfiguration;
    private String encodedMessage;
    private String expectedString;

    public TheBomb(EnigmaConfiguration enigmaConfiguration, String encodedMessage, String expectedString) {
        LOG.info("init ...");
        this.enigmaConfiguration = enigmaConfiguration;
        this.encodedMessage = encodedMessage;
        this.expectedString = expectedString;
    }

    public EnigmaConfiguration findEnigmaSetup() {
        LOG.info("searching for enigma setup ...");
        long duration = System.currentTimeMillis();
        EnigmaConfiguration result = new EnigmaConfiguration(enigmaConfiguration.getAplhabet(), enigmaConfiguration.getRotorParameters(), null);
        EnigmaSettings enigmaSettings = null;

        result.setEnigmaSetup(enigmaSettings);
        duration = System.currentTimeMillis() - duration;
        LOG.info("done: duration = " + duration + " ms");
        return result;
    }

}

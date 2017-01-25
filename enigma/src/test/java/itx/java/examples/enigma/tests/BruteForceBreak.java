package itx.java.examples.enigma.tests;

import itx.java.examples.enigma.Enigma;
import itx.java.examples.enigma.Utils;
import itx.java.examples.enigma.configuration.EnigmaConfiguration;
import itx.java.examples.enigma.thebomb.TheBomb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gergej on 24.1.2017.
 */
public class BruteForceBreak {

    final private static Logger LOG = LoggerFactory.getLogger(BruteForceBreak.class);

    @Test(enabled = false)
    public void findEnigmaSetup() throws IOException {
        String enigmaConfigPath = "configurations/enigma-test-configuration-26-02.json";
        String originalMessage = "THETOPSECRETMESSAGEITTHIS";
        String messagePart = "THE";

        LOG.info("encrypting message ...");
        InputStream is = UtilsTests.class.getClassLoader().getResourceAsStream(enigmaConfigPath);
        EnigmaConfiguration enigmaConfiguration = Utils.readEnigmaConfiguration(is);
        Enigma enigmaForEncryption = Enigma.builder().fromConfiguration(enigmaConfiguration).build();
        String encryptedMessage = Utils.encryptOrDecrypt(enigmaForEncryption, originalMessage);

        LOG.info("starting the bomb ...");
        EnigmaConfiguration config = new EnigmaConfiguration(enigmaConfiguration.getAplhabet(), enigmaConfiguration.getRotorParameters(), null);
        TheBomb theBomb = new TheBomb(config, encryptedMessage, messagePart);
        EnigmaConfiguration calculatedConfiguration = theBomb.findEnigmaSetup();

        LOG.info("checking enigma settings ...");
        Enigma enigmaForDecryption = Enigma.builder().fromConfiguration(calculatedConfiguration).build();
        String decryptedMessage = Utils.encryptOrDecrypt(enigmaForDecryption, originalMessage);

        LOG.info("evaluating the results ...");
        Assert.assertNotNull(encryptedMessage);
        Assert.assertNotNull(decryptedMessage);
        Assert.assertEquals(originalMessage, decryptedMessage);
    }

    public static void main(String[] args) throws IOException {
        BruteForceBreak bruteForceBreak = new BruteForceBreak();
        bruteForceBreak.findEnigmaSetup();
    }

}

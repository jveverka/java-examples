package itx.java.examples.enigma.tests;

import itx.java.examples.enigma.Enigma;
import itx.java.examples.enigma.EnigmaFactory;
import itx.java.examples.enigma.Utils;
import itx.java.examples.enigma.alphabet.Alphabet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gergej on 17.1.2017.
 *
 * https://en.wikipedia.org/wiki/Enigma_machine
 *
 */
public class EnigmaTest {

    @Test
    public void testSimpleMessage() {
        Alphabet alphabet = Alphabet.buildAlphabet26();
        int[] initialRotorPositions = new int[3];
        Character[][] plugBoardSetup = Utils.generateRandomPlugBoadrSetup(alphabet);
        initialRotorPositions[0] = 9;
        initialRotorPositions[1] = 19;
        initialRotorPositions[2] = 23;
        String originalMessage = "SECRETMESSAGE";
        Enigma enigmaForEncryption = EnigmaFactory.createEnigma(alphabet, initialRotorPositions, plugBoardSetup);
        String encryptedMessage = Utils.encryptOrDecrypt(enigmaForEncryption, originalMessage);
        Enigma enigmaForDecryption = EnigmaFactory.createEnigma(alphabet, initialRotorPositions, plugBoardSetup);
        String decryptedMessage = Utils.encryptOrDecrypt(enigmaForDecryption, encryptedMessage);
        Assert.assertNotNull(encryptedMessage);
        Assert.assertTrue(originalMessage.length() == encryptedMessage.length());
        Assert.assertNotNull(decryptedMessage);
        Assert.assertTrue(encryptedMessage.length() == decryptedMessage.length());
        Assert.assertEquals(originalMessage, decryptedMessage);
    }

}

package itx.java.examples.enigma.tests;

import itx.java.examples.enigma.Enigma;
import itx.java.examples.enigma.EnigmaFactory;
import itx.java.examples.enigma.Utils;
import itx.java.examples.enigma.alphabet.Alphabet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.commons.io.IOUtils;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * Created by gergej on 17.1.2017.
 *
 * https://en.wikipedia.org/wiki/Enigma_machine
 *
 */
public class EnigmaTest {

    @DataProvider(name = "messages26")
    public static Object[][] getMassages26() {
        return new Object[][]{
                {"SECRETMESSAGE"},
                {"HELLO"},
                {"ABCDEFGHIJKLMNOPQRSTUVWXYZ"},
                {"ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ"},
        };
    }

    @Test(dataProvider = "messages26")
    public void testSimpleMessageAlphabet26(String originalMessage) {
        Alphabet alphabet = Alphabet.buildAlphabet26();
        int[] initialRotorPositions = new int[3];
        Character[][] plugBoardSetup = Utils.generateRandomPlugBoadrSetup(alphabet);
        initialRotorPositions[0] = 9;
        initialRotorPositions[1] = 19;
        initialRotorPositions[2] = 23;
        Enigma enigmaForEncryption = EnigmaFactory.createEnigma26(initialRotorPositions, plugBoardSetup);
        Enigma enigmaForDecryption = EnigmaFactory.createEnigma26(initialRotorPositions, plugBoardSetup);
        String encryptedMessage = Utils.encryptOrDecrypt(enigmaForEncryption, originalMessage);
        String decryptedMessage = Utils.encryptOrDecrypt(enigmaForDecryption, encryptedMessage);
        Assert.assertNotNull(encryptedMessage);
        Assert.assertTrue(originalMessage.length() == encryptedMessage.length());
        Assert.assertNotNull(decryptedMessage);
        Assert.assertTrue(encryptedMessage.length() == decryptedMessage.length());
        Assert.assertEquals(originalMessage, decryptedMessage);
    }

    @DataProvider(name = "messagesBase64")
    public static Object[][] getMassagesBase64() {
        return new Object[][]{
                {"dataFiles/dataFilePlain01.txt"},
        };
    }

    @Test(dataProvider = "messagesBase64")
    public void testSimpleMessageAlphabetBase64(String inputStreamClassPath) throws IOException {
        Alphabet alphabet = Alphabet.buildAlphabetBase64();
        int[] initialRotorPositions = new int[3];
        Character[][] plugBoardSetup = Utils.generateRandomPlugBoadrSetup(alphabet);
        initialRotorPositions[0] = 18;
        initialRotorPositions[1] = 12;
        initialRotorPositions[2] = 21;
        Enigma enigmaForEncryption = EnigmaFactory.createEnigmaBase64(initialRotorPositions, plugBoardSetup);
        Enigma enigmaForDecryption = EnigmaFactory.createEnigmaBase64(initialRotorPositions, plugBoardSetup);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(inputStreamClassPath);
        Assert.assertNotNull(is);
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, Charset.forName("UTF-8"));
        String originalMessage = writer.toString();
        String encryptedMessage = Utils.encryptUnicodeString(enigmaForEncryption, originalMessage);
        String decryptedMessage = Utils.encryptUnicodeString(enigmaForDecryption, encryptedMessage);
        Assert.assertNotNull(encryptedMessage);
        Assert.assertNotNull(decryptedMessage);
        Assert.assertEquals(originalMessage, decryptedMessage);
    }

}
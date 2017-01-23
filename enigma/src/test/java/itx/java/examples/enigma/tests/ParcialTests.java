package itx.java.examples.enigma.tests;

import itx.java.examples.enigma.EnigmaSetup;
import itx.java.examples.enigma.Utils;
import itx.java.examples.enigma.rotors.Reflector;
import itx.java.examples.enigma.rotors.Rotor;
import itx.java.examples.enigma.rotors.RotorGroup;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gergej on 18.1.2017.
 */
public class ParcialTests {

    @Test
    public void testCopyTable() {
        int[][] copy = Utils.copySubstitutionTable(EnigmaSetup.rotor0Data);
        Assert.assertTrue(copy.length == EnigmaSetup.rotor0Data.length);
        Assert.assertTrue(copy[0].length == EnigmaSetup.rotor0Data[0].length);
        for (int i=0; i<copy.length; i++) {
            for (int j=0;j<copy[0].length; j++) {
                Assert.assertTrue(copy[i].length == EnigmaSetup.rotor0Data[i].length);
                Assert.assertTrue(copy[i][j] == EnigmaSetup.rotor0Data[i][j]);
            }
        }
    }

    @Test
    public void testSubstitution() {
        int[][] table = new int[3][2];
        table[0][0] = 0; table[0][1] = 2;
        table[1][0] = 1; table[1][1] = 0;
        table[2][0] = 2; table[2][1] = 1;
        int subsFwd = Utils.getSubstitutionOf(table, 0, false);
        int subsRev = Utils.getSubstitutionOf(table, 1, true);
        Assert.assertTrue(2 == subsFwd);
        Assert.assertTrue(2 == subsRev);
    }

    @Test
    public void testShiftTable() {
        int[][] table = new int[5][2];
        table[0][0] = 0; table[0][1] = 2;
        table[1][0] = 1; table[1][1] = 0;
        table[2][0] = 2; table[2][1] = 1;
        table[3][0] = 3; table[3][1] = 4;
        table[4][0] = 4; table[4][1] = 3;
        table = Utils.shiftSubstitutionTable(table);
        Assert.assertTrue(table[0][0] == 1);
        Assert.assertTrue(table[0][1] == 0);
        Assert.assertTrue(table[4][0] == 0);
        Assert.assertTrue(table[4][1] == 2);
    }

    @Test
    public void testRotorForward() {
        Rotor rotor = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor0Data)
                .setIndex(0)
                .build();
        for (int i=0; i<EnigmaSetup.rotor0Data.length; i++) {
            int result = rotor.substituteForward(i);
            int subtitute = EnigmaSetup.rotor0Data[i][1];
            Assert.assertEquals(subtitute, result);
        }
    }

    @Test
    public void testRotor() {
        Rotor rotor = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor0Data)
                .setIndex(0)
                .build();
        for (int i = 0; i < EnigmaSetup.rotor0Data.length; i++) {
            Integer result = rotor.substituteForward(i);
            Integer reverse = rotor.substituteReverse(result);
            Assert.assertEquals(reverse, new Integer(i));
        }
    }

    @Test
    public void testShiftedRotor() {
        Rotor rotor = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor0Data)
                .setIndex(5)
                .build();
        for (int i = 0; i < EnigmaSetup.rotor0Data.length; i++) {
            Integer result = rotor.substituteForward(i);
            Integer reverse = rotor.substituteReverse(result);
            Assert.assertEquals(reverse, new Integer(i));
        }
    }

    @Test
    public void testReflector() {
        Reflector reflector = Reflector.builder()
                .setSubstitutionTable(EnigmaSetup.reflectorData)
                .build();
        for (int i=0; i<EnigmaSetup.reflectorData.length; i++) {
            Integer result = reflector.substitute(i);
            Integer subtitute = EnigmaSetup.reflectorData[i][1];
            Assert.assertEquals(subtitute, result);
        }
    }

    @Test
    public void testRotorGroup() {
        Rotor[] rotors = new Rotor[3];
        rotors[0] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor0Data)
                .setIndex(0)
                .build();
        rotors[1] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor1Data)
                .setIndex(0)
                .build();
        rotors[2] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor2Data)
                .setIndex(0)
                .build();
        RotorGroup rotorGroup = RotorGroup.builder().setRotors(rotors).build();
        Reflector reflector = Reflector.builder().setSubstitutionTable(EnigmaSetup.reflectorData).build();

        for (int i=0; i<EnigmaSetup.rotor0Data.length; i++) {
            Integer result0 = rotorGroup.substituteForward(i);
            Integer reflected0 = reflector.substitute(result0);
            Integer outPut0 = rotorGroup.substituteReverse(reflected0);

            Integer result1 = rotorGroup.substituteForward(outPut0);
            Integer reflected1 = reflector.substitute(result1);
            Integer outPut1 = rotorGroup.substituteReverse(reflected1);

            Assert.assertTrue(i == outPut1, i + " != " + outPut0);
        }
    }

    @Test
    public void testRotorGroupRotate() {
        Rotor[] rotors = new Rotor[3];
        rotors[0] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor0Data)
                .setIndex(0)
                .build();
        rotors[1] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor1Data)
                .setIndex(1)
                .build();
        rotors[2] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor2Data)
                .setIndex(2)
                .build();
        RotorGroup rotorGroup = RotorGroup.builder().setRotors(rotors).build();
        Reflector reflector = Reflector.builder().setSubstitutionTable(EnigmaSetup.reflectorData).build();

        for (int r=0; r<10; r++) {
            for (int i = 0; i < EnigmaSetup.rotor0Data.length; i++) {
                Integer result0 = rotorGroup.substituteForward(i);
                Integer reflected0 = reflector.substitute(result0);
                Integer outPut0 = rotorGroup.substituteReverse(reflected0);

                Integer result1 = rotorGroup.substituteForward(outPut0);
                Integer reflected1 = reflector.substitute(result1);
                Integer outPut1 = rotorGroup.substituteReverse(reflected1);

                Assert.assertTrue(i == outPut1, i + " != " + outPut0);
            }
            rotorGroup.shift();
        }
    }

    @Test
    public void testRotorGroupRotateSameInput() {
        Rotor[] rotors = new Rotor[3];
        rotors[0] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor0Data)
                .setIndex(0)
                .build();
        rotors[1] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor1Data)
                .setIndex(1)
                .build();
        rotors[2] = Rotor.builder()
                .setSubstitutionTable(EnigmaSetup.rotor2Data)
                .setIndex(2)
                .build();
        RotorGroup rotorGroup = RotorGroup.builder().setRotors(rotors).build();
        Reflector reflector = Reflector.builder().setSubstitutionTable(EnigmaSetup.reflectorData).build();

        int input = 8;
        int encrypted = -1; //encrypted
        int decrypted = -1; //decrypted
        int encryptedOld = -1;

        for (int rotation=0; rotation<10; rotation++) {
            int result0 = rotorGroup.substituteForward(input);
            int reflected0 = reflector.substitute(result0);
            encrypted = rotorGroup.substituteReverse(reflected0);

            int result1 = rotorGroup.substituteForward(encrypted);
            int reflected1 = reflector.substitute(result1);
            decrypted = rotorGroup.substituteReverse(reflected1);

            Assert.assertTrue(input == decrypted, input + " != " + encrypted);

            if (rotation > 0) {
                Assert.assertTrue(encrypted != input);
                Assert.assertTrue(encrypted != encryptedOld);

            }
            encryptedOld = encrypted;
            rotorGroup.shift();
        }

    }

}

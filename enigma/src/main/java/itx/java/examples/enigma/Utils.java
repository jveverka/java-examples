package itx.java.examples.enigma;

import com.sun.javafx.image.IntPixelGetter;
import itx.java.examples.enigma.alphabet.Alphabet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gergej on 17.1.2017.
 */
public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException();
    }

    public static String encryptOrDecrypt(Enigma enigma, String message) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < message.length(); i++) {
            sb.append(enigma.encryptOrDecrypt(message.charAt(i)));
        }
        return sb.toString();
    }

    public static int[][] createSubstitutionMap(Alphabet alphabet, String randomizedAlphabet) {
        if (alphabet.getSize() != randomizedAlphabet.length()) {
            throw new UnsupportedOperationException("input and output string must have same lenght");
        }
        int[][] result = new int[alphabet.getSize()][2];
        for (int i = 0; i < alphabet.getSize(); i++) {
            result[i][0] = i;
            result[i][1] = alphabet.getIndex(randomizedAlphabet.charAt(i));
        }
        return result;
    }

    public static Character[][] generateRandomPlugBoadrSetup(Alphabet alphabet) {
        int length = alphabet.getSize() / 2;
        if (length % 2 == 0) {
            throw new UnsupportedOperationException("alphabet length must be divisible by 2");
        }
        Integer[] randomIndexes = generateRandomIndexes(length);
        Character[][] result = new Character[length][2];
        for (int i = 0; i < length; i++) {
            result[i][0] = alphabet.getCharacter(randomIndexes[i]);
            result[i][1] = alphabet.getCharacter(randomIndexes[i] + length);
        }
        return result;
    }

    public static Integer[] generateRandomIndexes(int length) {
        Random random = new Random(System.currentTimeMillis());
        Integer randomIndexes[] = new Integer[length];
        int counter = 0;
        while (true) {
            int rnd = random.nextInt(length);
            boolean isUsed = false;
            for (int i = 0; i < length; i++) {
                if (randomIndexes[i] != null && randomIndexes[i] == rnd) {
                    isUsed = true;
                }
            }
            if (!isUsed) {
                randomIndexes[counter] = rnd;
                counter++;
            }
            if (counter >= length) {
                break;
            }
        }
        return randomIndexes;
    }

    public static int getSubstitutionOf(int[][] table, int value, boolean reverse) {
        if (!reverse) {
            if (value >= table.length) {
                throw new UnsupportedOperationException("no forward substitution found for " + value);
            }
            return table[value][1];
        } else {
            for (int i = 0; i < table.length; i++) {
                if (table[i][1] == value) {
                    return i;
                }
            }
            throw new UnsupportedOperationException("no reverse substitution found for " + value);
        }
    }

    public static int[][] copySubstitutionTable(int[][] table) {
        int[][] result = new int[table.length][table[0].length];
        for (int i=0; i<table.length; i++) {
            for (int j=0; j<table[0].length; j++) {
                result[i][j] = table[i][j];
            }
        }
        return result;
    }

    public static int[][] shiftSubstitutionTable(int[][] table) {
        int firstRow[] = table[0];
        for (int i = 0; i < (table.length-1); i++) {
            table[i] = table[i + 1];
        }
        table[table.length - 1] = firstRow;
        return table;
    }

    public static int[][] shiftSubstitutionTable(int[][] table, int shift) {
        for (int i=0; i<shift; i++) {
            table = shiftSubstitutionTable(table);
        }
        return table;
    }

}
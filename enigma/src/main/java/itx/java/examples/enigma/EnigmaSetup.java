package itx.java.examples.enigma;

import itx.java.examples.enigma.alphabet.Alphabet;

import java.util.Map;

/**
 * Created by gergej on 17.1.2017.
 */
public final class EnigmaSetup {

    private EnigmaSetup() {
        throw new UnsupportedOperationException();
    }

    public static final int[][] reflectorData;
    public static final int[][] rotor0Data;
    public static final int[][] rotor1Data;
    public static final int[][] rotor2Data;

    static {
        rotor0Data = Utils.createSubstitutionMap(Alphabet.buildAlphabet26(),
                "OKXRZMPWGDYQLVBIFJAUSCTHEN");
        rotor1Data = Utils.createSubstitutionMap(Alphabet.buildAlphabet26(),
                "OKXRZMPWGDYQLVBIFJAUSCTHEN");
        rotor2Data = Utils.createSubstitutionMap(Alphabet.buildAlphabet26(),
                "OKXRZMPWGDYQLVBIFJAUSCTHEN");
        reflectorData = Utils.createSubstitutionMap(Alphabet.buildAlphabet26(),
                "WZXYVUTSRQPONMLKJIHGFEDCBA");
    }

}

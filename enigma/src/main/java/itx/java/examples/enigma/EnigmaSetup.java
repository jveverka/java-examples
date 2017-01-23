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

    public static final Alphabet defaultAlphabet;
    public static final int[][] reflectorData;
    public static final int[][] rotor0Data;
    public static final int[][] rotor1Data;
    public static final int[][] rotor2Data;

    static {
        defaultAlphabet = Alphabet.buildAlphabet26();
        rotor0Data = Utils.createSubstitutionMap(defaultAlphabet,
                "OKXRZMPWGDYQLVBIFJAUSCTHEN");
        rotor1Data = Utils.createSubstitutionMap(defaultAlphabet,
                "OKXRZMPWGDYQLVBIFJAUSCTHEN");
        rotor2Data = Utils.createSubstitutionMap(defaultAlphabet,
                "OKXRZMPWGDYQLVBIFJAUSCTHEN");
        reflectorData = Utils.createReflectorSubstitutionMap(Alphabet.buildAlphabet26());
    }

}

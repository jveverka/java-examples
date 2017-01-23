package itx.java.examples.enigma;

import itx.java.examples.enigma.alphabet.Alphabet;
import itx.java.examples.enigma.plugboard.PlugBoard;
import itx.java.examples.enigma.rotors.Reflector;
import itx.java.examples.enigma.rotors.Rotor;
import itx.java.examples.enigma.rotors.RotorGroup;

/**
 * Created by gergej on 17.1.2017.
 */
public final class EnigmaFactory {

    private EnigmaFactory() {
        throw new UnsupportedOperationException();
    }

    public static Enigma createEnigma(Alphabet alphabet, int[] initialRotorPositions, Character[][] setup) {
        Rotor rotor0 = Rotor.builder()
                .setIndex(initialRotorPositions[0])
                .setSubstitutionTable(EnigmaSetup.rotor0Data)
                .build();
        Rotor rotor1 = Rotor.builder()
                .setIndex(initialRotorPositions[1])
                .setSubstitutionTable(EnigmaSetup.rotor1Data)
                .build();
        Rotor rotor2 = Rotor.builder()
                .setIndex(initialRotorPositions[2])
                .setSubstitutionTable(EnigmaSetup.rotor2Data)
                .build();
        PlugBoard plugBoard = PlugBoard.builder()
                .setSetup(setup)
                .build();
        Rotor[] rotors = new Rotor[3];
        rotors[0] = rotor0;
        rotors[1] = rotor1;
        rotors[2] = rotor2;
        RotorGroup rotorGroup = RotorGroup.builder()
                .setRotors(rotors)
                .build();
        Reflector reflector = Reflector.builder()
                .setSubstitutionTable(EnigmaSetup.reflectorData)
                .build();
        return Enigma.builder()
                .setAlphabet(alphabet)
                .setReflector(reflector)
                .setPlugBoard(plugBoard)
                .setRotorGroup(rotorGroup)
                .build();
    }

}

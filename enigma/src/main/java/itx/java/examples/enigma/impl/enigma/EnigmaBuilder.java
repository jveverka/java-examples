package itx.java.examples.enigma.impl.enigma;

import itx.java.examples.enigma.Enigma;
import itx.java.examples.enigma.alphabet.Alphabet;
import itx.java.examples.enigma.plugboard.PlugBoard;
import itx.java.examples.enigma.rotors.Reflector;
import itx.java.examples.enigma.rotors.RotorGroup;

/**
 * Created by gergej on 17.1.2017.
 */
public class EnigmaBuilder {

    private Alphabet alphabet;
    private Reflector reflector;
    private RotorGroup rotorGroup;
    private PlugBoard plugBoard;

    public EnigmaBuilder setAlphabet(Alphabet alphabet) {
        this.alphabet = alphabet;
        return this;
    }

    public EnigmaBuilder setReflector(Reflector reflector) {
        this.reflector = reflector;
        return this;
    }

    public EnigmaBuilder setRotorGroup(RotorGroup rotorGroup) {
        this.rotorGroup = rotorGroup;
        return this;
    }

    public EnigmaBuilder setPlugBoard(PlugBoard plugBoard) {
        this.plugBoard = plugBoard;
        return this;
    }

    public Enigma build() {
        return new EnigmaImpl(alphabet, reflector, rotorGroup, plugBoard);
    }

}

package itx.java.examples.enigma.alphabet;

import itx.java.examples.enigma.impl.alphabet.AlphabetBuilder;
import itx.java.examples.enigma.impl.alphabet.AlphabetImpl;

/**
 * Created by gergej on 18.1.2017.
 */
public interface Alphabet {

    public static final String alphabet26 = "ABCDEFGHIJKLMNOPQRSTUVYXZW";

    public int getIndex(Character character);

    public Character getCharacter(int index);

    public int getSize();

    public String getAlphabet();

    public static AlphabetBuilder builder() {
        return new AlphabetBuilder();
    }

    public static Alphabet buildAlphabet26() {
        return new AlphabetImpl(Alphabet.alphabet26);
    }

}

package itx.java.examples.enigma.impl.rotors;

import itx.java.examples.enigma.rotors.Rotor;
import itx.java.examples.enigma.rotors.RotorGroup;

/**
 * Created by gergej on 17.1.2017.
 */
public class RotorGroupBuilder {

    private Rotor[] rotors;

    public RotorGroupBuilder setRotors(Rotor[] rotors) {
        this.rotors = rotors;
        return this;
    }

    public RotorGroup build() {
        return new RotorGroupImpl(rotors);
    }

}

package itx.java.examples.enigma.thebomb;

import itx.java.examples.enigma.Utils;

import java.util.Arrays;

/**
 * Created by gergej on 25.1.2017.
 */
public class Permutator {

    private int[] data;
    private int[] mutated;
    private long max;
    private long counter;
    private long shiftLeftCounter;

    public Permutator(int[] data) {
        this.data = data;
        this.mutated = Arrays.copyOf(data, data.length);
        this.max = Utils.factorial(data.length);
        this.counter = 0;
        this.shiftLeftCounter = 0;
    }

    public long getSize() {
        return max;
    }

    public int[] getNext() {
        if (counter >= max) {
            return null;
        } else {
            if (shiftLeftCounter < data.length) {
                if (counter > 0) {
                    mutated = shiftLeft(mutated);
                }
                shiftLeftCounter++;
            } else {
                shiftLeftCounter = 0;
                mutated = Arrays.copyOf(data, data.length);
                mutated = swapFirstLast(mutated);
            }
            counter++;
            return mutated;
        }
    }

    public int[] shiftLeft(int[] data) {
        int first = data[0];
        for (int i=1; i<(data.length); i++) {
            data[i-1] = data[i];
        }
        data[data.length - 1] = first;
        return data;
    }

    public int[] shiftRight(int[] data) {
        int last = data[data.length - 1];
        for (int i=(data.length - 1); i>0; i--) {
            data[i] = data[i-1];
        }
        data[0] = last;
        return data;
    }

    public int[] swapFirstLast(int[] data) {
        int first = data[0];
        int last = data[data.length - 1];
        data[0] = last;
        data[data.length - 1] = first;
        return data;
    }

}

package itx.java.examples.enigma.tests;

import itx.java.examples.enigma.Utils;
import itx.java.examples.enigma.thebomb.Permutator;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gergej on 25.1.2017.
 */
public class PermutatorTests {

    @Test
    public void shiftLeftTest() {
        int[] data = { 0, 1, 2, 3 };
        int[] dataShifted1 = { 1, 2, 3, 0 };
        int[] dataShifted2 = { 2, 3, 0, 1 };
        int[] dataShifted3 = { 3, 0, 1, 2 };
        int[] dataShifted4 = { 0, 1, 2, 3 };
        int[] dataShifted5 = { 1, 2, 3, 0 };
        Permutator permutator = new Permutator(data);
        data = permutator.shiftLeft(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted1));
        data = permutator.shiftLeft(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted2));
        data = permutator.shiftLeft(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted3));
        data = permutator.shiftLeft(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted4));
        data = permutator.shiftLeft(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted5));
    }

    @Test
    public void shiftRightTest() {
        int[] data = { 0, 1, 2, 3 };
        int[] dataShifted1 = { 3, 0, 1, 2 };
        int[] dataShifted2 = { 2, 3, 0, 1 };
        int[] dataShifted3 = { 1, 2, 3, 0 };
        int[] dataShifted4 = { 0, 1, 2, 3 };
        int[] dataShifted5 = { 3, 0, 1, 2 };
        Permutator permutator = new Permutator(data);
        data = permutator.shiftRight(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted1));
        data = permutator.shiftRight(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted2));
        data = permutator.shiftRight(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted3));
        data = permutator.shiftRight(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted4));
        data = permutator.shiftRight(data);
        Assert.assertTrue(Utils.compareArrays(data, dataShifted5));
    }

    @Test
    public void permutationTest() {
        int[] data = { 1, 2, 3 };
        Permutator permutator = new Permutator(data);
        Assert.assertTrue(permutator.getSize() == 6);
        long counter = 6;
        while(counter>0) {
            int[] next = permutator.getNext();
            Assert.assertNotNull(next);
            counter--;
        }
        int[] next = permutator.getNext();
        Assert.assertNull(next);
    }

}

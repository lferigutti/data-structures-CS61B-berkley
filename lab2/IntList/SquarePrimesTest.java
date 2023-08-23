package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimeEndBeg(){
        IntList lst_prime_end = IntList.of(14,15,17);
        IntList lst_prime_beg = IntList.of(17,15,16);
        boolean changed_end = IntListExercises.squarePrimes(lst_prime_end);
        boolean changed_beg = IntListExercises.squarePrimes(lst_prime_beg);

        assertEquals("14 -> 15 -> 289", lst_prime_end.toString());
        assertEquals("289 -> 15 -> 16", lst_prime_beg.toString());
        assertTrue(changed_end);
        assertTrue(changed_beg);
    }

    @Test
    public void testSquarePrimeNoChange(){
        IntList lst = IntList.of(14,15,18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 18", lst.toString());
        assertFalse(changed);

    }
    @Test
    public void testSquarePrimeComplex(){
        IntList lst_prime_end = IntList.of(1,17,3);
        IntList lst_prime_beg = IntList.of(10,17,0);
        boolean changed_end = IntListExercises.squarePrimes(lst_prime_end);
        boolean changed_beg = IntListExercises.squarePrimes(lst_prime_beg);

        assertEquals("1 -> 289 -> 9", lst_prime_end.toString());
        assertEquals("10 -> 289 -> 0", lst_prime_beg.toString());
        assertTrue(changed_end);
        assertTrue(changed_beg);
    }


}

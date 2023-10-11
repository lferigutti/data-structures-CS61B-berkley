package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;
public class ArrayDequeTest {
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> ad1 = new ArrayDeque<>();

        assertTrue("A newly initialized LLDeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");


        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());


        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }
    @Test
    public void testCircularAdding() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 5; i >= 0; i--){
            ad1.addFirst(i);
        }
        ad1.addLast(6);
        ad1.addLast(7);

        System.out.println("It should Print: 0 1 2 3 4 5 6 7");
        ad1.printDeque();

        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for (int i = 0; i <= 7; i++){
            ad2.addLast(i);
        }
        ad2.printDeque();
    }
    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        ArrayDeque<String>  lld1 = new ArrayDeque<String>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {


        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }
    @Test
    /* Test if get works. */
    public void getItemTest(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        int N = 7;
        for (int i=0;i < N;i++){
            lld1.addLast(i);
        }

        for (int i=0;i < 10;i++){
            int index = StdRandom.uniform(0, N);
            assertEquals("Should have the same value",index,(int)lld1.get(index));

        }
    }
    @Test
    public void simpleResizeTest(){
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        int N = 8;
        for (int i = N; i >= 0; i--){
            ad1.addFirst(i);
        }
        for (int i = N; i < 20; i++){
            ad1.addLast(i);
        }
        ad1.printDeque();
    }


    @Test
    /* Test if get works. */
    public void getItemTestResized(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        int N = 1000000;
        for (int i=0;i < N;i++){
            lld1.addLast(i);
        }

        for (int i=0;i < 10;i++){
            int index = StdRandom.uniform(0, N);
            assertEquals("Should have the same value",index,(int)lld1.get(index));

        }
    }
    /* Downsizing is not working yet*/
    @Test
    public void testDownSizing(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        int N = 1000000;
        for (int i=0;i < N;i++){
            lld1.addLast(i);
        }
        for (int i=0;i < N-10;i++){
            lld1.removeLast();
        }
    }
    @Test
    public void randomizedTest(){
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();

        int N = 500000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);

            } else if (operationNumber == 1) {
                // size
                int sizeL = L.size();
                int sizeB = B.size();
                assertEquals(sizeL,sizeB);

            } else if ( operationNumber ==2) {
                if (L.size()>0){

                    int lastL = L.removeFirst();
                    int lastB = B.removeFirst();
                    assertEquals(lastL, lastB);
                }
            } else if (operationNumber ==3) {
                if (L.size() > 0){
                    int removedL = L.removeLast();
                    int removedB = B.removeLast();
                    assertEquals(removedL, removedB);
                }
            } else if (operationNumber ==4) {
                int randValue = StdRandom.uniform(0, 100);
                L.addFirst(randValue);
                B.addFirst(randValue);

            } else if (operationNumber ==5) {
                if (L.size() > 0){
                    int randValue = StdRandom.uniform(0, L.size());
                    int itemL = L.get(randValue);
                    int itemB = B.get(randValue);
                    assertEquals(itemL, itemB);
                }
            }
        }
    }
    @Test
    // Test Iterator of ArrayDeque
    public void iteratorTest(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        int N = 10;
        for (int i = 0; i < N; i += 1) {
            lld1.addLast(i);
            lld1.addFirst(i+2);
        }
        int i = 0;
        for (int element : lld1){
            int number = lld1.get(i);
            assertEquals(number, element);
            i +=1;
        }

    }

}

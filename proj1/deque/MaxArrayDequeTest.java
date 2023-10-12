package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;
public class MaxArrayDequeTest {

    @Test
    public void inheritanceOfArrayList(){

        class MyIntegerComparator implements Comparator<Integer>{
            public int compare(Integer a, Integer b){
                return a -b;
            }
        }
        class MyIntegerComparatorDumb implements Comparator<Integer>{
            public int compare(Integer a, Integer b){
                return b - a;
            }
        }


        MyIntegerComparator myComparator = new MyIntegerComparator();
        MyIntegerComparatorDumb myDumbComparator = new MyIntegerComparatorDumb();

        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(myComparator);
        int N = 10;
        for (int i = 0; i < N; i += 1) {
            mad1.addLast(i);
            mad1.addFirst(i+2);
        }
        System.out.println(mad1.max());
        System.out.println(mad1.max(myDumbComparator));



    }

}

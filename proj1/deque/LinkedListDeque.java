package deque;

public class LinkedListDeque<T> {

    private class TNode{
        public T item;
        public TNode next;

        public TNode(T i, TNode n){
            item = i;
            next = n;
        }

    }
}

package deque;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int nextFirstItem;
    private int nextLastItem;
    private int arraySize = 8;

    public ArrayDeque() {
        items = (T[]) new Object[arraySize];
        size = 0;
        nextFirstItem = 3;
        nextLastItem = 4;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque(){
        int trackerPrintItem = nextFirstItem + 1;
        for (int i =0; i < size; i++){
            if (isEndOfTheArray(trackerPrintItem-1)){
                trackerPrintItem = 0;}
            System.out.print(items[trackerPrintItem] + " ");
            trackerPrintItem = trackerPrintItem+1;
        }
        System.out.println();
    }
    @Override
    public void addFirst(T item) {
        if (nextFirstItem == nextLastItem){
            // we need to do a resize here.
            resize(arraySize*2);
        }

        items[nextFirstItem] = item;
        size +=1;
        assignNextFirst();

    }
    @Override
    public void addLast(T item){
        if (nextFirstItem == nextLastItem){
            // we need to do a resize here.
            resize(arraySize*2);
        }

        items[nextLastItem] = item;
        size +=1;
        assignNextLast();
    }

    @Override
    public T removeFirst(){
        if ((size < items.length / 4) && (size > 16)){
            //resize need it
            resize(items.length/4);
        }

        int indexItemToRemove = indexFirstElement();
        T itemRemoved = removeItem(indexItemToRemove);
        if (itemRemoved != null) nextFirstItem = indexItemToRemove;
        return  itemRemoved;
    }
    @Override
    public T removeLast(){
        if ((size < items.length / 4) && (size > 16)){
            //resize need it
            resize(items.length/4);
        }
        int indexItemToRemove = indexLastElement();
        T itemRemoved = removeItem(indexItemToRemove);
        if (itemRemoved != null) nextLastItem = indexItemToRemove;
        return  itemRemoved;
    }
    @Override
    public T get(int index){
        T item = null;
        if (index<size){
            int internalIndex = getInternalIndex(index);
            item = items[internalIndex];
        }
        return item;
    }

    // Resize the array to a new capacity.
    private void resize(int capacity){
        T[] itemsCopy = (T[]) new Object[capacity];
        int indexFirstItem = indexFirstElement();
        int indexLastIdem = indexLastElement();
        int temporaryLengthNewArray = getTemporaryLengthNewArray(indexFirstItem, indexLastIdem);
        System.arraycopy(items,indexFirstItem,itemsCopy,0,temporaryLengthNewArray);
        if (temporaryLengthNewArray != size) {
            System.arraycopy(items, 0, itemsCopy, temporaryLengthNewArray, indexLastIdem + 1);
        }
        items = itemsCopy;
        arraySize = capacity;
        nextFirstItem = capacity-1;
        nextLastItem = size;
    }

    public Iterator<T> iterator() {
        return new ArrayDeque.ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDequeIterator() {
            wizPos = 0;
        }
        public boolean hasNext() {
            return wizPos < size;
        }
        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o instanceof ArrayDeque){
            ArrayDeque otherArrayDeque = (ArrayDeque) o;
            if (this.size != otherArrayDeque.size()){ return  false;}
            for (int i=0; i<size; i++){
                if(otherArrayDeque.get(i) != this.get(i)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private int getTemporaryLengthNewArray(int indexFirstItem, int indexLastItem){
        if(indexLastItem<indexFirstItem){
            return arraySize - indexFirstItem;
        } else {
            return indexLastItem - indexFirstItem + 1;
        }
    }
    /* Removed Item if the index is with the boundaries
     */
    private T removeItem(int index){
        T itemRemoved = null;
        if(size >0 && index<arraySize){
            itemRemoved = items[index];
            items[index] = null;
            size -= 1;
        }
        return itemRemoved;
    }
    private boolean isBeginningArray(int position){
        return position == 0;
    }
    private  boolean isEndOfTheArray(int position){
        return position == arraySize - 1;
    }

    /* Get the internal index of the first element*/
    private int indexFirstElement(){
        int indexFirstItem= nextFirstItem+1;
        if (isEndOfTheArray(nextFirstItem)){
            indexFirstItem =0;
        }
        return indexFirstItem;
    }

    /* Get the internal index of the last element*/
    private int indexLastElement(){
        int indexLastItem = nextLastItem-1;
        if (isBeginningArray(nextLastItem)){
            indexLastItem =arraySize-1;
        }
        return indexLastItem;
    }

    /* Translate the index to the internal composition of the array*/
    private int getInternalIndex(int index){
        int indexFirstElement = indexFirstElement();
        if (index + indexFirstElement >= arraySize){
            return indexFirstElement + index - arraySize;
        } else{
            return index + indexFirstElement;
        }
    }
    private void assignNextFirst(){
        if (isBeginningArray(nextFirstItem)){
            nextFirstItem = arraySize-1;
        } else {nextFirstItem -=1;}
    }

    private void assignNextLast(){
        if (isEndOfTheArray(nextLastItem)){
            nextLastItem = 0;
        } else {nextLastItem +=1;}
    }

}
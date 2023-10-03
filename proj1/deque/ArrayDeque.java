package deque;

public class ArrayDeque<T> {
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

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

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

    public void addFirst(T item) {
        if (nextFirstItem == nextLastItem){
            // we need to do a resize here.
        }

        items[nextFirstItem] = item;
        size +=1;
        assignNextFirst();

    }
    public void addLast(T item){
        if (nextFirstItem == nextLastItem){
            // we need to do a resize here.
        }

        items[nextLastItem] = item;
        size +=1;
        assignNextLast();
    }

    public T removeFirst(){
        T itemRemoved;
        int indexItemToRemove = indexFirstElement();
        itemRemoved = removeItem(indexItemToRemove);
        if (itemRemoved != null) nextFirstItem = indexItemToRemove;
        return  itemRemoved;
    }

    public T removeLast(){
        int indexItemToRemove = indexLastElement();
        T itemRemoved = removeItem(indexItemToRemove);
        if (itemRemoved != null) nextLastItem = indexItemToRemove;
        return  itemRemoved;
    }
    public T get(int index){
        T item = null;
        if (index<size){
            int internalIndex = getInternalIndex(index);
            item = items[internalIndex];
        }
        return item;
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
            indexLastItem =0;
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
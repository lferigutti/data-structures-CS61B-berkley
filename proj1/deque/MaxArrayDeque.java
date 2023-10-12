package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    Comparator<T> defaultComparator;
    public MaxArrayDeque(Comparator<T> c){
         defaultComparator = c;
    }

    public T max(){
        if (this.size() == 0) {return null;}
        int indexMaxItem = 0;
        for (int i=0; i<this.size(); i++){
            int compareResult  = defaultComparator.compare(this.get(i),this.get(indexMaxItem));
            if (compareResult >0){
                indexMaxItem = i;
            }
        }
        return this.get(indexMaxItem);
    }
    public T max(Comparator<T> c){
        if (this.size() == 0) {return null;}
        int indexMaxItem = 0;
        for (int i=0; i<this.size(); i++){
            int compareResult  = c.compare(this.get(i),this.get(indexMaxItem));
            if (compareResult >0){
                indexMaxItem = i;
            }
        }
        return this.get(indexMaxItem);
    }
}

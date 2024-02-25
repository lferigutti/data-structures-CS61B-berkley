package hashmap;

import java.util.*;
import java.util.LinkedList;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Leonardo Ferigutti
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size=0;
    private double maxLoad=0.75;
    private double loadFactor;
    private int initialSize=16;
    private int numberOfBuckets;
    private Collection<Node>[] hashTable;

    /** Constructors */
    public MyHashMap() {
        hashTable = createTable(initialSize);
        numberOfBuckets = initialSize;
        initializeBuckets(numberOfBuckets, hashTable);

    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        numberOfBuckets = initialSize;
        hashTable = createTable(initialSize);
        initializeBuckets(numberOfBuckets, hashTable);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
        numberOfBuckets = initialSize;
        hashTable = createTable(initialSize);
        initializeBuckets(numberOfBuckets, hashTable);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return null;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    private void initializeBuckets(int buckets, Collection<Node>[] hashTableToInit) {
        for (int i = 0; i < buckets; i++) {
            hashTableToInit[i] = createBucket();
        }
    }

    private int getIndexHashed(K key) {
        return Math.floorMod(key.hashCode(), numberOfBuckets);
    }

    private void resize(int newSize){
        Collection<Node>[] newHashTable = createTable(newSize);
        initializeBuckets(newSize, newHashTable);
        for (int i = 0; i < numberOfBuckets; i++) {
            Collection<Node> bucket = hashTable[i];
            for (Node node : bucket) {
                int index = Math.floorMod(node.key.hashCode(), newSize);
                newHashTable[index].add(node);
            }
        }
        hashTable = newHashTable;
        numberOfBuckets = newSize;

    }

    @Override
    public void clear() {
        hashTable = createTable(initialSize);
        size = 0;
        numberOfBuckets = initialSize;
        initializeBuckets(numberOfBuckets, hashTable);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < numberOfBuckets; i++) {
            Collection<Node> bucket = hashTable[i];
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getIndexHashed(key);
        Collection<Node> bucket = hashTable[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = getIndexHashed(key);
        Collection<Node> bucket = hashTable[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int index = getIndexHashed(key);
        Collection<Node> bucket = hashTable[index];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        bucket.add(new Node(key, value));
        size++;

        loadFactor = (double) size / numberOfBuckets;
        if (loadFactor > maxLoad) {
            resize(numberOfBuckets * 2);
        }

    }

    @Override
    public V remove(K key) {
            throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}

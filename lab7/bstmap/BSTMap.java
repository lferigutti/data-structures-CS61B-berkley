package bstmap;


import java.util.Iterator;
import java.util.Set;



public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {

    /* Represents the size of the BSTMap. */
    int size = 0;

    /* Represents the root of the BSTMap. */
    BSTNode root = null;

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }


    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null || root == null) {
            return false;
        } else {
            BSTNode lookup = root.get(root, key);
            if (lookup != null) {
                return true;
            } else {
                return false;
            }
        }
    }


    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null || root == null) {
            return null;
        } else {
            BSTNode lookup = root.get(root, key);
            if (lookup != null) {
                return lookup.value;
            } else {
                return null;
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K ik, V v) {
        if (root == null) {
            root = new BSTNode(ik, v, null, null);
            size += 1;
        } else {
            if (!containsKey(ik)){
                root.put(root, ik, v);
                size +=1;
            } else {
                BSTNode lookupNode = root.get(root, ik);
                lookupNode.value = v;
            }
        }
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    /* Private class that will represent the nodes in the BSTMap. */
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        /* Constructor for the BSTNode class. */
        BSTNode(K k, V v, BSTNode l, BSTNode r) {
            this.key = k;
            this.value = v;
            this.left = l;
            this.right = r;
        }

        /* Returns the BSTNode where the k match the Key. */
        BSTNode get(BSTNode node, K k) {
            if (node == null) {
                return null;
            }
            else if (k.equals(node.key)) {
                return node;
            } else if (k.compareTo(node.key) <= -1) {
                return get(node.left, k);
            } else {
                return get(node.right, k);
            }
        }

        /* Add BSTNode when there is no value assosiated to the intertion key. Otherwise does nothing */
        BSTNode put(BSTNode node, K ik, V v) {
            if (node == null) {
                return new BSTNode(ik, v, null, null);
            }
            if (node.key.equals(ik)) {
                node.value = v;
                return node;
            } else if (node.key.compareTo(ik) >= 1) {
                node.left = put(node.left, ik, v);
            } else if (node.key.compareTo(ik) <= 1) {
                node.right = put(node.right, ik, v);
            }
            return node;

        }
    }
}
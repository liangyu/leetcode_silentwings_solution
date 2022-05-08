package LC601_900;
import java.util.*;
public class LC706_DesignHashMap {
    /**
     * Design a HashMap without using any built-in hash table libraries.
     *
     * To be specific, your design should include these functions:
     *
     * put(key, value) : Insert a (key, value) pair into the HashMap. If the value already exists in the HashMap,
     * update the value.
     * get(key): Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
     * remove(key) : Remove the mapping for the value key if this map contains the mapping for the key.
     *
     * Note:
     *
     * All keys and values will be in the range of [0, 1000000].
     * The number of operations will be in the range of [1, 10000].
     * Please do not use the built-in HashMap library.
     */
    /** Initialize your data structure here. */
    /** Initialize your data structure here. */
    private static final int SIZE = 1000000;
    private Entry[] bucket;
    public LC706_DesignHashMap() {
        bucket = new Entry[SIZE];
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        Entry entry = bucket[key];
        if (entry == null) bucket[key] = new Entry(key, value);
        else entry.val = value;
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        Entry entry = bucket[key];
        return entry == null ? -1 : entry.val;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        Entry entry = bucket[key];
        if (entry != null) bucket[key] = null;
    }

    private class Entry {
        private int key;
        private int val;
        private Entry next;
        public Entry(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    // S2
    class MyHashMap {
        int n = 19997;
        List<int[]>[] buckets;
        public MyHashMap() {
            buckets = new List[n];
            for (int i = 0; i < n; i++) buckets[i] = new ArrayList<>();
        }

        public void put(int key, int value) {
            int t = key % n;
            int k = find(buckets[t], key);
            if (k == -1) buckets[t].add(new int[]{key, value});
            else buckets[t].get(k)[1] = value;
        }

        public int get(int key) {
            int t = key % n;
            int k = find(buckets[t], key);
            if (k == -1) return -1;
            return buckets[t].get(k)[1];
        }

        public void remove(int key) {
            int t = key % n;
            int k = find(buckets[t], key);
            if (k != -1) buckets[t].remove(k);
        }

        private int find(List<int[]> bucket, int key) {
            int t = key % n;
            for (int i = 0; i < bucket.size(); i++) {
                if (bucket.get(i)[0] == key) return i;
            }
            return -1;
        }
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */


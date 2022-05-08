package LC601_900;
import java.util.*;
public class LC705_DesignHashSet {
    /**
     * Design a HashSet without using any built-in hash table libraries.
     *
     * Implement MyHashSet class:
     *
     * void add(key) Inserts the value key into the HashSet.
     * bool contains(key) Returns whether the value key exists in the HashSet or not.
     * void remove(key) Removes the value key in the HashSet. If key does not exist in the HashSet, do nothing.
     *
     * Input
     * ["MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"]
     * [[], [1], [2], [1], [3], [2], [2], [2], [2]]
     * Output
     * [null, null, null, true, false, null, true, null, false]
     *
     * Constraints:
     *
     * 0 <= key <= 10^6
     * At most 10^4 calls will be made to add, remove, and contains.
     */
    // time = O(1), space = O(n)
    boolean[] set;
    public LC705_DesignHashSet() {
        set = new boolean[1000001];
    }

    public void add(int key) {
        if (contains(key)) return;
        set[key] = true;
    }

    public void remove(int key) {
        if (!contains(key)) return;
        set[key] = false;
    }

    public boolean contains(int key) {
        return set[key];
    }

    // S2: 拉链法
    // time = O(n), space = O(n)
    class MyHashSet {
        int n = 19997;
        List<Integer>[] h;
        public MyHashSet() {
            h = new List[n];
            for (int i = 0; i < n; i++) h[i] = new ArrayList<>();
        }

        public void add(int key) {
            int t = key % n;
            int k = find(h[t], key);
            if (k == -1) h[t].add(key);
        }

        public void remove(int key) {
            int t = key % n;
            int k = find(h[t], key);
            if (k != -1) h[t].remove(k);
        }

        public boolean contains(int key) {
            int t = key % n;
            int k = find(h[t], key);
            return k != -1;
        }

        private int find(List<Integer> h, int key) {
            for (int i = 0; i < h.size(); i++) {
                if (h.get(i) == key) return i;
            }
            return -1;
        }
    }
}
/**
 * 拉链法：一般开总数的2倍的质数 -> 10^4 ~ n = 19997
 * t = key % n
 */
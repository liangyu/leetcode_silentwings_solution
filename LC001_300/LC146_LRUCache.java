package LC001_300;
import java.util.*;
public class LC146_LRUCache {
    /**
     * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
     *
     * Implement the LRUCache class:
     *
     * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
     * int get(int key) Return the value of the key if the key exists, otherwise return -1.
     * void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to
     * the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
     * Follow up:
     * Could you do get and put in O(1) time complexity?
     *
     * Input
     * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
     * Output
     * [null, null, null, 1, null, -1, null, -1, 3, 4]
     *
     * Constraints:
     *
     * 1 <= capacity <= 3000
     * 0 <= key <= 3000
     * 0 <= value <= 10^4
     * At most 3 * 10^4 calls will be made to get and put.
     * @param capacity
     */
    // time = O(1), space = O(k)   k: capacity
    Node head, tail;
    HashMap<Integer, Node> map;
    int n;
    public LC146_LRUCache(int capacity) {
        map = new HashMap<>();
        n = capacity;
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node p = map.get(key);
        remove(p);
        insert(p);
        return p.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node p = map.get(key);
            p.val = value;
            remove(p);
            insert(p);
        } else {
            if (map.size() == n) {
                Node p = tail.prev;
                map.remove(p.key);
                remove(p);
            }
            Node p = new Node(key, value);
            insert(p);
            map.put(key, p);
        }
    }

    private void remove(Node p) {
        p.prev.next = p.next;
        p.next.prev = p.prev;
    }

    private void insert(Node p) {
        p.next = head.next;
        p.prev = head;
        head.next.prev = p;
        head.next = p;
    }

    private class Node {
        private int key, val;
        private Node prev, next;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}
/**
 * key1,key2,key3,...,key10,key3,key2,key11 -> discard the first one
 * 谁在前，最近访问的时间越早
 * 比较方便的取出它，比较方便的加到后面去 => list
 * list                vector
 * pushback()          push_back()
 * *iter               arr[10]
 * next(iter,10)
 */

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
    private int size, capacity;
    private Node head, tail;
    private HashMap<Integer, Node> map;
    public LC146_LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
        map = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        Node node = map.get(key);
        moveToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (!map.containsKey(key)) {
            Node node = new Node(key, value);
            if (size < capacity) size++;
            else {
                Node lastNode = tail.prev;
                lastNode.prev.next = tail;
                tail.prev = lastNode.prev;
                lastNode.prev = null;
                lastNode.next = null;
                map.remove(lastNode.key);
            }
            map.put(key, node);
            moveToHead(node);
        } else {
            Node node = map.get(key);
            node.val = value;
            moveToHead(node);
        }
    }

    private class Node {
        private int key, val;
        private Node prev, next;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private void moveToHead(Node node) {
        // remove the node
        Node prev = node.prev;
        Node next = node.next;
        if (prev != null) prev.next = next;
        if (next != null) next.prev = prev;

        // move to head
        node.next = head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
    }
}

package LC301_600;
import java.util.*;
public class LC432_AllOoneDataStructure {
    /**
     * Design a data structure to store the strings' count with the ability to return the strings with minimum and
     * maximum counts.
     *
     * Implement the AllOne class:
     *
     * AllOne() Initializes the object of the data structure.
     * inc(String key) Increments the count of the string key by 1. If key does not exist in the data structure, insert
     * it with count 1.
     * dec(String key) Decrements the count of the string key by 1. If the count of key is 0 after the decrement, remove
     * it from the data structure. It is guaranteed that key exists in the data structure before the decrement.
     * getMaxKey() Returns one of the keys with the maximal count. If no element exists, return an empty string "".
     * getMinKey() Returns one of the keys with the minimum count. If no element exists, return an empty string "".
     *
     * Input
     * ["AllOne", "inc", "inc", "getMaxKey", "getMinKey", "inc", "getMaxKey", "getMinKey"]
     * [[], ["hello"], ["hello"], [], [], ["leet"], [], []]
     * Output
     * [null, null, null, "hello", "hello", null, "hello", "leet"]
     *
     * Constraints:
     *
     * 1 <= key.length <= 10
     * key consists of lowercase English letters.
     * It is guaranteed that for each call to dec, key is existing in the data structure.
     * At most 5 * 104 calls will be made to inc, dec, getMaxKey, and getMinKey.
     */
    /** Initialize your data structure here. */
    // time = O(1), space = O(n)
    HashMap<String, Node> map;
    Node head, tail;
    public LC432_AllOoneDataStructure() {
        map = new HashMap<>();
        head = new Node("", 0);
        tail = new Node("", 0);
        head.next = tail;
        tail.prev = head;
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        if (!map.containsKey(key)) {
            Node node = new Node(key, 1);
            map.put(key, node);
            insertNext(node, head);
        } else {
            Node node = map.get(key);
            node.val++;
            if (node.next != tail) {
                Node temp = node.next;
                while (temp != tail && temp.val < node.val) temp = temp.next;
                remove(node);
                insertPre(node, temp);
            }
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        if (!map.containsKey(key)) return;
        Node node = map.get(key);
        node.val--;
        if (node.val == 0) {
            remove(node);
            map.remove(key);
        } else {
            if (node.prev != head) {
                Node temp = node.prev;
                while (temp != head && temp.val > node.val) temp = temp.prev;
                remove(node);
                insertNext(node, temp);
            }
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        return tail.prev.key;
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        return head.next.key;
    }

    private class Node {
        private String key;
        private int val;
        private Node prev, next;
        public Node(String key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    // insert the node before the base node
    private void insertPre(Node node, Node base) {
        node.prev = base.prev;
        base.prev.next = node;
        node.next = base;
        base.prev = node;
    }

    // insert the node after the base node
    private void insertNext(Node node, Node base) {
        node.next = base.next;
        base.next.prev = node;
        node.prev = base;
        base.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

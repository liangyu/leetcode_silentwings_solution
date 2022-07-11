package LC301_600;
import java.util.*;
public class LC460_LFUCache {
    /**
     * Design and implement a data structure for a Least Frequently Used (LFU) cache.
     *
     * Implement the LFUCache class:
     *
     * LFUCache(int capacity) Initializes the object with the capacity of the data structure.
     * int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
     * void put(int key, int value) Update the value of the key if present, or inserts the key if not already present.
     * When the cache reaches its capacity, it should invalidate and remove the least frequently used key before
     * inserting a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency),
     * the least recently used key would be invalidated.
     * To determine the least frequently used key, a use counter is maintained for each key in the cache. The key with
     * the smallest use counter is the least frequently used key.
     *
     * When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation). The use
     * counter for a key in the cache is incremented either a get or put operation is called on it.
     *
     * The functions get and put must each run in O(1) average time complexity.
     *
     * Input
     * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
     * Output
     * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
     *
     * Constraints:
     *
     * 0 <= capacity <= 10^4
     * 0 <= key <= 10^5
     * 0 <= value <= 10^9
     * At most 2 * 10^5 calls will be made to get and put.
     * @param capacity
     */
    // S1: LinkedHashSet
    // time = O(1), space = O(n)  n: capacity
    HashMap<Integer, Integer> map;
    HashMap<Integer, Integer> count;
    HashMap<Integer, LinkedHashSet<Integer>> lists;
    int capacity, minCount;
    public LC460_LFUCache(int capacity) {
        map = new HashMap<>();
        count = new HashMap<>();
        lists = new HashMap<>();
        this.capacity = capacity;
        this.minCount = -1;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        int cnt = count.get(key);
        count.put(key, cnt + 1);

        lists.get(cnt).remove(key);

        // update minCount, happen to remove the current minCount
        if (minCount == cnt && lists.get(cnt).size() == 0) minCount++;
        if (!lists.containsKey(cnt + 1)) lists.put(cnt + 1, new LinkedHashSet<>());
        lists.get(cnt + 1).add(key);
        return map.get(key);
    }

    public void put(int key, int value) {
        // corner case
        if (capacity <= 0) return; // be careful about this corner case! capacity might be 0!!!

        if (map.containsKey(key)) {
            map.put(key, value);
            get(key);
            return;
        }

        if (map.size() >= capacity) {
            int evictKey = lists.get(minCount).iterator().next();
            lists.get(minCount).remove(evictKey);
            map.remove(evictKey);
        }

        map.put(key, value);
        count.put(key, 1);
        minCount = 1;
        lists.putIfAbsent(1, new LinkedHashSet<>());
        lists.get(1).add(key);
    }

    // S2: Double LinkedList
    // time = O(1), space = O(n)  n: capacity
    class LFUCache {
        Block head, tail;
        int n;
        HashMap<Integer, Block> hash_block;
        HashMap<Integer, Node> hash_node;
        public LFUCache(int capacity) {
            n = capacity;
            hash_block = new HashMap<>();
            hash_node = new HashMap<>();
            head = new Block(0);
            tail = new Block(Integer.MAX_VALUE);
            head.right = tail;
            tail.left = head;
        }

        public int get(int key) {
            if (!hash_block.containsKey(key)) return -1;
            Block block = hash_block.get(key);
            Node node = hash_node.get(key);
            block.remove(node);
            if (block.right.cnt != block.cnt + 1) insert(block);
            block.right.insert(node);
            hash_block.put(key, block.right);
            if (block.empty()) remove(block);
            return node.val;
        }

        public void put(int key, int value) {
            if (n == 0) return;
            if (hash_block.containsKey(key)) {
                hash_node.get(key).val = value;
                get(key);
            } else {
                if (hash_block.size() == n) {
                    Node p = head.right.tail.left;
                    head.right.remove(p);
                    if (head.right.empty()) remove(head.right);
                    hash_block.remove(p.key);
                    hash_node.remove(p.key);
                }
                Node p = new Node(key, value);
                if (head.right.cnt > 1) insert(head);
                head.right.insert(p);
                hash_block.put(key, head.right);
                hash_node.put(key, p);
            }
        }

        private void insert(Block p) {  // 在p的右侧插入新块，cnt是p.cnt + 1
            Block cur = new Block(p.cnt + 1);
            cur.right = p.right;
            p.right.left = cur;
            p.right = cur;
            cur.left = p;
        }

        private void remove(Block p) {
            p.left.right = p.right;
            p.right.left = p.left;
        }

        private class Node {
            private int key, val;
            private Node left, right;
            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        private class Block {
            private Block left, right;
            private Node head, tail;
            private int cnt;
            public Block(int cnt) {
                this.cnt = cnt;
                head = new Node(-1, -1);
                tail = new Node(-1, -1);
                head.right = tail;
                tail.left = head;
            }

            private void insert(Node p) {
                p.right = head.right;
                head.right.left = p;
                p.left = head;
                head.right = p;
            }

            private void remove(Node p) {
                p.left.right = p.right;
                p.right.left = p.left;
            }

            private boolean empty() {
                return head.right == tail;
            }
        }
    }
}

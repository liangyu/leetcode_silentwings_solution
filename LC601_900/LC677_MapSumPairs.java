package LC601_900;
import java.util.*;
public class LC677_MapSumPairs {
    /**
     * Design a map that allows you to do the following:
     *
     * Maps a string key to a given value.
     * Returns the sum of the values that have a key with a prefix equal to a given string.
     * Implement the MapSum class:
     *
     * MapSum() Initializes the MapSum object.
     * void insert(String key, int val) Inserts the key-val pair into the map. If the key already existed, the original
     * key-value pair will be overridden to the new one.
     * int sum(string prefix) Returns the sum of all the pairs' value whose key starts with the prefix.
     *
     * Input
     * ["MapSum", "insert", "sum", "insert", "sum"]
     * [[], ["apple", 3], ["ap"], ["app", 2], ["ap"]]
     * Output
     * [null, null, 3, null, 5]
     *
     * Constraints:
     *
     * 1 <= key.length, prefix.length <= 50
     * key and prefix consist of only lowercase English letters.
     * 1 <= val <= 1000
     * At most 50 calls will be made to insert and sum.
     */
    /** Initialize your data structure here. */
    // time = O(k), space = O(k)    k: the length of the key
    TrieNode root;
    HashMap<String, Integer> map;
    public LC677_MapSumPairs() {
        root = new TrieNode();
        map = new HashMap<>();
    }

    public void insert(String key, int val) {
        int diff = val; // 注意：这里要先存一份val到diff,因为val的原值要放回到map里，不能在这里直接更新！！！
        if (map.containsKey(key)) diff -= map.get(key);
        map.put(key, val);

        TrieNode node = root;
        for (char c : key.toCharArray()) {
            if (node.next[c - 'a'] == null) {
                node.next[c - 'a'] = new TrieNode();
            }
            node = node.next[c - 'a'];
            node.val += diff;
        }
    }

    public int sum(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (node.next[c - 'a'] == null) return 0;
            node = node.next[c - 'a'];
        }
        return node.val;
    }

    private class TrieNode {
        private TrieNode[] next;
        private int val;
        public TrieNode() {
            this.next = new TrieNode[26];
            this.val = 0;
        }
    }
}

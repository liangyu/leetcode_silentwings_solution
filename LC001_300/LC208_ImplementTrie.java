package LC001_300;
import java.util.*;
public class LC208_ImplementTrie {
    /**
     * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys
     * in a dataset of strings. There are various applications of this data structure, such as autocomplete and
     * spellchecker.
     *
     * Implement the Trie class:
     *
     * Trie() Initializes the trie object.
     * void insert(String word) Inserts the string word into the trie.
     * boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and
     * false otherwise.
     * boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix
     * prefix, and false otherwise.
     *
     * Input
     * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
     * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
     * Output
     * [null, null, true, false, true, null, true]
     *
     * Constraints:
     *
     * 1 <= word.length, prefix.length <= 2000
     * word and prefix consist only of lowercase English letters.
     * At most 3 * 104 calls in total will be made to insert, search, and startsWith.
     */
    /** Initialize your data structure here. */
    // time = O(k), space = O(1)
    private TrieNode root;
    public LC208_ImplementTrie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.nexts[c - 'a'] == null) {
                cur.nexts[c - 'a'] = new TrieNode();
            }
            cur = cur.nexts[c - 'a'];
        }
        cur.isEnd = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.nexts[c - 'a'] == null) return false;
            cur = cur.nexts[c - 'a'];
        }
        return cur.isEnd;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (cur.nexts[c - 'a'] == null) return false;
            cur = cur.nexts[c - 'a'];
        }
        return true;
    }

    private class TrieNode {
        private TrieNode[] nexts;
        private boolean isEnd;
        public TrieNode() {
            this.nexts = new TrieNode[26];
            this.isEnd = false;
        }
    }
}

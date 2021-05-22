package LC1801_2100;
import java.util.*;
public class LC1804_ImplementTrieII {
    /**
     * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys
     * in a dataset of strings. There are various applications of this data structure, such as autocomplete and
     * spellchecker.
     *
     * Implement the Trie class:
     *
     * Trie() Initializes the trie object.
     * void insert(String word) Inserts the string word into the trie.
     * int countWordsEqualTo(String word) Returns the number of instances of the string word in the trie.
     * int countWordsStartingWith(String prefix) Returns the number of strings in the trie that have the string prefix
     * as a prefix.
     * void erase(String word) Erases the string word from the trie.
     *
     * Input
     * ["Trie", "insert", "insert", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsEqualTo",
     * "countWordsStartingWith", "erase", "countWordsStartingWith"]
     * [[], ["apple"], ["apple"], ["apple"], ["app"], ["apple"], ["apple"], ["app"], ["apple"], ["app"]]
     * Output
     * [null, null, null, 2, 2, null, 1, 1, null, 0]
     *
     * Constraints:
     *
     * 1 <= word.length, prefix.length <= 2000
     * word and prefix consist only of lowercase English letters.
     * At most 3 * 104 calls in total will be made to insert, countWordsEqualTo, countWordsStartingWith, and erase.
     * It is guaranteed that for any function call to erase, the string word will exist in the trie.
     */
    // time = O(k), space = O(1)
    private TrieNode root;
    public LC1804_ImplementTrieII() {
        root = new TrieNode('\0');
    }

    public void insert(String word) {
        TrieNode cur = root;
        for (char ch : word.toCharArray()) {
            if (cur.nexts[ch - 'a'] == null) {
                cur.nexts[ch - 'a'] = new TrieNode(ch);
            }
            cur = cur.nexts[ch - 'a'];
            cur.prefixCount++;
        }
        cur.wordCount++;
    }

    public int countWordsEqualTo(String word) {
        TrieNode cur = root;
        for (char ch : word.toCharArray()) {
            if (cur.nexts[ch - 'a'] == null) return 0;
            cur = cur.nexts[ch - 'a'];
        }
        return cur.wordCount;
    }

    public int countWordsStartingWith(String prefix) {
        TrieNode cur = root;
        for (char ch : prefix.toCharArray()) {
            if (cur.nexts[ch - 'a'] == null) return 0;
            cur = cur.nexts[ch - 'a'];
        }
        return cur.prefixCount;
    }

    public void erase(String word) {
        Stack<TrieNode> stack = new Stack<>();
        TrieNode cur = root;
        for (char ch : word.toCharArray()) {
            stack.push(cur);
            if (cur.nexts[ch - 'a'] == null) return;
            cur = cur.nexts[ch - 'a'];
        }
        cur.wordCount--;
        for (int i = word.length() - 1; i>= 0; i--) {
            char ch = word.charAt(i);
            TrieNode parent = stack.pop();
            TrieNode child = parent.nexts[ch - 'a'];
            child.prefixCount--;
            if (child.prefixCount == 0) parent.nexts[ch - 'a'] = null;
        }
    }

    private class TrieNode {
        private char ch;
        private TrieNode[] nexts;
        private int prefixCount;
        private int wordCount;
        public TrieNode(char ch) {
            this.ch = ch;
            this.nexts = new TrieNode[26];
            this.prefixCount = 0;
            this.wordCount = 0;
        }
    }
}

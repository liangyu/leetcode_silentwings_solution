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
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.next[c - 'a'] == null) {
                node.next[c - 'a'] = new TrieNode();
            }
            node = node.next[c - 'a'];
            node.count1++;
        }
        node.count2++;
    }

    public int countWordsEqualTo(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.next[c - 'a'] == null) return 0;
            node = node.next[c - 'a'];
        }
        return node.count2;
    }

    public int countWordsStartingWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            if (node.next[c - 'a'] == null) return 0;
            node = node.next[c - 'a'];
        }
        return node.count1; // 注意：prefix的case，我们这里返回的是count1 !!!
    }

    public void erase(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.next[c - 'a'] == null) return;
            node.next[c - 'a'].count1--;
            if (node.next[c - 'a'].count1 == 0) {
                node.next[c - 'a'] = null;
                return;
            }
            node = node.next[c - 'a'];
        }
        node.count2--;
    }

    private class TrieNode {
        private TrieNode[] next;
        private int count1; // prefix count
        private int count2; // word count
        public TrieNode() {
            this.next = new TrieNode[26];
            this.count1 = 0;
            this.count2 = 0;
        }
    }
}
/**
 * 对于erase(word)操作，我们将该单词沿途所经过的所有节点的count1都减1，
 * 如果某个节点减1之后的count1变成了0，说明该支路往下都只属于这个word，可以直接将次节点删除。
 * 另外，记得将该单词最后一个字母的节点的count2减1（如果该节点还存在的话）。
 */
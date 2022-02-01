package LC001_300;
import java.util.*;
public class LC211_DesignAddandSearchWordsDataStructure {
    /**
     * Design a data structure that supports adding new words and finding if a string matches
     * any previously added string.
     *
     * Implement the WordDictionary class:
     *
     * WordDictionary() Initializes the object.
     * void addWord(word) Adds word to the data structure, it can be matched later.
     * bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise.
     * word may contain dots '.' where dots can be matched with any letter.
     *
     * Constraints:
     *
     * 1 <= word.length <= 500
     * word in addWord consists lower-case English letters.
     * word in search consist of  '.' or lower-case English letters.
     * At most 50000 calls will be made to addWord and search.
     */
    /** Initialize your data structure here. */
    private TrieNode root;
    public LC211_DesignAddandSearchWordsDataStructure() {
        root = new TrieNode();
    }

    // time = O(k) = O(1), space = O(k)
    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.next[c - 'a'] == null) node.next[c - 'a'] = new TrieNode();
            node = node.next[c - 'a'];
        }
        node.isEnd = true;
    }

    // time = O(26^k) = O(1), space = O(k)
    public boolean search(String word) {
        return dfs(word, root, 0);
    }

    private boolean dfs(String word, TrieNode node, int idx) {
        // base case
        if (node == null) return false;
        if (idx == word.length()) return node.isEnd;

        if (word.charAt(idx) != '.') return dfs(word, node.next[word.charAt(idx) - 'a'], idx + 1);
        else {
            boolean flag = false;
            for (int k = 0; k < 26; k++) {
                if (dfs(word, node.next[k], idx + 1)) {
                    flag = true;
                    break;
                }
            }
            return flag;
        }
    }

    private class TrieNode {
        private TrieNode[] next;
        private boolean isEnd;
        public TrieNode() {
            this.next = new TrieNode[27];
            this.isEnd = false;
        }
    }
}
/**
 * Trie类的问题主要在于两大块：建树和搜索
 * 1. build tree: 主要通过从root出发，然后一步步通过cur.nexts[ch - 'a'] = new TrieNode(ch)来建立起来，最后将cur.isWord = true
 * 2. 搜索则直接利用DFS来做，模板为dfs(word, cur, idx)三元素来进行recursion，原则是一通百通！
 */
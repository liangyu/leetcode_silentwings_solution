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
        root = new TrieNode('\0');
    }

    // time = O(k) = O(1), space = O(k)
    public void addWord(String word) {
        char[] chars = word.toCharArray();
        TrieNode cur = root;
        for (char ch : chars) {
            if (cur.nexts[ch - 'a'] == null) cur.nexts[ch - 'a'] = new TrieNode(ch);
            cur = cur.nexts[ch - 'a'];
        }
        cur.isWord = true;
    }

    // time = O(26^k) = O(1), space = O(k)
    public boolean search(String word) {
        return dfs(word, root, 0);
    }

    private boolean dfs(String word, TrieNode cur, int idx) {
        int len = word.length();
        if (cur == null) return false;
        if (idx == len) return cur.isWord; // 有可能cur == null，导致NullPointerException，所以要放在check null的base case之后

        char ch = word.charAt(idx);
        if (ch >= 'a' && ch <= 'z') {  // case 1: lower case 'a' ~ 'z'
            return dfs(word, cur.nexts[ch - 'a'], idx + 1);
        } else { // case 2: '.'
            for (TrieNode next : cur.nexts) {
                if (dfs(word, next, idx + 1)) return true;
            }
            return false;
        }
    }

    private class TrieNode {
        private char ch;
        private TrieNode[] nexts;
        private boolean isWord;
        public TrieNode(char ch) {
            this.ch = ch;
            this.nexts = new TrieNode[26];
            this.isWord = false;
        }
    }
}
/**
 * Trie类的问题主要在于两大块：建树和搜索
 * 1. build tree: 主要通过从root出发，然后一步步通过cur.nexts[ch - 'a'] = new TrieNode(ch)来建立起来，最后将cur.isWord = true
 * 2. 搜索则直接利用DFS来做，模板为dfs(word, cur, idx)三元素来进行recursion，原则是一通百通！
 */
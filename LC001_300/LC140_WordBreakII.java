package LC001_300;
import java.util.*;
public class LC140_WordBreakII {
    /**
     * Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word
     * is a valid dictionary word. Return all such possible sentences in any order.
     *
     * Note that the same word in the dictionary may be reused multiple times in the segmentation.
     *
     * Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
     * Output: ["cats and dog","cat sand dog"]
     *
     * Constraints:
     *
     * 1 <= s.length <= 20
     * 1 <= wordDict.length <= 1000
     * 1 <= wordDict[i].length <= 10
     * s and wordDict[i] consist of only lowercase English letters.
     * All the strings of wordDict are unique.
     * @param s
     * @param wordDict
     * @return
     */
    // S1: Trie
    // time = O(2^n), space = O(n)
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0) return res;

        Trie trie = new Trie();

        // step 1: build trie
        for (String str : wordDict) {
            trie.insert(str);
        }
        int[] mem = new int[21];
        dfs(s, trie, 0, mem, new StringBuilder(), res);
        return res;
    }

    private boolean dfs(String s, Trie trie, int idx, int[] mem, StringBuilder path, List<String> res) {
        // base case - success
        if (idx == s.length()) {
            path.setLength(path.length() - 1);
            res.add(path.toString());
            return true;
        }

        if (mem[idx] == 1) return false;

        TrieNode cur = trie.root;
        boolean flag = false;
        int len = path.length();

        for (int i = idx; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (cur.nexts[ch - 'a'] != null) {
                cur = cur.nexts[ch - 'a'];
                if (cur.isWord) {
                    path.append(s.substring(idx, i + 1) + " ");
                    if (dfs(s, trie, i + 1, mem, path, res)) flag = true;
                    path.setLength(len);
                }
            } else break; // 注意：当trie的分支走不通时，要直接break放弃该分支！
        }
        if (!flag) mem[idx] = 1;
        return flag;
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

    private class Trie {
        private TrieNode root;
        public Trie() {
            root = new TrieNode('\0');
        }

        private void insert(String word) {
            TrieNode cur = root;
            for (char ch : word.toCharArray()) {
                if (cur.nexts[ch - 'a'] == null) {
                    cur.nexts[ch - 'a'] = new TrieNode(ch);
                }
                cur = cur.nexts[ch - 'a'];
            }
            cur.isWord = true;
        }
    }
}
/**
 * Trie总共最多也就10层，所以最多通过O(10)的时间就能确定哪些前缀是在Trie里面的，效率非常高
 */

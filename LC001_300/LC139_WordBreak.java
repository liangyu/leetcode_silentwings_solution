package LC001_300;
import java.util.*;
public class LC139_WordBreak {
    /**
     * Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated
     * sequence of one or more dictionary words.
     *
     * Note that the same word in the dictionary may be reused multiple times in the segmentation.
     *
     * Input: s = "leetcode", wordDict = ["leet","code"]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 300
     * 1 <= wordDict.length <= 1000
     * 1 <= wordDict[i].length <= 20
     * s and wordDict[i] consist of only lowercase English letters.
     * All the strings of wordDict are unique.
     * @param s
     * @param wordDict
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n)
    public boolean wordBreak(String s, List<String> wordDict) {
        // corner case
        if (s == null || s.length() == 0) return true;

        HashSet<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                String word = s.substring(j, i); // O(n)
                dp[i] = dp[j] && dict.contains(word);
                if (dp[i]) break; // 一通百通
            }
        }
        return dp[n];
    }

    // S2: Trie
    // time = O(n^2), space = O(n)
    public boolean wordBreak2(String s, List<String> wordDict) {
        // corner case
        if (s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) return false;

        Trie trie = new Trie();
        for (String word : wordDict) {
            trie.insert(word);
        }

        return dfs(s, 0, trie, new int[301]);
    }

    private boolean dfs(String s, int idx, Trie trie, int[] mem) {
        // base case
        if (idx == s.length()) return true;

        if (mem[idx] == 1) return false;

        TrieNode cur = trie.root;
        for (int i = idx; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (cur.nexts[ch - 'a'] != null) {
                cur = cur.nexts[ch - 'a'];
                if (cur.isWord && dfs(s, i + 1, trie, mem)) {
                    return true;
                }
            } else break;
        }
        mem[idx] = 1;
        return false;
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
            this.root = new TrieNode('\0');
        }

        private void insert(String s) {
            TrieNode cur = root;
            for (char ch : s.toCharArray()) {
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
 * abc
 * abd
 * acb
 * acbe
 *
 * acb
 * trie: use constant time to check, save space
 * 往下走看经过哪些单词
 *
 * dp[i]: if s[0:i] break successfully
 * dp[i] = (dp[j] == true && s[j + 1 : i] is a word) for all j < i
 */

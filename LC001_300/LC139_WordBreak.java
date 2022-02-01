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
    // time = O(n^3), space = O(n)
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
    TrieNode root;
    boolean[] memo;
    public boolean wordBreak2(String s, List<String> wordDict) {
        root = new TrieNode();
        memo = new boolean[s.length()];

        for (String word : wordDict) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
            }
            node.isEnd = true;
        }

        return dfs(s, 0);
    }

    private boolean dfs(String s, int cur) {  // s[cur:]
        // base case
        if (cur == s.length()) return true;
        if (memo[cur]) return false;

        TrieNode node = root;
        for (int i = cur; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (node.next[ch - 'a'] != null) {
                node = node.next[ch - 'a'];
                if (node.isEnd && dfs(s, i + 1)) return true;
            } else break;
        }
        memo[cur] = true;
        return false;
    }

    private class TrieNode {
        private TrieNode[] next;
        private boolean isEnd;
        public TrieNode() {
            this.next = new TrieNode[26];
            this.isEnd = false;
        }
    }
}
/**
 * 非常好的NP问题
 * 找前缀
 * 遍历字典来一个个match?
 * => 传统dfs+trie
 * 如果你有一堆字符串的集合，首选想到字典树
 * 这样在集合里搜索会很高效
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

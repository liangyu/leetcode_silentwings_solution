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
    // time = O(n * 2^n), space = O(n * 2^n)
    TrieNode root;
    boolean[] memo;
    List<String> res;
    public List<String> wordBreak(String s, List<String> wordDict) {
        root = new TrieNode();
        memo = new boolean[s.length()];
        res = new ArrayList<>();

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

        List<String> words = new ArrayList<>();
        dfs(s, 0, words);
        return res;
    }

    private boolean dfs(String s, int cur, List<String> words) {  // s[cur:]
        // base case
        if (cur == s.length()) {
            StringBuilder sb = new StringBuilder();
            for (String x : words) sb.append(x).append(' ');
            sb.setLength(sb.length() - 1);
            res.add(sb.toString());
            return true;
        }
        if (memo[cur]) return false;

        TrieNode node = root;
        boolean flag = false;
        for (int i = cur; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (node.next[ch - 'a'] != null) {
                node = node.next[ch - 'a'];
                if (node.isEnd) {
                    words.add(s.substring(cur, i + 1));
                    if (dfs(s, i + 1, words)) flag = true; // can't directly return, needs to find other solutions
                    words.remove(words.size() - 1); // setback
                }
            } else break;
        }
        if (!flag) memo[cur] = true; // no successful cases were found!
        return flag;
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
 * Trie 共用前缀
 * Trie总共最多也就10层，所以最多通过O(10)的时间就能确定哪些前缀是在Trie里面的，效率非常高
 */

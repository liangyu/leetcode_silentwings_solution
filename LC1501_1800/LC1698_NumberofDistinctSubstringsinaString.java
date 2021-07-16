package LC1501_1800;
import java.util.*;
public class LC1698_NumberofDistinctSubstringsinaString {
    /**
     * Given a string s, return the number of distinct substrings of s.
     *
     * A substring of a string is obtained by deleting any number of characters (possibly zero) from the front of the
     * string and any number (possibly zero) from the back of the string.
     *
     * Input: s = "aabbaba"
     * Output: 21
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * s consists of lowercase English letters.
     *
     *
     * Follow up: Can you solve this problem in O(n) time complexity?
     * @param s
     * @return
     */
    // S1: rolling hash
    // time = O(n^2), space = O(n)
    public int countDistinct(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        long base = 26;
        int count = 0;

        for (int len = 1; len <= n; len++) {
            long power = 1;
            for (int i = 0; i < len - 1; i++) {
                power *= base;
            }
            HashSet<Long> set = new HashSet<>();
            long hash = 0;
            for (int i = 0; i < n; i++) {
                if (i >= len) hash -= (s.charAt(i - len) - 'a') * power;
                hash = hash * base + s.charAt(i) - 'a';
                if (i >= len - 1) set.add(hash);
            }
            count += set.size(); // 进一步降低不同长度之间hash collision的概率
        }
        return count;
    }

    // S2: trie
    // time = O(n^2), space = O(1)
    public int countDistinct2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        TrieNode root = new TrieNode();
        int n = s.length(), res = 0;

        for (int i = 0; i < n; i++) {
            TrieNode cur = root;
            for (int j = i; j < n; j++) {
                if (cur.nexts[s.charAt(j) - 'a'] == null) {
                    cur.nexts[s.charAt(j) - 'a'] = new TrieNode();
                    res++;
                }
                cur = cur.nexts[s.charAt(j) - 'a'];
            }
        }
        return res;
    }

    private class TrieNode {
        private TrieNode[] nexts = new TrieNode[26];
    }
}
/**
 * naive: 遍历所有substring, O(n^2)，再用一个集合，确定头尾来确定一个substring，再在set里去重，剩下的就是答案
 * 问题是把所有substring往set里面塞，可能string比较长，效率低下
 * => O(n) suffix tree / array 模板题
 * O(n^2)的另一个解法：string -> int
 * 如何把一个字符串编码？
 * "abba" -> (0110)_26
 * "aabbaba" -> (0011010)_26
 * 转化成整数放到集合里就可以拿来去重
 *
 * string编码 -> n^3 => 500^3 有点悬
 * rolling hash => 固定一个string的长度，先把
 * [aab]baba
 * a[abb]aba
 * (aab - a*26^2) * 26 + b  => O(1)均摊搞定
 * 2.5e5,   1e19 远远大于前者的平方 => 相对安全，就不需要mod了
 */

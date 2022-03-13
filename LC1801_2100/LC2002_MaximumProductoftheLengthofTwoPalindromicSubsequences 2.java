package LC1801_2100;

public class LC2002_MaximumProductoftheLengthofTwoPalindromicSubsequences {
    /**
     * Given a string s, find two disjoint palindromic subsequences of s such that the product of their lengths is
     * maximized. The two subsequences are disjoint if they do not both pick a character at the same index.
     *
     * Return the maximum possible product of the lengths of the two palindromic subsequences.
     *
     * A subsequence is a string that can be derived from another string by deleting some or no characters without
     * changing the order of the remaining characters. A string is palindromic if it reads the same forward and backward.
     *
     * Input: s = "leetcodecom"
     * Output: 9
     *
     * Constraints:
     *
     * 2 <= s.length <= 12
     * s consists of lowercase English letters only.
     * @param s
     * @return
     */
    // S1: DFS
    // time = O(3^n), space = O(n)
    private int res = 0;
    public int maxProduct(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        dfs(s, 0, "", "");
        return res;
    }

    private void dfs(String s, int idx, String s1, String s2) {
        // base case
        if (idx == s.length()) {
            if (isPalin(s1) && isPalin(s2)) {
                res = Math.max(res, s1.length() * s2.length());
            }
            return;
        }

        dfs(s, idx + 1, s1 + s.charAt(idx), s2);
        dfs(s, idx + 1, s1, s2 + s.charAt(idx));
        dfs(s, idx + 1, s1, s2);
    }

    private boolean isPalin(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    // S2: bit mask + dp
    public int maxProduct2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length(), ret = 0;
        for (int subset = 1; subset < (1 << n) - 1; subset++) {
            ret = Math.max(ret, lps(s, subset) * lps(s, (1 << n) - 1 - subset));
        }
        return ret;
    }

    private int lps(String s, int state) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (((state >> i) & 1) == 1) sb.append(s.charAt(s.length() - 1 - i));
        }
        int n = sb.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) dp[i][i] = 1; // 自身一个字符都是长度为1的回文串

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                if (sb.charAt(i) == sb.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else { // i, j 不可能同时在回文串中
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }
}
/**
 * 2 <= s.length <= 12
 * 穷举都是有可能的
 * 具体有多少种拆分方法？=> 找combination
 * 任意间隔，可以选也可以不选 => 2^12 选择
 * 拆成2个回文子序列
 * 很粗糙的分为2部分，只要每个部分有回文子序列就可以了
 * x x x x x x x x x  找一个最长回文子序列
 * dp[i][j]: the length of the longest palindromic subsequence in [i:j]
 * O(2^n * n^2) n特别小，2^12 = 4096，时间复杂度是够的
 * 拆分成2部分，里面垃圾元素不影响计算回文子序列长度
 */

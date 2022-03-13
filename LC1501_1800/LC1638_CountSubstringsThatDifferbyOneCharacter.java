package LC1501_1800;

public class LC1638_CountSubstringsThatDifferbyOneCharacter {
    /**
     * Given two strings s and t, find the number of ways you can choose a non-empty substring of s and replace a single
     * character by a different character such that the resulting substring is a substring of t. In other words, find
     * the number of substrings in s that differ from some substring in t by exactly one character.
     *
     * For example, the underlined substrings in "computer" and "computation" only differ by the 'e'/'a', so this is a
     * valid way.
     *
     * Return the number of substrings that satisfy the condition above.
     *
     * A substring is a contiguous sequence of characters within a string.
     *
     * Input: s = "aba", t = "baba"
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= s.length, t.length <= 100
     * s and t consist of lowercase English letters only.
     * @param s
     * @param t
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int countSubstrings(String s, String t) {
        int m = s.length(), n = t.length();
        s = "#" + s + "#";
        t = "#" + t + "#";

        int[][] dp1 = new int[105][105];
        int[][] dp2 = new int[105][105];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i) == t.charAt(j)) {
                    dp1[i][j] = dp1[i - 1][j - 1] + 1;
                }
            }
        }


        for (int i = m; i >= 1; i--) {
            for (int j = n; j >= 1; j--) {
                if (s.charAt(i) == t.charAt(j)) {
                    dp2[i][j] = dp2[i + 1][j + 1] + 1;
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i) != t.charAt(j)) {
                    res += (dp1[i - 1][j - 1] + 1) * (dp2[i + 1][j + 1] + 1);
                }
            }
        }
        return res;
    }
}
/**
 * 暴力O(10^4)可做
 * 套路：双序列型dp
 * dp[i][j]: the number of ways for s[0:i], t[0:j]
 * s[i] != t[j]
 * x x x x [z z i z z z]
 * y y y [z z j z z z]
 * 左边界最多移动到i和j前面有多少个相同的char
 * 把所有这样substring配对分成若干类
 * （i，j): 3 * 4 = 12
 * for (int i = ...)
 *      for (int j = ...)
 *          a = left(i, j);
 *          b = right(i, j);
 *          ret += (a + 1) * (b + 1)
 * x x [x x x i]
 * y y y y j
 * dp[i][j] = dp[i-1][j-1] + 1   if (s[i] == s[j])
 *          = 0 if (s[i] != s[j])
 */

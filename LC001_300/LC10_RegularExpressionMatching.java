package LC001_300;
import java.util.*;
public class LC10_RegularExpressionMatching {
    /**
     * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'
     * where:
     *
     * '.' Matches any single character.
     * '*' Matches zero or more of the preceding element.
     * The matching should cover the entire input string (not partial).
     *
     * Input: s = "aa", p = "a"
     * Output: false
     *
     * Constraints:
     *
     * 0 <= s.length <= 20
     * 0 <= p.length <= 30
     * s contains only lowercase English letters.
     * p contains only lowercase English letters, '.', and '*'.
     * It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.
     * @param s
     * @param p
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];

        s = "#" + s;
        p = "#" + p;

        // init -> 可能越界的情况
        dp[0][0] = true;

        // for dp[0][j] case
        for (int j = 2; j <= n; j++) {
            dp[0][j] = p.charAt(j) == '*' && dp[0][j - 2]; // 注意：这里要用到dp[0][0],所以dp[0][0] = true要在上面设定好！
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (Character.isLowerCase(p.charAt(j))) {
                    dp[i][j] = (s.charAt(i) == p.charAt(j)) && dp[i - 1][j - 1];
                } else if (p.charAt(j) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    boolean possible1 = dp[i][j - 2]; // '*' is zero of previous char
                    boolean possible2 = dp[i - 1][j] && (s.charAt(i) == p.charAt(j - 1) || p.charAt(j - 1) == '.');
                    dp[i][j] = possible1 || possible2;
                }
            }
        }
        return dp[m][n];
    }

    // S2: DP
    // time = O(m * n), space = O(m * n)
    public boolean isMatch2(String s, String p) {
        int m = s.length(), n = p.length();
        s = "#" + s;
        p = "#" + p;
        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;

        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (j + 1 <= n && p.charAt(j + 1) == '*') continue;
                if (p.charAt(j) != '*') {
                    f[i][j] = i > 0 && f[i - 1][j - 1] && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
                } else {
                    f[i][j] = f[i][j - 2] || i > 0 && f[i - 1][j] && (s.charAt(i) == p.charAt(j - 1) || p.charAt(j - 1) == '.');
                }
            }
        }
        return f[m][n];
    }
}
/**
 * dp[i][j]: whether s[0: i] and p[0:j] are matched
 * dp[i - 1][j - 1]
 * dp[i - 1][j]
 * dp[i][j - 1]
 *
 * s: X X X X X i
 * p: Y Y Y Z --- Z
 *
 * dp[i][0]: i : 1 ~ m  -> false (default)
 * dp[0][j]: j : 1 ~ n     Y * Y * Y *
 * dp[0][0]: true
 *
 * if (isalpha(p[j]) {
 *     dp[i][j] = (s[i] == p[j]) && dp[i - 1][j - 1];
 * }
 *
 * if (p[j] == '.') {
 *     dp[i][j] = dp[i - 1][j - 1];
 * }
 *
 * if (p[j] == '*') {
 *     dp[i][j] = (s[i] == p[j - 1] || p[j - 1] == '.') && dp[i - 1][j] || dp[i][j - 2];
 * }
 *
 * 动态规划：
 * 状态表示 f[i,j]:
 * 1. 集合：所有s[1:i]和p[1:j]的匹配方案
 * 2. 属性：boolean 是否存在一个合法方案
 * 状态计算：
 * p[j] != '*'   (s[i] == p[j] || p[j] == '.') && f[i-1, j-1]
 * p[j] == '*'  j 表示多少个字符?
 * 0个字符： f[i,j-2]
 * 1个字符: f[i-1,j-2] && s[i] == p[j]
 * 2个字符：f[i-2,j-2] && s[i] == p[j] && s[i-1] == p[j-1]
 * => f[i,j] = f[i,j-2] || f[i-1,j] && (s[i] == p[j])
 */

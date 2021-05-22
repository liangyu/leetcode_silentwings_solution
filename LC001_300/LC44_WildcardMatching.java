package LC001_300;
import java.util.*;
public class LC44_WildcardMatching {
    /**
     * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
     *
     * '?' Matches any single character.
     * '*' Matches any sequence of characters (including the empty sequence).
     * The matching should cover the entire input string (not partial)
     *
     * Input: s = "aa", p = "a"
     * Output: false
     *
     * Constraints:
     *
     * 0 <= s.length, p.length <= 2000
     * s contains only lowercase English letters.
     * p contains only lowercase English letters, '?' or '*'.
     * @param s
     * @param p
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int i = 1; i <= m; i++) dp[i][0] = false;
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') { // s为空，*也可以替换成空串，则当s为空p为连续的*号时候也是匹配成功的
                dp[0][j] = dp[0][j - 1]; // 如果是'*'的话，当前状态取决于截止上一位时的情况
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    if (p.charAt(j - 1) == '?') {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else if (p.charAt(j - 1) == '*') {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                    }
                }
            }
        }
        return dp[m][n];
    }

    // S2: Two Pointers 最优解！
    // time = O(min(m, n)), space = O(1)
    public boolean isMatch2(String s, String p) {
        int i = 0, j = 0, match = 0, star = -1;

        while (i < s.length()) { // O(min(m, n))
            if (j < p.length() && s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
                i++;
                j++;
            } else if (j < p.length() && p.charAt(j) == '*') {
                star = j;
                match = i;
                j++;
            } else if (star != -1) { // '*' exists
                j = star + 1; // 将当前非通配符的字母归纳入前一位'*'的cover范围之内
                match++; // match同步跟进匹配
                i = match; // 更新当前i到对应匹配好的新位置上
            } else return false;
        }

        while (j < p.length()) {  // 如果出while loop后p还有剩余，那只能全是'*'来充当empty完成匹配，否则一定false
            if (p.charAt(j) != '*') return false;
            j++;
        }
        return true;
    }
}

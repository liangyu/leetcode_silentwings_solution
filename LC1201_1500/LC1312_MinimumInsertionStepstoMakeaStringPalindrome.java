package LC1201_1500;
import java.util.*;
public class LC1312_MinimumInsertionStepstoMakeaStringPalindrome {
    /**
     * Given a string s. In one step you can insert any character at any index of the string.
     *
     * Return the minimum number of steps to make s palindrome.
     *
     * A Palindrome String is one that reads the same backward as well as forward.
     *
     * Input: s = "zzazz"
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * All characters of s are lower case English letters.
     * @param s
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n^2)
    public int minInsertions(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int[][] dp = new int[n][n + 1];

        // init
        for (int i = 0; i < n; i++) dp[i][i] = 0;
        for (int i = 0; i + 1 < n; i++) dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1) ? 0 : 1;

        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1; // right end
                if (s.charAt(i) == s.charAt(j)) dp[i][j] = dp[i + 1][j - 1];
                else dp[i][j] = Math.min(dp[i + 1][j] + 1, dp[i][j - 1] + 1);
            }
        }
        return dp[0][n - 1];
    }

    // S2: SCS
    // time = O(n^2), space = O(n^2)
    public int minInsertions2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        StringBuilder sb = new StringBuilder(s);
        String t = sb.reverse().toString();

        s = "#" + s;
        t = "#" + t;

        int[][] dp = new int[n + 1][n + 1];

        // init
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
            dp[0][i] = i;
        }

        // SCS
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i) == t.charAt(j)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
            }
        }
        return dp[n][n] - n;
    }
}
/**
 * dp 区间型dp
 * XXXXX[XX]XXXXX
 * dp[i][j]: the minimum number of steps to make s[i:j] palindrome
 * dp[i+1][j], dp[i][j-1], dp[i+1][j-1], dp[...]
 * if (s[i] == s[j]) dp[i][j] = dp[i+1][j-1]
 * else dp[i][j] = min{dp[i+1][j]+1, dp[i][j-1]+1}
 * dp[0][n-1]
 *
 * t = s[0:-1}
 * s t the shortest common supersequence (ref: LC1092)
 * 得到的这个supersequence其实就是s通过最少的insertion得到的一个palindrome
 * s => palindrome
 * t => palindrome
 *
 * s: leetcode => leetcodocteel
 * t: edocteel => leetcodocteel
 * 如何使它最短呢？
 * 逆序，互相堆成
 * s的后半段与t的前半段能够越重合就越好 => supersequence尽量短
 * => 两个互逆的串，你得到的SCS一定是个回文串
 *
 * LCS variants:
 * dp[i][j]： the length of SCS for s[1:i] and t[1:j]
 */

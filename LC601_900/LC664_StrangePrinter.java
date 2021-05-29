package LC601_900;
import java.util.*;
public class LC664_StrangePrinter {
    /**
     * There is a strange printer with the following two special properties:
     *
     * The printer can only print a sequence of the same character each time.
     * At each turn, the printer can print new characters starting from and ending at any place and will cover the
     * original existing characters.
     * Given a string s, return the minimum number of turns the printer needed to print it.
     *
     * Input: s = "aaabbb"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // S1: DP
    // time = O(n^3), space = O(n^2)
    public int strangePrinter(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2);

        // len == 1
        for (int i = 0; i < n; i++) dp[i][i] = 1;

        // what if k == i??? dp[i][k-1] = dp[i][i-1] 无意义 dp[k+1][j] = dp[i+1][j]
        // [a] {x x x a x x x} => 1 + dp[i+1][j]
        // if k == j => dp[k+1][j] = dp[j+1][j] => 【axxxxxa] => [axxxxx] + a => dp[i][j-1] + 0
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                dp[i][j] = 1 + dp[i + 1][j];
                for (int k = i + 1; k < j; k++) {
                    if (s.charAt(k) == s.charAt(i)) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k - 1] + dp[k + 1][j]);
                    }
                }
                if (s.charAt(i) == s.charAt(j)) dp[i][j] = Math.min(dp[i][j], dp[i][j - 1]);
            }
        }
        return dp[0][n - 1];
    }

    // S2: DP
    // time = O(n^3), space = O(n^2)
    public int strangePrinter2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        // 最底层的状态是确定的，就是1，所以要逆序从最底层开始不断向上推，否则i正序的话并不知道底层状态，无法求解
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i][j - 1];
                } else {
                    for (int k = i; k < j; k++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }
}
/**
 * aaabaaa
 * 区间型DP
 * dp[i][j] -> [i:j]
 * [a x x x a x x x a x x x a x x x]
 *  i                       z     j
 *  如果区间里后面的a跟第一个a不是同批次一起打印出来的话，那么打印第一个a的时候就根本不需要打到那个a，在之前就可以停止了。
 *  关键是首字母的a打到哪里，取决于后面的a跟这个首字母a是否是同一个批次。
 *  如果是同一个批次的话，那意味着以后这个位置都不会被后面的打印批次所覆盖。
 *  => dp[i][j] = dp[i][z-1] + dp[z+1][j] = min{dp[i][k-1] + dp[k+1][j]} (i <= k <= j, s[k] == s[i])
 *  dp[i][j] = dp[i][z-1] + ...
 *  dp[i][z-1] = dp[i][y-1] + ...
 *  有意义的是最后一个与首字母同批次打印的字符，不会再被覆盖，而有些a会被稍后覆盖，因而没有意义
 */
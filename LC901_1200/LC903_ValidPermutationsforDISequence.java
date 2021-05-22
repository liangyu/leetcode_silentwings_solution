package LC901_1200;
import java.util.*;
public class LC903_ValidPermutationsforDISequence {
    /**
     * We are given s, a length n string of characters from the set {'D', 'I'}. (These letters stand for "decreasing"
     * and "increasing".)
     *
     * A valid permutation is a permutation p[0], p[1], ..., p[n] of integers {0, 1, ..., n}, such that for all i:
     *
     * If s[i] == 'D', then p[i] > p[i+1], and;
     * If s[i] == 'I', then p[i] < p[i+1].
     * How many valid permutations are there?  Since the answer may be large, return your answer modulo 10^9 + 7.
     *
     * Input: s = "DID"
     * Output: 5
     *
     * Note:
     *
     * 1 <= s.length <= 200
     * s consists only of characters from the set {'D', 'I'}.
     * @param s
     * @return
     */
    // time = O(n^3), space = O(n^2)
    public int numPermsDISequence(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        dp[0][0] = 1;

        s = "#" + s;
        int M = (int)(1e9 + 7);

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == 'I') {
                    for (int jj = 0; jj < j; jj++) {
                        dp[i][j] += dp[i - 1][jj];
                        dp[i][j] %= M;
                    }
                } else {
                    for (int jj = j; jj <= i - 1; jj++) {
                        dp[i][j] += dp[i - 1][jj];
                        dp[i][j] %= M;
                    }
                }
            }
        }
        long res = 0;
        for (int j = 0; j <= n; j++) {
            res = (res + dp[n][j]) % M;
        }
        return (int)res;
    }
}
/**
 * ref: LC1866
 * dp[i][j]: how many permutations of [0:i] s.t. the i-th element is j
 * 前i个，考虑一个大的permutation问题，前i个位置上就是0~i的permutation
 * #DIDI
 * [xxxxi]xxxx
 * i-1th: 0, 1, 2, ...j-1
 * dp[i][j] = sum{dp[i-1][0], dp[i-1][1], ..., dp[i-1][j-1]}
 * dp[i][j] = sum{dp[i-1][j], dp[i-1][j+1], dp[i-1][j+2], ..., dp[i-1][i-1]}
 * 0312 3 -> 0412 3 (提升1位)
 * 0312 4 -> 0312 4
 * dp[i-1][2]
 *      I
 * 0321 1 => 0432 1
 */

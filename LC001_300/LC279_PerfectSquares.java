package LC001_300;
import java.util.*;
public class LC279_PerfectSquares {
    /**
     * Given an integer n, return the least number of perfect square numbers that sum to n.
     *
     * A perfect square is an integer that is the square of an integer; in other words, it is the product of some
     * integer with itself. For example, 1, 4, 9, and 16 are perfect squares while 3 and 11 are not.
     *
     * Input: n = 12
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= n <= 10^4
     * @param n
     * @return
     */
    // time = O(n^2), space = O(n)
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int x = 1; x <= Math.sqrt(i); x++) {
                dp[i] = Math.min(dp[i], dp[i - x * x] + 1);
            }
        }
        return dp[n];
    }
}
/**
 * n 是若干个平方数相加
 * n = a^2 + b^2 + ... + x^2
 * dp[i]: the least number of perfect square numbers which sum to i
 * dp[n] = Math.min{}dp[n - x^2] + 1} for all x => O(n^2)
 */

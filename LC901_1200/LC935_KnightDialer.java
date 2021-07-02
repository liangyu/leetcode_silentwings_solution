package LC901_1200;
import java.util.*;
public class LC935_KnightDialer {
    /**
     * The chess knight has a unique movement, it may move two squares vertically and one square horizontally, or two
     * squares horizontally and one square vertically (with both forming the shape of an L). The possible movements of
     * chess knight are shown in this diagaram:
     *
     * A chess knight can move as indicated in the chess diagram below:
     *
     * We have a chess knight and a phone pad as shown below, the knight can only stand on a numeric cell (i.e. blue cell).
     *
     * Given an integer n, return how many distinct phone numbers of length n we can dial.
     *
     * You are allowed to place the knight on any numeric cell initially and then you should perform n - 1 jumps to dial
     * a number of length n. All jumps should be valid knight jumps.
     *
     * As the answer may be very large, return the answer modulo 10^9 + 7.
     *
     * Input: n = 1
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= n <= 5000
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int knightDialer(int n) {
        int[] dp = new int[10];
        Arrays.fill(dp, 1);
        int M = (int)(1e9 + 7);
        for (int i = 2; i <= n; i++) {
            int[] dp_old = dp.clone();
            dp[0] = dp_old[4] + dp_old[6];
            dp[1] = dp_old[6] + dp_old[8];
            dp[2] = dp_old[7] + dp_old[9];
            dp[3] = dp_old[4] + dp_old[8];
            dp[4] = (dp_old[3] + dp_old[9]) % M + dp_old[0];
            dp[5] = 0;
            dp[6] = (dp_old[1] + dp_old[7]) % M + dp_old[0];
            dp[7] = dp_old[2] + dp_old[6];
            dp[8] = dp_old[1] + dp_old[3];
            dp[9] = dp_old[4] + dp_old[2];

            for (int k = 0; k <= 9; k++) {
                dp[k] %= M;
            }
        }

        int res = 0;
        for (int i = 0; i <= 9; i++) {
            res += dp[i];
            res %= M;
        }
        return res;
    }
}
/**
 * 1 <- 6 / 8
 * 2 <- 7 / 9
 * dp[1] = dp[6] + dp[8]
 * dp[n][1] = dp[n-1][6] + dp[n-1][8]
 * dp[n][2] =
 * ...
 * dp[n][0]
 */

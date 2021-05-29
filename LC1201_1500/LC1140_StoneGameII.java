package LC1201_1500;
import java.util.*;
public class LC1140_StoneGameII {
    /**
     * Alice and Bob continue their games with piles of stones.  There are a number of piles arranged in a row, and
     * each pile has a positive integer number of stones piles[i].  The objective of the game is to end with the most
     * stones.
     *
     * Alice and Bob take turns, with Alice starting first.  Initially, M = 1.
     *
     * On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M.
     * Then, we set M = max(M, X).
     *
     * The game continues until all the stones have been taken.
     *
     * Assuming Alice and Bob play optimally, return the maximum number of stones Alice can get.
     *
     * Input: piles = [2,7,9,4,4]
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= piles.length <= 100
     * 1 <= piles[i] <= 10^4
     * @param piles
     * @return
     */
    // time = O(n), space = O(n^2)
    public int stoneGameII(int[] piles) {
        // corner case
        if (piles == null || piles.length == 0) return 0;

        int n = piles.length;
        int[][] dp = new int[n + 1][n + 1];
        int[] suf = new int[n + 1];

        suf[n] = 0;
        for (int i = n - 1; i >= 0; i--) suf[i] = suf[i + 1] + piles[i];
        return solve(piles, 0, 1, dp, suf);
    }

    private int solve(int[] piles, int i, int M, int[][] dp, int[] suf) {
        int n = piles.length;
        // base case
        if (i == piles.length) return 0;

        if (dp[i][M] != 0) return dp[i][M];

        int sum = 0;
        for (int x = 1; x <= 2 * M; x++) {
            if (i + x - 1 >= n) break;
            sum += piles[i + x - 1];
            dp[i][M] = Math.max(dp[i][M], sum + suf[i + x] - solve(piles, i + x, Math.max(x, M), dp, suf))
            ;
        }
        return dp[i][M];
    }
}
/**
 * M = 1, [1, 2]   X = 1
 * M = 2, [1, 4]   X = 3  solve(2,2)
 * M = 3, [1, 6]   X = 1
 * M = 3, [1, 6]   X = ...
 *
 * maximize：           dp[state]
 *                         ||
 * minimize (maximize: dp[state'])
 */

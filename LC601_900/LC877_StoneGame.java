package LC601_900;
import java.util.*;
public class LC877_StoneGame {
    /**
     * Alex and Lee play a game with piles of stones.  There are an even number of piles arranged in a row, and each
     * pile has a positive integer number of stones piles[i].
     *
     * The objective of the game is to end with the most stones.  The total number of stones is odd, so there are no
     * ties.
     *
     * Alex and Lee take turns, with Alex starting first.  Each turn, a player takes the entire pile of stones from
     * either the beginning or the end of the row.  This continues until there are no more piles left, at which point
     * the person with the most stones wins.
     *
     * Assuming Alex and Lee play optimally, return True if and only if Alex wins the game.
     *
     * Input: piles = [5,3,4,5]
     * Output: true
     *
     * Constraints:
     *
     * 2 <= piles.length <= 500
     * piles.length is even.
     * 1 <= piles[i] <= 500
     * sum(piles) is odd.
     * @param piles
     * @return
     */
    // S1: recursion
    // time = O(2^n), space = O(n^2)
    public boolean stoneGame(int[] piles) {
        // corner case
        if (piles == null || piles.length == 0) return true;

        int n = piles.length;
        int[][] dp = new int[n + 1][n + 1];
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + piles[i - 1];

        int gain = solve(piles, dp, presum, 1, n);
        return gain > presum[n] - gain;
    }

    private int solve(int[] piles, int[][] dp, int[] presum, int a, int b) {
        // base case
        if (a == b) return piles[a - 1];
        if (dp[a][b] != 0) return dp[a][b];

        dp[a][b] = Math.max(presum[b] - presum[a - 1] - solve(piles, dp, presum, a + 1, b), presum[b] - presum[a - 1] - solve(piles, dp, presum, a, b - 1));
        return dp[a][b];
    }

    //  S2: dp
    // time = O(n^2), space = O(n^2)
    public boolean stoneGame2(int[] piles) {
        // corner case
        if (piles == null || piles.length == 0) return true;

        int n = piles.length;
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) dp[i][i] = piles[i - 1];
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + piles[i - 1];

        for (int len = 1; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                if (i != j) {
                    dp[i][i + len - 1] = Math.max(presum[j] - presum[i - 1] - dp[i + 1][j], presum[j] - presum[i - 1] - dp[i][j - 1]);
                }
            }
        }
        return dp[1][n] > presum[n] - dp[1][n];
    }

    // S3: Math
    // time = O(1), space = O(1)
    public boolean stoneGame3(int[] piles) {
        return true;
    }
}
/**
 * x [o x o x o x o] ->
 * x [o x o x o x ] o
 * 我方保证可以把所有的奇数对都给取了，对方只能取偶数堆，如果奇数堆的和比偶数堆大，反之亦然,而总和是奇数，所以必定不会出现tie => 巧解
 * 因为此题的N为偶数，且不会有平局。
 * 说明奇数堆的总和，必然与偶数堆的总和不一样。
 * 先手玩家可以总是取奇数堆（或者总是取偶数堆），因此可以必胜。
 * x [x x x x x x x]
 *     solve(2,n)
 * int solve(1, n)
 * 1. pick piles[1] => piles[1] + sum[2:n] - solve(2,n) => sum[1:n] - solve(2,n)
 * 2. pick piles[n] -> piles[n] + sum[1:n-1] - solve(1,n-1) => sum[1:n] - solve(1,n-1)
 * solve(a,a) = piles[a]
 */

package LC1501_1800;
import java.util.*;
public class LC1563_StoneGameV {
    /**
     * There are several stones arranged in a row, and each stone has an associated value which is an integer given in
     * the array stoneValue.
     *
     * In each round of the game, Alice divides the row into two non-empty rows (i.e. left row and right row), then Bob
     * calculates the value of each row which is the sum of the values of all the stones in this row. Bob throws away
     * the row which has the maximum value, and Alice's score increases by the value of the remaining row. If the value
     * of the two rows are equal, Bob lets Alice decide which row will be thrown away. The next round starts with the
     * remaining row.
     *
     * The game ends when there is only one stone remaining. Alice's is initially zero.
     *
     * Return the maximum score that Alice can obtain.
     *
     * Input: stoneValue = [6,2,3,4,5,5]
     * Output: 18
     *
     * Constraints:
     *
     * 1 <= stoneValue.length <= 500
     * 1 <= stoneValue[i] <= 10^6
     * @param stoneValue
     * @return
     */
    // S1: dp
    // time = O(n^3), space = O(n^2)
    public int stoneGameV(int[] stoneValue) {
        // corner case
        if (stoneValue == null || stoneValue.length == 0) return 0;

        int n = stoneValue.length;
        int[][] dp = new int[n + 1][n + 1];
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + stoneValue[i - 1];
        // 把最后只剩长度为2时一劈为二时候的情况先考虑确定了
        for (int i = 1; i <= n - 1; i++) dp[i][i + 1] = Math.min(stoneValue[i - 1], stoneValue[i]);

        for (int len = 3; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                for (int k = i; k < j; k++) {
                    int leftSum = presum[k] - presum[i - 1];
                    int rightSum = presum[j] - presum[k];
                    if (leftSum > rightSum) dp[i][j] = Math.max(dp[i][j], rightSum + dp[k + 1][j]);
                    else if (leftSum < rightSum) dp[i][j] = Math.max(dp[i][j], leftSum + dp[i][k]);
                    else dp[i][j] = Math.max(dp[i][j], leftSum + Math.max(dp[i][k], dp[k + 1][j]));
                }
            }
        }
        return dp[1][n];
    }

    // S2: recursion
    // time = O(n^3), space = O(n^2)
    public int stoneGameV2(int[] stoneValue) {
        // corner case
        if (stoneValue == null || stoneValue.length == 0) return 0;

        int n = stoneValue.length;
        int[][] dp = new int[n + 1][n + 1];
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + stoneValue[i - 1];

        return solve(dp, presum, 1, n);
    }

    private int solve(int[][] dp, int[] presum, int i, int j) {
        if (dp[i][j] != 0) return dp[i][j];
        if (i == j) return 0;

        for (int k = i; k < j; k++) {
            int leftSum = presum[k] - presum[i - 1];
            int rightSum = presum[j] - presum[k];
            if (leftSum > rightSum) dp[i][j] = Math.max(dp[i][j], rightSum + solve(dp, presum, k + 1, j));
            else if (leftSum < rightSum) dp[i][j] = Math.max(dp[i][j], leftSum + solve(dp, presum, i, k));
            else dp[i][j] = Math.max(dp[i][j], leftSum + Math.max(solve(dp, presum, i, k), solve(dp, presum, k + 1, j)));
        }
        return dp[i][j];
    }
}
/**
 * 区间型dp
 * dp[i][j]: the maximum score we can get from interval [i:j]
 * 如何划分区间？暴力扫一遍，再比较区间和 -> 递归调用
 * for (int k = i; k < j; k++)
 *     [i:k], [k+1:j]
 *     if (leftsum > rightsum)
 *          dp[i][j] = rightsum + dp[k+1][j];
 *    else if (leftsum < rightsum)
 *          dp[i][j] = leftsum + dp[i][k];
 *    else
 *          dp[i][j] = leftsum/rightsum + max(dp[i][k], dp[k+1][j]);
 */

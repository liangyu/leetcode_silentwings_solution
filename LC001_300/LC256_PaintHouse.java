package LC001_300;
import java.util.*;
public class LC256_PaintHouse {
    /**
     * There is a row of n houses, where each house can be painted one of three colors: red, blue, or green. The cost
     * of painting each house with a certain color is different. You have to paint all the houses such that no two
     * adjacent houses have the same color.
     *
     * The cost of painting each house with a certain color is represented by an n x 3 cost matrix costs.
     *
     * For example, costs[0][0] is the cost of painting house 0 with the color red; costs[1][2] is the cost of painting
     * house 1 with color green, and so on...
     * Return the minimum cost to paint all houses.
     *
     * Input: costs = [[17,2,17],[16,16,5],[14,3,19]]
     * Output: 10
     *
     * Constraints:
     *
     * costs.length == n
     * costs[i].length == 3
     * 1 <= n <= 100
     * 1 <= costs[i][j] <= 20
     * @param costs
     * @return
     */
    // S1: DP
    // time = O(n), space = O(n)
    public int minCost(int[][] costs) {
        // corner case
        if (costs == null || costs.length == 0 || costs[0] == null || costs[0].length == 0) return 0;

        int n = costs.length;
        int[][] dp = new int[n + 1][3];
        dp[0][0] = dp[0][1] = dp[0][2] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 3; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = 0; k < 3; k++) {
                    if (k != j) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + costs[i - 1][j]);
                    }
                }
            }
        }
        return Math.min(dp[n][0], Math.min(dp[n][1], dp[n][2]));
    }

    // S2: 最优解！
    // time = O(n), space = O(1)
    public int minCost2(int[][] costs) {
        // corner case
        if (costs == null || costs.length == 0 || costs[0] == null || costs[0].length == 0) return 0;

        int n = costs.length;
        for (int i = 1; i < n; i++) {
            costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
            costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] += Math.min(costs[i - 1][0], costs[i - 1][1]);
        }
        return Math.min(costs[n - 1][0], Math.min(costs[n - 1][1], costs[n - 1][2]));
    }
}

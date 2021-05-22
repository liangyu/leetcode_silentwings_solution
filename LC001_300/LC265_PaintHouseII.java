package LC001_300;
import java.util.*;
public class LC265_PaintHouseII {
    /**
     * There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house
     * with a certain color is different. You have to paint all the houses such that no two adjacent houses have the
     * same color.
     *
     * The cost of painting each house with a certain color is represented by an n x k cost matrix costs.
     *
     * For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house
     * 1 with color 2, and so on...
     * Return the minimum cost to paint all houses.
     *
     * Input: costs = [[1,5,3],[2,9,4]]
     * Output: 5
     *
     * Constraints:
     *
     * costs.length == n
     * costs[i].length == k
     * 1 <= n <= 100
     * 1 <= k <= 20
     * 1 <= costs[i][j] <= 20
     * @param costs
     * @return
     */
    // S1: DP (最优解！)
    // time = O(n * k), space = O(1)
    public int minCostII(int[][] costs) {
        // corner case
        if (costs == null || costs.length == 0 || costs[0] == null || costs[0].length == 0) return 0;

        int n = costs.length, k = costs[0].length;
        // min1 is the index of the 1st-smallest cost till previous house
        // min2 is the index of the 2nd-smallest cost till previous house
        int min1 = -1, min2 = -1;

        for (int i = 0; i < n; i++) {
            int last1 = min1, last2 = min2;
            min1 = -1;
            min2 = -1;
            for (int j = 0; j < k; j++) {
                if (j != last1) { // 与前一所房子为止的最小值不冲突
                    costs[i][j] += last1 < 0 ? 0 : costs[i - 1][last1];
                } else { // 冲突的话，只能取次小值
                    costs[i][j] += last2 < 0 ? 0 : costs[i - 1][last2];
                }
                // find the indices of 1st and 2nd smallest cost of painting current house i
                if (min1 < 0 || costs[i][j] < costs[i][min1]) {
                    min2 = min1;
                    min1 = j;
                } else if (min2 < 0 || costs[i][j] < costs[i][min2]) {
                    min2 = j;
                }
            }
        }
        return costs[n - 1][min1];
    }

    // S2: DP
    // time = O(n * klogk), space = O(n * k)
    public int minCostII2(int[][] costs) {
        // corner case
        if (costs == null || costs.length == 0 || costs[0] == null || costs[0].length == 0) return 0;

        int n = costs.length, k = costs[0].length;
        int[][] dp = new int[n][k];
        for (int j = 0; j < k; j++) dp[0][j] = costs[0][j];

        for (int i = 1; i < n; i++) {
            int[][] temp = new int[k][2];
            for (int j = 0; j < k; j++) {
                temp[j][0] = dp[i - 1][j];
                temp[j][1] = j;
            }
            Arrays.sort(temp, (o1, o2) -> o1[0] - o2[0]);
            for (int j = 0; j < k; j++) { // 对于每个j，我们都要找一个最小值
                if (j == temp[0][1]) {
                    dp[i][j] = temp[1][0] + costs[i][j];
                } else {
                    dp[i][j] = temp[0][0] + costs[i][j];
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            res = Math.min(res, dp[n - 1][j]);
        }
        return res;
    }
}
/**
 * i-1, 0
 *      1
 *      2
 *      ..
 * i, j
 * dp[i][j]: the minimum cost of painting houses [0:j] ending with cost[i][j]
 * ref: LC1289
 * */

package LC901_1200;
import java.util.*;
public class LC1029_TwoCityScheduling {
    /**
     * A company is planning to interview 2n people. Given the array costs where costs[i] = [aCosti, bCosti], the cost
     * of flying the ith person to city a is aCosti, and the cost of flying the ith person to city b is bCosti.
     *
     * Return the minimum cost to fly every person to a city such that exactly n people arrive in each city.
     *
     * Input: costs = [[10,20],[30,200],[400,50],[30,20]]
     * Output: 110
     *
     * Constraints:
     *
     * 2 * n == costs.length
     * 2 <= costs.length <= 100
     * costs.length is even.
     * 1 <= aCosti, bCosti <= 1000
     * @param costs
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n^2)
    public int twoCitySchedCost(int[][] costs) {
        int n = costs.length;
        int[][] dp = new int[n / 2 + 1][n / 2 + 1];

        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= Math.min(n / 2, len); i++) {
                int j = len - i;
                if (j > n / 2) continue;
                if (i == 0 && j == 0) dp[i][j] = 0;
                else if (i == 0) dp[i][j] = dp[i][j - 1] + costs[len - 1][1];
                else if (j == 0) dp[i][j] = dp[i - 1][j] + costs[len - 1][0];
                else dp[i][j] = Math.min(dp[i - 1][j] + costs[len - 1][0], dp[i][j - 1] + costs[len - 1][1]);
            }
        }
        return dp[n / 2][n / 2];
    }

    // S2: Greedy
    // time = O(nlogn), space = O(1)
    public int twoCitySchedCost2(int[][] costs) {
        Arrays.sort(costs, (o1, o2) -> Math.abs(o1[0] - o1[1]) - Math.abs(o2[0] - o2[1]));

        int n = costs.length, res = 0, countA = 0, countB = 0;
        for (int i = n - 1; i >= 0; i--) {
            int a = costs[i][0], b = costs[i][1];
            if (a < b) {
                if (countA < n / 2) {
                    res += a;
                    countA++;
                } else {
                    res += b;
                    countB++;
                }
            } else {
                if (countB < n / 2) {
                    res += b;
                    countB++;
                } else {
                    res += a;
                    countA++;
                }
            }
        }
        return res;
    }

    // S3: Greedy (最优解!)
    // time = O(nlogn), space = O(1)
    public int twoCitySchedCost3(int[][] costs) {
        int n = costs.length, res = 0;
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) {
            res += costs[i][0];
            diff[i] = costs[i][1] - costs[i][0];
        }

        Arrays.sort(diff);
        for (int i = 0; i < n / 2; i++) res += diff[i]; // 补最小的n/2差价，选择b！
        return res;
    }
}

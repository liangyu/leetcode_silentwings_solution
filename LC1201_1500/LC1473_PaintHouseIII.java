package LC1201_1500;
import java.util.*;
public class LC1473_PaintHouseIII {
    /**
     * There is a row of m houses in a small city, each house must be painted with one of the n colors (labeled from 1
     * to n), some houses that have been painted last summer should not be painted again.
     *
     * A neighborhood is a maximal group of continuous houses that are painted with the same color.
     *
     * For example: houses = [1,2,2,3,3,2,1,1] contains 5 neighborhoods [{1}, {2,2}, {3,3}, {2}, {1,1}].
     * Given an array houses, an m x n matrix cost and an integer target where:
     *
     * houses[i]: is the color of the house i, and 0 if the house is not painted yet.
     * cost[i][j]: is the cost of paint the house i with the color j + 1.
     * Return the minimum cost of painting all the remaining houses in such a way that there are exactly target
     * neighborhoods. If it is not possible, return -1.
     *
     * Input: houses = [0,0,0,0,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
     * Output: 9
     *
     * Constraints:
     *
     * m == houses.length == cost.length
     * n == cost[i].length
     * 1 <= m <= 100
     * 1 <= n <= 20
     * 1 <= target <= m
     * 0 <= houses[i] <= n
     * 1 <= cost[i][j] <= 10^4
     * @param houses
     * @param cost
     * @param m
     * @param n
     * @param target
     * @return
     */
    // S1: DP
    // time = O(m^2 * n^2), space = O(m^2 * n)
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[105][105][25];

        int[] nums = new int[m + 1];
        for (int i = 0; i < m; i++) nums[i + 1] = houses[i];
        houses = nums;

        int[][] arr = new int[m + 1][n];
        for (int i = 0; i < m; i++) arr[i + 1] = cost[i];
        cost = arr;

        // init
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    dp[i][j][k] = Integer.MAX_VALUE / 2;
                }
            }
        }

        for (int k = 0; k <= n; k++) dp[0][0][k] = 0;

        for (int i = 1; i <= m; i++) {
            if (houses[i] != 0) {
                int k = houses[i];
                for (int j = 1; j <= target; j++) {
                    for (int kk = 1; kk <= n; kk++) {
                        if (kk != k) {
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j - 1][kk]);
                        } else {
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j][kk]);
                        }
                    }
                }
            } else {
                for (int j = 1; j <= target; j++) {
                    for (int k = 1; k <= n; k++) {
                        // update[i][j][k]
                        for (int kk = 1; kk <= n; kk++) {
                            if (kk != k) {
                                dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j - 1][kk] + cost[i][k - 1]);
                            } else {
                                dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j][kk] + cost[i][k - 1]);
                            }
                        }
                    }
                }
            }
        }

        int res = Integer.MAX_VALUE / 2;
        for (int k = 1; k <= n; k++) {
            res = Math.min(res, dp[m][target][k]);
        }
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }

    // S1.2: dp (优化掉多余遍历的kk那一层循环)
    // time = O(m^2 * nlogn), space = O(m^2 * n)
    public int minCost2(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[105][105][25];

        int[] nums = new int[m + 1];
        for (int i = 0; i < m; i++) nums[i + 1] = houses[i];
        houses = nums;

        int[][] arr = new int[m + 1][n];
        for (int i = 0; i < m; i++) arr[i + 1] = cost[i];
        cost = arr;

        // init
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    dp[i][j][k] = Integer.MAX_VALUE / 2;
                }
            }
        }

        for (int k = 0; k <= n; k++) dp[0][0][k] = 0;

        for (int i = 1; i <= m; i++) { // O(m)
            if (houses[i] != 0) {
                int k = houses[i];
                for (int j = 1; j <= target; j++) {
                    for (int kk = 1; kk <= n; kk++) {
                        if (kk != k) {
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j - 1][kk]);
                        } else {
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j][kk]);
                        }
                    }
                }
            } else {
                for (int j = 1; j <= target; j++) { // O(m)
                    List<int[]> temp = new ArrayList<>();
                    for (int kk = 1; kk <= n; kk++) { // O(n)
                        temp.add(new int[]{dp[i - 1][j - 1][kk], kk});
                    }
                    Collections.sort(temp, (o1, o2) -> o1[0] - o2[0]); // O(nlogn)

                    for (int k = 1; k <= n; k++) {  // O(n)
                        // consider the last two houses painted with same color
                        dp[i][j][k] = dp[i - 1][j][k] + cost[i][k - 1];

                        // consider the last two houses painted with different color
                        if (k != temp.get(0)[1]) {
                            dp[i][j][k] = Math.min(dp[i][j][k], temp.get(0)[0] + cost[i][k - 1]);
                        } else {
                            dp[i][j][k] = Math.min(dp[i][j][k], temp.get(1)[0] + cost[i][k - 1]);
                        }
                    }
                }
            }
        }

        int res = Integer.MAX_VALUE / 2;
        for (int k = 1; k <= n; k++) {
            res = Math.min(res, dp[m][target][k]);
        }
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }

    // S1.3: dp (优化掉排序)
    // time = O(m^2 * n), space = O(m^2 * n)
    public int minCost3(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[m + 1][m + 1][n + 1];

        int[] nums = new int[m + 1];
        for (int i = 0; i < m; i++) nums[i + 1] = houses[i];
        houses = nums;

        int[][] arr = new int[m + 1][n];
        for (int i = 0; i < m; i++) arr[i + 1] = cost[i];
        cost = arr;

        // init
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    dp[i][j][k] = Integer.MAX_VALUE / 2;
                }
            }
        }

        for (int k = 0; k <= n; k++) dp[0][0][k] = 0;

        for (int i = 1; i <= m; i++) {
            if (houses[i] != 0) {
                int k = houses[i];
                for (int j = 1; j <= target; j++) {
                    for (int kk = 1; kk <= n; kk++) {
                        if (kk != k) {
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j - 1][kk]);
                        } else {
                            dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j][kk]);
                        }
                    }
                }
            } else {
                for (int j = 1; j <= target; j++) {
                    List<int[]> temp = new ArrayList<>();
                    for (int kk = 1; kk <= n; kk++) {
                        temp.add(new int[]{dp[i - 1][j - 1][kk], kk});
                    }

                    // find min and 2nd min
                    int val1 = Integer.MAX_VALUE, val2 = Integer.MAX_VALUE;
                    int idx1 = -1, idx2 = -1;
                    for (int[] x : temp) {
                        if (x[0] < val1) {
                            val2 = val1;
                            val1 = x[0];
                            idx2 = idx1;
                            idx1 = x[1];
                        } else if (x[0] < val2) {
                            val2 = x[0];
                            idx2 = x[1];
                        }
                    }

                    for (int k = 1; k <= n; k++) {
                        // consider the last two houses painted with same color
                        dp[i][j][k] = dp[i - 1][j][k] + cost[i][k - 1];

                        // consider the last two houses painted with different color
                        if (k != idx1) {
                            dp[i][j][k] = Math.min(dp[i][j][k], val1 + cost[i][k - 1]);
                        } else {
                            dp[i][j][k] = Math.min(dp[i][j][k], val2 + cost[i][k - 1]);
                        }
                    }
                }
            }
        }

        int res = Integer.MAX_VALUE / 2;
        for (int k = 1; k <= n; k++) {
            res = Math.min(res, dp[m][target][k]);
        }
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }
}
/**
 * dp[i][j][k]: the min cost when we paint the first i houses to form j neighborhoods and tthe i-th house is pained with color k
 * x x x x (i-1)(i)
 * 4层循环
 * 有些情况下，第四层可以省去，或者和某一层循环并列以防止TLE， ref: LC1959
 * for i =
 *  for j =
 *      for k =
 *          for prevState = ...
 *              dp[i][j][k] = max(dp[prevState])
 * 所有的关注点都在前一个，无后效性 -> dp
 */
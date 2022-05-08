package LC1201_1500;
import java.util.*;
public class LC1388_PizzaWith3nSlices {
    /**
     * There is a pizza with 3n slices of varying size, you and your friends will take slices of pizza as follows:
     *
     * You will pick any pizza slice.
     * Your friend Alice will pick next slice in anti clockwise direction of your pick.
     * Your friend Bob will pick next slice in clockwise direction of your pick.
     * Repeat until there are no more slices of pizzas.
     * Sizes of Pizza slices is represented by circular array slices in clockwise direction.
     *
     * Return the maximum possible sum of slice sizes which you can have.
     *
     * Input: slices = [1,2,3,4,5,6]
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= slices.length <= 500
     * slices.length % 3 == 0
     * 1 <= slices[i] <= 1000
     * @param slices
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n^2)
    public int maxSizeSlices(int[] slices) {
        int n = slices.length;
        return Math.max(helper(slices, 0, n - 2, n / 3), helper(slices, 1, n - 1, n / 3));
    }

    private int helper(int[] nums, int a, int b, int k) {
        int n = b - a + 1;
        int[][][] dp = new int[n + 1][k + 1][2];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1]);
                dp[i][j][1] = dp[i - 1][j - 1][0] + nums[i - 1 + a];
            }
        }
        return Math.max(dp[n][k][0], dp[n][k][1]);
    }

    // S1.2: DP
    // time = O(n^2), space = O(n^2)
    public int maxSizeSlices2(int[] slices) {
        int n = slices.length;
        return Math.max(helper2(slices, 0, n - 2, n / 3), helper2(slices, 1, n - 1, n / 3));
    }

    private int helper2(int[] nums, int a, int b, int k) {
        int[][] dp = new int[k + 1][2]; // dp[i][j]: the maximum when picking i slices and the current slice's status is j

        for (int i = a; i <= b; i++) {
            for (int j = Math.min(k, i - a + 1); j >= 1; j--) {
                dp[j][0] = Math.max(dp[j][0], dp[j][1]);
                dp[j][1] = dp[j - 1][0] + nums[i];
            }
        }
        return Math.max(dp[k][0], dp[k][1]);
    }

    // S1.3
    public int maxSizeSlices3(int[] slices) {
        int n = slices.length;
        return Math.max(helper3(slices, 0, n - 2, n / 3), helper3(slices, 1, n - 1, n / 3));
    }

    private int helper3(int[] nums, int a, int b, int k) {
        int[][] dp = new int[k + 1][2];

        for (int i = a; i <= b; i++) {
            int[][] copy = new int[k + 1][2];
            for (int j = 0; j <= k; j++) copy[j] = dp[j].clone();
            // 按顺序则要先将之前i - 1的dp二维数组先保存下来，否则dp[i-1][j-1][0] 会被上一行的操作覆盖！！！
            // 替代方案入S1.2，就是逆序来计算！！！
            for (int j = 1; j <= Math.min(k, i - a + 1); j++) {
                dp[j][0] = Math.max(copy[j][0], copy[j][1]);
                dp[j][1] = copy[j - 1][0] + nums[i];
            }
        }
        return Math.max(dp[k][0], dp[k][1]);
    }
}
/**
 * The reason behind process i in desecending order:
 * When we implement " dp[i][1] = dp[i-1][0] + current slice value" part,
 * we need to remember the dp[i-1] in the previous row (that is to say dp[i-1][j-1] in 3D array mode).
 * If we take increasing order, however, the dp[i-1] has already been changed as we first compute dp[i-1] then dp[i].
 * If we take descending order, we first compute dp[i] then dp[i-1],
 * where in this situation the value of dp[i-1] has not been changed
 * (The value of dp[i-1] is still the value representing dp[i-1][j-1]).
 */
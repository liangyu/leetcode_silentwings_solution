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

    private int helper(int[] nums, int start, int end, int k) {
        int n = end - start + 1, res = 0;
        int[][][] dp = new int[n][k + 1][2];
        dp[0][1][1] = nums[start];
        for (int i = start + 1; i <= end; i++) {
            int x = i - start;
            for (int j = 1; j <= k; j++) {
                dp[x][j][0] = Math.max(dp[x - 1][j][0], dp[x - 1][j][1]);
                dp[x][j][1] = dp[x - 1][j - 1][0] + nums[i];
                if (j == k) res = Math.max(res, Math.max(dp[x][j][0], dp[x][j][1]));
            }
        }
        return res;
    }

    // S1.2: DP
    // time = O(n^2), space = O(n^2)
    public int maxSizeSlices2(int[] slices) {
        int n = slices.length;
        return Math.max(helper2(slices, 0, n - 2, n / 3), helper2(slices, 1, n - 1, n / 3));
    }

    private int helper2(int[] nums, int start, int end, int k) {
        int n = end - start + 1;
        int[][] dp = new int[k + 1][2];
        dp[1][1] = nums[start];
        for (int i = start + 1; i <= end; i++) {
            for (int j = k; j > 0; j--) {
                dp[j][0] = Math.max(dp[j][0], dp[j][1]);
                dp[j][1] = dp[j - 1][0] + nums[i];
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
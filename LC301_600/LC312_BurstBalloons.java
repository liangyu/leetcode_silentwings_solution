package LC301_600;

public class LC312_BurstBalloons {
    /**
     * You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an
     * array nums. You are asked to burst all the balloons.
     *
     * If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out
     * of bounds of the array, then treat it as if there is a balloon with a 1 painted on it.
     *
     * Return the maximum coins you can collect by bursting the balloons wisely.
     *
     * Input: nums = [3,1,5,8]
     * Output: 167
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 500
     * 0 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // time = O(n^3), space = O(n^2)
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] nums2 = new int[n + 2];
        nums2[0] = nums2[n + 1] = 1;
        for (int i = 0; i < n; i++) nums2[i + 1] = nums[i];
        nums = nums2;

        int[][] dp = new int[n + 2][n + 2];
        for (int len = 1; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                for (int k = i; k <= j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k - 1] + dp[k + 1][j] + nums[k] * nums[i - 1] * nums[j + 1]);
                }
            }
        }
        return dp[1][n];
    }
}
/**
 * 经典区间型dp
 * x x x j x x x i
 * dp[i] => dp[j]  j < i
 * 区间型dp无法从一个较小index的dp推断出一个较大index的dp
 * 一个区间一个区间的射
 * i x x x x x [k] x x x x x j
 * "最后一个"射爆的是哪个气球
 * dp[i][j]:maximum coins you can collect by bursting the balloons[i:j]
 * 假设k是最后一个射爆的，两端都已经被射爆了
 * [i:k-1] k [k+1:j]
 * dp[i][k-1] + dp[k+1][j] + nums[k] * nums[i-1] * nums[j+1]  当前残存的左边的和右边的气球
 * Q: 我怎么知道nums[i-1]和nums[j+1]一定存在呢？
 * 这是因为我们考察dp的顺序，这是一个递归的过程
 * 考虑dp[i][j]的时候，k是最后留存的，你必须先解决小的dp，dp[i][j]之外的还没有被消掉
 * 如果换做考虑整个区间里要爆的第一个气球是什么，就比较难了
 * 小区间左右两边之外的值总是存在的，就比较方便了，可以放心使用
 * dp[i][j] i > j => 默认值 = 0没问题
 */
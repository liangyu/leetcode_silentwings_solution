package LC901_1200;

public class LC1004_MaxConsecutiveOnesIII {
    /**
     * Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can
     * flip at most k 0's.
     *
     * Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * nums[i] is either 0 or 1.
     * 0 <= k <= nums.length
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int longestOnes(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int i = 0, n = nums.length, count = 0, res = 0;
        for (int j = 0; j < n; j++) {
            if (nums[j] == 1) {
                res = Math.max(res, j - i + 1);
            } else {
                count++;
                while (count > k) {
                    if (nums[i] == 0) count--; // 注意先check nums[i] == 0 再移动i，否则就会漏掉对当前位置i的考虑！
                    i++;
                }
                res = Math.max(res, j - i + 1);
            }
        }
        return res;
    }
}
/**
 * dp -> 效率不高但可以做
 * k次翻转，有一个维度表示有多少种翻转的权利 -> 行使某种权利的次数，加1个这种维度
 * dp[i][k]: ending at i A[0:i], using k privileges
 * the length of the longest (contiguous) subarray that contains only 1s.
 * A[i] == 1, dp[i][k] = dp[i-1][k]+1
 * x x x x x 1
 * x x x x x 0
 * A[i] == 0, dp[i][k] = dp[i-1][k-1]+1
 * O(n^2) => 10^5 * 10^5 -> TLE
 *
 * 双指针 subArray，只需要确定左右边界
 * 先固定遍历一个左边界，然后尝试看右边界能取到哪里
 * x [i x x x x j] 0 x
 * x 1 [i x x x j] 0 x   如果左边界外面是1，则不会影响区间内的，
 * 只能继续让i往右走直到遇到原来是0的点才会影响后面区间内的k，从而多了一次翻转机会
 * [i:j] 已经翻转k次了，这时j不能再往右移动了，最大的满足条件的区间
 * time = O(n)
 */
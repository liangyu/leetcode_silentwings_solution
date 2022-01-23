package LC1501_1800;

public class LC1685_SumofAbsoluteDifferencesinaSortedArray {
    /**
     * You are given an integer array nums sorted in non-decreasing order.
     *
     * Build and return an integer array result with the same length as nums such that result[i] is equal to the
     * summation of absolute differences between nums[i] and all the other elements in the array.
     *
     * In other words, result[i] is equal to sum(|nums[i]-nums[j]|) where 0 <= j < nums.length and j != i (0-indexed).
     *
     * Input: nums = [2,3,5]
     * Output: [4,3,5]
     *
     * Constraints:
     *
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i] <= nums[i + 1] <= 10^4
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) res[0] += nums[i] - nums[0];
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] + (nums[i] - nums[i - 1]) * (i - (n - i));
        }
        return res;
    }
}
/**
 * res[i] = sum{|nums[k] - nums[i]|} over k = 0,1,2,...,n-1
 * n = 10^5
 * ref: 2121
 * 优化：转移结论
 * nums: {x x x i-1}, {i, x x x x}
 * res[i-1] => res[i]
 * 差值增大了 + (nums[i]-nums[i-1]) * i
 *          - (nums[i]-nums[i-1]) * (n-i)
 * O(1)结论转化
 * 关于中位数的题，也是这个类似的思想
 * x x x m x x x
 */
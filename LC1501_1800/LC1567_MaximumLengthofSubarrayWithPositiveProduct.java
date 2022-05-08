package LC1501_1800;

public class LC1567_MaximumLengthofSubarrayWithPositiveProduct {
    /**
     * Given an array of integers nums, find the maximum length of a subarray where the product of all its elements is
     * positive.
     *
     * A subarray of an array is a consecutive sequence of zero or more values taken out of that array.
     *
     * Return the maximum length of a subarray with positive product.
     *
     * Input: nums = [1,-2,-3,4]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: Greedy
    // time = O(n), space = O(1)
    public int getMaxLen(int[] nums) {
        int pos = 0, neg = 0, res = 0;
        for (int x : nums) {
            if (x == 0) {
                pos = 0;
                neg = 0;
            } else if (x > 0) {
                pos++;
                neg = (neg == 0 ? 0 : neg + 1);
            } else {
                int temp = pos;
                pos = (neg == 0 ? 0 : neg + 1);
                neg = temp + 1;
            }
            res = Math.max(res, pos);
        }
        return res;
    }

    // S2
    public int getMaxLen2(int[] nums) {
        int n = nums.length, res = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) continue;
            int j = i, count = 0, k = -1;
            while (j < n && nums[j] != 0) {
                count += nums[j] < 0 ? 1 : 0;
                if (count % 2 == 0) res = Math.max(res, j - i + 1);
                else if (k != -1) res = Math.max(res, j - k);
                if (nums[j] < 0 && k == -1) k = j;
                j++;
            }
            i = j;
        }
        return res;
    }
}
/**
 * 要求负数的个数为偶数个
 * 特别要注意下，要求严格是正的，最大的问题是中间不能有0
 * 有0的话分割成相互独立的区域
 * 遍历一个边界，固定另一个边界
 * ... 0 x x x x x x 0 ...
 * ... 0 i k [x x j] 0 ...
 * if [i:j] negative count % 2 == 0 => [i:j]
 * else [k+1:j]
 * elements      :   9    5    8    2    -6    4    -3    0    2    -5    15    10    -7    9    -2
 * positive_len  :   1    2    3    4     0    1     7    0    1     0     1     2     5    6     5
 * negative_len  :   0    0    0    0     5    6     2    0    0     2     3     4     3    4     7
 *
 * If we see a 0, we gotta start off things again
 * If we see a positive number :
 * 2.1. Increase length of positive mutilpicative result till now.
 * 2.2. Increase length of negative mutilpicative result till now, unless we had not encountered any negative before.
 * If we see a negative number:
 * 3.1. It's time to swap positive and negative multiplicative results' lengths and do similar task as we did in above case.
 * In each iteration, use the length of positive mutilpicative result to compute answer.
 */

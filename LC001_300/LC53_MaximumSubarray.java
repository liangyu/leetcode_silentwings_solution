package LC001_300;
import java.util.*;
public class LC53_MaximumSubarray {
    /**
     * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest
     * sum and return its sum.
     * <p>
     * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6
     * <p>
     * Constraints:
     * <p>
     * 1 <= nums.length <= 3 * 10^4
     * -10^5 <= nums[i] <= 10^5
     * <p>
     * <p>
     * Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer
     * approach, which is more subtle.
     *
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n), space = O(1)
    public int maxSubArray(int[] nums) {
        int curSum = 0, res = Integer.MIN_VALUE;
        for (int x : nums) {
            curSum = Math.max(curSum + x, x);
            res = Math.max(res, curSum);
        }
        return res;
    }

    // S2: Divide & Conquer (Segment Tree)
    // time = O(n), space = O(logn)
    public int maxSubArray2(int[] nums) {
        int res = Integer.MIN_VALUE;
        for (int x : nums) res = Math.max(res, x);
        if (res < 0) return res;
        int[] ans = build(nums, 0, nums.length - 1); // res: sum, s, ls, rs
        return ans[1];
    }

    private int[] build(int[]nums, int l, int r) {
        if (l == r) {
            int v = Math.max(nums[l], 0);
            return new int[]{nums[l], v, v, v};
        }

        int mid = l + (r - l) / 2;
        int[] left = build(nums, l, mid);
        int[] right = build(nums, mid + 1, r);

        int[] res = new int[4];
        res[0] = left[0] + right[0];
        res[1] = Math.max(Math.max(left[1], right[1]), left[3] + right[2]); // 左，右以及横跨左右这3种情况
        res[2] = Math.max(left[2], left[0] + right[2]); // 前缀：左前缀，左边和+右前缀
        res[3] = Math.max(right[3], right[0] + left[3]); // 后缀：右后缀，左后缀+右边和
        return res;
    }
}
/**
 * f[i]表示所有以nums[i]即为的区间中的最大和是多少
 * 状态计算：
 * 1.区间长度 >= 2
 * 2. 区间长度 = 1
 * f[i] = max{nums[i], f[i - 1] + nums[i]} = nums[i] + max{f[i - 1], 0}
 * f[i-1] -> last
 * 滚动
 *
 * S2: 分治
 * 1. 最大子段和
 * 2. 最大前缀
 * 3. 最大后缀
 * 4. 总和
 */
package LC901_1200;

public class LC992_SubarrayswithKDifferentIntegers {
    /**
     * Given an integer array nums and an integer k, return the number of good subarrays of nums.
     *
     * A good array is an array where the number of different integers in that array is exactly k.
     *
     * For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.
     * A subarray is a contiguous part of an array.
     *
     * Input: nums = [1,2,1,2,3], k = 2
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * 1 <= nums[i], k <= nums.length
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int subarraysWithKDistinct(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return 0;

        return helper(nums, k) - helper(nums, k - 1);
    }

    private int helper(int[] nums, int k) {
        int[] freq = new int[20005];
        int n = nums.length, count = 0, start = 0, res = 0;

        for (int i = 0; i < n; i++) {
            if (freq[nums[i]]++ == 0) count++;
            while (count > k) {
                if (--freq[nums[start++]] == 0) count--;
            }
            res += i - start + 1;
        }
        return res;
    }
}
/**
 * ref: LC340, 159
 * 此题的解法非常巧妙.它代表了一类思想:求关于K的解,是否可以化成求at most K的解(LC340)减去求at most K-1的解.
 * 我们需要写一个helper函数,计算数组A里面最多含有K个不同数字的subarray的个数.
 * 于是最终答案就是helper(K)-helper(K-1).
 * 对于这个helper函数,标准答案很显然就是用双指针和滑动窗口的方法.遍历右指针,考察对应的最大的滑窗是多少.
 * 于是在该右边界固定的条件下,满足题意的subarray的个数就是count+=右指针-左指针+1
 */
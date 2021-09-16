package LC601_900;

public class LC644_MaximumAverageSubarrayII {
    /**
     * You are given an integer array nums consisting of n elements, and an integer k.
     *
     * Find a contiguous subarray whose length is greater than or equal to k that has the maximum average value and
     * return this value. Any answer with a calculation error less than 10-5 will be accepted.
     *
     * Input: nums = [1,12,-5,-6,50,3], k = 4
     * Output: 12.75000
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= k <= n <= 10^4
     * -10^4 <= nums[i] <= 10^4
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlog(max - min)), space = O(1)
    public double findMaxAverage(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return 0.0;

        int n = nums.length;

        double left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            left = Math.min(left, nums[i]);
            right = Math.max(right, nums[i]);
        }

        while (left + 1e-5 < right) {
            double mid = left + (right - left) / 2;
            if (checkOK(nums, mid, k)) left = mid;
            else right = mid; // 不满足的话，也因为1e-5这样的calculation error存在而不用mid - 1！！！
        }
        return left;
    }

    private boolean checkOK(int[] nums, double mid, int k) {
        int n = nums.length;
        double curSum = 0, preSum = 0, minSum = 0;

        for (int i = 0; i < n; i++) {
            curSum += nums[i] - mid;
            if (i >= k - 1) {
                if (curSum - minSum >= 0) return true;
                preSum += nums[i - k + 1] - mid; // presum移动到当前curSum window的起点，而curSum在下一轮时会因i++到达下个起点k
                minSum = Math.min(minSum, preSum); // keep一个最小值，只要比k-size window大的区间前缀和都是满足条件的
            }
        }
        return false;
    }
}
/**
 * 二分搜索的上限就是nums里的最大值，下限就是nums里的最小值。
 * 我们每次通过low和high确定一个mid，查找nums里是否存在一个子数组是的其平均值大于mid。如果有，那么我们就调高下限至mid；
 * 如果没有，我们就降低上限至mid
 * 但是我们如何知道是否存在一个子数组，其平均值大于mid呢？
 * 如果我们将这个数组整体都减去mid，那么任务就等价于找到一个平均值大于零的子数组，也就是是说，找到一个和为正数的子数组。
 * 如何确认一个数组里有一段子数组的和为正数呢？
 * 我们只要遍历一遍前缀和，发现curSum[i]>preMin即说明存在。这里的preMin就是在i之前的最小的前缀和。
 * 可见只要o(n)的搜索就可以完成判断。总体的时间长度就是o(NlogK)，其中K是nums最大最小值之差。
 */

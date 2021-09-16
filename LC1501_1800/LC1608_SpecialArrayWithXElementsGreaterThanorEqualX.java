package LC1501_1800;

public class LC1608_SpecialArrayWithXElementsGreaterThanorEqualX {
    /**
     * You are given an array nums of non-negative integers. nums is considered special if there exists a number x such
     * that there are exactly x numbers in nums that are greater than or equal to x.
     *
     * Notice that x does not have to be an element in nums.
     *
     * Return x if the array is special, otherwise, return -1. It can be proven that if nums is special, the value for
     * x is unique.
     *
     * Input: nums = [3,5]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // S1: brute-force
    // time = O(n^2), space = O(1)
    public int specialArray(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        for (int i = 1; i <= n; i++) {
            int count = 0;
            for (int num : nums) {
                if (i <= num) count++;
            }
            if (count == i) return i;
        }
        return -1;
    }

    // S2: BS
    // time = O(nlogn), space = O(1)
    public int specialArray2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int left = 1, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (count(nums, mid) == mid) return mid;
            if (count(nums, mid) > mid) left = mid + 1;
            else right = mid - 1;
        }
        return count(nums, left) == left ? left : -1;
    }

    private int count(int[] nums, int target) {
        int count = 0;
        for (int num : nums) {
            if (num >= target) count++;
        }
        return count;
    }
}

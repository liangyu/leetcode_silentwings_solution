package LC901_1200;

public class LC1060_MissingElementinSortedArray {
    /**
     * Given an integer array nums which is sorted in ascending order and all of its elements are unique and given also
     * an integer k, return the kth missing number starting from the leftmost number of the array.
     *
     * Input: nums = [4,7,9,10], k = 1
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * 1 <= nums[i] <= 10^7
     * nums is sorted in ascending order, and all the elements are unique.
     * 1 <= k <= 10^8
     * @param nums
     * @param k
     * @return
     */
    // time = O(logn), space = O(1)
    public int missingElement(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int left = nums[0], right = nums[n - 1] + k;
        while (left < right) {
            int mid = right - (right - left) / 2;
            int M = mid - nums[0]; // nums[0] ~ mid之间的元素个数
            int T = lowerBound(nums, mid) + 1; // idx + 1 => 个数，0-index
            if (M - T < k) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid] < target) left = mid;
            else right = mid - 1;
        }
        return nums[left] < target ? left : left - 1;
    }
}

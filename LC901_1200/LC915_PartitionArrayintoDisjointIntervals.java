package LC901_1200;

public class LC915_PartitionArrayintoDisjointIntervals {
    /**
     * Given an array nums, partition it into two (contiguous) subarrays left and right so that:
     *
     * Every element in left is less than or equal to every element in right.
     * left and right are non-empty.
     * left has the smallest possible size.
     * Return the length of left after such a partitioning.  It is guaranteed that such a partitioning exists.
     *
     * Input: nums = [5,0,3,8,6]
     * Output: 3
     *
     * Note:
     *
     * 2 <= nums.length <= 30000
     * 0 <= nums[i] <= 10^6
     * It is guaranteed there is at least one way to partition nums as described.
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int partitionDisjoint(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] maxLeft = new int[n];
        int[] minRight = new int[n];

        int max = nums[0];
        for (int i = 0; i < n; i++) {
            max = Math.max(nums[i], max);
            maxLeft[i] = max;
        }

        int min = nums[n - 1];
        for (int i = n - 1; i >= 0; i--) {
            min = Math.min(nums[i], min);
            minRight[i] = min;
        }

        for (int i = 1; i < n; i++) {
            if (maxLeft[i - 1] <= minRight[i]) return i; // both left and right can't be empty, so i must start at 1.
        }
        return -1;
    }
}

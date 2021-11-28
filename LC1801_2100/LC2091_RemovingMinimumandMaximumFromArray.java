package LC1801_2100;

public class LC2091_RemovingMinimumandMaximumFromArray {
    /**
     * You are given a 0-indexed array of distinct integers nums.
     *
     * There is an element in nums that has the lowest value and an element that has the highest value. We call them
     * the minimum and maximum respectively. Your goal is to remove both these elements from the array.
     *
     * A deletion is defined as either removing an element from the front of the array or removing an element from the
     * back of the array.
     *
     * Return the minimum number of deletions it would take to remove both the minimum and maximum element from the
     * array.
     *
     * Input: nums = [2,10,7,5,4,1,8,6]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -105 <= nums[i] <= 10^5
     * The integers in nums are distinct.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        int maxIdx = -1, minIdx = -1;
        for (int i = 0; i < n; i++) {
            if (nums[i] > max) {
                max = nums[i];
                maxIdx = i;
            }
            if (nums[i] < min) {
                min = nums[i];
                minIdx = i;
            }
        }

        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);
        int range = right - left - 1;
        int res1 = left + 1 + n - right;
        int res2 = left + 1 + range + 1;
        int res3 = range + 1 + n - right;
        return Math.min(res1, Math.min(res2, res3));
    }
}

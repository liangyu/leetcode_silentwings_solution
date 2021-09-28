package LC1801_2100;

public class LC2012_SumofBeautyintheArray {
    /**
     * You are given a 0-indexed integer array nums. For each index i (1 <= i <= nums.length - 2) the beauty of nums[i]
     * equals:
     *
     * 2, if nums[j] < nums[i] < nums[k], for all 0 <= j < i and for all i < k <= nums.length - 1.
     * 1, if nums[i - 1] < nums[i] < nums[i + 1], and the previous condition is not satisfied.
     * 0, if none of the previous conditions holds.
     * Return the sum of beauty of all nums[i] where 1 <= i <= nums.length - 2.
     *
     * Input: nums = [1,2,3]
     * Output: 2
     *
     * Constraints:
     *
     * 3 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int sumOfBeauties(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] leftMax = new int[n];
        int[] rightMin = new int[n];

        leftMax[0] = nums[0];
        for (int i = 1; i < n; i++) leftMax[i] = Math.max(leftMax[i - 1], nums[i]);
        rightMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) rightMin[i] = Math.min(rightMin[i + 1], nums[i]);

        int sum = 0;
        for (int i = 1; i <= n - 2; i++) {
            if (nums[i] > leftMax[i - 1] && nums[i] < rightMin[i + 1]) sum += 2;
            else if (nums[i] > nums[i - 1] && nums[i] < nums[i + 1]) sum++;
        }
        return sum;
    }
}

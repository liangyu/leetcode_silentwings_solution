package LC2100_2400;

public class LC2148_CountElementsWithStrictlySmallerandGreaterElements {
    /**
     * Given an integer array nums, return the number of elements that have both a strictly smaller and a strictly
     * greater element appear in nums.
     *
     * Input: nums = [11,7,2,15]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * -10^5 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int countElements(int[] nums) {
        int n = nums.length, min = nums[0], max = nums[0];
        for (int x : nums) {
            min = Math.min(min, x);
            max = Math.max(max, x);
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] > min && nums[i] < max) count++;
        }
        return count;
    }
}

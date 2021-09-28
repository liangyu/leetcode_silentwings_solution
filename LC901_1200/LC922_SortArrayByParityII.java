package LC901_1200;

public class LC922_SortArrayByParityII {
    /**
     * Given an array of integers nums, half of the integers in nums are odd, and the other half are even.
     *
     * Sort the array so that whenever nums[i] is odd, i is odd, and whenever nums[i] is even, i is even.
     *
     * Return any answer array that satisfies this condition.
     *
     * Input: nums = [4,2,5,7]
     * Output: [4,5,2,7]
     *
     * Constraints:
     *
     * 2 <= nums.length <= 2 * 10^4
     * nums.length is even.
     * Half of the integers in nums are even.
     * 0 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] sortArrayByParityII(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length, j = 1;
        for (int i = 0; i < n; i += 2) {
            if (nums[i] % 2 == 1) {
                while (nums[j] % 2 == 1) j += 2;
                swap(nums, i, j);
            }
        }
        return nums;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

package LC1201_1500;

public class LC1283_FindtheSmallestDivisorGivenaThreshold {
    /**
     * Given an array of integers nums and an integer threshold, we will choose a positive integer divisor, divide
     * all the array by it, and sum the division's result. Find the smallest divisor such that the result mentioned
     * above is less than or equal to threshold.
     *
     * Each result of the division is rounded to the nearest integer greater than or equal to that element.
     * (For example: 7/3 = 3 and 10/2 = 5).
     *
     * It is guaranteed that there will be an answer.
     *
     * Input: nums = [1,2,5,9], threshold = 6
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * 1 <= nums[i] <= 10^6
     * nums.length <= threshold <= 10^6
     * @param nums
     * @param threshold
     * @return
     */
    public int smallestDivisor(int[] nums, int threshold) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int left = 1, right = Integer.MAX_VALUE;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (helper(nums, mid) > threshold) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private int helper(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            // sum += (int) Math.ceil(num * 1.0 / target);
            if (num % target == 0) sum += num / target;
            else sum += num / target + 1;
        }
        return sum;
    }
}

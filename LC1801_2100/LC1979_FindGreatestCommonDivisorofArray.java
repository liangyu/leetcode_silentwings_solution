package LC1801_2100;

public class LC1979_FindGreatestCommonDivisorofArray {
    /**
     * Given an integer array nums, return the greatest common divisor of the smallest number and largest number in nums.
     *
     * The greatest common divisor of two numbers is the largest positive integer that evenly divides both numbers.
     *
     * Input: nums = [2,5,6,9,10]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int findGCD(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int min = nums[0], max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        return gcd(min, max);
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}

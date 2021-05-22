package LC001_300;
import java.util.*;
public class LC268_MissingNumber {
    /**
     * Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that
     * is missing from the array.
     *
     * Follow up: Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
     *
     * Input: nums = [3,0,1]
     * Output: 2
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^4
     * 0 <= nums[i] <= n
     * All the numbers of nums are unique.
     * @param nums
     * @return
     */
    // S1: XOR
    // time = O(n), space = O(1)
    public int missingNumber(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return -1;

        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
            res ^= i ^ nums[i];
        }
        return res;
    }

    // S2: Math
    // time = O(n), space = O(1)
    public int missingNumber2(int[] nums) {
        int expectedSum = nums.length * (nums.length + 1) / 2;
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        return expectedSum - actualSum;
    }
}
/**
 * missing =4∧(0∧0)∧(1∧1)∧(2∧3)∧(3∧4)
 * =(4∧4)∧(0∧0)∧(1∧1)∧(3∧3)∧2
 * =0∧0∧0∧0∧2
 * =2
 */

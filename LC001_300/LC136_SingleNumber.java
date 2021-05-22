package LC001_300;
import java.util.*;
public class LC136_SingleNumber {
    /**
     * Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
     *
     * Follow up: Could you implement a solution with a linear runtime complexity and without using extra memory?
     *
     * Input: nums = [2,2,1]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 10^4
     * -3 * 10^4 <= nums[i] <= 3 * 10^4
     * Each element in the array appears twice except for one element which appears only once.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int singleNumber(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int res = 0;
        for (int n : nums) res ^= n;
        return res;
    }
}
/**
 * 0 ^ 1 = 1
 * 0 ^ 0 = 0
 * 1 ^ 1 = 0
 *
 * a ^ a = 0
 * 0 ^ a = a
 *
 * xor_sum = 0
 * for (int a : nums) xor_sum ^= a; 亦或和
 *
 * a ^ b = b ^ a
 * a ^ b ^ c ^ b = b ^ b ^ c ^ a = c ^ a
 */
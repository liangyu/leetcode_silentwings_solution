package LC1801_2100;
import java.util.*;
public class LC1814_CountNicePairsinanArray {
    /**
     * You are given an array nums that consists of non-negative integers. Let us define rev(x) as the reverse of the
     * non-negative integer x. For example, rev(123) = 321, and rev(120) = 21. A pair of indices (i, j) is nice if it
     * satisfies all of the following conditions:
     *
     * 0 <= i < j < nums.length
     * nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
     * Return the number of nice pairs of indices. Since that number can be too large, return it modulo 10^9 + 7.
     *
     * Input: nums = [13,10,35,24,76]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int countNicePairs(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        final int MOD = 1000000007;
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;

        for (int i = 0; i < nums.length; i++) {
            int diff = nums[i] - rev(nums[i]);
            map.put(diff, map.getOrDefault(diff, 0) + 1);
            res = (res + map.get(diff) - 1) % MOD;
        }
        return res;
    }

     private int rev(int num) {
        int res = 0;
        while (num > 0) {
            res = res * 10 + num % 10;
            num /= 10;
        }
        return res;
     }
}
/**
 * nums[i] + rev(nums[j]) == nums[j] + rev(nums[i]) =>
 * nums[i] - rev(nums[i] = nums[j] - rev(nums[j])
 */

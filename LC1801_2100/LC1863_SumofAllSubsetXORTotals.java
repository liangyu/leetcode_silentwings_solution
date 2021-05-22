package LC1801_2100;
import java.util.*;
public class LC1863_SumofAllSubsetXORTotals {
    /**
     * The XOR total of an array is defined as the bitwise XOR of all its elements, or 0 if the array is empty.
     *
     * For example, the XOR total of the array [2,5,6] is 2 XOR 5 XOR 6 = 1.
     * Given an array nums, return the sum of all XOR totals for every subset of nums.
     *
     * Note: Subsets with the same elements should be counted multiple times.
     *
     * An array a is a subset of an array b if a can be obtained from b by deleting some (possibly zero) elements of b.
     *
     * Input: nums = [1,3]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= nums.length <= 12
     * 1 <= nums[i] <= 20
     * @param nums
     * @return
     */
    // time = O(2^n), space = O(n);
    private int res = 0;
    public int subsetXORSum(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        dfs(nums, 0, 0);
        return res;
    }

    private void dfs(int[] nums, int idx, int xor) {
        // base case
        if (idx == nums.length) {
            res += xor;
            return;
        };

        // case 1: not xor
        dfs(nums, idx + 1, xor);
        // case 2: xor with nums[idx]
        dfs(nums, idx + 1, xor ^ nums[idx]);
    }
}

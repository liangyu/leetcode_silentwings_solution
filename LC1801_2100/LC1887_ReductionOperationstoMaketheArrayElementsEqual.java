package LC1801_2100;
import java.util.*;
public class LC1887_ReductionOperationstoMaketheArrayElementsEqual {
    /**
     * Given an integer array nums, your goal is to make all elements in nums equal. To complete one operation, follow
     * these steps:
     *
     * Find the largest value in nums. Let its index be i (0-indexed) and its value be largest. If there are multiple
     * elements with the largest value, pick the smallest i.
     * Find the next largest value in nums strictly smaller than largest. Let its value be nextLargest.
     * Reduce nums[i] to nextLargest.
     * Return the number of operations to make all elements in nums equal.
     *
     * Input: nums = [5,1,3]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * 1 <= nums[i] <= 5 * 10^4
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int reductionOperations(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        Arrays.sort(nums);
        int count = 0, res = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) count++;
            res += count;
        }
        return res;
    }
}

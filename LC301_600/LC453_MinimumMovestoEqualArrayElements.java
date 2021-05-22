package LC301_600;
import java.util.*;
public class LC453_MinimumMovestoEqualArrayElements {
    /**
     * Given an integer array nums of size n, return the minimum number of moves required to make all array elements equal.
     *
     * In one move, you can increment n - 1 elements of the array by 1.
     *
     * Input: nums = [1,2,3]
     * Output: 3
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * The answer is guaranteed to fit in a 32-bit integer.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int minMoves(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int res = 0, min = nums[0];
        for (int num : nums) min = Math.min(min, num);
        for (int num : nums) res += num - min;
        return res;
    }
}

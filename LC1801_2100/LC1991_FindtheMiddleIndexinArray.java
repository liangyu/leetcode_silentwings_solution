package LC1801_2100;
import java.util.*;
public class LC1991_FindtheMiddleIndexinArray {
    /**
     * Given a 0-indexed integer array nums, find the leftmost middleIndex (i.e., the smallest amongst all the
     * possible ones).
     *
     * A middleIndex is an index where nums[0] + nums[1] + ... + nums[middleIndex-1] == nums[middleIndex+1] +
     * nums[middleIndex+2] + ... + nums[nums.length-1].
     *
     * If middleIndex == 0, the left side sum is considered to be 0. Similarly, if middleIndex == nums.length - 1,
     * the right side sum is considered to be 0.
     *
     * Return the leftmost middleIndex that satisfies the condition, or -1 if there is no such index.
     *
     * Input: nums = [2,3,-1,8,4]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * -1000 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int findMiddleIndex(int[] nums) {
        int sum = 0;
        for (int x : nums) sum += x;

        int n = nums.length, cur = 0;
        for (int i = 0; i < n; i++) {
            if (cur == sum - cur - nums[i]) return i;
            cur += nums[i];
        }
        return -1;
    }
}

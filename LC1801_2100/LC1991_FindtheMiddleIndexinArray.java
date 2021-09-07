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
    public int findMiddleIndex(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(-1, 0);
        map.put(n, 0);

        for (int i = 0; i < n; i++) {
            sum += nums[i];
            map.put(i, sum);
        }

        for (int i = 0; i < n; i++) {
            int left = map.get(i) - nums[i];
            int right = map.get(n - 1) - map.get(i);
            if (left == right) return i;
        }
        return -1;
    }
}

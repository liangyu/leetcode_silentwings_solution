package LC901_1200;
import java.util.*;
public class LC930_BinarySubarraysWithSum {
    /**
     * Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.
     *
     * A subarray is a contiguous part of the array.
     *
     * Input: nums = [1,0,1,0,1], goal = 2
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 10^4
     * nums[i] is either 0 or 1.
     * 0 <= goal <= nums.length
     * @param nums
     * @param goal
     * @return
     */
    // time = O(n), space = O(n)
    public int numSubarraysWithSum(int[] nums, int goal) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, sum = 0, res = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        for (int j = 0; j < n; j++) {
            sum += nums[j];
            if (map.containsKey(sum - goal)) res += map.get(sum - goal);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
    }
}

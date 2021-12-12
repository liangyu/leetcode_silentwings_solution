package LC301_600;
import java.util.*;
public class LC594_LongestHarmoniousSubsequence {
    /**
     * We define a harmonious array as an array where the difference between its maximum value and its minimum value is
     * exactly 1.
     *
     * Given an integer array nums, return the length of its longest harmonious subsequence among all its possible
     * subsequences.
     *
     * A subsequence of array is a sequence that can be derived from the array by deleting some or no elements without
     * changing the order of the remaining elements.
     *
     * Input: nums = [1,3,2,2,5,2,3,7]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int findLHS(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : nums) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }

        int res = 0;
        for (int key : map.keySet()) {
            if (map.containsKey(key + 1)) {
                res = Math.max(res, map.get(key) + map.get(key + 1));
            }
        }
        return res;
    }
}

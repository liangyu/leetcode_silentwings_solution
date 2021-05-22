package LC001_300;
import java.util.*;
public class LC1_TwoSum {
    /**
     * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up
     * to target.
     *
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     *
     * You can return the answer in any order.
     *
     * Input: nums = [2,7,11,15], target = 9
     * Output: [0,1]
     *
     * Constraints:
     *
     * 2 <= nums.length <= 10^3
     * -10^9 <= nums[i] <= 10^9
     * -10^9 <= target <= 10^9
     * Only one valid answer exists.
     * @param nums
     * @param target
     * @return
     */
    // time = O(n), space = O(n)
    public int[] twoSum(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        HashMap<Integer, Integer> map = new HashMap<>();

        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (!map.containsKey(diff)) {
                map.put(nums[i], i);
            } else {
                res[0] = map.get(diff);
                res[1] = i;
                break;
            }
        }
        return res;
    }
}

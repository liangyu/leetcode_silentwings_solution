package LC301_600;
import java.util.*;
public class LC377_CombinationSumIV {
    /**
     * Given an array of distinct integers nums and a target integer target, return the number of possible combinations
     * that add up to target.
     *
     * The answer is guaranteed to fit in a 32-bit integer.
     *
     * Input: nums = [1,2,3], target = 4
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 1000
     * All the elements of nums are unique.
     * 1 <= target <= 1000
     *
     *
     * Follow up: What if negative numbers are allowed in the given array? How does it change the problem? What
     * limitation we need to add to the question to allow negative numbers?
     * @param nums
     * @param target
     * @return
     */
    // S1: DFS + pruning
    // time = O(n * t), space = O(t)   t: target value
    public int combinationSum4(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        return dfs(nums, target, map);
    }

    private int dfs(int[] nums, int target, HashMap<Integer, Integer> map) {
        // base case - success
        if (target == 0)  return 1;

        // base case - fail
        if (target < 0) return 0;

        if (map.containsKey(target)) return map.get(target);

        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res += dfs(nums, target - nums[i], map);
        }
        map.put(target, res);
        return res;
    }

    // S2: DP
    //time = O(n * t), space = O(t)  t: target value
    public int combinationSum42(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int i = 1; i < dp.length; i++) { // i: target range
            for (int n : nums) {
                if (i - n >= 0) dp[i] += dp[i - n]; // 注意这里是 i - n >= 0 而不仅仅是 > 0
            }
        }
        return dp[target];
    }
}

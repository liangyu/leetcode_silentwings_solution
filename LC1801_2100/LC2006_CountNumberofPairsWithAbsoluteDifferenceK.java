package LC1801_2100;
import java.util.*;
public class LC2006_CountNumberofPairsWithAbsoluteDifferenceK {
    /**
     * Given an integer array nums and an integer k, return the number of pairs (i, j) where i < j such that
     * |nums[i] - nums[j]| == k.
     *
     * The value of |x| is defined as:
     *
     * x if x >= 0.
     * -x if x < 0.
     *
     * Input: nums = [1,2,2,1], k = 1
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 100
     * 1 <= k <= 99
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int countKDifference(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);

        int count = 0;
        for (int num : nums) {
            if (map.containsKey(num + k)) count += map.get(num + k);
        }
        return count;
    }
}

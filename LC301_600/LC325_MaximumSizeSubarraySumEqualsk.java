package LC301_600;
import java.util.*;
public class LC325_MaximumSizeSubarraySumEqualsk {
    /**
     * Given an integer array nums and an integer k, return the maximum length of a subarray that sums to k. If there
     * isn't one, return 0 instead.
     *
     * Input: nums = [1,-1,5,-2,3], k = 3
     * Output: 4
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^5
     * -10^4 <= nums[i] <= 10^4
     * -10^9 <= k <= 10^9
     *
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int maxSubArrayLen(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int res = 0;

        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1]; // reuse nums to let it be the presum array
        }

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i] - k)) {
                res = Math.max(res, i - map.get(nums[i] - k));
            }
            if (!map.containsKey(nums[i])) map.put(nums[i], i); // only update the map with new value and its index
        }
        return res;
    }
}

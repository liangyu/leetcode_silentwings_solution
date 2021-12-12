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
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 注意对于求前缀和的问题，一定要在HashMap里补(0, -1)这一对来应付从头开始到现在的subarray之和的情况

        int n = nums.length, sum = 0, res = -1;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            if (!map.containsKey(sum)) map.put(sum, i); // 从左到右依次扫，只需要记录第一次出现该sum时的index

            int temp = sum - k;
            if (map.containsKey(temp)) {
                res = Math.max(res, i - map.get(temp));
            }
        }
        return res == -1 ? 0 : res;
    }
}

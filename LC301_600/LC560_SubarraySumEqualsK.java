package LC301_600;
import java.util.*;
public class LC560_SubarraySumEqualsK {
    /**
     * Given an array of integers nums and an integer k, return the total number of continuous subarrays
     * whose sum equals to k.
     *
     * Input: nums = [1,1,1], k = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * -1000 <= nums[i] <= 1000
     * -10^7 <= k <= 10^7
     *
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int subarraySum(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1); // 注意，sum - k = 0时也是一个有效答案，表明从nums的头部开始到当前为止的subarray就是1个有效解！！！

        int sum = 0, res = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) { // 通过prefix sum来做，首尾相减，夹在中间的subarray就是有效解。
                res += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return res;
    }
}

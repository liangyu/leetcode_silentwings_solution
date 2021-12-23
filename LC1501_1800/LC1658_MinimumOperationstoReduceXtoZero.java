package LC1501_1800;
import java.util.*;
public class LC1658_MinimumOperationstoReduceXtoZero {
    /**
     * You are given an integer array nums and an integer x. In one operation, you can either remove the leftmost or
     * the rightmost element from the array nums and subtract its value from x. Note that this modifies the array for
     * future operations.
     *
     * Return the minimum number of operations to reduce x to exactly 0 if it's possible, otherwise, return -1.
     *
     * Input: nums = [1,1,4,2,3], x = 5
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^4
     * 1 <= x <= 10^9
     *
     * @param nums
     * @param x
     * @return
     */
    // time = O(n), space = O(n)
    public int minOperations(int[] nums, int x) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // init，前缀和为0的index是-1，对应于下面的a + 1,即元素个数 = idx + 1
        int n = nums.length, res = Integer.MAX_VALUE;
        int presum = 0, sufsum = 0;

        for (int i = 0; i < n; i++) {
            presum += nums[i];
            if (!map.containsKey(presum)) map.put(presum, i);
        }

        // 注意这里是res = map.get(x) + 1，不能直接return,因为无法保证光靠presum获得的就是最少的operations
        if (map.containsKey(x)) res = map.get(x) + 1; // 注意这里可以没有sufsum,直接光靠presum就解决也是一种可能。

        for (int b = n - 1; b >= 0; b--) {
            sufsum += nums[b];
            int pre = x - sufsum;
            if (map.containsKey(pre)) {
                int a = map.get(pre);
                if (a < b) res = Math.min(res, a + 1 + n - b); // 对应着pre左边有多少个元素,不能与b有overlap
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
/**
 * a  b
 * presum[a] + sufsum[b] = x
 * 最简单粗暴就是O(n^2)
 * 加入确定presum[a], 那么sufsum[b] = x - presum[a]
 * => 最好O(1)实现 -> hash 从后往前撸一遍
 * sufsum[i] -> i
 * 最后找一个a + (n - b)最小的
 * 当有2个变量的时候，一般先扫第一个，然后看第二个变量能不能高效的求出来
 * 对于一个前缀和后缀和，记录一个hash的位置，在LC有很多的题目: hash + prefix
 * 扫后缀和，对应不同index的时候，尽量取靠右的，越靠右越好
 */
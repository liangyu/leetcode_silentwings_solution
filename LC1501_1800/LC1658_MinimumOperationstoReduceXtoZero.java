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
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 104
     * 1 <= x <= 109
     *
     * @param nums
     * @param x
     * @return
     */
    public int minOperations(int[] nums, int x) {
        // corner case
        if (nums == null || nums.length == 0) return 0;
        if (nums[0] > x || nums[nums.length - 1] > x) return -1;

        HashMap<Integer, Integer> map1 = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();
        PriorityQueue<Integer> res = new PriorityQueue<>();

        int sum = 0, len = nums.length;
        for (int i = 0; i < len; i++) {
            sum += nums[i];
            map1.put(i + 1, sum);
            if (sum == x) res.offer(i + 1);
        }

        sum = 0;
        for (int i = len - 1; i >= 0; i--) {
            sum += nums[i];
            if (sum == x) res.offer(len - i);
            if (!map2.containsKey(sum)) map2.put(sum, len - i);
        }

        for (int i = 1; i < map1.size(); i++) {
            int diff = x - map1.get(i);
            if (diff >= 0 && map2.containsKey(diff)) {
                if (i + map2.get(diff) <= len) res.offer(i + map2.get(diff));
            }
        }
        return res.isEmpty() ? -1 : res.peek();
    }
}

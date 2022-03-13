package LC1501_1800;
import java.util.*;
public class LC1675_MinimizeDeviationinArray {
    /**
     * You are given an array nums of n positive integers.
     *
     * You can perform two types of operations on any element of the array any number of times:
     *
     * If the element is even, divide it by 2.
     * For example, if the array is [1,2,3,4], then you can do this operation on the last element, and the array will
     * be [1,2,3,2].
     * If the element is odd, multiply it by 2.
     * For example, if the array is [1,2,3,4], then you can do this operation on the first element, and the array will
     * be [2,2,3,4].
     * The deviation of the array is the maximum difference between any two elements in the array.
     *
     * Return the minimum deviation the array can have after performing some number of operations.
     *
     * Input: nums = [1,2,3,4]
     * Output: 1
     *
     * Constraints:
     *
     * n == nums.length
     * 2 <= n <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int minimumDeviation(int[] nums) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int x : nums) { // O(nlogn)
            if (x % 2 == 0) map.put(x, map.getOrDefault(x, 0) + 1);
            else map.put(x * 2, map.getOrDefault(x * 2, 0) + 1);
        }

        int res = Integer.MAX_VALUE;
        while (true) {
            res = Math.min(res, map.lastKey() - map.firstKey());
            int m = map.lastKey();
            map.put(m, map.get(m) - 1);
            if (map.get(m) == 0) map.remove(m);
            if (m % 2 == 0) map.put(m / 2, map.getOrDefault(m / 2, 0) + 1);
            else break;
        }
        return res;
    }
}
/**
 * ref: LC632
 * [1,2]
 * [2,1] -> [1,2]
 * [3,6]
 * [4,2,1] -> [1,2,4]
 * k个数组，找diff最小
 * 2,2,[6],4
 * 一旦pick了6 => 2, 2, 4也就确定下来了 => dev = 6 - 2 = 4 -> sorted set
 * 2,2,3,[4] 按照从大到小替补
 * 2,2,[3],2 => 3 - 2
 * 遍历range的最大值
 */

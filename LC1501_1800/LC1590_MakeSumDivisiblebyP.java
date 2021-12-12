package LC1501_1800;
import java.util.*;
public class LC1590_MakeSumDivisiblebyP {
    /**
     * Given an array of positive integers nums, remove the smallest subarray (possibly empty) such that the sum of the
     * remaining elements is divisible by p. It is not allowed to remove the whole array.
     *
     * Return the length of the smallest subarray that you need to remove, or -1 if it's impossible.
     *
     * A subarray is defined as a contiguous block of elements in the array.
     *
     * Input: nums = [3,1,4,2], p = 6
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= p <= 10^9
     * @param nums
     * @param p
     * @return
     */
    // time = O(n), space = O(n)
    public int minSubarray(int[] nums, int p) {
        int n = nums.length;
        long totalSum = 0;
        HashMap<Long, Integer> map = new HashMap<>();
        map.put(0L, -1); // 用前缀和之前，别忘了设置一个初始值(0, -1)
        for (int a : nums) totalSum += a;
        long r0 = totalSum % p;
        if (r0 == 0) return 0; // 如果一开始整个数组和 % p == 0 -> 1个都不用删除，直接return 0

        long presum = 0, res = n;
        for (int j = 0; j < n; j++) {
            presum += nums[j];
            long r = presum % p;
            long dr = (r - r0 + p) % p;
            if (map.containsKey(dr)) {
                int i = map.get(dr) + 1; // i - 1 = map.get(r - r0)
                res = Math.min(res, j - i + 1);
            }
            map.put(presum % p, j);
        }
        return res == n ? -1 : (int) res;
    }
}
/**
 * x x x [x x x x] x x x
 * totalSum % p = r0
 * 本质上就是把r0求出来，找一个最小的窗口，被p除得到的余数是r0
 * 利用前缀和+hash+remainder as key
 * subarray只要求2个端点
 * x x x [i x x j] x x x
 * 固定j，来找到i
 * i越靠近j越好
 * 怎么去搜索呢？
 * 需要精准定位i
 * 一种套路方法：把区间和转化成前缀和的差值
 * sum[i:j] = presum[j] - presum[i - 1]
 *  r0           r          r - r0
 *  3            1           -2 + 5 -> 加上一个余数周期， 余3 + 余3 = 余6 -> 余1
 * map[r'] = i
 * 套路：prefix + hash + mod
 * 用余数来作为key
 * Hash[prefix[index] % mod] = index
 */
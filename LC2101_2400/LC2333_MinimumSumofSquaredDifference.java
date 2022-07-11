package LC2101_2400;
import java.util.*;
public class LC2333_MinimumSumofSquaredDifference {
    /**
     * You are given two positive 0-indexed integer arrays nums1 and nums2, both of length n.
     *
     * The sum of squared difference of arrays nums1 and nums2 is defined as the sum of (nums1[i] - nums2[i])2 for each
     * 0 <= i < n.
     *
     * You are also given two positive integers k1 and k2. You can modify any of the elements of nums1 by +1 or -1 at
     * most k1 times. Similarly, you can modify any of the elements of nums2 by +1 or -1 at most k2 times.
     *
     * Return the minimum sum of squared difference after modifying array nums1 at most k1 times and modifying array
     * nums2 at most k2 times.
     *
     * Note: You are allowed to modify the array elements to become negative integers.
     *
     * Input: nums1 = [1,2,3,4], nums2 = [2,10,20,19], k1 = 0, k2 = 0
     * Output: 579
     *
     * Input: nums1 = [1,4,10,12], nums2 = [5,8,6,9], k1 = 1, k2 = 1
     * Output: 43
     *
     * Constraints:
     *
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * 0 <= nums1[i], nums2[i] <= 10^5
     * 0 <= k1, k2 <= 10^9
     * @param nums1
     * @param nums2
     * @param k1
     * @param k2
     * @return
     */
    // S1: Binary Search
    // time = O(nlogn), space = O(1)
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length, m = k1 + k2;
        for (int i = 0; i < n; i++) nums1[i] = Math.abs(nums1[i] - nums2[i]);

        int l = 0, r = (int) 1e5;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (helper(nums1, mid) > m) l = mid + 1;
            else r = mid;
        }

        long sum = 0;
        for (int x : nums1) {
            if (x >= l) sum += x - l;
        }
        m -= sum;

        long res = 0;
        for (int x : nums1) {
            if (x >= l) {
                if (l > 0 && m > 0) {
                    res += (long)(l - 1) * (l - 1);
                    m--;
                } else res += (long) l * l;
            } else res += (long) x * x;
        }
        return res;
    }

    private long helper(int[] nums, int t) {
        long sum = 0;
        for (int x : nums) {
            if (x >= t) sum += x - t;
        }
        return sum;
    }

    // S2: TreeMap
    // time = O(nlogn), space = O(n)
    public long minSumSquareDiff2(int[] nums1, int[] nums2, int k1, int k2) {
        TreeMap<Integer, Long> map = new TreeMap<>();
        int n = nums1.length;
        for (int i = 0; i < n; i++) {
            int diff = Math.abs(nums1[i] - nums2[i]);
            map.put(diff, map.getOrDefault(diff, 0L) + 1);
        }

        long total = k1 + k2;
        while (total > 0 && map.size() > 0) {
            int last = map.lastKey();
            long count = map.get(last);
            map.remove(last);
            long amount = count * (last - (map.size() > 0 ? map.lastKey() : 0));
            if (amount <= total) {
                if (map.size() > 0) map.put(map.lastKey(), map.get(map.lastKey()) + count);
                total -= amount;
            } else {
                long x = total / count, r = total % count;
                map.put((int)(last - x), count - r);
                map.put((int)(last - x - 1), map.getOrDefault((int)(last - x - 1), 0L) + r);
                total = 0;
            }
        }

        long res = 0;
        for (int x : map.keySet()) {
            res += map.get(x) * ((long) x * x);
        }
        return res;
    }

    // S3: presum
    // time = O(nlogn), space = O(n)
    public long minSumSquareDiff3(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        List<Long> nums = new ArrayList<>();
        for (int i = 0; i < n; i++) nums.add((long) Math.abs(nums1[i] - nums2[i]));
        Collections.sort(nums, (o1, o2) -> Long.compare(o2, o1));

        long[] presum = new long[n];
        for (int i = 0; i < n; i++) presum[i] = (i == 0 ? 0 : presum[i - 1]) + nums.get(i);

        int i = 0, k = k1 + k2;
        if (presum[n - 1] <= k) return 0;
        while (i < n && presum[i] - (i + 1) * nums.get(i) <= k) i++;
        i--;

        long diff = k - (presum[i] - (i + 1) * nums.get(i));
        long extra = diff / (i + 1);
        long r = diff % (i + 1);

        long res = 0;
        res += (nums.get(i) - extra) * (nums.get(i) - extra) * (i + 1 - r);
        res += (nums.get(i) - extra - 1) * (nums.get(i) - extra - 1) * r;
        for (int j = i + 1; j < n; j++) res += nums.get(j) * nums.get(j);
        return res;
    }
}
/**
 * nums[i] = Math.abs(nums1[i] - nums2[i])
 * 并不需要区分是k1还是k2
 * k operations
 * min: sum{nums[i]^2}
 * ref: LC2233
 * 给nums排序
 *     10 8 6 3 1 0
 * =>   8 8 6 3 1 0 => 降到跟第三个元素一样大
 * int i = 0;
 * {3 3 3 3} 1 0
 *  0     i
 * while (presum[i] - nums[i] * (i+1) <= k) {
 *     i++;
 * }
 * i--;
 * k次操作还有剩余，这个时候只能作微调
 * int diff = k - (presum[i] - nums[i] * (i + 1));
 * 尽量均匀的砍
 * extra = diff / (i + 1)
 * r = diff % (i + 1)
 * 3部分：
 * nums[i] - extra => i + 1 - r
 * nums[i] - extra - 1  => r
 * nums[j] => i + 1 ~ n - 1
 *
 * 多路归并
 * 总共的和 - 2*ai - 1
 * 每个数看成一个序列
 * 1次：2*a0 - 1
 * 2次：2*a0 - 3
 * n个序列里最多选m个数，使得选择的所有数最大
 * 将n个有序序列归并成一个有序序列
 * 贪心的模型就是多路归并
 * 二分
 */
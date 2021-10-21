package LC1801_2100;
import java.util.*;
public class LC2035_PartitionArrayIntoTwoArraystoMinimizeSumDifference {
    /**
     * You are given an integer array nums of 2 * n integers. You need to partition nums into two arrays of length n
     * to minimize the absolute difference of the sums of the arrays. To partition nums, put each element of nums into
     * one of the two arrays.
     *
     * Return the minimum possible absolute difference.
     *
     * Input: nums = [3,9,7,3]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 15
     * nums.length == 2 * n
     * -10^7 <= nums[i] <= 10^7
     * @param nums
     * @return
     */
    // time = O(2^n * n), space = O(n)
    public int minimumDifference(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length / 2;
        List<Integer> nums1 = new ArrayList<>();
        List<Integer> nums2 = new ArrayList<>();
        for (int i = 0; i < n; i++) nums1.add(nums[i]);
        for (int i = n; i < 2 * n; i++) nums2.add(nums[i]);

        HashMap<Integer, List<Long>> map = helper(nums2);
        long sum = 0;
        for (int num : nums) sum += num;

        long res = Long.MAX_VALUE;
        for (int state = 0; state < (1 << n); state++) {
            int i = Integer.bitCount(state); // 对于这个combination有多少个元素
            long x = 0;
            for (int k = 0; k < n; k++) {
                if (((state >> k) & 1) == 1) x += (long) nums1.get(k);
            }
            int j = n - i;
            int idx = upperBound(map.get(j), sum / 2 - x);
            if (idx != map.get(j).size()) {
                long y = map.get(j).get(idx);
                res = Math.min(res, Math.abs(sum - 2 * (x + y)));
            }
            if (idx != 0) {
                long y = map.get(j).get(idx - 1);
                res = Math.min(res, Math.abs(sum - 2 * (x + y)));
            }
        }
        return (int) res;
    }

    private HashMap<Integer, List<Long>> helper(List<Integer> nums) {
        int n = nums.size();
        HashMap<Integer, List<Long>> map = new HashMap<>();
        for (int state = 0; state < (1 << n); state++) {
            int j = Integer.bitCount(state); // 对于这个combination有多少个元素
            long y = 0;
            for (int k = 0; k < n; k++) {
                if (((state >> k) & 1) == 1) y += (long) nums.get(k);
            }
            map.putIfAbsent(j, new ArrayList<>());
            map.get(j).add(y);
        }
        for (List<Long> list : map.values()) {
            Collections.sort(list);
        }
        return map;
    }

    private int upperBound(List<Long> nums, long t) {
        int left = 0, right = nums.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums.get(mid) < t) left = mid + 1;
            else right = mid;
        }
        return nums.get(left) >= t ? left : left + 1;
    }

    // S1.2: TreeMap
    // time = O(2^n * n), space = O(n)
    public int minimumDifference2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        // step 1: calculate sum
        int sum = 0;
        for (int num : nums) sum += num;

        // step 2: split into two halves
        int n = nums.length / 2;
        List<Integer> nums1 = new ArrayList<>();
        List<Integer> nums2 = new ArrayList<>();
        for (int i = 0; i < n; i++) nums1.add(nums[i]);
        for (int i = n; i < 2 * n; i++) nums2.add(nums[i]);

        // step 3:  pre-processing second half
        HashMap<Integer, TreeSet<Integer>> map = helper2(nums2);

        // step 4: calculate sum of first half and try fo find y in second half
        int res = Integer.MAX_VALUE;
        for (int state = 0; state < (1 << n); state++) {
            int i = Integer.bitCount(state);
            int x = 0;
            for (int k = 0; k < n; k++) {
                if (((state >> k) & 1) == 1) x += nums1.get(k);
            }
            int j = n - i;

            Integer y = map.get(j).ceiling(sum / 2 - x);
            if (y != null) res = Math.min(res, Math.abs(sum - 2 * (x + y)));

            y = map.get(j).lower(sum / 2 - x);
            if (y != null) res = Math.min(res, Math.abs(sum - 2 * (x + y)));
        }
        return res;
    }

    private HashMap<Integer, TreeSet<Integer>> helper2(List<Integer> nums) {
        int n = nums.size();
        HashMap<Integer, TreeSet<Integer>> map = new HashMap<>();

        for (int state = 0; state < (1 << n); state++) {
            int j = Integer.bitCount(state);
            int y = 0;
            for (int k = 0; k < n; k++) {
                if (((state >> k) & 1) == 1) y += nums.get(k);
            }
            map.putIfAbsent(j, new TreeSet<>());
            map.get(j).add(y);
        }
        return map;
    }
}
/**
 * ref: LC1755
 * x -> sum - x
 * x -> sum / 2
 * 关键点：1 <= n <= 15
 * 没有什么巧解，指数级 => 暴力搜索
 * 30个里面挑15个，是天文数字
 * ref: LC1755
 * 2^15可以，但这里是30，能不能拆成15来做
 * 双向奔赴 => 30 = 15 + 15
 * 把数组先砍一半，把前15个里面拿出来，2^15
 * First Half: 2^15 => i, sum1
 * Second Half: j = n - i
 * x + y = sum - (x + y) => 2x + 2y = sum => y = sum/2 - x
 * 提前把第二部分预先处理下，穷举所有combination
 * Map[j] = {y1, y2, ...}
 * 提前排好序 + 二分查找 => 尽量接近
 * res = min(res, abs(sum-2(x+y)));
 * time = O(2^n * n) 遍历一个combination，要计算它的和，是个O(n)操作，预处理没有问题
 * 遍历 = O(2^n),在map[j]里找一个理想的y，二分查找 => O(2^n * log(2^n)) => (2^n * n)
 * 掌握这种思想：
 * 一劈二，先在15个数里找i个，再在另外15个里找j个，前面取得的数就会给后半段施加很多限制，找起来效率会更高
 * 很神奇的把原来30个里面找15个这么一个庞大的搜索化简成可以接受的一个时间复杂度，要好好体会！！！
 * 如果n比较下，但又不是那么小，2^n又不在6个0以内，就可以一拆二，利用双向奔赴的做法解出来！
 */
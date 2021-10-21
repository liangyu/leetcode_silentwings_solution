package LC1501_1800;
import java.util.*;
public class LC1755_ClosestSubsequenceSum {
    /**
     * You are given an integer array nums and an integer goal.
     *
     * You want to choose a subsequence of nums such that the sum of its elements is the closest possible to goal.
     * That is, if the sum of the subsequence's elements is sum, then you want to minimize the absolute difference
     * abs(sum - goal).
     *
     * Return the minimum possible value of abs(sum - goal).
     *
     * Note that a subsequence of an array is an array formed by removing some elements (possibly all or none) of the
     * original array.
     *
     * Input: nums = [5,-7,3,5], goal = 6
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= nums.length <= 40
     * 10^7 <= nums[i] <= 10^7
     * -10^9 <= goal <= 10^9
     * @param nums
     * @param goal
     * @return
     */
    // S1: HashMap + TreeSet
    // time = O(2^(n/2) + nlogn), space = O(n)
    public int minAbsDifference(int[] nums, int goal) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        HashSet<Integer> first = new HashSet<>();
        HashSet<Integer> second = new HashSet<>();

        helper(nums, 0, n / 2, 0, first);
        helper(nums, n / 2, n, 0, second);

        TreeSet<Integer> treeSet = new TreeSet<>(first);

        int res = Integer.MAX_VALUE;
        for (int t : second) {
            int diff = goal - t;
            if (treeSet.contains(diff)) return 0;
            Integer lk = treeSet.lower(diff);
            Integer hk = treeSet.higher(diff);
            if (lk != null) res = Math.min(res, Math.abs(t + lk - goal));
            if (hk != null) res = Math.min(res, Math.abs(t + hk - goal));
        }
        return res;
    }

    private void helper(int[] nums, int cur, int end, int sum, HashSet<Integer> set) {
        if (cur == end) {
            set.add(sum);
            return;
        }

        helper(nums, cur + 1, end, sum + nums[cur], set); // take
        helper(nums, cur + 1, end, sum, set); // not take
    }

    // S2: Bit Mask
    // time = (2^(n/2) * n), space = O(n)
    private int res = Integer.MAX_VALUE;
    public int minAbsDifference2(int[] nums, int goal) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        List<Integer> nums1 = new ArrayList<>();
        List<Integer> nums2 = new ArrayList<>();
        for (int i = 0; i < n / 2; i++) nums1.add(nums[i]);
        for (int i = n / 2; i < n; i++) nums2.add(nums[i]);

        List<Integer> a = getSubsetSums(nums1);
        List<Integer> b = getSubsetSums(nums2);

//        bs(a, b, goal);
        helper(a, b, goal);
        return res;
    }

    private List<Integer> getSubsetSums(List<Integer> nums) {
        List<Integer> sums = new ArrayList<>();
        sums.add(0);
        for (int x : nums) {
            int i = 0, j = 0, n = sums.size();
            List<Integer> temp = new ArrayList<>();
            while (i < n && j < n) {
                if (sums.get(i) < sums.get(j) + x) temp.add(sums.get(i++));
                else temp.add(sums.get(j++) + x);
            }
            while (i < n) temp.add(sums.get(i++));
            while (j < n) temp.add(sums.get(j++) + x);
            sums = temp;
        }
        return sums;
    }

    private void helper(List<Integer> a, List<Integer> b, int goal) {
        int j = b.size() - 1;
        for (int i = 0; i < a.size(); i++) {
            while (j >= 0 && a.get(i) + b.get(j) >= goal) {
                res = Math.min(res, Math.abs(goal - a.get(i) - b.get(j)));
                j--;
            }
            if (j >= 0) {
                res = Math.min(res, Math.abs(goal - a.get(i) - b.get(j)));
            }
        }
    }

//    private List<Integer> getSubsetSums(List<Integer> nums) {
//        // use bit mask
//        int m = nums.size();
//        List<Integer> sums = new ArrayList<>();
//        for (int state = 0; state < (1 << m); state++) {
//            int sum = 0;
//            for (int i = 0; i < m; i++) {
//                if (((state >> i) & 1) == 1) sum += nums.get(i);
//            }
//            sums.add(sum);
//        }
//        Collections.sort(sums);
//        return sums;
//    }
//
//    private void bs(List<Integer> a, List<Integer> b, int goal) {
//        for (int x : a) {
//            int idx = upperBound(b, goal - x);
//            if (idx != b.size()) {
//                res = Math.min(res, Math.abs(b.get(idx) - (goal - x)));
//            }
//            if (idx != 0) {
//                res = Math.min(res, Math.abs(b.get(idx - 1) - (goal - x)));
//            }
//        }
//    }
//
//    private int upperBound(List<Integer> nums, int t) {
//        int left = 0, right = nums.size() - 1;
//        while (left < right) {
//            int mid = left + (right - left) / 2;
//            if (nums.get(mid) < t) left = mid + 1;
//            else right = mid;
//        }
//        return nums.get(left) >= t ? left : left + 1;
//    }
}
/**
 * pos = -insertionPoint - 1 => insertionPoint = -pos - 1 = -1 * (pos + 1)
 * nums.size() <= 20
 * check every subset sum: 2^20, 可以穷举所有的可能性
 * total |nums| sum <= 1000   动态规划 01背包
 * sum的可能性如果是一个非常有限的范围，穷举的就是这个sum，每查看一个物品来重新update这个dp[sum]
 * for (int i = 0; i < nums.size(); i++)
 *      dp[sum] = dp_old[sum]
 *      for sum in range(0, 1000):
 *          dp[sum] = dp_old[sum - nums[i]]
 * return xxx
 * 两层循环
 * 本题是40 * 10^7 = 4 * 10^8  => NP hard 问题
 * 本质还是暴力
 * 突破口在这个条件：1 <= nums.size() <= 40
 * 正好是20的2倍，如果是20是可以穷举的 => 把数组拆成2部分，每个部分20
 * subset sum of nums1 -> 10^6   sorted
 * subset sum of nums2 -> 10^6   sorted
 * 2 sum closest => 前提条件得是有序！！！
 * for x in (subset sums of nums1):
 *      find the closest element goal - x in (subset sums of nums2)  => 用2分即可。
 *
 * 2^(n/2) * log(2^(n/2))  => mlogm  m = 2^(n/2)
 * [x x x x x] i
 * sums = {a1, a2, ... ak} 假设已经有序
 * sums' = {a1 + nums[i], a2 + nums[i], ... ak + nums[i]} 这些subset都肯定是不一样的
 * 归并排序
 * sums = {b1,b2,...b2k}  => time = O(2^(n/2))
 */
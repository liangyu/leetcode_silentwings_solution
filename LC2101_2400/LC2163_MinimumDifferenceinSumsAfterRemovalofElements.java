package LC2101_2400;
import java.util.*;
public class LC2163_MinimumDifferenceinSumsAfterRemovalofElements {
    /**
     * You are given a 0-indexed integer array nums consisting of 3 * n elements.
     *
     * You are allowed to remove any subsequence of elements of size exactly n from nums. The remaining 2 * n elements
     * will be divided into two equal parts:
     *
     * The first n elements belonging to the first part and their sum is sumfirst.
     * The next n elements belonging to the second part and their sum is sumsecond.
     * The difference in sums of the two parts is denoted as sumfirst - sumsecond.
     *
     * For example, if sumfirst = 3 and sumsecond = 2, their difference is 1.
     * Similarly, if sumfirst = 2 and sumsecond = 3, their difference is -1.
     * Return the minimum difference possible between the sums of the two parts after the removal of n elements.
     *
     * Input: nums = [3,1,2]
     * Output: -1
     *
     * Input: nums = [7,9,5,8,1,3]
     * Output: 1
     *
     * Constraints:
     *
     * nums.length == 3 * n
     * 1 <= n <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(n)
    public long minimumDifference(int[] nums) {
        int n = nums.length / 3;

        long[] leftMin = new long[3 * n]; // leftMin[k]: the min sum of N elements from nums[0:k]
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        long sum = 0;
        for (int i = 0; i < 3 * n; i++) {
            pq.offer(nums[i]);
            sum += nums[i];
            if (pq.size() > n) sum -= pq.poll();
            leftMin[i] = sum;
        }

        long[] rightMax = new long[3 * n];
        pq = new PriorityQueue<>();
        sum = 0;
        for (int i = 3 * n - 1; i >= 0; i--) {
            pq.offer(nums[i]);
            sum += nums[i];
            if (pq.size() > n) sum -= pq.poll();
            rightMax[i] = sum;
        }

        long res = Long.MAX_VALUE;
        for (int i = n - 1; i < 2 * n; i++) {
            res = Math.min(res, leftMin[i] - rightMax[i + 1]);
        }
        return res;
    }
}
/**
 * [3 pass]
 * 最关心的是剩下这些元素里，分界点在哪里
 * 眼光放在存留的2n个里，找个分界线
 * 遍历分界点
 * x x x x x x | x x x x x
 * nlogn          nlogn
 * 稍微滑过一些，从左往右滑，非常粗暴的维护一个pq，永远放的就是n个最小的元素
 * for k = 0 : n - 1
 * 最佳分界点 min{leftMin[k] - rightMax[k + 1]}
 * 找的就是分界点，以分界点为中心！
 */

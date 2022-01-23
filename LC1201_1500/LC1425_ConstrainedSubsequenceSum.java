package LC1201_1500;
import java.util.*;
public class LC1425_ConstrainedSubsequenceSum {
    /**
     * Given an integer array nums and an integer k, return the maximum sum of a non-empty subsequence of that array
     * such that for every two consecutive integers in the subsequence, nums[i] and nums[j], where i < j, the condition
     * j - i <= k is satisfied.
     *
     * A subsequence of an array is obtained by deleting some number of elements (can be zero) from the array, leaving
     * the remaining elements in their original order.
     *
     * Input: nums = [10,2,-10,5,20], k = 2
     * Output: 37
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * @param nums
     * @param k
     * @return
     */
    // S1: TreeSet
    // time = O(nlogk), space = O(n + k)
    public int constrainedSubsetSum(int[] nums, int k) {
        TreeSet<int[]> set = new TreeSet<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
        int n = nums.length;
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            if (set.size() > k && i - k - 1 >= 0) {
                set.remove(new int[]{dp[i - k - 1], i - k - 1});
            }
            dp[i] = nums[i];
            if (set.size() > 0) dp[i] = Math.max(dp[i], nums[i] + set.last()[0]);
            set.add(new int[]{dp[i], i});
        }

        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // S2: Deque
    // time = O(n), space = O(n)
    public int constrainedSubsetSum2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] dp = new int[n];
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && i - deque.peekFirst() > k) deque.pollFirst();
            dp[i] = nums[i];
            if (!deque.isEmpty()) dp[i] = Math.max(dp[i], dp[deque.peekFirst()] + nums[i]);
            while (!deque.isEmpty() && dp[deque.peekLast()] <= dp[i]) deque.pollLast();
            deque.offer(i);
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) res = Math.max(res, dp[i]);
        return res;
    }
}
/**
 * dp[i]: the maximum sum of a non-empty subsequence of that array ending with nums[i]
 * x x [x j x x x] i
 * dp[i] = max{nums[i], dp[j] + nums[i]} for j = i-1, i-2,...i-k
 * O(nk)
 * => 在一个sliding window里挑一个最大值 => ref: LC239
 * 用有序容器 => O(nlogk)
 *
 * [x x x x -1] 5 维护一个单调递减的队列，把所有比5小的都扔掉 -> 队列是单调递减的 -> 队首就是最大值，看是否过期
 * 根据index之差来弹掉那些过期的值
 * [7 6 5] -> max = 7
 * 1. maintain a monotonic decreasing queue by poping out back elements smaller than nums[i]
 * 2. check if head element is out of date ( in terms of sliding window)
 * 3. The current head element of deque is the maximum of the sliding window
 */
package LC1501_1800;
import java.util.*;
public class LC1696_JumpGameVI {
    /**
     * You are given a 0-indexed integer array nums and an integer k.
     *
     * You are initially standing at index 0. In one move, you can jump at most k steps forward without going outside
     * the boundaries of the array. That is, you can jump from index i to any index in the range
     * [i + 1, min(n - 1, i + k)] inclusive.
     *
     * You want to reach the last index of the array (index n - 1). Your score is the sum of all nums[j] for each
     * index j you visited in the array.
     *
     * Return the maximum score you can get.
     *
     * Input: nums = [1,-1,-2,4,-7,3], k = 2
     * Output: 7
     *
     * Constraints:
     *
     *  1 <= nums.length, k <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int maxResult(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        Deque<int[]> deque = new LinkedList<>();
        deque.offerLast(new int[]{nums[0], 0});

        for (int i = 1; i < n; i++) {
            if (!deque.isEmpty() && i - deque.peekFirst()[1] > k) deque.pollFirst(); // 在头部只会每次弹出1个
            int curSteps = deque.peekFirst()[0] + nums[i];
            while (!deque.isEmpty() && curSteps > deque.peekLast()[0]) deque.pollLast(); // 在尾部可能会踢走很多老人
            deque.offerLast(new int[]{curSteps, i});
        }
        return deque.peekLast()[0];
    }
}
/**
 * dp[i]: the maximum score you can get when you arrive at nums[i]
 * max{dp[i-k], dp[i-k+1], [i-k+2], ..., dp[i-1]} + nums[i] => dp[i]
 * k个元素里挑一个最大值
 * O(NK) => 10^5 * 10^5 => TLE
 * 更新dp[i]与dp[i+1]时很多区间是重复的
 * sliding window maximum，长度为k，始终求sliding window里的最大值 ref:239 [deque] 原始模板题
 * deque：monotonic decreasing queue
 * [8 7 6 5] i -> [8 7 6 5 i] => [8 7 7] 保持递减，小的老人立马淘汰
 * window也是会向后移动，有些大的元素会出局 => O(n) 每个元素只会出入window一次
 * 还有一类dp，dp[i]的更新取决于它的前k个区间的和 => 再对dp做一个presum，这样可以O(1)时间把sum求出来
 */
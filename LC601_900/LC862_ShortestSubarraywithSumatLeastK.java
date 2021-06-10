package LC601_900;
import java.util.*;
public class LC862_ShortestSubarraywithSumatLeastK {
    /**
     * Return the length of the shortest, non-empty, contiguous subarray of nums with sum at least k.
     *
     * If there is no non-empty subarray with sum at least k, return -1.
     *
     * Input: nums = [1], k = 1
     * Output: 1
     *
     * Note:
     *
     * 1 <= nums.length <= 50000
     * -10^5 <= nums[i] <= 10^5
     * 1 <= k <= 10^9
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int shortestSubarray(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];
        Deque<Integer> deque = new LinkedList<>();

        int sum = 0, res = n + 1;
        for (int i = 0; i <= n; i++) { // 特别注意： 这里是 i <= n !!! 等于的情况不要漏掉了！！！
            while (!deque.isEmpty() && presum[deque.peekFirst()] + k <= presum[i]) {
                res = Math.min(res, i - deque.peekFirst());
                deque.pollFirst();
            }
            while (!deque.isEmpty() && presum[deque.peekLast()] >= presum[i]) deque.pollLast();
            deque.offer(i);
        }
        return res == n + 1 ? -1 : res;
    }
}
/**
 * 维护一个双端队列q，里面存储的q[j]表示的是一个递增的index的序列．同时要保证presum[q[j]]也是递增的．
 */

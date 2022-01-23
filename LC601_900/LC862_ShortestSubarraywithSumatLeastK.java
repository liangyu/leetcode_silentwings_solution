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
        int n = nums.length;
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];

        Deque<Integer> deque = new LinkedList<>();
        deque.offer(0); // don't forget to add 0 at first!!!

        int res = n + 1;
        for (int i = 1; i <= n; i++) {
            while (!deque.isEmpty() && presum[deque.peekFirst()] <= presum[i] - k) {
                res = Math.min(res, i - deque.pollFirst());
            }

            while (!deque.isEmpty() && presum[deque.peekLast()] >= presum[i]) {
                deque.pollLast();
            }
            deque.offer(i);
        }
        return res == n + 1 ? -1 : res;
    }
}
/**
 * 维护一个双端队列q，里面存储的q[j]表示的是一个递增的index的序列．同时要保证presum[q[j]]也是递增的．
 * 假设我们现在处理A[i]，其对应的前缀和是presum[i]，那么我们想在这个队列里面找到一个位置j，恰好使得presum[q[j]]+K<=presum[i]，
 * 那么队列中的q[0]~q[j]这些index都是满足at least K条件的位置，我们可以找其中最大的一个，比如说q[j']，
 * 就能使得subarray长度i-q[j']是最小的．
 * 接下来的操作很重要，我们可以将q[0]到q[j']都从队列前端弹出．因为以后的i会更大，
 * 如果它在队列中找到的满足at least K条件的左边界位置比q[j']小的话，不会比已经得到的result更好．
 * 所以任何早于q[j']的队列元素对以后的搜索都没有帮助．
 * 接下来，我们需要将presum[i]的信息加入这个队列．
 * 我们的策略是不断在后端弹出元素，直到presum[q.back()]<presum[i]，即保证这个队列对应的presum依然是递增的．
 * 因为当前的i是最靠后，那么所有队里中已有的presum大于presum[i]的元素都是没有意义的，
 * 完全可以被i取代（即依然保证at least K同时能使subarray更短）．
 * 所以每次处理一个presum[i]时，遵循上述两个步骤，就能保证队列存储的是一个递增的index序列，而且对应的presum也是递增的．
 */

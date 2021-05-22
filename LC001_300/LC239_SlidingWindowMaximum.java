package LC001_300;
import java.util.*;
public class LC239_SlidingWindowMaximum {
    /**
     * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left
     * of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
     *
     * Return the max sliding window.
     *
     * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [3,3,5,5,6,7]
     *
     * Window position                Max
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     *  1 [3  -1  -3] 5  3  6  7       3
     *  1  3 [-1  -3  5] 3  6  7       5
     *  1  3  -1 [-3  5  3] 6  7       5
     *  1  3  -1  -3 [5  3  6] 7       6
     *  1  3  -1  -3  5 [3  6  7]      7
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * 1 <= k <= nums.length
     * @param nums
     * @param k
     * @return
     */
    // S1: TreeSet
    // time = O(nlogn), space = O(n)
    public int[] maxSlidingWindow(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int[] res = new int[nums.length - k + 1];
        TreeSet<Node> set = new TreeSet<>((o1, o2) -> o1.val == o2.val ? o1.pos - o2.pos : o1.val - o2.val);
        int idx = 0;

        for (int i = 0; i < nums.length; i++) {
            set.add(new Node(i, nums[i]));
            if (set.size() > k) {
                Node delNode = new Node(i - k, nums[i - k]);
                set.remove(delNode);
            }
            if (set.size() == k) res[idx++] = set.last().val;
        }
        return res;
    }

    private class Node {
        private int pos;
        private int val;
        public Node(int pos, int val) {
            this.pos = pos;
            this.val = val;
        }
    }

    // S2: Deque (最优解）
    // time = O(n), space = O(n)
    public int[] maxSlidingWindow2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int[] res = new int[nums.length - k + 1];
        Deque<Integer> deque = new LinkedList<>(); // deque stores the index of the array
        int idx = 0;

        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) deque.pollLast();
            deque.offerLast(i);
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) deque.pollFirst(); // head is outdated
            if (i >= k - 1) res[idx++] = nums[deque.peekFirst()];
        }
        return res;
    }
}
/**
 * 有序容器的话，比如递增，窗口最尾部就是最大值
 * naive: O(nk)
 * heap: O(nlogk)
 * monotone queue: O(n)  LC1425, 1438
 * [3 -1 -3] 5
 *  j  i
 * [7 5]   => if (i - j <= k) => max of sliding window = 7
 *         => if (i - j > k) => max of sliding window = 5
 * 1. maintain a mono-decreasing queue
 * 2. check if the queue head is outdated
 * 3. the maximum of the sliding window is the queue head
 * => O(n) + O(n) 每个元素都最多只进出window一次
 */
package LC601_900;
import java.util.*;
public class LC703_KthLargestElementinaStream {
    /**
     * Design a class to find the kth largest element in a stream. Note that it is the kth largest element in the
     * sorted order, not the kth distinct element.
     *
     * Implement KthLargest class:
     *
     * KthLargest(int k, int[] nums) Initializes the object with the integer k and the stream of integers nums.
     * int add(int val) Returns the element representing the kth largest element in the stream.
     *
     * Input
     * ["KthLargest", "add", "add", "add", "add", "add"]
     * [[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
     * Output
     * [null, 4, 5, 5, 8, 8]
     *
     * Constraints:
     *
     * 1 <= k <= 10^4
     * 0 <= nums.length <= 10^4
     * -10^4 <= nums[i] <= 10^4
     * -10^4 <= val <= 10^4
     * At most 10^4 calls will be made to add.
     * It is guaranteed that there will be at least k elements in the array when you search for the kth element.
     * @param k
     * @param nums
     */
    // time = O(nlogk), space = O(k)
    private PriorityQueue<Integer> pq;
    private int k;
    public LC703_KthLargestElementinaStream(int k, int[] nums) {
        pq = new PriorityQueue<>();
        this.k = k;
        for (int num : nums) {
            add(num);
        }
    }

    public int add(int val) {
        pq.offer(val);
        if (pq.size() > k) pq.poll();
        return pq.peek();
    }
}

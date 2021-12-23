package LC301_600;
import java.util.*;
public class LC480_SlidingWindowMedian {
    /**
     * The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle
     * value. So the median is the mean of the two middle values.
     *
     * For examples, if arr = [2,3,4], the median is 3.
     * For examples, if arr = [1,2,3,4], the median is (2 + 3) / 2 = 2.5.
     * You are given an integer array nums and an integer k. There is a sliding window of size k which is moving from
     * the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding
     * window moves right by one position.
     *
     * Return the median array for each window in the original array. Answers within 10-5 of the actual value will be
     * accepted.
     *
     * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [1.00000,-1.00000,-1.00000,3.00000,5.00000,6.00000]
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 10^5
     * -2^31 <= nums[i] <= 2^31 - 1
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogk), space = O(k)
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] res = new double[n - k + 1];
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2.compareTo(o1));
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            if (maxHeap.size() <= minHeap.size()) {
                minHeap.offer(nums[i]);
                maxHeap.offer(minHeap.poll());
            } else {
                maxHeap.offer(nums[i]);
                minHeap.offer(maxHeap.poll());
            }
            int idx = i - k + 1;
            if (idx < 0) continue;
            double median = 0;
            if (maxHeap.size() == minHeap.size()) {
                median = ((long) maxHeap.peek() + (long) minHeap.peek()) / 2.0;
            } else median = maxHeap.peek();
            res[idx] = median;
            if (!minHeap.remove(nums[idx])) maxHeap.remove(nums[idx]);
        }
        return res;
    }
}
/**
 * 希望能够自动排序的容器，首选set或者multiset。
 */


package LC001_300;
import java.util.*;
public class LC295_FindMedianfromDataStream {
    /**
     * The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle
     * value and the median is the mean of the two middle values.
     *
     * For example, for arr = [2,3,4], the median is 3.
     * For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
     * Implement the MedianFinder class:
     *
     * MedianFinder() initializes the MedianFinder object.
     * void addNum(int num) adds the integer num from the data stream to the data structure.
     * double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be
     * accepted.
     *
     * Input
     * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
     * [[], [1], [2], [], [3], []]
     * Output
     * [null, null, null, 1.5, null, 2.0]
     *
     * Constraints:
     *
     * -10^5 <= num <= 10^5
     * There will be at least one element in the data structure before calling findMedian.
     * At most 5 * 10^4 calls will be made to addNum and findMedian.
     *
     *
     * Follow up:
     *
     * If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
     * If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
     */
    /** initialize your data structure here. */
    // time = O(nlogn), space = O(n)
    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;
    /** initialize your data structure here. */
    public LC295_FindMedianfromDataStream() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
    }

    public void addNum(int num) {
        if (minHeap.size() == maxHeap.size()) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());
        } else {
            minHeap.offer(num);
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (minHeap.size() == maxHeap.size()) return (minHeap.peek() + maxHeap.peek()) / 2.0;
        return (double)minHeap.peek();
    }
}
/**
 * 只进不出
 */
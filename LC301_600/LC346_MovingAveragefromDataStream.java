package LC301_600;
import java.util.*;
public class LC346_MovingAveragefromDataStream {
    /**
     * Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
     *
     * Implement the MovingAverage class:
     *
     * MovingAverage(int size) Initializes the object with the size of the window size.
     * double next(int val) Returns the moving average of the last size values of the stream.
     *
     * Input
     * ["MovingAverage", "next", "next", "next", "next"]
     * [[3], [1], [10], [3], [5]]
     * Output
     * [null, 1.0, 5.5, 4.66667, 6.0]
     *
     * Constraints:
     *
     * 1 <= size <= 1000
     * -10^5 <= val <= 10^5
     * At most 10^4 calls will be made to next.
     */
    /** Initialize your data structure here. */
    // time = O(1), space = O(n)
    private Queue<Integer> queue;
    private double sum;
    private int size;
    public LC346_MovingAveragefromDataStream(int size) {
        queue = new LinkedList<>();
        sum = 0;
        this.size = size;
    }

    public double next(int val) {
        if (queue.size() == size) {
            sum -= queue.poll();
        }
        queue.offer(val);
        sum += val;
        return sum / queue.size();
    }
}

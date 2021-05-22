package LC1801_2100;
import java.util.*;
public class LC1825_FindingMKAverage {
    /**
     * You are given two integers, m and k, and a stream of integers. You are tasked to implement a data structure
     * that calculates the MKAverage for the stream.
     *
     * The MKAverage can be calculated using these steps:
     *
     * If the number of the elements in the stream is less than m you should consider the MKAverage to be -1. Otherwise,
     * copy the last m elements of the stream to a separate container.
     * Remove the smallest k elements and the largest k elements from the container.
     * Calculate the average value for the rest of the elements rounded down to the nearest integer.
     * Implement the MKAverage class:
     *
     * MKAverage(int m, int k) Initializes the MKAverage object with an empty stream and the two integers m and k.
     * void addElement(int num) Inserts a new element num into the stream.
     * int calculateMKAverage() Calculates and returns the MKAverage for the current stream rounded down to the nearest
     * integer.
     *
     * Input
     * ["MKAverage", "addElement", "addElement", "calculateMKAverage", "addElement", "calculateMKAverage", "addElement",
     * "addElement", "addElement", "calculateMKAverage"]
     * [[3, 1], [3], [1], [], [10], [], [5], [5], [5], []]
     * Output
     * [null, null, null, -1, null, 3, null, null, null, 5]
     *
     * Constraints:
     *
     * 3 <= m <= 10^5
     * 1 <= k*2 < m
     * 1 <= num <= 10^5
     * At most 105 calls will be made to addElement and calculateMKAverage.
     * @param m
     * @param k
     */
    // time = O(nlogn), space = O(n)
    TreeMap<Integer, Integer> left, mid, right;
    Queue<Integer> queue;
    private int sum, m, k, cntL, cntR;

    public LC1825_FindingMKAverage(int m, int k) {
        this.m = m;
        this.k = k;
        left = new TreeMap<>();
        mid = new TreeMap<>();
        right = new TreeMap<>();
        queue = new LinkedList<>();
    }

    public void addElement(int num) {
        if (queue.size() == m) {
            int cur = queue.poll();
            if (right.containsKey(cur)) {
                remove(right, cur);
                cntR--;
            } else if (mid.containsKey(cur)) {
                remove(mid, cur);
                sum -= cur;
            } else {
                remove(left, cur);
                cntL--;
            }
        }

        // insert to mid first
        add(mid, num);
        queue.offer(num);
        sum += num;

        // move item from mid to right, to fill k slots
        while (cntR < k && !mid.isEmpty()) {
            cntR++;
            sum -= mid.lastKey();
            add(right, remove(mid, mid.lastKey()));
        }

        // rebalance mid and right
        while (!mid.isEmpty() && !right.isEmpty() && right.firstKey() < mid.lastKey()) {
            sum += right.firstKey();
            add(mid, remove(right, right.firstKey()));
            sum -= mid.lastKey();
            add(right, remove(mid, mid.lastKey()));
        }

        //move item from mid to left, to fill k slots
        while (cntL < k && !mid.isEmpty()) {
            cntL++;
            sum -= mid.firstKey();
            add(left, remove(mid, mid.firstKey()));
        }

        // rebalance mid and left
        while (!mid.isEmpty() && !left.isEmpty() && left.lastKey() > mid.firstKey()) {
            sum += left.lastKey();
            add(mid, remove(left, left.lastKey()));
            sum -= mid.firstKey();
            add(left, remove(mid, mid.firstKey()));
        }
    }

    public int calculateMKAverage() {
        return queue.size() == m ? sum / (m - 2 * k) : -1;
    }

    private int remove(TreeMap<Integer, Integer> map, int num) {
        map.put(num, map.get(num) - 1);
        if (map.get(num) == 0) map.remove(num);
        return num;
    }

    private void add(TreeMap<Integer, Integer> map, int num) {
        map.put(num, map.getOrDefault(num, 0) + 1);
    }
}
/**
 * X X Y X O Y X X X
 * arr = stream[a:b]
 * sort(arr)
 * sum(arr[k : m - k])/(m - 2k)
 *
 * 维护3个heap
 * left: X X X O A
 * mid: A X X X X X X B
 * right: X O X X
 *
 * shiftLeft(set1, set2)
 * shiftRight(set1, set2)
 */

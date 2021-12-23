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
    int m, k;
    TreeMap<Integer, Integer> left, mid, right;
    int cntL, cntM, cntR;
    long sum = 0;
    Queue<Integer> queue;
    public LC1825_FindingMKAverage(int m, int k) {
        this.m = m;
        this.k = k;
        left = new TreeMap<>();
        mid = new TreeMap<>();
        right = new TreeMap<>();
        queue = new LinkedList<>();
    }

    public void addElement(int num) {
        if (queue.size() < m) {
            queue.offer(num);
            mid.put(num, mid.getOrDefault(num, 0) + 1);
            sum += num;
            cntM++;

            if (queue.size() == m) {
                while (cntL < k) {
                    sum -= mid.firstKey();
                    shiftLeft(left, mid);
                    cntL++;
                    cntM--;
                }
                while (cntR < k) {
                    sum -= mid.lastKey();
                    shiftRight(mid, right);
                    cntR++;
                    cntM--;
                }
            }
        } else if (queue.size() == m) {
            queue.offer(num);
            if (num <= left.lastKey()) {
                left.put(num, left.getOrDefault(num, 0) + 1);
                cntL++;
            } else if (num >= right.firstKey()) {
                right.put(num, right.getOrDefault(num, 0) + 1);
                cntR++;
            } else {
                sum += num;
                mid.put(num, mid.getOrDefault(num, 0) + 1);
                cntM++;
            }

            // adjust
            if (cntL > k) {
                sum += left.lastKey();
                shiftRight(left, mid);
                cntL--;
                cntM++;
            }
            if (cntR > k) {
                sum += right.firstKey();
                shiftLeft(mid, right);
                cntR--;
                cntM++;
            }

            // delete old element
            int x = queue.poll();
            if (left.containsKey(x)) {
                left.put(x, left.get(x) - 1);
                if (left.get(x) == 0) left.remove(x);
                cntL--;
            } else if (right.containsKey(x)) {
                right.put(x, right.get(x) - 1);
                if (right.get(x) == 0) right.remove(x);
                cntR--;
            } else {
                sum -= x;
                mid.put(x, mid.get(x) - 1);
                if (mid.get(x) == 0) mid.remove(x);
                cntM--;
            }

            // adjust -> 注意：delete之后还需要adjust！
            if (cntL < k) {
                sum -= mid.firstKey();
                shiftLeft(left, mid);
                cntL++;
                cntM--;
            }
            if (cntR < k) {
                sum -= mid.lastKey();
                shiftRight(mid, right);
                cntR++;
                cntM--;
            }
        }
    }

    public int calculateMKAverage() {
        if (queue.size() < m) return -1;
        return (int) sum / (m - 2 * k);
    }

    private void shiftLeft(TreeMap<Integer, Integer> a, TreeMap<Integer, Integer> b) {
        int x = b.firstKey();
        a.put(x, a.getOrDefault(x, 0) + 1);
        b.put(x, b.get(x) - 1);
        if (b.get(x) == 0) b.remove(x);
    }


    private void shiftRight(TreeMap<Integer, Integer> a, TreeMap<Integer, Integer> b) {
        int x = a.lastKey();
        b.put(x, b.getOrDefault(x, 0) + 1);
        a.put(x, a.get(x) - 1);
        if (a.get(x) == 0) a.remove(x);
    }
}
/**
 * ref: LC295
 * X X Y X O Y X X X
 * arr = stream[a:b]
 * sort(arr)
 * sum(arr[k : m - k])/(m - 2k)
 *
 * 直接维护3个heap
 * left: X X X O A
 * mid: A X X X X X X B
 * right: X O X X
 *
 * shiftLeft(set1, set2)
 * shiftRight(set1, set2)
 */

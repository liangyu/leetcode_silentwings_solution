package LC301_600;
import java.util.*;
public class LC362_DesignHitCounter {
    /**
     * Design a hit counter which counts the number of hits received in the past 5 minutes (i.e., the past 300 seconds).
     *
     * Your system should accept a timestamp parameter (in seconds granularity), and you may assume that calls are being
     * made to the system in chronological order (i.e., timestamp is monotonically increasing). Several hits may arrive
     * roughly at the same time.
     *
     * Implement the HitCounter class:
     *
     * HitCounter() Initializes the object of the hit counter system.
     * void hit(int timestamp) Records a hit that happened at timestamp (in seconds). Several hits may happen at the
     * same timestamp.
     * int getHits(int timestamp) Returns the number of hits in the past 5 minutes from timestamp (i.e., the past 300
     * seconds).
     *
     * Input
     * ["HitCounter", "hit", "hit", "hit", "getHits", "hit", "getHits", "getHits"]
     * [[], [1], [2], [3], [4], [300], [300], [301]]
     * Output
     * [null, null, null, null, 3, null, 4, 3]
     *
     * Constraints:
     *
     * 1 <= timestamp <= 2 * 10^9
     * All the calls are being made to the system in chronological order (i.e., timestamp is monotonically increasing).
     * At most 300 calls will be made to hit and getHits.
     */
    TreeMap<Integer, Integer> map;
    int sum;
    public LC362_DesignHitCounter() {
        map = new TreeMap<>();
        sum = 0;
    }

    // time = O(logn), space = O(n)
    public void hit(int timestamp) {
        sum++;
        map.put(timestamp, sum);
    }

    // time = O(logn), space = O(n)
    public int getHits(int timestamp) {
        Integer left = map.floorKey(timestamp - 300);
        Integer right = map.floorKey(timestamp);
        int a = 0, b = 0;
        if (left != null) a = map.get(left);
        if (right != null) b = map.get(right);
        return b - a;
    }
}

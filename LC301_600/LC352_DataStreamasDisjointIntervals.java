package LC301_600;
import java.util.*;
public class LC352_DataStreamasDisjointIntervals {
    /**
     * Given a data stream input of non-negative integers a1, a2, ..., an, summarize the numbers seen so far as a list
     * of disjoint intervals.
     *
     * Implement the SummaryRanges class:
     *
     * SummaryRanges() Initializes the object with an empty stream.
     * void addNum(int val) Adds the integer val to the stream.
     * int[][] getIntervals() Returns a summary of the integers in the stream currently as a list of disjoint intervals
     * [starti, endi].
     *
     * Input
     * ["SummaryRanges", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum",
     * "getIntervals", "addNum", "getIntervals"]
     * [[], [1], [], [3], [], [7], [], [2], [], [6], []]
     * Output
     * [null, null, [[1, 1]], null, [[1, 1], [3, 3]], null, [[1, 1], [3, 3], [7, 7]], null, [[1, 3], [7, 7]], null,
     * [[1, 3], [6, 7]]]
     *
     * Constraints:
     *
     * 0 <= val <= 10^4
     * At most 3 * 10^4 calls will be made to addNum and getIntervals.
     *
     *
     * Follow up: What if there are lots of merges and the number of disjoint intervals is small compared to the size
     * of the data stream?
     */
    /** Initialize your data structure here. */
    TreeMap<Integer, int[]> map;
    public LC352_DataStreamasDisjointIntervals() {
        map = new TreeMap<>(); // {key: start, val: [start, end]}
    }

    // time = O(logn), space = O(n)
    public void addNum(int val) {
        if (map.containsKey(val)) return;
        Integer lk = map.lowerKey(val);
        Integer hk = map.higherKey(val);
        if (lk != null && hk != null && map.get(lk)[1] + 1 == val && map.get(hk)[0] - 1 == val) { // low，high中间刚好缺一个val
            map.get(lk)[1] = map.get(hk)[1];
            map.remove(hk);
        } else if (lk != null && val <= map.get(lk)[1] + 1) { // val落在左半边，最远可以拓展到low key对应的interval end + 1
            map.get(lk)[1] = Math.max(map.get(lk)[1], val);
        } else if (hk != null && map.get(hk)[0] - 1 == val) { // val落在右半边，最大只能到high key对应interval start - 1
            map.put(val, new int[]{val, map.get(hk)[1]});
            map.remove(hk);
        } else map.put(val, new int[]{val, val}); // val落在中间，左右都不接壤，只能自立门户
    }

    // time = O(n), space = O(n)
    public int[][] getIntervals() {
        int n = map.size();
        int[][] res = new int[n][2];
        int i = 0;
        for (int key : map.keySet()) {
            res[i++] = map.get(key);
        }
        return res;
    }
}

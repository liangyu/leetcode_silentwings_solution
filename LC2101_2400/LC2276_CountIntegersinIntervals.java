package LC2101_2400;
import java.util.*;
public class LC2276_CountIntegersinIntervals {
    /**
     * Given an empty set of intervals, implement a data structure that can:
     *
     * Add an interval to the set of intervals.
     * Count the number of integers that are present in at least one interval.
     * Implement the CountIntervals class:
     *
     * CountIntervals() Initializes the object with an empty set of intervals.
     * void add(int left, int right) Adds the interval [left, right] to the set of intervals.
     * int count() Returns the number of integers that are present in at least one interval.
     * Note that an interval [left, right] denotes all the integers x where left <= x <= right.
     *
     * Input
     * ["CountIntervals", "add", "add", "count", "add", "count"]
     * [[], [2, 3], [7, 10], [], [5, 8], []]
     * Output
     * [null, null, null, 6, null, 8]
     *
     * Constraints:
     *
     * 1 <= left <= right <= 10^9
     * At most 10^5 calls in total will be made to add and count.
     * At least one call will be made to count.
     *
     */
    TreeSet<int[]> set;
    int count;
    public LC2276_CountIntegersinIntervals() {
        set = new TreeSet<>(((o1, o2) -> o1[1] - o2[1]));
        count = 0;
    }

    // time = O(nlogn), space = O(n)
    public void add(int left, int right) {
        while (!set.isEmpty()) {
            int[] ck = set.ceiling(new int[]{0, left - 1}); // check 有没有区间的右端点是越过待查区间的左端点
            if (ck == null || ck[0] > right + 1) break; // 如果有的话，再check左端点是否也在待查区间右端点的相交范围内
            int[] x = new int[]{Math.min(left, ck[0]), Math.max(right, ck[1])};
            left = x[0]; // 更新左右端点
            right = x[1];
            set.remove(ck);
            count -= ck[1] - ck[0] + 1;
        }
        count += right - left + 1;
        set.add(new int[]{left, right});
    }

    // time = O(1), space = O(1)
    public int count() {
        return count;
    }


    // S2: TreeMap
    class CountIntervals {
        TreeMap<Integer, Integer> map;
        int count = 0;
        public CountIntervals() {
            map = new TreeMap<>();
        }

        // time = O(nlogn), space = O(n)
        public void add(int left, int right) {
            // add interval if there is no overlap with existing intervals -> only check the right end
            if (map.floorKey(right) == null || map.get(map.floorKey(right)) < left - 1) {
                map.put(left, right);
                count += right - left + 1;
            } else {
                int start = left, end = right;
                // remove overlapping intervals and update the count
                while (true) {
                    int l = map.floorKey(end);
                    int r = map.get(l);
                    start = Math.min(start, l);
                    end = Math.max(end, r);
                    count -= r - l + 1;
                    map.remove(l);
                    // break the loop until there is no overlapping with current interval (start, end)
                    if (map.floorKey(end) == null || map.get(map.floorKey(end)) < start - 1) break;
                }
                map.put(start, end);
                count += end - start + 1;
            }
        }
        // time = O(1), space = O(1)
        public int count() {
            return count;
        }
    }
}
/**
 * Heap,类似LC715
 * map<int, int>
 *      L   R
 * 隐含着一系列"互不相交"的区间
 * iter = map.lower_bound(left) 定位第一个>= l的区间
 * iter = prev(iter) 最后一个< l 的key  => lowerKey
 * A的右边界要比left大
 *    A            B
 * _________     _____   _________
 *       __________________
 *     left              right
 * 如何定义D呢？从B遍历过来，只要左边界都比right小，直到左边界比right大。
 * 边删的过程中边扩展右边界。
 *    A       B    C       D
 * _________  __  ___  _________
 *       __________________
 *     left              right
 *
 */
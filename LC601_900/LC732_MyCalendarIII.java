package LC601_900;
import java.util.*;
public class LC732_MyCalendarIII {
    /**
     * A k-booking happens when k events have some non-empty intersection (i.e., there is some time that is common to
     * all k events.)
     *
     * You are given some events [start, end), after each given event, return an integer k representing the maximum
     * k-booking between all the previous events.
     *
     * Implement the MyCalendarThree class:
     *
     * MyCalendarThree() Initializes the object.
     * int book(int start, int end) Returns an integer k representing the largest integer such that there exists a
     * k-booking in the calendar.
     *
     * Input
     * ["MyCalendarThree", "book", "book", "book", "book", "book", "book"]
     * [[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
     * Output
     * [null, 1, 1, 2, 3, 3, 3]
     *
     * Constraints:
     *
     * 0 <= start < end <= 10^9
     * At most 400 calls will be made to book.
     */
    // S1: TreeSet
    // time = O(n^2), space = O(n)
    private TreeSet<int[]> diff; // 自动排序，但是会自动去重，所以加上一个idx累加来区分两个值相同的端点加入到set里
    private int idx = 0;
    public LC732_MyCalendarIII() {
        diff = new TreeSet<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : (o1[1] != o2[1] ? o1[1] - o2[1] : o1[2] - o2[2]));
    }

    public int book(int start, int end) {
        diff.add(new int[]{start, 1, idx++}); // 注意：这里加1个idx来区分多个相同的start，比如例子中有2个10和2个5都是作为start!!!
        diff.add(new int[]{end, -1, idx++});

        int count = 0, res = 0;
        for (int[] x : diff) {
            count += x[1];
            res = Math.max(res, count);
        }
        return res;
    }

    // S2: TreeMap
    class MyCalendarThree {
        // time = O(n^2), space = O(n)
        TreeMap<Integer, Integer> map;
        public MyCalendarThree() {
            map = new TreeMap<>();
        }

        public int book(int start, int end) {
            map.put(start, map.getOrDefault(start, 0) + 1);
            map.put(end, map.getOrDefault(end, 0) - 1);

            int sum = 0, res = 0;
            for (int v : map.values()) {
                sum += v;
                res = Math.max(sum, res);
            }
            return res;
        }
    }
}
/**
 * sweep line
 * 当有两个点出现相同时间点时，先处理-1还是先处理+1是需要考虑的。
 * 因为这里是左闭右开区间，所以这里并不会重合，我们先处理-1再+1
 */

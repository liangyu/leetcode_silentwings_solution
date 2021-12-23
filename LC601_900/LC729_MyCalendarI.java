package LC601_900;
import java.util.*;
public class LC729_MyCalendarI {
    /**
     * You are implementing a program to use as your calendar. We can add a new event if adding the event will not
     * cause a double booking.
     *
     * A double booking happens when two events have some non-empty intersection (i.e., some moment is common to both
     * events.).
     *
     * The event can be represented as a pair of integers start and end that represents a booking on the half-open
     * interval [start, end), the range of real numbers x such that start <= x < end.
     *
     * Implement the MyCalendar class:
     *
     * MyCalendar() Initializes the calendar object.
     * boolean book(int start, int end) Returns true if the event can be added to the calendar successfully without
     * causing a double booking. Otherwise, return false and do not add the event to the calendar.
     *
     * Input
     * ["MyCalendar", "book", "book", "book"]
     * [[], [10, 20], [15, 25], [20, 30]]
     * Output
     * [null, true, false, true]
     *
     * Constraints:
     *
     * 0 <= start < end <= 10^9
     * At most 1000 calls will be made to book.
     */
    // time = O(nlogn), space = O(n)
    private TreeMap<Integer, Integer> map;
    public LC729_MyCalendarI() {
        map = new TreeMap<>();
    } // 把起点做为key，终点作为end,注意区间是左闭右开！

    public boolean book(int start, int end) { // 2种情况：1种是起点与[start, end]有重合，一种是终点和[start, end]有重合。
        Integer fk = map.floorKey(start);
        if (fk != null) {
            if (map.get(fk) > start) return false;
        }
        Integer hk = map.higherKey(start);
        if (hk != null) {
            if (hk < end) return false;
        }
        map.put(start, end);
        return true;
    }
}
/**
 * 首先，使用Map.upper_bound(start)找到第一个大于start的迭代器iter，检查其对应的区间[a,b)是否与[start,end)重合。
 * 记得前提是iter有意义，也就是iter!=Map.end().
 * 接着，将iter回退一个位置，找到第一个小于等于start的迭代器，检查其对应的区间[a,b)是否与[start,end)重合。
 * 同样，记得前提是此时的iter有意义，也就是iter!=Map.begin().
 */

package LC601_900;
import java.util.*;
public class LC731_MyCalendarII {
    /**
     * You are implementing a program to use as your calendar. We can add a new event if adding the event will not
     * cause a triple booking.
     *
     * A triple booking happens when three events have some non-empty intersection (i.e., some moment is common to all
     * the three events.).
     *
     * The event can be represented as a pair of integers start and end that represents a booking on the half-open
     * interval [start, end), the range of real numbers x such that start <= x < end.
     *
     * Implement the MyCalendarTwo class:
     *
     * MyCalendarTwo() Initializes the calendar object.
     * boolean book(int start, int end) Returns true if the event can be added to the calendar successfully without
     * causing a triple booking. Otherwise, return false and do not add the event to the calendar.
     *
     * ["MyCalendarTwo", "book", "book", "book", "book", "book", "book"]
     * [[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
     * Output
     * [null, true, true, true, false, true, true]
     *
     * Constraints:
     *
     * 0 <= start < end <= 10^9
     * At most 1000 calls will be made to book.
     */
    // S1: List
    // time = O(nlogn), space = O(n)
    private List<int[]> events;
    public LC731_MyCalendarII() {
        events = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        List<int[]> temp = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (!(events.get(i)[1] <= start || events.get(i)[0] >= end)) {
                temp.add(new int[]{events.get(i)[0], events.get(i)[1]});
            }
        }
        Collections.sort(temp, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        for (int i = 1; i < temp.size(); i++) {
            if (temp.get(i)[0] < temp.get(i - 1)[1]) return false;
        }
        events.add(new int[]{start, end});
        return true;
    }

    // S2: TreeSet (may get TLE!)
    // time = O(nlogn), space = O(n)
    private TreeSet<int[]> set;
    public LC731_MyCalendarII2() {
        // 可能存在重复对，比如[27, 36], [27, 36]，针对这种情况，再存一个当前set的size来将二者都存入来验证是否有triple booking.
        set = new TreeSet<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : (o1[1] != o2[1]) ? o1[1] - o2[1] : o1[2] - o2[2]);
    }

    public boolean book2(int start, int end) {
        List<int[]> temp = new ArrayList<>();
        List<int[]> backSet = new ArrayList<>();
        while (set.size() > 0) { // TreeSet的key无法自动按从小到大顺序遍历，只能通过不断移除首位暂存于一个list里，回头再塞回来。
            int[] x = set.first();
            if (!(x[1] <= start || end <= x[0])) {
                temp.add(new int[]{x[0], x[1]});
            }
            backSet.add(new int[]{x[0], x[1], x[2]});
            set.remove(x);
        }

        for (int[] x : backSet) set.add(x); // 塞回set
        for (int i = 1; i < temp.size(); i++) {
            if (temp.get(i)[0] < temp.get(i - 1)[1]) return false;
        }
        set.add(new int[]{start, end, set.size()});
        return true;
    }
}
package LC001_300;
import java.util.*;
public class LC252_MeetingRooms {
    /**
     * Given an array of meeting time intervals where intervals[i] = [starti, endi], determine if a person could attend
     * all meetings.
     *
     * Input: intervals = [[0,30],[5,10],[15,20]]
     * Output: false
     *
     * Constraints:
     *
     * 0 <= intervals.length <= 10^4
     * intervals[i].length == 2
     * 0 <= starti < endi <= 10^6
     * @param intervals
     * @return
     */
    // S1: Sort
    // time = O(nlogn), space = O(1)
    public boolean canAttendMeetings(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return true;
        }

        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        int n = intervals.length;

        for (int i = 1; i < n; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) return false;
        }
        return true;
    }

    // S2: sweep line
    // time = O(nlogn), space = O(n)
    public boolean canAttendMeetings2(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return true;
        }

        int n = intervals.length;
        List<int[]> diff = new ArrayList<>();
        for (int[] interval : intervals) {
            diff.add(new int[]{interval[0], 1});
            diff.add(new int[]{interval[1], -1});
        }

        Collections.sort(diff, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int count = 0;
        for (int i = 0; i < diff.size(); i++) {
            count += diff.get(i)[1];
            if (count > 1) return false;
        }
        return true;
    }
}
/**
 * 遍历所有的intervals，将这样所有的{start,1}和{end,-1}加入一个数组ｑ．然后将ｑ排序．
 * 注意，对于同一个时刻，{end,-1}会比{start,1}先处理．
 * 这在本题是合理的，因为所有的有效区间的长度都至少为１，这样的话类似[t,t]这样的区间就不会被包括进来．
 * 我们依次遍历这个ｑ的元素，将第二个label的值累加进count．发现当count > 1时，即说明有两个重合的区间，返回false
 */
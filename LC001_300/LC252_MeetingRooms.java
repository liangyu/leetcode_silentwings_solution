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
}

package LC001_300;
import java.util.*;
public class LC253_MeetingRoomsII {
    /**
     * Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number
     * of conference rooms required.
     *
     * Input: intervals = [[0,30],[5,10],[15,20]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 10^4
     * 0 <= starti < endi <= 10^6
     * @param intervals
     * @return
     */
    // time = O(nlogn, space = O(n)
    public int minMeetingRooms(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return 0;
        }

        List<EndPoint> eps = new ArrayList<>();
        for (int[] interval : intervals) {
            eps.add(new EndPoint(interval[0], true));
            eps.add(new EndPoint(interval[1], false));
        }

        Collections.sort(eps);

        int pool = 0, max = 0;
        for (EndPoint ep : eps) {
            if (ep.isStart) {
                pool++;
                max = Math.max(pool, max);
            } else pool--;
        }
        return max;
    }

    private class EndPoint implements Comparable<EndPoint> {
        private int val;
        private boolean isStart;
        public EndPoint(int val, boolean isStart) {
            this.val = val;
            this.isStart = isStart;
        }
        @Override
        public int compareTo(EndPoint that) {
            if (this.val != that.val) return this.val - that.val;
            else return this.isStart ? 1 : -1;
        }
    }
}

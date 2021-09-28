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
    // S1
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

    // S2: Sort + PQ
    // time = O(nlogn, space = O(n)
    public int minMeetingRooms2(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return 0;
        }

        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

        int count = 0, i = 0, n = intervals.length;
        while (i < n) {
            while (pq.isEmpty() || i < n && pq.peek()[1] > intervals[i][0]) {
                pq.offer(intervals[i]);
                i++;
            }
            count = Math.max(count, pq.size());
            pq.poll(); // 弹出最早结束的一个会议
        }
        return count;
    }

    // S3: sweep line
    // time = O(nlogn, space = O(n)
    public int minMeetingRooms3(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return 0;
        }

        List<int[]> diff = new ArrayList<>();
        int n = intervals.length;
        for (int[] interval : intervals) {
            diff.add(new int[]{interval[0], 1});
            diff.add(new int[]{interval[1], -1});
        }

        Collections.sort(diff, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int count = 0, res = 0;
        for (int i = 0; i < diff.size(); i++) {
            count += diff.get(i)[1];
            res = Math.max(res, count);
        }
        return res;
    }
}

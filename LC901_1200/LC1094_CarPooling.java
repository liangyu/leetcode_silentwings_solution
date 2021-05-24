package LC901_1200;
import java.util.*;
public class LC1094_CarPooling {
    /**
     * You are driving a vehicle that has capacity empty seats initially available for passengers.  The vehicle only
     * drives east (ie. it cannot turn around and drive west.)
     *
     * Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains information about the
     * i-th trip: the number of passengers that must be picked up, and the locations to pick them up and drop them off.
     * The locations are given as the number of kilometers due east from your vehicle's initial location.
     *
     * Return true if and only if it is possible to pick up and drop off all passengers for all the given trips.
     *
     * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
     * Output: false
     *
     * Constraints:
     *
     * trips.length <= 1000
     * trips[i].length == 3
     * 1 <= trips[i][0] <= 100
     * 0 <= trips[i][1] < trips[i][2] <= 1000
     * 1 <= capacity <= 100000
     * @param trips
     * @param capacity
     * @return
     */
    // S1: diff array
    // time = O(nlogn), space = O(n)
    public boolean carPooling(int[][] trips, int capacity) {
        List<int[]> diff = new ArrayList<>();
        for (int[] t : trips) {
            diff.add(new int[]{t[1], t[0]});
            diff.add(new int[]{t[2], -t[0]});
        }

        Collections.sort(diff, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int total = 0;
        for (int[] d : diff) {
            total += d[1];
            if (total > capacity) return false;
        }
        return true;
    }

    // S2: Interval
    // time = O(nlogn), space = O(n)
    public boolean carPooling2(int[][] trips, int capacity) {
        List<EndPoint> eps = new ArrayList<>();
        for (int[] t : trips) {
            eps.add(new EndPoint(t[0], t[1], true));
            eps.add(new EndPoint(t[0], t[2], false));
        }

        Collections.sort(eps);

        int count = 0;
        for (EndPoint ep : eps) {
            if (ep.isStart) {
                count += ep.size;
                if (count > capacity) return false;
            } else {
                count -= ep.size;
            }
        }
        return true;
    }

    private class EndPoint implements Comparable<EndPoint> {
        private int size;
        private int val;
        private boolean isStart;
        public EndPoint(int size, int val, boolean isStart) {
            this.size = size;
            this.val = val;
            this.isStart = isStart;
        }
        @Override
        public int compareTo(EndPoint that) {
            if (this.val != that.val) {
                return this.val - that.val;
            } else return isStart ? 1 : -1;
        }
    }


}
/**
 * 差分数组-扫描线
 * 上车，下车，如何monitor车上的人数呢？-> 每次在上车下车的时候看下，做个差分
 * +10， [+4 -5]， +10 -6， -12
 * 只关心差分量，从0开始累积这些差分量来实时监测
 * 按照时间从早到晚检查，按照站点排序
 * 如果同一站有人上车，有人下车 -> 先减后加，这样的话就不会误报警
 */

package LC001_300;
import java.util.*;
public class LC57_InsertInterval {
    /**
     * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
     *
     * You may assume that the intervals were initially sorted according to their start times.
     *
     * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
     * Output: [[1,5],[6,9]]
     *
     * Constraints:
     *
     * 0 <= intervals.length <= 10^4
     * intervals[i].length == 2
     * 0 <= intervals[i][0] <= intervals[i][1] <= 10^5
     * intervals is sorted by intervals[i][0] in ascending order.
     * newInterval.length == 2
     * 0 <= newInterval[0] <= newInterval[1] <= 10^5
     * @param intervals
     * @param newInterval
     * @return
     */
    // time = O(n), space = O(n)
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return new int[][]{newInterval};
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int[] interval : intervals) {
            if (interval[1] < newInterval[0]) {
                res.add(Arrays.asList(interval[0], interval[1]));
            } else if (interval[0] > newInterval[1]) {
                res.add(Arrays.asList(newInterval[0], newInterval[1]));
                newInterval = interval;
            } else if (interval[0] < newInterval[1] || interval[1] > newInterval[0]) {
                newInterval = new int[]{Math.min(interval[0], newInterval[0]), Math.max(interval[1], newInterval[1])};
            }
        }
        res.add(Arrays.asList(newInterval[0], newInterval[1]));
        int[][] ans = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            ans[i][0] = res.get(i).get(0);
            ans[i][1] = res.get(i).get(1);
        }
        return ans;
    }

    // S2: sweep line
    // time = O(nlogn), space = O(n)
    public int[][] insert2(int[][] intervals, int[] newInterval) {
        List<int[]> diff = new ArrayList<>(); //
        for (int[] interval : intervals) {
            diff.add(new int[]{interval[0], 1});
            diff.add(new int[]{interval[1], -1});
        }
        diff.add(new int[]{newInterval[0], 1});
        diff.add(new int[]{newInterval[1], -1});

        Collections.sort(diff, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);

        int count = 0, start = -1, end = -1;
        List<int[]> res = new ArrayList<>();
        for (int[] x : diff) {
            count += x[1];
            if (x[1] == 1 && count == 1) {
                start = x[0];
            } else if (x[1] == -1 && count == 0) {
                end = x[0];
                res.add(new int[]{start, end});
            }
        }

        int[][] ans = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }
}
/**
 * 扫描线算法
 * count计数器表明当前有多少个区间重合
 * 只要count > 1，说明在一个interval里，重合的
 * 遇到相切点，breaking tie,要先+1，再-1，保证count > 1
 */
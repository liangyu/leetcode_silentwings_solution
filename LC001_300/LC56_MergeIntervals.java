package LC001_300;
import java.util.*;
public class LC56_MergeIntervals {
    /**
     * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an
     * array of the non-overlapping intervals that cover all the intervals in the input.
     *
     * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
     * Output: [[1,6],[8,10],[15,18]]
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 10^4
     * intervals[i].length == 2
     * 0 <= starti <= endi <= 10^4
     * @param intervals
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[][] merge(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return new int[0][0];
        }

        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);

        List<List<Integer>> res = new ArrayList<>();
        for (int[] interval : intervals) {
            if (res.isEmpty()) res.add(Arrays.asList(interval[0], interval[1]));
            int[] lastInterval = new int[2];
            lastInterval[0] = res.get(res.size() - 1).get(0);
            lastInterval[1] = res.get(res.size() - 1).get(1);
            if (lastInterval[1] < interval[0]) res.add(Arrays.asList(interval[0], interval[1]));
            else {
                res.remove(res.size() - 1);
                res.add(Arrays.asList(lastInterval[0], Math.max(lastInterval[1], interval[1])));
            }
        }
        int[][] ans = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            ans[i][0] = res.get(i).get(0);
            ans[i][1] = res.get(i).get(1);
        }
        return ans;
    }
}

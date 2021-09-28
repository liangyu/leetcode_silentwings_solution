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
    // S1
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

    // S2: sweep line
    // time = O(nlogn), space = O(n)
    public int[][] merge2(int[][] intervals) {
        List<int[]> diff = new ArrayList<>();
        for (int[] x : intervals) {
            diff.add(new int[]{x[0], 1});
            diff.add(new int[]{x[1], -1});
        }

        Collections.sort(diff, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]); // 先+1再-1使计数器不会出现0而中断！

        int count = 0, start = -1, end = -1;
        List<int[]> res = new ArrayList<>();
        for (int[] x : diff) {
            if (x[1] == 1) {
                count++;
                if (count == 1) start = x[0];
            }
            else {
                count--; // count 0 -> 1就表示新的merge interval; 1 -> 0，merge interval结束
                if (count == 0) {
                    end = x[0];
                    res.add(new int[]{start, end});
                }
            }
        }
        int[][] ans = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            ans[i] = res.get(i);
        }
        return ans;
    }
}
/**
 * 扫描线的解法
 * int[][] time(start/end), label(1,-1)
 * 0_____4
 *    2____6
 * {0,1},
 * {4,-1},
 * {2,1},
 * {6,-1}
 * sort
 * 用计数器去累加，表示在此时此刻，如果你做下扫描线的话，你会和几个区间重合
 * 0~6 count计数器从0开始
 */
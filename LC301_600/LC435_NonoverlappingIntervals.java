package LC301_600;
import java.util.*;
public class LC435_NonoverlappingIntervals {
    /**
     * Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of intervals
     * you need to remove to make the rest of the intervals non-overlapping.
     *
     * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 10^5
     * intervals[i].length == 2
     * -5 * 10^4 <= starti < endi <= 5 * 10^4
     * @param intervals
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int eraseOverlapIntervals(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return 0;
        }

        Arrays.sort(intervals, (o1, o2) -> o1[1] - o2[1]);

        int i = 0, n = intervals.length, count = 0;
        while (i < n) {
            count++; // 保留的区间
            int j = i + 1;
            while (j < n && intervals[j][0] < intervals[i][1]) j++;
            i = j;
        }
        return n - count;
    }
}
/**
 * 区间类问题2大方法：Greedy  手动设计一种最优策略
 * 1. sweeping line / diff -> 擅长解决多个区间互相overlap,不需要从中选取区间，只是考察其中overlap的情况。
 * 区间里去选一些区间来保留某个性质，扫描线没什么帮助，用扫描线的话眼中不再有区间，只有点
 * 2. sort
 * (1) sort by starting point => minimum number of intervals to cover the whole range
 * (2) sort by ending point => maximum number of intervals that are non-overlapping
 * 区间型dp
 * 按照ending point排序的话，永远保留第一个，因为它的ending point最靠前，它对后面这些区间的干扰就会越小
 * 这样就可以保证所选的这些区间都不会overlap
 * 在这里是从里面选区间，必须保证区间是完整的，而用扫描线则是把starting point ,end point 都打散了。
 */

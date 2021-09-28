package LC1201_1500;
import java.util.*;
public class LC1288_RemoveCoveredIntervals {
    /**
     * Given an array intervals where intervals[i] = [li, ri] represent the interval [li, ri), remove all intervals
     * that are covered by another interval in the list.
     *
     * The interval [a, b) is covered by the interval [c, d) if and only if c <= a and b <= d.
     *
     * Return the number of remaining intervals.
     *
     * Input: intervals = [[1,4],[3,6],[2,8]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 1000
     * intervals[i].length == 2
     * 0 <= li <= ri <= 10^5
     * All the given intervals are unique.
     * @param intervals
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int removeCoveredIntervals(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) return 0;

        Arrays.sort(intervals, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);

        int n = intervals.length;
        int i = 0, count = 0;

        while (i < n) {
            count++;
            int j = i + 1;
            while (j < n && intervals[j][1] <= intervals[i][1]) j++;
            i = j;
        }
        return count;
    }
}
/**
 * the minimum number of overlapped intervals to cover the whole range => sort by starting point
 * 不是特别典型，这是个确定性的问题，并不是一个优化问题 => 答案是唯一的
 * 第一个区间首部分是独一无二的，对于第一个区间是不可能被其他区间整个都包进去，意味着第一个区间肯定是要保留的。
 * 确定第一个区间之后，就有了一个范围，能被这个区间包含的区间都可以踢掉，顺次找即可
 * 如果首端点相同，意味着其中一个会包围在另一个里面 => 优先选长的区间
 */

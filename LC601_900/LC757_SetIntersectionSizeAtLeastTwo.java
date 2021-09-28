package LC601_900;
import java.util.*;
public class LC757_SetIntersectionSizeAtLeastTwo {
    /**
     * An integer interval [a, b] (for integers a < b) is a set of all consecutive integers from a to b, including a
     * and b.
     *
     * Find the minimum size of a set S such that for every integer interval A in intervals, the intersection of S with
     * A has a size of at least two.
     *
     * Input: intervals = [[1,3],[1,4],[2,5],[3,5]]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 3000
     * intervals[i].length == 2
     * 0 <= ai < bi <= 10^8
     * @param intervals
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int intersectionSizeTwo(int[][] intervals) {
        // corner case
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) return 0;

        // 末端点相同的时候，把较短的interval放在前面，即首端点靠后的放在前面
        Arrays.sort(intervals, (o1, o2) -> o1[1] != o2[1] ? o1[1] - o2[1] : o2[0] - o1[0]);

        int n = intervals.length;
        int i = 0, count = 2; // count初始值为2，为a,b亮点
        int a = intervals[i][1] - 1, b = intervals[i][1];
        for (int[] interval : intervals) {
            if (interval[0] <= a) continue;
            else if (interval[0] <= b) {
                a = b;
                b = interval[1];
                count++;
            } else {
                count += 2;
                a = interval[1] - 1;
                b = interval[1];
            }
        }
        return count;
    }
}
/**
 * ref: LC452
 * 每个区间至少射2箭，看要多少箭把区间都射掉
 * maximum number of non-overlapping intervals => sort by ending point
 * 选区间的最后2个点，更有可能与后面的区间重合
 * 1. 新区间已经和之前最大2点重合了 => 啥都不用做
 * 2. 如果新区间只跟最大点重合 => 再加一个新区间的末位点
 * 3. 如果一点都不重合 => 取这个新区间的最后2个点
 */
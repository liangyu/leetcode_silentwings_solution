package LC1201_1500;
import java.util.*;
public class LC1272_RemoveInterval {
    /**
     * A set of real numbers can be represented as the union of several disjoint intervals, where each interval is in
     * the form [a, b). A real number x is in the set if one of its intervals [a, b) contains x (i.e. a <= x < b).
     *
     * You are given a sorted list of disjoint intervals intervals representing a set of real numbers as described
     * above, where intervals[i] = [ai, bi] represents the interval [ai, bi). You are also given another interval
     * toBeRemoved.
     *
     * Return the set of real numbers with the interval toBeRemoved removed from intervals. In other words, return the
     * set of real numbers such that every x in the set is in intervals but not in toBeRemoved. Your answer should be a
     * sorted list of disjoint intervals as described above.
     *
     * Input: intervals = [[0,2],[3,4],[5,7]], toBeRemoved = [1,6]
     * Output: [[0,1],[6,7]]
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 10^4
     * -10^9 <= ai < bi <= 10^9
     * @param intervals
     * @param toBeRemoved
     * @return
     */
    // time = O(n), space = O(1)
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        List<List<Integer>> res = new ArrayList<>();

        for (int[] interval : intervals) {
            if (interval[0] < toBeRemoved[0]) {
                res.add(Arrays.asList(interval[0], Math.min(interval[1], toBeRemoved[0])));
            }
            if (interval[1] > toBeRemoved[1]) {
                res.add(Arrays.asList(Math.max(interval[0], toBeRemoved[1]), interval[1]));
            }
        }
        return res;
    }
}
/**
 * 这道题不需要排序，只需要逐个区间去和toBeRemoved区间去比较左右端点即可。
 * 如果a和b不相交：if (a[1]<=b[0] || a[0]>=b[1]，那么就将a加入结果。
 * 剩下的情况就是a和b相交。我们需要加入结果的是a在b前面的部分，和a在b后面的部分。
 * 前者需要if (a[0]<b[0])，然后加入{a[0], min(a[1],b[0])}；
 * 后者需要if (a[1]>b[1])，然后加入{max(a[0],b[1]), a[1]}；
 * 仔细观察发现，a和b不相交的情况，其实在后面2种情况里也通过求min(a[1],b[0])和max(a[1],b[1]) 包括了，所以可以拿掉。
 */

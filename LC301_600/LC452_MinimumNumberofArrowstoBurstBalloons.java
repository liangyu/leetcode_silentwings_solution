package LC301_600;
import java.util.*;
public class LC452_MinimumNumberofArrowstoBurstBalloons {
    /**
     * There are some spherical balloons taped onto a flat wall that represents the XY-plane. The balloons are
     * represented as a 2D integer array points where points[i] = [xstart, xend] denotes a balloon whose horizontal
     * diameter stretches between xstart and xend. You do not know the exact y-coordinates of the balloons.
     *
     * Arrows can be shot up directly vertically (in the positive y-direction) from different points along the x-axis.
     * A balloon with xstart and xend is burst by an arrow shot at x if xstart <= x <= xend. There is no limit to the
     * number of arrows that can be shot. A shot arrow keeps traveling up infinitely, bursting any balloons in its path.
     *
     * Given the array points, return the minimum number of arrows that must be shot to burst all balloons.
     *
     * Input: points = [[10,16],[2,8],[1,6],[7,12]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= points.length <= 10^5
     * points[i].length == 2
     * -2^31 <= xstart < xend <= 2^31 - 1
     * @param points
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int findMinArrowShots(int[][] points) {
        // corner case
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0) return 0;

        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) return 0;
                if (o1[1] < o2[1]) return -1;
                return 1;
            }
        });
        // We can't simply use the o1[1] - o2[1] trick, as this will cause an integer overflow for very large or small values.

        int n = points.length;
        int i = 0, count = 0;
        while (i < n) {
            count++;
            int j = i + 1;
            while (j < n && points[j][0] <= points[i][1]) j++;
            i = j;
        }
        return count;
    }
}
/**
 * 区间型贪心法，通过区间排序得到意想不到的效果
 * sort by starting points: the minimum number of intervals to cover the whole range
 * sort by ending point: the maximum number of non-overlapping intervals
 * 如果倒过来看，ending point 与 starting point等价的话，两者都可以做
 * 6个non-overlapping intervals -> 至少要6根箭  ans >= m
 * 只有选中某些特定的m个non-overlapping intervals才能把其他overlapping的intervals射穿
 * 射在区间的右端点，目的是等更多的区间出现来射穿，一箭多雕
 * ref: LC435，解法是一模一样的
 */

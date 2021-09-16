package LC001_300;
import java.util.*;
public class LC296_BestMeetingPoint {
    /**
     * Given an m x n binary grid grid where each 1 marks the home of one friend, return the minimal total travel distance.
     *
     * The total travel distance is the sum of the distances between the houses of the friends and the meeting point.
     *
     * The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
     *
     * Input: grid = [[1,0,0,0,1],[0,0,0,0,0],[0,0,1,0,0]]
     * Output: 6
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 200
     * grid[i][j] is either 0 or 1.
     * There will be at least two friends in the grid.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m + n)
    public int minTotalDistance(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) xList.add(i);
            }
        }
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (grid[i][j] == 1) yList.add(j);
            }
        }
        return findMedian(xList) + findMedian(yList);
    }

    private int findMedian(List<Integer> list) {
        int left = 0, right = list.size() - 1;
        int sum = 0;
        while (left < right) {
            sum += list.get(right--) - list.get(left++);
        }
        return sum;
    }

    // S2
    // time = O(m * n + log(m * n)), space = O(m + n)
    public int minTotalDistance2(int[][] grid) {
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();

        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    x.add(i);
                    y.add(j);
                }
            }
        }
        Collections.sort(x);
        Collections.sort(y);
        int r = x.get(x.size() / 2), c = y.get(y.size() / 2);

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    res += Math.abs(i - r) + Math.abs(j - c);
                }
            }
        }
        return res;
    }
}
/**
 * min |x1-x|+|y1-y| + |x2-x|+|y2-y| + ... +|xn-x|+|yn-y|
 * => x = argmin |x1-x| + |x2-x| + ... +|xn-x|
 *      = median of {xi}
 *    y = argmin |y1-y| + |y2-y| + ... +|yn-y|
 * follow-up 1: min w1*|x1-x| + w2*|x2-x| + ... +wn*|xn-x|
 * x = weighted median of {xi}
 * 前面的权重之和 > 后面的权重之和，那么这个点就是拐点 => O(1)可以解出来
 * follow-up 2:
 * totalD = |x1-x| + |x2-x| + ... + |xn-x|
 * x = x1, x2, x3, ..., xn
 * d = ?   ?   ?        ？
 * 第一步老老实实算一遍，然后呢你每一栋一格，对前面的来说多了一格，对后面的来说每个少了这么一格 => totalD上净减 => 减掉net gain
 * 每次query都是O(1) => O(n)
 */

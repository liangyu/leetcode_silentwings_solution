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
    // time = O(m * n), space = O(m * n)
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
}

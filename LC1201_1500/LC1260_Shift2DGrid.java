package LC1201_1500;
import java.util.*;
public class LC1260_Shift2DGrid {
    /**
     * Given a 2D grid of size m x n and an integer k. You need to shift the grid k times.
     *
     * In one shift operation:
     *
     * Element at grid[i][j] moves to grid[i][j + 1].
     * Element at grid[i][n - 1] moves to grid[i + 1][0].
     * Element at grid[m - 1][n - 1] moves to grid[0][0].
     * Return the 2D grid after applying shift operation k times.
     *
     * Input: grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1
     * Output: [[9,1,2],[3,4,5],[6,7,8]]
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m <= 50
     * 1 <= n <= 50
     * -1000 <= grid[i][j] <= 1000
     * 0 <= k <= 100
     * @param grid
     * @param k
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        List<List<Integer>> res = new ArrayList<>();
        int m = grid.length, n = grid[0].length;
        Integer[][] t = new Integer[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = (i * n + j + k) % (m * n);
                int x = val / n, y = val % n;
                t[x][y] = grid[i][j];
            }
        }

        for (int i = 0; i < m; i++) {
            res.add(Arrays.asList(t[i]));
        }
        return res;
    }
}

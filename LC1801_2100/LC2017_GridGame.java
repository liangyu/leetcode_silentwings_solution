package LC1801_2100;

public class LC2017_GridGame {
    /**
     * You are given a 0-indexed 2D array grid of size 2 x n, where grid[r][c] represents the number of points at
     * position (r, c) on the matrix. Two robots are playing a game on this matrix.
     *
     * Both robots initially start at (0, 0) and want to reach (1, n-1). Each robot may only move to the right ((r, c)
     * to (r, c + 1)) or down ((r, c) to (r + 1, c)).
     *
     * At the start of the game, the first robot moves from (0, 0) to (1, n-1), collecting all the points from the cells
     * on its path. For all cells (r, c) traversed on the path, grid[r][c] is set to 0. Then, the second robot moves
     * from (0, 0) to (1, n-1), collecting the points on its path. Note that their paths may intersect with one another.
     *
     * The first robot wants to minimize the number of points collected by the second robot. In contrast, the second
     * robot wants to maximize the number of points it collects. If both robots play optimally, return the number of
     * points collected by the second robot.
     *
     * Input: grid = [[2,5,4],[1,5,1]]
     * Output: 4
     *
     * Constraints:
     *
     * grid.length == 2
     * n == grid[r].length
     * 1 <= n <= 5 * 10^4
     * 1 <= grid[r][c] <= 10^5
     * @param grid
     * @return
     */
    // time = O(n), space = O(n)
    public long gridGame(int[][] grid) {
        int n = grid[0].length;
        long[] p1 = new long[n + 1];
        long[] p2 = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            p1[i] = p1[i - 1] + grid[0][i - 1];
            p2[i] = p2[i - 1] + grid[1][i - 1];
        }

        long min = Long.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (Math.max(p1[n] - p1[i], p2[i - 1]) < min) {
                min = Math.max(p1[n] - p1[i], p2[i - 1]);
            }
        }
        return min;
    }
}

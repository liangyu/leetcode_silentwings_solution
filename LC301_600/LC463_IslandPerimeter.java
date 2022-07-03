package LC301_600;

public class LC463_IslandPerimeter {
    /**
     * You are given row x col grid representing a map where grid[i][j] = 1 represents land and grid[i][j] = 0
     * represents water.
     *
     * Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water,
     * and there is exactly one island (i.e., one or more connected land cells).
     *
     * The island doesn't have "lakes", meaning the water inside isn't connected to the water around the island. One
     * cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. Determine the
     * perimeter of the island.
     *
     * Input: grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
     * Output: 16
     *
     * Constraints:
     *
     * row == grid.length
     * col == grid[i].length
     * 1 <= row, col <= 100
     * grid[i][j] is 0 or 1.
     * @param grid
     * @return
     */
    // S1: traverse
    // time = O(m * n), space = O(1)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int islandPerimeter(int[][] grid) {
        int res = 0;
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    for (int[] dir : directions) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        if (x < 0 || x >= m || y < 0 || y >= n) res++;
                        else if (grid[x][y] == 0) res++;
                    }
                }
            }
        }
        return res;
    }

    // S2: count (最优解!!!)
    // time = O(m * n), space = O(1)
    public int islandPerimeter2(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length, res = 0;
        int island = 0, neighbor = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    island++;
                    if (i < m - 1 && grid[i + 1][j] == 1) neighbor++;
                    if (j < n - 1 && grid[i][j + 1] == 1) neighbor++;
                }
            }
        }
        return island * 4 - neighbor * 2; // self and neighbor share one same edge, meaning two sides eliminated together!
    }
}

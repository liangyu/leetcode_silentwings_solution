package LC601_900;

public class LC883_ProjectionAreaof3DShapes {
    /**
     * You are given an n x n grid where we place some 1 x 1 x 1 cubes that are axis-aligned with the x, y, and z axes.
     *
     * Each value v = grid[i][j] represents a tower of v cubes placed on top of the cell (i, j).
     *
     * We view the projection of these cubes onto the xy, yz, and zx planes.
     *
     * A projection is like a shadow, that maps our 3-dimensional figure to a 2-dimensional plane. We are viewing the
     * "shadow" when looking at the cubes from the top, the front, and the side.
     *
     * Return the total area of all three projections.
     *
     * Input: grid = [[1,2],[3,4]]
     * Output: 17
     *
     * Constraints:
     *
     * n == grid.length == grid[i].length
     * 1 <= n <= 50
     * 0 <= grid[i][j] <= 50
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m + n)
    public int projectionArea(int[][] grid) {
        int n = grid.length;
        int[] row = new int[n];
        int[] col = new int[n];
        int res = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) res++;
                row[i] = Math.max(row[i], grid[i][j]);
                col[i] = Math.max(col[i], grid[j][i]);
            }
            res += row[i] + col[i];
        }
        return res;
    }
}
/**
 * 从题目给出的示意图就可以看出，从某一个方向看过去的投影高度，完全取决于这个方向上能看到的最高的那根柱子的高度。
 * 比如对于x=i方向上，投影到y-z平面上的高度其实就是max{grid[i][j]} for j=0,1,2,...。所以对于y-z平面上的总投影面积，
 * 就是把所有的max{grid[i]}加起来就行。
 * 对于x-z平面上的总投影也是如此处理。计算每个col[j]，表示第j列上的的最大值。再把所有col[j]相加。
 * 对于x-y平面上的总投影，处理起来更为简单，就是计算grid[i][j]有多少个非零元素即可。
 */
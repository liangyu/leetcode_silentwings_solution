package LC1801_2100;
import java.util.*;
public class LC1878_GetBiggestThreeRhombusSumsinaGrid {
    /**
     * You are given an m x n integer matrix grid.
     *
     * A rhombus sum is the sum of the elements that form the border of a regular rhombus shape in grid.
     * The rhombus must have the shape of a square rotated 45 degrees with each of the corners centered in a grid cell.
     * Below is an image of four valid rhombus shapes with the corresponding colored cells that should be included in
     * each rhombus sum:
     *
     * Note that the rhombus can have an area of 0, which is depicted by the purple rhombus in the bottom right corner.
     *
     * Return the biggest three distinct rhombus sums in the grid in descending order. If there are less than three
     * distinct values, return all of them.
     *
     * Input: grid = [[3,4,5,1,3],[3,3,4,2,3],[20,30,200,40,10],[1,5,5,4,1],[4,3,2,2,5]]
     * Output: [228,216,211]
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * 1 <= grid[i][j] <= 10^5
     * @param grid
     * @return
     */
    // time = O(m * n * min(m, n)), space = O(1)
    public int[] getBiggestThree(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return new int[0];

        int m = grid.length, n= grid[0].length;
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                set.add(grid[i][j]);
                if (set.size() > 3) set.pollFirst();
            }
        }

        int k = 1;
        while (k < Math.min(m, n)) {
            for (int i = k; i < m - k; i++) {
                for (int j = k; j < n - k; j++) {
                    set.add(calSum(grid, i, j, k));
                    if (set.size() > 3) set.pollFirst();
                }
            }
            k++;
        }

        int[] res = new int[set.size()];
        for (int i = 0; i < res.length; i++) res[i] = set.pollLast();
        return res;
    }

    private int calSum(int[][] grid, int i, int j, int k) {
        int sum = 0;
        int x = i, y = j - k;

        // upper left
        while (x > i - k && y < j) sum += grid[x--][y++];

        // upper right
        while (x < i && y < j + k) sum += grid[x++][y++];

        // bottom right
        while (x < i + k && y > j) sum += grid[x++][y--];

        // bottom left
        while (x > i && y  > j - k) sum += grid[x--][y--];

        return sum;
    }
}

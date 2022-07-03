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
    // time = O(m * n * min(m, n) * min(m, n)), space = O(1)
    public int[] getBiggestThree(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return new int[0];

        int m = grid.length, n = grid[0].length;
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int R = Math.min(i, j);
                R = Math.min(R, m - 1 - i);
                R = Math.min(R, n - 1 - j);

                set.add(grid[i][j]);
                for (int r = 1; r <= R; r++) {
                    int a = i - r, b = j;
                    int sum = 0;

                    for (int k = 0; k < r; k++) {
                        a += 1;
                        b -= 1;
                        sum += grid[a][b];
                    }
                    for (int k = 0; k < r; k++) {
                        a += 1;
                        b += 1;
                        sum += grid[a][b];
                    }
                    for (int k = 0; k < r; k++) {
                        a -= 1;
                        b += 1;
                        sum += grid[a][b];
                    }
                    for (int k = 0; k < r; k++) {
                        a -= 1;
                        b -= 1;
                        sum += grid[a][b];
                    }
                    set.add(sum);
                    if (set.size() > 3) set.pollFirst(); // limit the treeSet in size of 3
                }
            }
        }

        int[] res = new int[Math.min(3, set.size())];
        for (int i = 0; i < res.length; i++) {
            res[i] = set.pollLast();
        }
        return res;
    }

    // S2: presum (optimization)
    // time = O(m * n * min(m, n)), space = O(1)
    public int[] getBiggestThree2(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return new int[0];

        int m = grid.length, n = grid[0].length;
        TreeSet<Integer> set = new TreeSet<>();
        int[][] presum1 = new int[m][n]; // "\"
        int[][] presum2 = new int[m][n]; // "/"

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                presum1[i][j] = ((i - 1 >= 0 && j - 1 >= 0) ? presum1[i - 1][j - 1] : 0) + grid[i][j];
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = n - 1; j >= 0; j--) {
                presum2[i][j] = ((i - 1 >= 0 && j + 1 < n) ? presum2[i - 1][j + 1] : 0) + grid[i][j];
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int R = Math.min(i, j);
                R = Math.min(R, m - 1 - i);
                R = Math.min(R, n - 1 - j);

                set.add(grid[i][j]);
                for (int r = 1; r <= R; r++) {
                    int sum = 0;
                    int x1, y1, x2, y2;

                    x1 = i - r; y1 = j;
                    x2 = i; y2 = j + r;
                    sum += presum1[x2][y2] - ((x1 - 1 >= 0 && y1 - 1 >= 0) ? presum1[x1 - 1][y1 - 1] : 0);

                    x1 = i; y1 = j - r;
                    x2 = i + r; y2 = j;
                    sum += presum1[x2][y2] - ((x1 - 1 >= 0 && y1 - 1 >= 0) ? presum1[x1 - 1][y1 - 1] : 0);

                    x1 = i - r; y1 = j;
                    x2 = i; y2 = j - r;
                    sum += presum2[x2 - 1][y2 + 1] - presum2[x1][y1];

                    x1 = i; y1 = j + r;
                    x2 = i + r; y2 = j;
                    sum += presum2[x2 - 1][y2 + 1] - presum2[x1][y1];

                    set.add(sum);
                    if (set.size() > 3) set.pollFirst(); // limit the treeSet in size of 3
                }
            }
        }

        int[] res = new int[Math.min(3, set.size())];
        for (int i = 0; i < res.length; i++) {
            res[i] = set.pollLast();
        }
        return res;
    }
}
/**
 * 以中心点来枚举所有可能的菱形 => O(m * n * m * n) 暴力解
 * 优化：区间和 -> 用前缀和数组
 * 如何高效的遍历菱形 => 遍历中心，然后确定4个顶点来走
 */
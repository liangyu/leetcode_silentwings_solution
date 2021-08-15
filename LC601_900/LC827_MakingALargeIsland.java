package LC601_900;
import java.util.*;
public class LC827_MakingALargeIsland {
    /**
     * You are given an n x n binary matrix grid. You are allowed to change at most one 0 to be 1.
     *
     * Return the size of the largest island in grid after applying this operation.
     *
     * An island is a 4-directionally connected group of 1s.
     *
     * Input: grid = [[1,0],[0,1]]
     * Output: 3
     *
     * Constraints:
     *
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 500
     * grid[i][j] is either 0 or 1.
     * @param grid
     * @return
     */
    // time = O(n^2), space = O(n^2)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int largestIsland(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        // step 1: dfs each island and give each island an index
        int n  = grid.length, index = 2, res = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    map.put(index, dfs(grid, i, j, index));
                    res = Math.max(res, map.get(index++));
                }
            }
        }

        // step 2: traverse each 0 cell and count the biggest island it can connect
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    HashSet<Integer> set = new HashSet<>();
                    int cur = 1;
                    for (int[] dir : DIRECTIONS) {
                        int ii = i + dir[0];
                        int jj = j + dir[1];
                        if (ii >= 0 && ii < n && jj >= 0 && jj < n) {
                            index = grid[ii][jj]; // 因为grid[i][j]只有0或者1，为了区别开index,index必须从2开始！
                            if (index > 1 && set.add(index)) {
                                cur += map.get(index);
                            }
                        }
                    }
                    res = Math.max(res, cur);
                }
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int i, int j, int index) {
        int n = grid.length, area = 0;
        grid[i][j] = index; // coloring the island with index!
        for (int[] dir : DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            if (ii >= 0 && ii < n && jj >= 0 && jj < n) {
                if (grid[ii][jj] == 1) {
                    area += dfs(grid, ii, jj, index);
                }
            }
        }
        return area + 1;
    }
}

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
    int n;
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int largestIsland(int[][] grid) {
        // step 1: dfs each island and give each island an index
        n = grid.length;
        int index = 2, res = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int area = dfs(grid, i, j, index);
                    map.put(index++, area);
                    res = Math.max(res, area); // 有可能grid里没有0， 所以res要在一开始遍历原始island的时候就要更新！
                }
            }
        }

        // step 2: traverse each 0 cell and count the biggest island it can connect
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    HashSet<Integer> set = new HashSet<>();
                    int area = 1;
                    for (int[] dir : directions) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        if (x < 0 || x >= n || y < 0 || y >= n) continue;
                        if (grid[x][y] == 0) continue;
                        index = grid[x][y]; // 因为grid[i][j]只有0或者1，为了区别开index,index必须从2开始！
                        if (set.add(index)) area += map.get(index); // 注意查重，防止四周连接上的是同一个岛。
                    }
                    res = Math.max(res, area);
                }
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int i, int j, int index) {
        // base case
        if (i < 0 || i >= n || j < 0 || j >= n) return 0;
        if (grid[i][j] != 1) return 0;

        grid[i][j] = index; // coloring the island with index!
        int area = 1;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            area += dfs(grid, x, y, index);
        }
        return area;
    }
}

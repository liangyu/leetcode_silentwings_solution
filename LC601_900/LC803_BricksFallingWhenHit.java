package LC601_900;
import java.util.*;
public class LC803_BricksFallingWhenHit {
    /**
     * You are given an m x n binary grid, where each 1 represents a brick and 0 represents an empty space. A brick is
     * stable if:
     *
     * It is directly connected to the top of the grid, or
     * At least one other brick in its four adjacent cells is stable.
     * You are also given an array hits, which is a sequence of erasures we want to apply. Each time we want to erase
     * the brick at the location hits[i] = (rowi, coli). The brick on that location (if it exists) will disappear.
     * Some other bricks may no longer be stable because of that erasure and will fall. Once a brick falls, it is
     * immediately erased from the grid (i.e., it does not land on other stable bricks).
     *
     * Return an array result, where each result[i] is the number of bricks that will fall after the ith erasure is
     * applied.
     *
     * Note that an erasure may refer to a location with no brick, and if it does, no bricks drop.
     *
     * Input: grid = [[1,0,0,0],[1,1,1,0]], hits = [[1,0]]
     * Output: [2]
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 200
     * grid[i][j] is 0 or 1.
     * 1 <= hits.length <= 4 * 10^4
     * hits[i].length == 2
     * 0 <= xi <= m - 1
     * 0 <= yi <= n - 1
     * All (xi, yi) are unique.
     * @param grid
     * @param hits
     * @return
     */
    // S1: Union Find
    // time = O((k + m * n) * log(m * n)), space = O(m * n) alpha: Inverse-Ackermann function
    int m, n;
    int[] parent;
    int[] size;
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int[] hitBricks(int[][] grid, int[][] hits) {
        m = grid.length;
        n = grid[0].length;
        parent = new int[m * n];
        size = new int[m * n];
        for (int i = 0; i < m * n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // produce the last scene
        for (int[] hit : hits) {
            int a = hit[0], b = hit[1];
            grid[a][b] *= -1; // grid: 0 empty; 1 suspended brick; -1 hit
        }

        // union in the last scene
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 1) continue;
                for (int[] dir : directions) {
                    int x = i + dir[0];
                    int y = j + dir[1];
                    if (x < 0 || x >= m || y < 0 || y >= n) continue;
                    if (grid[x][y] != 1) continue;

                    int u = i * n + j, v = x * n + y;
                    if (findParent(u) != findParent(v)) union(u, v);
                }
            }
        }

        // time back
        int[] res = new int[hits.length];
        for (int t = hits.length - 1; t >= 0; t--) {
            int i = hits[t][0], j = hits[t][1];
            // check if (i, j) has brick or not before hits
            if (grid[i][j] == 0) {
                res[t] = 0;
                continue;
            }
            grid[i][j] = 1; // recover back to 1
            int count = 0;
            boolean flag = i == 0; // check if the hit piece was attached to the ceiling itself
            for (int[] dir : directions) {
                int x = i + dir[0];
                int y = j + dir[1];
                if (x < 0 || x >= m || y < 0 || y >= n) continue;
                if (grid[x][y] != 1) continue;

                int u = i * n + j, v = x * n + y;
                if (findParent(u) != findParent(v)) {
                    // check parent if in the first row (ceiling)
                    if (findParent(v) < n) flag = true; // check if (x, y) was attached to the ceiling
                    if (findParent(v) >= n) {
                        count += size[findParent(v)]; // need to know the member size in this rescued group
                    }
                    union(u, v);
                }
            }

            if (flag) res[t] = count;
            else res[t] = 0; // has no meaning, not attached to the ceiling
        }
        return res;
    }

    private int findParent(int x) {
        if (parent[x] != x) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) {
            parent[y] = x; // 小的作为族长
            size[x] += size[y];
        }
        else {
            parent[x] = y;
            size[y] += size[x];
        }
    }

    // S2: DFS
//    int m, n;
//    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int[] hitBricks2(int[][] grid, int[][] hits) {
        m = grid.length;
        n = grid[0].length;
        for (int[] hit : hits) {
            int a = hit[0], b = hit[1];
            grid[a][b] *= -1;
        }

        // grid: 0 empty; 1 suspended brick; 2 ceiling brick; -1 hit
        for (int j = 0; j < n; j++) {
            if (grid[0][j] == 1) { // first row
                dfs(grid, 0, j); // mark as 2
            }
        }

        int[] res = new int[hits.length];
        // time backtrack
        for (int t = hits.length - 1; t >= 0; t--) {
            int i = hits[t][0], j = hits[t][1];
            if (grid[i][j] != -1) { // fake point -> original empty spot
                res[t] = 0;
                continue;
            }

            boolean connectCeil = (i == 0); // the deleted point might be at ceiling originally
            for (int[] dir : directions) {
                int x = i + dir[0];
                int y = j + dir[1];
                if (x < 0 || x >= m || y < 0 || y >= n) continue;
                if (grid[x][y] == 2) {
                    connectCeil = true;
                    break;
                }
            }

            if (connectCeil) {
                res[t] = dfs(grid, i, j) - 1; // can't count in the recovered one
            } else {
                grid[i][j] = 1;
                res[t] = 0;
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int x, int y) {
        grid[x][y] = 2;
        int count = 1;
        for (int[] dir : directions) {
            int i = x + dir[0];
            int j = y + dir[1];
            if (i < 0 || i >= m || j < 0 || j >= n) continue;
            if (grid[i][j] == 1) {
                count += dfs(grid, i, j);
            }
        }
        return count;
    }
}
/**
 * ref: LC1970 时光倒流
 * 看最后一次
 * 如果它被接上去 -> union find
 * union之后会出现，使得这块又接到天花板上去了
 * 接上去之后，连接天花板的砖块会多几个 => 意味着补上去的这块能让这么多块相连，也就是说敲掉这块会造成这么多砖块的掉落
 * 小细节：原来没有砖块，hit是没有意义的，时光倒流的时候注意不能把它恢复成1
 * 看族长的id是否< n，是的话就是表示接在天花板上的，否则就不是,说明当初是掉落的，我们要拼接的
 * 注意另一种情况：如果被恢复的这块砖本身就在第一行的话，那么它也是可以拯救下面的砖块的 => i == 0
 */

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
    // time = O((k + m * n) * log(m * n)), space = O(m * n) alpha: Inverse-Ackermann function
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int[] parent;
    private int[] size;
    private int m, n;
    public int[] hitBricks(int[][] grid, int[][] hits) {
        m = grid.length;
        n = grid[0].length;
        int[][] backup = new int[m][n];
        for (int i = 0; i < m; i++) backup[i] = grid[i].clone(); // 逐行deep copy 2D matrix

        parent = new int[m * n];
        size = new int[m * n];
        for (int i = 0; i < m * n; i++) { // O(m * n)
            parent[i] = i; // (i, j) -> i * n + j
            size[i] = 1;
        }

        for (int[] hit : hits) { // O(k)
            grid[hit[0]][hit[1]] = 0;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                for (int[] dir : DIRECTIONS) {
                    int x = i + dir[0];
                    int y = j + dir[1];
                    if (x < 0 || x >= m || y < 0 || y >= n) continue;
                    if (grid[x][y] == 0) continue;
                    if (findParent(i * n + j) != findParent(x * n + y)) {
                        union(i * n + j, x * n + y);
                    }
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int t = hits.length - 1; t >= 0; t--) { // O(k)
            int i = hits[t][0], j = hits[t][1];
            if (backup[i][j] == 0) { // (i, j) is empty originally, not being empty because of hit
                res.add(0);
                continue;
            }
            grid[i][j] = 1; // recover the brick
            int count = 0;
            int flag = 0;
            for (int[] dir : DIRECTIONS) {
                int x = i + dir[0];
                int y = j + dir[1];
                if (x < 0 || x >= m || y < 0 || y >= n) continue;
                if (grid[x][y] == 0) continue;
                if (findParent(i * n + j) != findParent(x * n + y)) { // O(log(m * n))
                    if (findParent(x * n + y) < n || i == 0) flag = 1;
                    if (findParent(x * n + y) >= n) count += size[findParent(x * n + y)];
                    union(i * n + j, x * n + y);
                }
            }
            res.add(flag == 1 ? count : 0);
        }

        Collections.reverse(res);
        int[] ans = new int[res.size()];
        int k = 0;
        for (int x : res) ans[k++] = x;
        return ans;
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

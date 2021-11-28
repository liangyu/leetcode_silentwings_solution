package LC1801_2100;
import java.util.*;
public class LC1970_LastDayWhereYouCanStillCross {
    /**
     * There is a 1-based binary matrix where 0 represents land and 1 represents water. You are given integers row and
     * col representing the number of rows and columns in the matrix, respectively.
     *
     * Initially on day 0, the entire matrix is land. However, each day a new cell becomes flooded with water. You are
     * given a 1-based 2D array cells, where cells[i] = [ri, ci] represents that on the ith day, the cell on the rith
     * row and cith column (1-based coordinates) will be covered with water (i.e., changed to 1).
     *
     * You want to find the last day that it is possible to walk from the top to the bottom by only walking on land
     * cells. You can start from any cell in the top row and end at any cell in the bottom row. You can only travel
     * in the four cardinal directions (left, right, up, and down).
     *
     * Return the last day where it is possible to walk from the top to the bottom by only walking on land cells.
     *
     * Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= row, col <= 2 * 10^4
     * 4 <= row * col <= 2 * 10^4
     * cells.length == row * col
     * 1 <= ri <= row
     * 1 <= ci <= col
     * All the values of cells are unique.
     * @param row
     * @param col
     * @param cells
     * @return
     */
    // S1: BS + BFS
    // time = O(m * n * log(m * n)), space = O(m * n)
    public int latestDayToCross(int row, int col, int[][] cells) {
        // corner case
        if (cells == null || cells.length == 0) return -1;

        int left = 1, right = cells.length;
        while (left < right) { // O(logk)
            int mid = right - (right - left) / 2;
            if (canCross(cells, row, col, mid)) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private boolean canCross(int[][] cells, int m, int n, int day) {
        int[][] grid = new int[m][n];
        for (int i = 0; i < day; i++) grid[cells[i][0] - 1][cells[i][1] - 1] = 1; // O(k)

        Queue<Integer> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        for (int j = 0; j < n; j++) {  // O(n)
            if (grid[0][j] == 0) {
                queue.offer(j);
                visited[0][j] = true;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int i = cur / n, j = cur % n;
            if (i == m - 1) return true;
            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii >= 0 && ii < m && jj >= 0 && jj < n && !visited[ii][jj] && grid[ii][jj] == 0) {
                    queue.offer(ii * n + jj);
                    visited[ii][jj] = true;
                }
            }
        }
        return false;
    }

    // S2: Union Find
    // time = O(m * n * a(m * n)), space = O(m * n)
    private int[] parent;
    public int latestDayToCross2(int row, int col, int[][] cells) {
        int m = row, n = col;
        parent = new int[m * n + 2];
        for (int i = 0; i < m * n + 2; i++) parent[i] = i;

        // build matrix
        int[][] mat = new int[m][n]; // 0 is land, init -> all 0
        for (int[] cell : cells) {
            mat[cell[0] - 1][cell[1] - 1] = 1;
        }

        for (int j = 0; j < n; j++) {
            union(0 * n + j, m * n); // top
        }

        for (int j= 0; j < n; j++) {
            union((m - 1) * n + j, m * n + 1); // bottom
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) continue;
                for (int[] dir : DIRECTIONS) {
                    int x = i + dir[0];
                    int y = j + dir[1];
                    if (x < 0 || x >= m || y < 0 || y >= n) continue;
                    if (mat[x][y] == 1) continue;
                    if (findParent(i * n + j) != findParent(x * n + y)) {
                        union(i * n + j, x * n + y);
                    }
                }
            }
        }

        // 时光倒流
        for (int t = cells.length - 1; t >= 0; t--) {
            if (findParent(m * n) == findParent(m * n + 1)) return t + 1;
            // recover the box
            int i = cells[t][0] - 1;
            int j = cells[t][1] - 1;
            mat[i][j] = 0; // water -> land
            for (int[] dir : DIRECTIONS) {
                int x = i + dir[0];
                int y = j + dir[1];
                if (x < 0 || x >= m || y < 0 || y >= n) continue;
                if (mat[x][y] == 1) continue;
                if (findParent(i * n + j) != findParent(x * n + y)) {
                    union(i * n + j, x * n + y);
                }
            }
        }
        return 0;
    }

    private int findParent(int x) {
        if (parent[x] != x) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
/**
 * ref: LC803
 * BS -> 重构下地图，看能不能走过去，一直二分到某天恰好能够走完为止
 * time = O(logD*(m*n+D)=log(10^4)*10^4
 * 时光倒流法 -> union find
 * 每往前走一个，就会有水变成陆地，新格子周围有陆地的话，就会union
 * 如果时光倒流，每走一步，有个新格子做了新的union
 * 我们只要看有没有任意一个第一行的格子与任意一个最后一行的格子最后被union起来了。
 * 这个更直接，从后往前
 *
 * add a fake point top that has already been unioned with all of boxes in the first row
 * same thing for the bottom
 * as long as any path between top and bottom is unioned
 * 设置两个虚拟结点top and bottom，看这2个结点是否能链接在一起
 * 对于这种逐个逐个抽去一些东西，有这种题型的可以考虑"时光倒流"的解法！！！
 */

package LC301_600;
import java.util.*;
public class LC417_PacificAtlanticWaterFlow {
    /**
     * Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent,
     * the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and
     * bottom edges.
     *
     * Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal
     * or lower.
     *
     * Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.
     *
     * Note:
     *
     * The order of returned grid coordinates does not matter.
     * Both m and n are less than 150.
     *
     * Given the following 5x5 matrix:
     *
     *   Pacific ~   ~   ~   ~   ~
     *        ~  1   2   2   3  (5) *
     *        ~  3   2   3  (4) (4) *
     *        ~  2   4  (5)  3   1  *
     *        ~ (6) (7)  1   4   5  *
     *        ~ (5)  1   1   2   4  *
     *           *   *   *   *   * Atlantic
     *
     * Return:
     *
     * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
     * @param matrix
     * @return
     */
    // S1: bfs
    // time = O(m + n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return res;

        int m = matrix.length, n = matrix[0].length;
        Queue<Integer> queue = new LinkedList<>();
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        AddPacificPoints(matrix, queue, pacific);
        bfs(matrix, queue, pacific, atlantic, res);

        AddAtlanticPoint(matrix, queue, atlantic);
        bfs(matrix, queue, atlantic, pacific, res);

        return res;
    }

    private void AddPacificPoints(int[][] matrix, Queue<Integer> queue, boolean[][] pacific) {
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            queue.offer(i * n);
            pacific[i][0] = true;
        }
        for (int j = 1; j < n; j++) {
            queue.offer(j);
            pacific[0][j] = true;
        }
    }

    private void AddAtlanticPoint(int[][] matrix, Queue<Integer> queue, boolean[][] atlantic) {
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            queue.offer(i * n + n - 1);
            atlantic[i][n - 1] = true;
        }
        for (int j = 0; j < n - 1; j++) {
            queue.offer((m - 1) * n + j);
            atlantic[m - 1][j] = true;
        }
    }

    private void bfs(int[][] matrix, Queue<Integer> queue, boolean[][] self, boolean[][] other, List<List<Integer>> res) {
        int m = matrix.length, n = matrix[0].length;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int i = cur / n, j = cur % n;
            if (other[i][j]) res.add(Arrays.asList(i, j));
            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii >= 0 && ii < m && jj >= 0 && jj < n && matrix[ii][jj] >= matrix[i][j] && !self[ii][jj]) {
                    queue.offer(ii * n + jj);
                    self[ii][jj] = true;
                }
            }
        }
    }

    // S2: dfs
    // time = O(m + n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public List<List<Integer>> pacificAtlantic2(int[][] heights) {
        List<List<Integer>> res = new ArrayList<>();
        int m = heights.length, n = heights[0].length;
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        for (int i = 0; i < m; i++) dfs(heights, i, 0, pacific);
        for (int j = 0; j < n; j++) dfs(heights, 0, j, pacific);
        for (int i = 0; i < m; i++) dfs(heights, i, n - 1, atlantic);
        for (int j = 0; j < n; j++) dfs(heights, m - 1, j, atlantic);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }
        return res;
    }

    private void dfs(int[][] heights, int i, int j, boolean[][] sea) {
        int m = heights.length, n = heights[0].length;

        sea[i][j] = true;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x < 0 || x >= m || y < 0 || y >= n) continue;
            if (sea[x][y]) continue;
            if (heights[x][y] < heights[i][j]) continue;
            dfs(heights, x, y, sea);
        }
    }
}

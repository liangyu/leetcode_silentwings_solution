package LC301_600;
import java.util.*;
public class LC542_01Matrix {
    /**
     * Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.
     *
     * The distance between two adjacent cells is 1.
     *
     * Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
     * Output: [[0,0,0],[0,1,0],[0,0,0]]
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 10^4
     * 1 <= m * n <= 10^4
     * mat[i][j] is either 0 or 1.
     * There is at least one 0 in mat.
     * @param mat
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int[][] updateMatrix(int[][] mat) {
        // corner case
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) return null;

        int m = mat.length, n = mat[0].length;
        int[][] res = new int[m][n];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    queue.offer(i * n + j);
                }
            }
        }

        int step = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int i = cur / n, j = cur % n;
                for (int[] dir : DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii >= 0 && ii < m && jj >= 0 && jj < n && mat[ii][jj] == 1 && res[ii][jj] == 0) {
                        queue.offer(ii * n + jj);
                        res[ii][jj] = step;
                    }
                }
            }
            step++;
        }
        return res;
    }
}

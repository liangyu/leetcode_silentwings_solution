package LC1801_2100;
import java.util.*;
public class LC1886_DetermineWhetherMatrixCanBeObtainedByRotation {
    /**
     * Given two n x n binary matrices mat and target, return true if it is possible to make mat equal to target by
     * rotating mat in 90-degree increments, or false otherwise.
     *
     * Input: mat = [[0,1],[1,0]], target = [[1,0],[0,1]]
     * Output: true
     *
     * Constraints:
     *
     * n == mat.length == target.length
     * n == mat[i].length == target[i].length
     * 1 <= n <= 10
     * mat[i][j] and target[i][j] are either 0 or 1.
     * @param mat
     * @param target
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public boolean findRotation(int[][] mat, int[][] target) {
        // corner case
        if (mat == null || mat.length == 0 || target == null || target.length == 0) return false;

        for (int i = 0; i < 4; i++) {
            rotate(mat);
            if (isSame(mat, target)) return true;
        }
        return false;
    }

    private void rotate(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][m - 1 - i] = mat[i][j];
            }
        }
        for (int i = 0; i < m; i++) {
            mat[i] = res[i].clone();
        }
    }

    private boolean isSame(int[][] mat, int[][] target) {
        int m = mat.length, n = mat[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] != target[i][j]) return false;
            }
        }
        return true;
    }
}

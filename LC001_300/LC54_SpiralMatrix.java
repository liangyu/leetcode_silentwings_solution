package LC001_300;
import java.util.*;
public class LC54_SpiralMatrix {
    /**
     * Given an m x n matrix, return all elements of the matrix in spiral order.
     *
     * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: [1,2,3,6,9,8,7,4,5]
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 10
     * -100 <= matrix[i][j] <= 100
     * @param matrix
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return res;

        int rowBegin = 0, rowEnd = matrix.length - 1;
        int colBegin = 0, colEnd = matrix[0].length - 1;

        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            for (int i = colBegin; i <= colEnd; i++) res.add(matrix[rowBegin][i]);
            rowBegin++;

            for (int i = rowBegin; i <= rowEnd; i++) res.add(matrix[i][colEnd]);
            colEnd--;

            if (rowBegin <= rowEnd) {  // 注意：这里要加上判断，否则不用继续下去！！！
                for (int i = colEnd; i >= colBegin; i--) res.add(matrix[rowEnd][i]);
            }
            rowEnd--;

            if (colBegin <= colEnd) { // 注意：这里要加上判断，否则不用继续下去！！！
                for (int i = rowEnd; i >= rowBegin; i--) res.add(matrix[i][colBegin]);
            }
            colBegin++;
        }
        return res;
    }

    // S2
    // time = O(m * n), space = O(1)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (matrix == null || matrix.length == 0) return res;

        int m = matrix.length, n = matrix[0].length;
        int i = 0, j = 0, d = 1;

        for (int k = 0; k < m * n; k++) {
            res.add(matrix[i][j]);
            matrix[i][j] = Integer.MAX_VALUE;
            int x = i + directions[d][0];
            int y = j + directions[d][1];
            if (x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] == Integer.MAX_VALUE) {
                d = (d + 1) % 4;
                x = i + directions[d][0];
                y = j + directions[d][1];
            }
            i = x;
            j = y;
        }
        return res;
    }
}

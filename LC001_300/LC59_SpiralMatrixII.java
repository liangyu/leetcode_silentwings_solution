package LC001_300;
import java.util.*;
public class LC59_SpiralMatrixII {
    /**
     * Given a positive integer n, generate an n x n matrix filled with elements from 1 to n2 in spiral order.
     *
     * Input: n = 3
     * Output: [[1,2,3],[8,9,4],[7,6,5]]
     *
     * Constraints:
     *
     * 1 <= n <= 20
     * @param n
     * @return
     */
    // time = O(n^2), space = O(1)
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];

        int rowBegin = 0, rowEnd = n - 1;
        int colBegin = 0, colEnd = n - 1;

        int num = 1;
        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            for (int i = colBegin; i <= colEnd; i++) {
                res[rowBegin][i] = num++;
            }
            rowBegin++;

            for (int i = rowBegin; i <= rowEnd; i++) {
                res[i][colEnd] = num++;
            }
            colEnd--;

            for (int i = colEnd; i >= colBegin; i--) {
                res[rowEnd][i] = num++;
            }
            rowEnd--;

            for (int i = rowEnd; i >= rowBegin; i--) {
                res[i][colBegin] = num++;
            }
            colBegin++;
        }
        return res;
    }
}

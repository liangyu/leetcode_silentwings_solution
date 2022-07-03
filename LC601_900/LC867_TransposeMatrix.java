package LC601_900;

public class LC867_TransposeMatrix {
    /**
     * Given a 2D integer array matrix, return the transpose of matrix.
     *
     * The transpose of a matrix is the matrix flipped over its main diagonal, switching the matrix's row and column
     * indices.
     *
     * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: [[1,4,7],[2,5,8],[3,6,9]]
     *
     * Input: matrix = [[1,2,3],[4,5,6]]
     * Output: [[1,4],[2,5],[3,6]]
     *
     *
     Constraints:

     m == matrix.length
     n == matrix[i].length
     1 <= m, n <= 1000
     1 <= m * n <= 10^5
     -10^9 <= matrix[i][j] <= 10^9
     * @param matrix
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int[][] transpose(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] res = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][i] = matrix[i][j];
            }
        }
        return res;
    }
}

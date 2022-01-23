package LC2100_2400;

public class LC2133_CheckifEveryRowandColumnContainsAllNumbers {
    /**
     * An n x n matrix is valid if every row and every column contains all the integers from 1 to n (inclusive).
     *
     * Given an n x n integer matrix matrix, return true if the matrix is valid. Otherwise, return false.
     *
     * Input: matrix = [[1,2,3],[3,1,2],[2,3,1]]
     * Output: true
     *
     * Constraints:
     *
     * n == matrix.length == matrix[i].length
     * 1 <= n <= 100
     * 1 <= matrix[i][j] <= n
     * @param matrix
     * @return
     */
    // time = O(n^2), space = O(1)
    public boolean checkValid(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            int r = 0, c = 0;
            for (int j = 0; j < n; j++) {
                r ^= matrix[i][j] ^ (j + 1);
                c ^= matrix[j][i] ^ (j + 1);
            }
            if (r != 0 || c != 0) return false;
        }
        return true;
    }
}

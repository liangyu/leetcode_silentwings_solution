package LC001_300;
import java.util.*;
public class LC48_RotateImage {
    /**
     * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
     *
     * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT
     * allocate another 2D matrix and do the rotation.
     *
     * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: [[7,4,1],[8,5,2],[9,6,3]]
     *
     * Constraints:
     *
     * matrix.length == n
     * matrix[i].length == n
     * 1 <= n <= 20
     * -1000 <= matrix[i][j] <= 1000
     * @param matrix
     */
    // S1
    // time = O(n^2), space = O(1)
    public void rotate(int[][] matrix) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return;

        int n = matrix.length;

        for (int i = 0; i < n / 2; i++) {
            int first = i, last = n - 1 - i;
            for (int j = first; j < last; j++) {
                int offset = j - i;

                // save top
                int top = matrix[first][j];

                // left -> top
                matrix[first][j] = matrix[last - offset][first];

                // bottom -> left
                matrix[last - offset][first] = matrix[last][last - offset];

                // right - > bottom
                matrix[last][last - offset] = matrix[j][last];

                // top -> right
                matrix[j][last] = top;
            }
        }
    }
    // S2: transpose + exchange
    // time = O(n^2), space = O(1)
    public void rotate2(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) { // 转置一半的点，在这里必须要让 j < i
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];

                matrix[j][i] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) { // 同样对换一半的点， j < n/2
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
}
/**
 * (i, j) -> (j, n-1-i)
 * transpose 转置 横 -> 竖 (in-place)
 * A'[i][j] = A[j][i]  非常容易做in-place 做swap就行
 * A: 1 2 3 4
 * B: 5 6 7 8
 * step 1: 转置
 * step 2: 左右对换 => 只要遍历一半的点，找它小伙伴的点翻转，否则翻转2次就又回来了
 * follow-up:
 * if asked for anti-clockwise, then for transpose:
 * for (int j = 0; j <= n - 1 - i; j++) swap(matrix[i][j], matrix[n - 1 - j][n - 1 - i])
 */


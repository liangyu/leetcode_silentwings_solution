package LC301_600;
import java.util.*;
public class LC498_DiagonalTraverse {
    /**
     * Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as
     * shown in the below image.
     *
     * Input:
     * [
     *  [ 1, 2, 3 ],
     *  [ 4, 5, 6 ],
     *  [ 7, 8, 9 ]
     * ]
     *
     * Output:  [1,2,4,7,5,3,6,8,9]
     *
     * @param mat
     * @return
     */
    // S1
    // time = O(m * n), space = O(1)
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[] res = new int[m * n];
        int[][] directions = new int[][]{{-1, 1}, {1, -1}};

        int x = 0, y = 0, k = 0;
        for (int i = 0; i < m * n; i++) {
            res[i] = mat[x][y];

            x += directions[k][0];
            y += directions[k][1];
            if (y >= n) {
                x += 2;
                y = n - 1;
                k = 1 - k;
            }

            if (x >= m) {
                x = m - 1;
                y += 2;
                k = 1 - k;
            }
            if (x < 0) {
                x = 0;
                k = 1 - k;
            }
            if (y < 0) {
                y = 0;
                k = 1 - k;
            }
        }
        return res;
    }

    // S2
    // time = O(m * n), space = O(1)   每个元素遍历一次，所以时间是O(m * n)
    public int[] findDiagonalOrder2(int[][] matrix) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return new int[0];

        int row = matrix.length, col = matrix[0].length;
        int[] res = new int[row * col];
        int count = row + col - 1; // 总共要遍历(row + col - 1)次
        int i = 0, j = 0, k = 0;

        for (int c = 0; c < count; c++) {
            if (c % 2 == 0) { // case 1: 向右上方遍历，比如0：1 (右上); 1：2 -> 4(左下); 2: 7 -> 5 -> 3 (右上)
                while (i >= 0 && j < col) { // 7 -> 5 -> 3   ==> i--, j++ -> 边界：i >= 0, j < col
                    res[k++] = matrix[i--][j++]; // 右上
                }
                if (j < col) i++; // 出界1: 1往后出界 -> 在2的顶部 -> 拉回到2 -> i++，j不变
                else { // 出界2：3往后出界 -> 拉回到6 -> i = i + 2, j = j - 1
                    i += 2;
                    j--;
                }
            } else { // case 2: 向左下方遍历
                while (i < row && j >= 0) {  // 6 -> 8 -> i++, j--
                    res[k++] = matrix[i++][j--];
                }
                if (i < row) j++; // 出界1：4往前出界 -> 在7的左边 -> 拉回到7 -> j++
                else { // 出界2： 8往前出界 -> 在7的下方 -> 拉回到9 -> j = j + 2, i = i - 1
                    i--;
                    j += 2;
                }
            }
        }
        return res;
    }

    // S3:
    // time = O(n), space = O(1)
    public int[] findDiagonalOrder3(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[] res = new int[m * n];
        int idx = 0;
        for (int i = 0; i < n + m - 1; i++) { // 对角线总数 = m + n - 1
            if (i % 2 == 0) {
                // 看最后一条对角线，列数一定是n - 1, 那么根据性质row + col = i => row = i - (n - 1) = 1 - n + i
                for (int j = Math.min(i, m - 1); j >= Math.max(0, 1 - n + i); j--) {
                    res[idx++] = mat[j][i - j]; // row = j, col = i - row = i - j
                }
            } else {
                for (int j = Math.max(0, 1 - n + i); j <= Math.min(i, m - 1); j++) {
                    res[idx++] = mat[j][i - j];
                }
            }
        }
        return res;
    }
}

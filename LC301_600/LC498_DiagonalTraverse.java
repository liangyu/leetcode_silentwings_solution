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
     * @param matrix
     * @return
     */
    // time = O(m * n), space = O(1)   每个元素遍历一次，所以时间是O(m * n)
    public int[] findDiagonalOrder(int[][] matrix) {
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
}

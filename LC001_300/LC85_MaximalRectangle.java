package LC001_300;
import java.util.*;
public class LC85_MaximalRectangle {
    /**
     * Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and
     * return its area.
     *
     * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
     * Output: 6
     *
     * Constraints:
     *
     * rows == matrix.length
     * cols == matrix[i].length
     * 0 <= row, cols <= 200
     * matrix[i][j] is '0' or '1'.
     * @param matrix
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int maximalRectangle(char[][] matrix) {
        // corner case
        if (matrix == null || matrix.length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;
        int res = 0;
        int[] row = new int[n + 2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') row[j + 1]++;
                else row[j + 1] = 0;
            }
            int area = largestRectangleArea(row);
            res = Math.max(res, area);
        }
        return res;
    }

    private int largestRectangleArea(int[] row) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int area = 0;

        for (int i = 1; i < row.length; i++) {
            if (row[i] >= row[stack.peek()]) stack.push(i);
            else {
                while (!stack.isEmpty() && row[i] < row[stack.peek()]) {
                    int h = row[stack.pop()];
                    int w = i - stack.peek() - 1;
                    area = Math.max(area, h * w);
                }
                stack.push(i);
            }
        }
        return area;
    }
}
/**
 * 在二维矩阵里找一个正方形，确定rectangle上下界，这样的话用O(n^2),尝试把它拍扁，纵向加起来
 * 拍扁成一维数组之后
 * 3 1 3 2 2
 * *   *
 * *   * * *
 * * * * * *
 * 如果能确定上下界，可以拍扁成一维数组，相当于在这个直方图里找这个最大矩形 => 思路一模一样
 * 1. 遍历上下界，把它拍扁 (ref.LC363)
 * 2. 用单调栈求最大长方形
 * 这道题其实不需要确定上界，只要搞定下界然后把它拍扁即可。
 * 拍的时候只要从下往上拍，能顶多少顶多少
 * 找以它为base的histogram
 */

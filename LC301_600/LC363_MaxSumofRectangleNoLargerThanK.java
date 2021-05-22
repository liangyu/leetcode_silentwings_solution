package LC301_600;
import java.util.*;
public class LC363_MaxSumofRectangleNoLargerThanK {
    /**
     * Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that
     * its sum is no larger than k.
     *
     * Input: matrix = [[1,0,1],[0,-2,3]], k = 2
     * Output: 2
     *
     * Note:
     *
     * The rectangle inside the matrix must have an area > 0.
     * What if the number of rows is much larger than the number of columns?
     *
     * X X X X X X
     * X X X X X X
     * X X X X X X
     * X X X X X X
     *
     * Y [Y Y Y] Y Y  <- 上下2行拍扁 -> 1D数组里找一个subArray -> presum之差
     *
     * fix j:
     * presum[j] - presum[i] <= k ==> presum[i] >= presum[j] - k = K
     * presum[0] presum[1], .... presum[j - 1]  里面挑一个作为presum[i], 0 <= i <= j - 1 -> 用B.S.
     * 只要找到一个最接近K的数，而不care i 到底是多少
     *
     * O（m * m * n * logn) / O(n * n * m * logm)
     * 确定上边界和下边界 O(m * m); 拍扁 -> O(n), n：# of cols；遍历所有的j -> O(n) 有序数组里找i -> O(nlogn)
     *
     * @param matrix
     * @param k
     * @return
     */
    // time = O(m * m * nlogn) => O(min(m, n)^2 * max(m, n) * log(max(m, n)), space = O(max(m, n))
    // when m >> n => time = O(n * n * mlogm) < O(m * m * nlogn) as O(nlogm) < O(mlogn)
    public int maxSumSubmatrix(int[][] matrix, int k) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;

        int row = matrix.length, col = matrix[0].length;

        // step 3: 优化 -> 转置
        if (row > col) {
            int[][] matrix2 = new int[col][row];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    matrix2[j][i] = matrix[i][j];
                }
            }
            return maxSumSubmatrix(matrix2, k);
        }
        int res = Integer.MIN_VALUE;

        // step 1: 拍扁
        for (int i = 0; i < row; i++) { // fix upper bound O(m)
            int[] temp = new int[col];
            for (int j = i; j < row; j++) { // O(m)(
                for (int l = 0; l < col; l++) { // O(n) 注意： 这里的O(n)与下面的O(nlogn)在同一层 => 取O(nlogn)
                    temp[l] = temp[l] + matrix[j][l];
                }
                res = Math.max(res, helper(temp, k)); // O(nlogn)
            }
        }
        return res;
    }

    // step 2: helper 找不大于k的subArray
    private int helper(int[] temp, int k) {
        int len = temp.length;
        int presum = 0;
        int res = Integer.MIN_VALUE;
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(0);
        for (int j = 0; j < len; j++) { // O(nlogn)
            presum += temp[j];
            Integer ck = treeSet.ceiling(presum - k);
            if (ck != null) {
                res = Math.max(res, presum - ck);
            }
            treeSet.add(presum);
        }
        return res;
    }
}

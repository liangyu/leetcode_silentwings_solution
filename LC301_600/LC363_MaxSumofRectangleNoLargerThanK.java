package LC301_600;
import java.util.*;
public class LC363_MaxSumofRectangleNoLargerThanK {
    /**
     * Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that
     * its sum is no larger than k.
     *
     * It is guaranteed that there will be a rectangle with a sum no larger than k.
     *
     * Input: matrix = [[1,0,1],[0,-2,3]], k = 2
     * Output: 2
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 100
     * -100 <= matrix[i][j] <= 100
     * -10^5 <= k <= 10^5
     * Follow up: What if the number of rows is much larger than the number of columns?
     * @param matrix
     * @param k
     * @return
     */
    // time = O(m * m * nlogn) => O(min(m, n)^2 * max(m, n) * log(max(m, n)), space = O(max(m, n))
    // when m >> n => time = O(n * n * mlogm) < O(m * m * nlogn) as O(nlogm) < O(mlogn)
    // time = O(m^2 * n^2 * logn), space = O(n)
    public int maxSumSubmatrix(int[][] matrix, int k) {
        // corner case
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;

        // 转置
        if (m > n) {
            int[][] matrix2 = new int[n][m];
            for (int i = 0; i < n; i++) { // O(m * n)
                for (int j = 0; j < m; j++) {
                    matrix2[i][j] = matrix[j][i];
                }
            }
            return maxSumSubmatrix(matrix2, k);
        }

        // 拍扁
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {  // O(m^2 * n)
            int[] temp = new int[n];
            for (int j = i; j < m; j++) {
                for (int l = 0; l < n; l++) {
                    temp[l] += matrix[j][l];
                }
                res = Math.max(res, helper(temp, k)); // O(nlogn)
            }
        }
        return res;
    }

    // presum[j] - presum[i] <= k  => presum[i] >= presum[j] - k
    private int helper(int[] temp, int k) {  // O(nlogn)
        int n = temp.length;
        int presum = 0;
        TreeSet<Integer> set = new TreeSet<>();
        set.add(0); // set里不能一个元素都没有，必须一开始包含一个0！！！否则不能正确反映从起点开始的subarray
        int res = Integer.MIN_VALUE;
        for (int j = 0; j < n; j++) {
            presum += temp[j];
            Integer ck = set.ceiling(presum - k);
            if (ck != null) { // 找到presum[i]
                res = Math.max(res, presum - ck);
            }
            set.add(presum);
        }
        return res;
    }
}
/**
 * ref: LC304
 * The rectangle inside the matrix must have an area > 0.
 * What if the number of rows is much larger than the number of columns?
 *
 * X X X X X X
 * X X X X X X
 * X X X X X X
 * X X X X X X
 *
 * 固定一个子矩阵 => 定2个顶点，左上角和右下角 => 最差4层循环遍历。
 * 在一个矩阵中确定一个小矩阵，基本思想是：
 * 固定上边界和下边界，用掉2层循环，左右边界如何确定呢？=> 把它拍扁！
 * 找所有的子矩阵 => 找一个连续的子区间，使它们的和尽量接近于k => 前缀和
 * 相当于找一个presum[j]-presum[i]使它们尽量接近于k
 * 遍历j，然后考虑如何优化选择这个i
 * fix j:
 * ideally,如果这个presum[j]-presum[i]<=k
 * presum[i] >= presum[j]-k = K => 使用二分法，第一个>= k的，lowerbound
 * presum[0], presum[1], ..., presum[j-1] 挑一个presum[i]来满足以上条件
 * 由于本题只要找到这个数字来，并不需要确定i具体是哪一个，我们只要找到一个最接近大K的数即可。
 * O(m*m*n*n*logn) = O(m^2*n^2*logn)
 *
 * 本题体现的2个基本思想：
 * 1. 大矩阵里找小矩阵，固定上界和下界，然后把它拍扁，变成一个在一维数组里找一个subarray，降了一维
 * 2. 找一个子subarray，最接近于k，肯定要用有序数组，multiSet,用前缀和过度下，两个presum之差，固定j找i
 * 把所有的presum候选都放在一个有序数组里面，直接找第一个>=k的数
 * 3.矩阵转置，可能速度上会更高效一些
 *
 * 如果n较小，就在纵向把它拍扁；反之，如果m较小的话，我们就在横向把它们拍扁。
 * logn vs logm => 常数级别的差异
 * 当m更大的时候，logm能比logn节省更多的时间
 * 比如n越大，列数越大，那我们这个算法的优越性nlogn体现得就越大
 * 因为传统上我们要确定一个subarray，我们要确定一个左边界，确定一个右边界，那就是O(n^2),现在是nlogn
 * n越大，就越赚 => 纵向拍扁
 * 相反，m越大，我们就横向拍扁，mlogm就会发挥优势，所以m > n的时候，即行特别多的时候，复杂度会降低更多一些
 * 在处理矩阵的时候，这个技巧很常见，转置之后在另一个维度上去做的话，速度更快。
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
 */
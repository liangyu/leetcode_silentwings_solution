package LC1201_1500;

public class LC1292_MaximumSideLengthofaSquarewithSumLessthanorEqualtoThreshold {
    /**
     * Given a m x n matrix mat and an integer threshold. Return the maximum side-length of a square with a sum less
     * than or equal to threshold or return 0 if there is no such square.
     *
     * Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= m, n <= 300
     * m == mat.length
     * n == mat[i].length
     * 0 <= mat[i][j] <= 10000
     * 0 <= threshold <= 10^5
     * @param mat
     * @param threshold
     * @return
     */
    // time = O(m * n *log(min(m, n)), space = O(m * n)
    public int maxSideLength(int[][] mat, int threshold) {
        // corner case
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) return 0;

        int m = mat.length, n = mat[0].length;
        int[][] presum = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                presum[i][j] = presum[i - 1][j] + presum[i][j - 1] - presum[i - 1][j - 1] + mat[i - 1][j - 1];
            }
        }

        // 二分猜边长
        int left = 1, right = Math.min(m, n);
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (isOK(presum, mid, threshold)) left = mid; // 可能还可以猜更大一些
            else right = mid - 1;
        }
        return isOK(presum, left, threshold) ? left : 0;
    }

    private boolean isOK(int[][] presum, int len, int threshold) {
        int m = presum.length, n = presum[0].length;
        for (int i = len; i <= m; i++) {
            for (int j = len; j <= n; j++) {
                int sum = presum[i][j] - presum[i][j - len] - presum[i - len][j] + presum[i - len][j - len];
                if (sum <= threshold) return true;
            }
        }
        return false;
    }
}
/**
 * presum[i][j]: sum of rectangle (0,0), (i,j)
 * sum[i][j] of len: presum[i][j] - presum[i][j - len] - presum[i - len][j] + presum[i - len][j - len]  => O(1)
 * 暴力法：搜索所有的方阵，确定右下角，探索方阵的边长 O(n^2) * n = O(n^3)
 * len: O(logn * n^2)
 * 快速找一个矩阵的面积O(1)，需要熟练掌握！！！提前预处理前缀和数组！！！左上角到某个点所围成的矩形，面积是多少。
 */

package LC601_900;

public class LC668_KthSmallestNumberinMultiplicationTable {
    /**
     * Nearly everyone has used the Multiplication Table. The multiplication table of size m x n is an integer matrix
     * mat where mat[i][j] == i * j (1-indexed).
     *
     * Given three integers m, n, and k, return the kth smallest element in the m x n multiplication table.
     *
     * Input: m = 3, n = 3, k = 5
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= m, n <= 3 * 10^4
     * 1 <= k <= m * n
     * @param m
     * @param n
     * @param k
     * @return
     */
    // time = O(mlog(m * n)), space = O(1)
    public int findKthNumber(int m, int n, int k) {
        int left = 1, right = m * n;
        while (left < right) {
            int mid = left + (right - left) / 2; // [0,1] => 0
            if (countEqualOrSmaller(m, n, mid) < k) left = mid + 1;
            else right = mid; // 可能有若干个相同的数，>= k的时候还是可能是有效解！
        }
        return left;
    }

    private int countEqualOrSmaller(int m, int n, int val) {
        int count = 0;
        for (int i = 1; i <= m; i++) {
            count += Math.min(n, val / i); // 每一行的个数不可能大于n，有可能val非常大，在行数上比较大
        }
        return count;
    }
}
/**
 * 乘法表里第k个元素
 * sorted matrix
 * [0, INT_MAX] => 32
 * 不停二分
 * <= val => x < k
 */
package LC2101_2400;

public class LC2338_CounttheNumberofIdealArrays {
    /**
     * You are given two integers n and maxValue, which are used to describe an ideal array.
     *
     * A 0-indexed integer array arr of length n is considered ideal if the following conditions hold:
     *
     * Every arr[i] is a value from 1 to maxValue, for 0 <= i < n.
     * Every arr[i] is divisible by arr[i - 1], for 0 < i < n.
     * Return the number of distinct ideal arrays of length n. Since the answer may be very large, return it modulo
     * 10^9 + 7.
     *
     * Input: n = 2, maxValue = 5
     * Output: 10
     *
     * Input: n = 5, maxValue = 3
     * Output: 11
     *
     * Constraints:
     *
     * 2 <= n <= 10^4
     * 1 <= maxValue <= 10^4
     * @param n
     * @param maxValue
     * @return
     */
    // time = O(mlogm * logm), space = O(m)
    public int idealArrays(int n, int maxValue) {
        long M = (long)(1e9 + 7);
        long[][] f = new long[maxValue + 1][15];
        for (int i = 1; i <= maxValue; i++) f[i][1] = 1;
        for (int j = 1; j < 14; j++) { // j + 1 <= 14 => j < 14
            for (int i = 1; i <= maxValue; i++) {
                for (int k = 2; k * i <= maxValue; k++) {
                    f[k * i][j + 1] = (f[k * i][j + 1] + f[i][j]) % M;
                }
            }
        }

        long[][] c = new long[n][15];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i && j <= 14; j++) {
                if (j == 0) c[i][j] = 1;
                else c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % M;
            }
        }

        long res = 0;
        for (int i = 1; i <= maxValue; i++) {
            for (int j = 1; j <= 14 && j <= n; j++) {
                res = (res + f[i][j] * c[n - 1][j - 1]) % M;
            }
        }
        return (int) res;
    }
}
/**
 * 假如第1个数是x,第2个数要和第一个数不同的话，至少得是2x，然后2^2*x,...,是个指数提升的状态
 * 而我们的数的范围一共只有10^4 => 所以这个序列不会很长，只有log2(10^4) = log2(1000)+log2(10)=10+log2(10) <= 14
 * 所以一个序列最多只包括14个数：2^0 ~ 2^14
 * f(i,j): 最后一个数是i，并且长度为j的不同序列数
 * 不同的序列数最多也只有500多万个
 * 可以枚举下一个数是谁？枚举i的倍数
 * 不同状态最多只有14万个
 * 暴搜 + 记忆化搜索
 * a1,a2,...,ak
 * x1,x2,....,xk  总和 = n
 * 隔板法
 * 一种隔板方法对应一个不定方程的解
 * C(n-1,k-1)
 * f(i,j) * C(n-1,j-1)
 * C(a,b) = C(a-1,b) + C(a-1,b-1)
 */
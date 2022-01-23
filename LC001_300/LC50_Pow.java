package LC001_300;
import java.util.*;
public class LC50_Pow {
    /**
     * Implement pow(x, n), which calculates x raised to the power n (i.e., x^n).
     *
     * Input: x = 2.00000, n = 10
     * Output: 1024.00000
     * Constraints:
     *
     * -100.0 < x < 100.0
     * -2^31 <= n <= 2^31-1
     * -10^4 <= xn <= 10^4
     *
     * @param x
     * @param n
     * @return
     */
    // S1
    // time = O(logn), space = O(logn)
    public double myPow(double x, int n) {
        if (n >= 0) return helper(x, n);
        else return 1 / helper(x, n);
    }

    private double helper(double x, int n) {
        if (n == 0) return 1.0;

        double temp = helper(x, n / 2);
        return n % 2 == 0 ? temp * temp : temp * temp * x;
    }

    // S2
    // time = O(logn), space = O(1)
    public double myPow2(double x, int n) {
        long k = n;
        if (n < 0) {
            x = 1 / x;
            k = -k;
        }

        double res = 1, cur = x;
        // 在对 i 进行二进制拆分的同时计算答案
        for (long i = k; i > 0; i /= 2) { // 舍弃 i 二进制表示的最低位，这样我们每次只要判断最低位即可
            if (i % 2 == 1) res *= cur; // 如果 i 二进制表示的最低位为 1，那么需要计入贡献
            cur *= cur; // 将贡献不断地平方
        }
        return res;
    }
}

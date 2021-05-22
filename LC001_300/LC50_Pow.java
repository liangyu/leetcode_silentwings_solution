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
        for (long i = k; i > 0; i /= 2) {
            if (i % 2 == 1) res *= cur;
            cur *= cur;
        }
        return res;
    }
}

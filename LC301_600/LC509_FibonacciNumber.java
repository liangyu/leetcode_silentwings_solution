package LC301_600;
import java.util.*;
public class LC509_FibonacciNumber {
    /**
     * The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibonacci sequence, such that each
     * number is the sum of the two preceding ones, starting from 0 and 1. That is,
     *
     * F(0) = 0, F(1) = 1
     * F(n) = F(n - 1) + F(n - 2), for n > 1.
     * Given n, calculate F(n).
     *
     * Input: n = 2
     * Output: 1
     *
     * Constraints:
     *
     * 0 <= n <= 30
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int fib(int n) {
        if (n == 0 || n == 1) return n;

        int lo = 0, hi = 1;
        int res = 0;
        for (int i = 2; i <= n; i++) {
            res = lo + hi;
            lo = hi;
            hi = res;
        }
        return res;
    }
}

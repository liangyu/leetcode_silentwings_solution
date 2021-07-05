package LC1801_2100;

import java.math.BigInteger;

public class LC1922_CountGoodNumbers {
    /**
     * A digit string is good if the digits (0-indexed) at even indices are even and the digits at odd indices are
     * prime (2, 3, 5, or 7).
     *
     * For example, "2582" is good because the digits (2 and 8) at even positions are even and the digits (5 and 2) at
     * odd positions are prime. However, "3245" is not good because 3 is at an even index but is not even.
     * Given an integer n, return the total number of good digit strings of length n. Since the answer may be large,
     * return it modulo 109 + 7.
     *
     * A digit string is a string consisting of digits 0 through 9 that may contain leading zeros.
     *
     * Input: n = 1
     * Output: 5
     *
     * Input: n = 50
     * Output: 564908303
     *
     * Constraints:
     *
     * 1 <= n <= 10^15
     * @param n
     * @return
     */
    // time = O(logn), space = O(logn)
    final int M = (int)(1e9 + 7);
    public int countGoodNumbers(long n) {
        long even = (n + 1) / 2;
        long odd = n / 2;

        return (int)(pow(5, even) * pow(4, odd) % M);
    }

    private long pow(int x, long n) {
        if (n == 0) return 1;
        if (n == 1) return x;

        if (n % 2 == 1) return x * pow(x, n - 1) % M;

        long half = pow(x, n / 2);
        return half * half % M;
    }
}

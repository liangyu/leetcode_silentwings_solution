package LC1501_1800;
import java.util.*;
public class LC1680_ConcatenationofConsecutiveBinaryNumbers {
    /**
     * Given an integer n, return the decimal value of the binary string formed by concatenating
     * the binary representations of 1 to n in order, modulo 10^9 + 7.
     *
     * Input: n = 1
     * Output: 1
     *
     * Input: n = 12
     * Output: 505379714
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     *
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int concatenatedBinary(int n) {
        long res = 1;
        int M = 1000000007;
        for (int i = 2; i <= n; i++) {
            int len = (int)(Math.log(i) / Math.log(2)) + 1;
            res = ((res << len) + i) % M;
            res %= M;
        }
        return (int)res;
    }
}

/**
 * 根本没办法拼起来转化成10进制
 * n = 1 => 1
 * n = 2 => 1 10 => 抬高2位， 1 * 2^2 + 2 = 6
 * n = 3 => 1 10 11 => 6 * 2^2 + 3 = 27
 *
 * => ret[n] = ret[n - 1] * 2^len + n -> 极有可能会溢出，long可以装得下
 * len = how many bits for n
 *
 * n = 1, 1
 * n = 2, 2
 * n = 3, 2
 * n = 4, 3
 * => log(2) + 1位
 */

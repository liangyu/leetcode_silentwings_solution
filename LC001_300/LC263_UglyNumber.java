package LC001_300;
import java.util.*;
public class LC263_UglyNumber {
    /**
     * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
     *
     * Given an integer n, return true if n is an ugly number.
     *
     * Input: n = 6
     * Output: true
     *
     * Constraints:
     *
     * -2^31 <= n <= 2^31 - 1
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isUgly(int n) {
        while (n > 1 && n % 5 == 0) n /= 5;
        while (n > 1 && n % 3 == 0) n /= 3;
        while (n > 1 && n % 2 == 0) n /= 2;
        return n == 1;
    }
}

package LC001_300;
import java.util.*;
public class LC172_FactorialTrailingZeroes {
    /**
     * Given an integer n, return the number of trailing zeroes in n!.
     *
     * Follow up: Could you write a solution that works in logarithmic time complexity?
     *
     * Input: n = 3
     * Output: 0
     *
     * Constraints:
     *
     * 0 <= n <= 10^4
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            count += n / 5;
            n /= 5;
        }
        return count;
    }
}

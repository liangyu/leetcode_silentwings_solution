package LC001_300;
import java.util.*;
public class LC233_NumberofDigitOne {
    /**
     * Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal
     * to n.
     *
     * Input: n = 13
     * Output: 6
     *
     * Constraints:
     *
     * 0 <= n <= 2 * 10^9
     * @param n
     * @return
     */
    // time = O(logn), space = O(1)
    public int countDigitOne(int n) {
        int res = 0;
        for (long m = 1; m <= n; m *= 10) {
            long a = n / m;
            long b = n % m;
            res += (a + 8) / 10 * m;
            if (a % 10 == 1) res += b + 1;
        }
        return res;
    }
}

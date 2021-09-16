package LC1801_2100;
import java.util.*;
public class LC1999_SmallestGreaterMultipleMadeofTwoDigits {
    /**
     * Given three integers, k, digit1, and digit2, you want to find the smallest integer that is:
     *
     * Larger than k,
     * A multiple of k, and
     * Comprised of only the digits digit1 and/or digit2.
     * Return the smallest such integer. If no such integer exists or the integer exceeds the limit of a signed 32-bit
     * integer (2^31 - 1), return -1.
     *
     * Input: k = 2, digit1 = 0, digit2 = 2
     * Output: 20
     *
     * Constraints:
     *
     * 1 <= k <= 1000
     * 0 <= digit1 <= 9
     * 0 <= digit2 <= 9
     * @param k
     * @param digit1
     * @param digit2
     * @return
     */
    // time = O(1), space = O(1)
    public int findInteger(int k, int digit1, int digit2) {
        // corner case
        if (digit1 == 0 && digit2 == 0) return -1;

        long x = 0;
        return helper(k, digit1, digit2, x);
    }

    private int helper(int k, int a, int b, long x) {
        if (x > Integer.MAX_VALUE) return -1;
        if (x > k && x % k == 0) return (int) x;

        int x1 = 0, x2 = 0;
        x1 = (x + a == 0 ? -1 : helper(k, a, b, x * 10 + a));
        x2 = (x + b == 0 ? -1 : helper(k, a, b, x * 10 + b));
        return x1 > 1 && x2 > 1 ? Math.min(x1, x2) : Math.max(x1, x2);
    }
}

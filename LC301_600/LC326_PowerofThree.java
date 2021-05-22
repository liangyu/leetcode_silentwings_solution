package LC301_600;
import java.util.*;
public class LC326_PowerofThree {
    /**
     * Given an integer n, return true if it is a power of three. Otherwise, return false.
     *
     * An integer n is a power of three, if there exists an integer x such that n == 3^x.
     *
     * Input: n = 27
     * Output: true
     *
     * Constraints:
     *
     * -2^31 <= n <= 2^31 - 1
     * @param n
     * @return
     */
    // S1
    // time = O(log3(n)) = O(1), space = O(log3(n)) = O(1)
    public boolean isPowerOfThree(int n) {
        // corner case
        if (n <= 0) return false;

        while (n > 1) {
            if (n % 3 != 0) return false;
            n /= 3;
        }
        return true;
    }

    // S2: Math
    // time = O(1), space = O(1)
}

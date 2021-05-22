package LC001_300;
import java.util.*;
public class LC69_Sqrtx {
    /**
     * Given a non-negative integer x, compute and return the square root of x.
     *
     * Since the return type is an integer, the decimal digits are truncated, and only the integer part of the result
     * is returned.
     *
     * Input: x = 4
     * Output: 2
     *
     * Constraints:
     *
     * 0 <= x <= 2^31 - 1
     * @param x
     * @return
     */
    // time = O(logn), space = O(1)
    public int mySqrt(int x) {
        // corner case
        if (x <= 1) return x;

        int start = 1, end = x / 2;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (mid <= x / mid && mid + 1 > x / (mid + 1)) return mid;
            else if (mid > x / mid) end = mid - 1;
            else start = mid + 1;
        }
        return -1;
    }
}

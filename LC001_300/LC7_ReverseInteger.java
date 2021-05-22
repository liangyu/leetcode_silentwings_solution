package LC001_300;
import java.util.*;
public class LC7_ReverseInteger {
    /**
     * Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside
     * the signed 32-bit integer range [-231, 231 - 1], then return 0.
     *
     * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
     *
     * Input: x = 123
     * Output: 321
     *
     * Input: x = -123
     * Output: -321
     *
     * Constraints:
     *
     * -2^31 <= x <= 2^31 - 1
     * @param x
     * @return
     */
    // time = O(logx), space = O(1)
    public int reverse(int x) {
        long res = 0;

        while (x != 0) {
            res = res * 10 + x % 10;
            x /= 10;
            if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) return 0;
        }
        return (int) res;
    }
}

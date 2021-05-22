package LC001_300;
import java.util.*;
public class LC246_StrobogrammaticNumber {
    /**
     * Given a string num which represents an integer, return true if num is a strobogrammatic number.
     *
     * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
     *
     * Input: num = "69"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= num.length <= 50
     * num consists of only digits.
     * num does not contain any leading zeros except for zero itself.
     *
     * @param num
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isStrobogrammatic(String num) {
        // corner case
        if (num == null || num.length() == 0) return true;

        int left = 0, right = num.length() - 1;
        while (left <= right) {
            char c1 = num.charAt(left++), c2 = num.charAt(right--);
            if (c1 == c2) {
                if (c1 != '0' && c1 != '1' && c1 != '8') return false;
            } else {
                if (c1 == '6' && c2 == '9' || c1 == '9' && c2 == '6') continue;
                return false;
            }
        }
        return true;
    }
}

/**
 * 0 1 2 3 4 5 6 7 8 9
 * => 0  1   6 - 9  8
 */

package LC001_300;
import java.util.*;
public class LC9_PalindromeNumber {
    /**
     * Given an integer x, return true if x is palindrome integer.
     *
     * An integer is a palindrome when it reads the same backward as forward. For example, 121 is palindrome while 123
     * is not.
     *
     * Input: x = -121
     * Output: false
     *
     * Constraints:
     *
     * -2^31 <= x <= 2^31 - 1
     * @param x
     * @return
     */
    // time = O(logx), space = O(1)
    public boolean isPalindrome(int x) {
        // corner case
        if (x < 0) return false;

        int backup = x, rev = 0;
        while (x > 0) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return rev == backup;
    }
}

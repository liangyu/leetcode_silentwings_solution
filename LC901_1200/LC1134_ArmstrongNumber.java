package LC901_1200;
import java.util.*;
public class LC1134_ArmstrongNumber {
    /**
     * Given an integer n, return true if and only if it is an Armstrong number.
     *
     * The k-digit number n is an Armstrong number if and only if the kth power of each digit sums to n.
     *
     * Input: n = 153
     * Output: true
     *
     * Constraints:
     *
     * 1 <= n <= 10^8
     * @param n
     * @return
     */
    // time = O(n), space = O(n)   n: the number of digits in integer n
    public boolean isArmstrong(int n) {
        int res = 0;
        String s = String.valueOf(n);
        for (char c : s.toCharArray()) res += (int)(Math.pow(c - '0', s.length()));
        return n == res;
    }
}

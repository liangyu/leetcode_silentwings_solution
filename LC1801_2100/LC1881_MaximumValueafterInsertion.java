package LC1801_2100;
import java.util.*;
public class LC1881_MaximumValueafterInsertion {
    /**
     * You are given a very large integer n, represented as a string, and an integer digit x. The digits in n and the
     * digit x are in the inclusive range [1, 9], and n may represent a negative number.
     *
     * You want to maximize n's numerical value by inserting x anywhere in the decimal representation of n.
     * You cannot insert x to the left of the negative sign.
     *
     * For example, if n = 73 and x = 6, it would be best to insert it between 7 and 3, making n = 763.
     * If n = -55 and x = 2, it would be best to insert it before the first 5, making n = -255.
     * Return a string representing the maximum value of n after the insertion.
     *
     * Input: n = "99", x = 9
     * Output: "999"
     *
     * Input: n = "-13", x = 2
     * Output: "-123"
     *
     * Constraints:
     *
     * 1 <= n.length <= 10^5
     * 1 <= x <= 9
     * The digits in n are in the range [1, 9].
     * n is a valid representation of an integer.
     * In the case of a negative n, it will begin with '-'.
     * @param n
     * @param x
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public String maxValue(String n, int x) {
        // corner case
        if (n == null || n.length() == 0) return "";

        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(0) != '-' && n.charAt(i) - '0' < x || n.charAt(0) == '-' && n.charAt(i) - '0' > x) {
                return n.substring(0, i) + x + n.substring(i);
            }
        }
        return n + x;
    }

    // S2
    // time = O(n), space = O(1)
    public String maxValue2(String n, int x) {
        // corner case
        if (n == null || n.length() == 0) return "";

        boolean isNeg = false;
        int idx = -1;
        if (n.charAt(0) == '-') isNeg = true;
        else if (n.charAt(0) - '0' < x) idx = 0;

        for (int i = 1; i < n.length(); i++) {
            int val = n.charAt(i) - '0';
            if (!isNeg && val < x || isNeg && val > x) {
                if (idx == -1) idx = i;
                break;
            }
        }
        if (idx == -1) return n + x;
        return n.substring(0, idx) + x + n.substring(idx);
    }
}

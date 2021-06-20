package LC1801_2100;
import java.util.*;
public class LC1903_LargestOddNumberinString {
    /**
     * You are given a string num, representing a large integer. Return the largest-valued odd integer (as a string)
     * that is a non-empty substring of num, or an empty string "" if no odd integer exists.
     *
     * A substring is a contiguous sequence of characters within a string.
     *
     * Input: num = "52"
     * Output: "5"
     *
     * Constraints:
     *
     * 1 <= num.length <= 10^5
     * num only consists of digits and does not contain any leading zeros.
     * @param num
     * @return
     */
    // time = O(n), space = O(1)
    public String largestOddNumber(String num) {
        // corner case
        if (num == null || num.length() == 0) return "";

        int n = num.length();
        for (int i = n - 1; i >= 0; i--) {
            char c = num.charAt(i);
            if ((c - '0') % 2 != 0) return num.substring(0, i + 1);
        }
        return "";
    }
}

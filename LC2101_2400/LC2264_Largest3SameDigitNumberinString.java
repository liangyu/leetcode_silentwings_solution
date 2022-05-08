package LC2101_2400;
import java.util.*;
public class LC2264_Largest3SameDigitNumberinString {
    /**
     * You are given a string num representing a large integer. An integer is good if it meets the following conditions:
     *
     * It is a substring of num with length 3.
     * It consists of only one unique digit.
     * Return the maximum good integer as a string or an empty string "" if no such integer exists.
     *
     * Note:
     *
     * A substring is a contiguous sequence of characters within a string.
     * There may be leading zeroes in num or a good integer.
     *
     * Input: num = "6777133339"
     * Output: "777"
     *
     * Constraints:
     *
     * 3 <= num.length <= 1000
     * num only consists of digits.
     * @param num
     * @return
     */
    // time = O(n), space = O(1)
    public String largestGoodInteger(String num) {
        int n = num.length(), res = -1;
        for (int i = 0; i <= n - 3; i++) {
            String s = num.substring(i, i + 3);
            if (helper(s)) res = Math.max(res, Integer.parseInt(s));
        }
        if (res == -1) return "";
        if (res == 0) return "000";
        return String.valueOf(res);
    }

    private boolean helper(String s) {
        HashSet<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) set.add(c);
        return set.size() == 1;
    }
}

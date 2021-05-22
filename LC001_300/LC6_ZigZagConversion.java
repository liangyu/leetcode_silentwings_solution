package LC001_300;
import java.util.*;
public class LC6_ZigZagConversion {
    /**
     * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to
     * display this pattern in a fixed font for better legibility)
     *
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * And then read line by line: "PAHNAPLSIIGYIR"
     *
     * Input: s = "PAYPALISHIRING", numRows = 3
     * Output: "PAHNAPLSIIGYIR"
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of English letters (lower-case and upper-case), ',' and '.'.
     * 1 <= numRows <= 1000
     * @param s
     * @param numRows
     * @return
     */
    // time = O(n), space = O(n)
    public String convert(String s, int numRows) {
        // corner case
        if (s == null || s.length() == 0) return "";
        if (numRows <= 1) return s;

        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) sb[i] = new StringBuilder("");

        for (int i = 0; i < s.length(); i++) {
            int idx = i % (2 * numRows - 2);
            idx = idx < numRows ? idx : 2 * numRows - 2 - idx;
            sb[idx].append(s.charAt(i));
        }
        for (int i = 1; i < numRows; i++) sb[0].append(sb[i]);
        return sb[0].toString();
    }
}

package LC001_300;
import java.util.*;
public class LC168_ExcelSheetColumnTitle {
    /**
     * Given an integer columnNumber, return its corresponding column title as it appears in an Excel sheet.
     *
     * For example:
     *
     * A -> 1
     * B -> 2
     * C -> 3
     * ...
     * Z -> 26
     * AA -> 27
     * AB -> 28
     * ...
     *
     * Input: columnNumber = 1
     * Output: "A"
     *
     * Constraints:
     *
     * 1 <= columnNumber <= 2^31 - 1
     * @param columnNumber
     * @return
     */
    // time = O(n), space = O(n)
    public String convertToTitle(int columnNumber) {
        // corner case
        if (columnNumber <= 0) return "";

        StringBuilder sb = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            sb.append((char)(columnNumber % 26 + 'A'));
            columnNumber /= 26;
        }
        return sb.reverse().toString();
    }
}

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
        if (numRows == 1) return s;

        StringBuilder[] arr = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) arr[i] = new StringBuilder();

        int n = s.length(), p = numRows * 2 - 2;
        for (int i = 0; i < n; i++) {
            int idx = i % p;
            idx = idx < numRows ? idx : p - idx;
            arr[idx].append(s.charAt(i));
        }

        String res = "";
        for (int i = 0; i < numRows; i++) {
            res += arr[i].toString();
        }
        return res;
    }
}
/**
 * 观察这个数列的周期性。一个周期的元素数目M=numRows*2-2，因此这个数列的周期数目是N=(len(s)-1)/M+1。
 * 注意这种根据M计算Ｎ的的技巧。
 * 在CUDA编程中，确定block数目的计算和此很相似。
 * 在每个周期中，第一行是第０个元素，第二行是第１和M-1个元素，第三行是第２和Ｍ-2个元素，直至最后一行是第numRows-1个元素。
 * 所以我们只要按行遍历，在每一行中将Ｎ个周期里对应该行的数字都找出来。
 */
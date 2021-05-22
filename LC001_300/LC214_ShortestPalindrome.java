package LC001_300;
import java.util.*;
public class LC214_ShortestPalindrome {
    /**
     * Given a string s, you can convert it to a palindrome by adding characters in front of it. Find and return
     * the shortest palindrome you can find by performing this transformation.
     *
     * Input: s = "aacecaaa"
     * Output: "aaacecaaa"
     *
     * Input: s = "abcd"
     * Output: "dcbabcd"
     *
     * Constraints:
     *
     * 0 <= s.length <= 5 * 104
     * s consists of lowercase English letters only.
     *
     * @param s
     * @return
     */
    // time = O(n^2) for aaaaabcaaaaa, space = O(n) for reverse the string
    public String shortestPalindrome(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int i = 0, j = s.length() - 1;

        int end = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else { // 出现不对称
                i = 0; // 左端点从头开始
                end--; // 右端点从末尾收进一位再从头开始check是否首尾对称
                j = end;
            }
        }
        return new StringBuilder(s.substring(end + 1)).reverse().toString() + s; // 不对称的部分取反 + s
    }
}

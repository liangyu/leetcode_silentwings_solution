package LC001_300;
import java.util.*;
public class LC151_ReverseWordsinaString {
    /**
     * Given an input string s, reverse the order of the words.
     *
     * A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.
     *
     * Return a string of the words in reverse order concatenated by a single space.
     *
     * Note that s may contain leading or trailing spaces or multiple spaces between two words. The returned string
     * should only have a single space separating the words. Do not include any extra spaces.
     *
     * Input: s = "the sky is blue"
     * Output: "blue is sky the"
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^4
     * s contains English letters (upper-case and lower-case), digits, and spaces ' '.
     * There is at least one word in s.
     * @param s
     * @return
     */
    // time = O(n), space = o(n)
    public String reverseWords(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        s = s.trim();
        String[] strs = s.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (int i = strs.length - 1; i >= 0; i--) {
            sb.append(strs[i]).append(" ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    // S2
    // time = O(n), space = O(n)
    public String reverseWords2(String s) {
        s = s.trim();
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        reverse(chars, 0, n - 1);

        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && chars[j] != ' ') j++;
            reverse(chars, i, j - 1);
            sb.append(new String(chars, i, j - i)).append(' ');
            while (j < n && chars[j] == ' ') j++;
            i = j - 1;
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private void reverse(char[] chars, int i, int j) {
        while (i < j) {
            char t = chars[i];
            chars[i++] = chars[j];
            chars[j--] = t;
        }
    }
}

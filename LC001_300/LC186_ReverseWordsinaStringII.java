package LC001_300;
import java.util.*;
public class LC186_ReverseWordsinaStringII {
    /**
     * Given a character array s, reverse the order of the words.
     *
     * A word is defined as a sequence of non-space characters. The words in s will be separated by a single space.
     *
     * Input: s = ["t","h","e"," ","s","k","y"," ","i","s"," ","b","l","u","e"]
     * Output: ["b","l","u","e"," ","i","s"," ","s","k","y"," ","t","h","e"]
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is an English letter (uppercase or lowercase), digit, or space ' '.
     * There is at least one word in s.
     * s does not contain leading or trailing spaces.
     * All the words in s are guaranteed to be separated by a single space.
     *
     *
     * Follow up: Could you do it in-place without allocating extra space?
     * @param s
     */
    // time = O(n), space = O(1)
    public void reverseWords(char[] s) {
        // corner case
        if (s == null || s.length == 0) return;

        reverse(s, 0, s.length - 1);
        int r = 0;
        while (r < s.length) {
            int l = r;
            while (r < s.length && s[r] != ' ') r++;
            reverse(s, l, r - 1);
            r++;
        }
    }

    private void reverse(char[] s, int i, int j) {
        while (i < j) {
            char temp = s[i];
            s[i++] = s[j];
            s[j--] = temp;
        }
    }
}

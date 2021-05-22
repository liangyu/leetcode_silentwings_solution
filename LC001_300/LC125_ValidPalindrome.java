package LC001_300;
import java.util.*;
public class LC125_ValidPalindrome {
    /**
     * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
     *
     * Note: For the purpose of this problem, we define empty string as valid palindrome.
     *
     * Input: "A man, a plan, a canal: Panama"
     * Output: true
     *
     * Input: "race a car"
     * Output: false
     *
     * Constraints:
     *
     * s consists only of printable ASCII characters.
     *
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isPalindrome(String s) {
        // corner case
        if (s == null || s.length() == 0) return true;

        s = s.toLowerCase();
        int i = 0, j = s.length() - 1;

        while (i < j) {
            while (i < j && !isValid(s.charAt(i))) i++; // 注意i < j不能相等或者越过
            while (i < j && !isValid(s.charAt(j))) j--;
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    private boolean isValid(char c) {
        if (c >= 'a' && c <= 'z' || c >= '0' && c <= '9') return true;
        return false;
    }
}

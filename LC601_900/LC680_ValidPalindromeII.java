package LC601_900;
import java.util.*;
public class LC680_ValidPalindromeII {
    /**
     * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
     *
     * Input: "aba"
     * Output: True
     *
     * Note:
     * The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
     *
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        boolean flag = false;
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else return isPalin(s, i + 1, j) || isPalin(s, i, j - 1);
        }
        return true;
    }

    private boolean isPalin(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}

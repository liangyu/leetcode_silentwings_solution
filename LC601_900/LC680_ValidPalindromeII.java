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
        // corner case
        if (s == null || s.length() == 0) return true;

        int i = 0, j = s.length() - 1;
        while (i < j) { // O(n)
            char c1 = s.charAt(i), c2 = s.charAt(j);
            if (c1 == c2) {
                i++;
                j--;
            } else {
                return isPalin(s, i + 1, j) || isPalin(s, i, j - 1); // 分两叉分别讨论
            }
        }
        return true;
    }

    private boolean isPalin(String s, int i, int j) { // O(n)
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) { // 双指针相向而行
                return false;
            }
        }
        return true;
    }
}

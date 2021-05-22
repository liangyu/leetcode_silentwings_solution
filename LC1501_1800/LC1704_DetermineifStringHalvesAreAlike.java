package LC1501_1800;
import java.util.*;
public class LC1704_DetermineifStringHalvesAreAlike {
    /**
     * You are given a string s of even length. Split this string into two halves of equal lengths, and let a be the
     * first half and b be the second half.
     *
     * Two strings are alike if they have the same number of vowels ('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U').
     * Notice that s contains uppercase and lowercase letters.
     *
     * Return true if a and b are alike. Otherwise, return false.
     *
     * Input: s = "book"
     * Output: true
     *
     * Constraints:
     *
     * 2 <= s.length <= 1000
     * s.length is even.
     * s consists of uppercase and lowercase letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean halvesAreAlike(String s) {
        // corner case
        if (s == null || s.length() == 0) return true;

        String dict = "aeiouAEIOU";
        int first = 0, second = 0;
        int i = 0, j = s.length() - 1;

        while (i < j) {
            char c1 = s.charAt(i++), c2 = s.charAt(j--);
            if (dict.indexOf(c1) != -1) first++;
            if (dict.indexOf(c2) != -1) second++;
        }
        return first == second;
    }
}

package LC2101_2400;
import java.util.*;
public class LC2309_GreatestEnglishLetterinUpperandLowerCase {
    /**
     * Given a string of English letters s, return the greatest English letter which occurs as both a lowercase and
     * uppercase letter in s. The returned letter should be in uppercase. If no such letter exists, return an empty string.
     *
     * An English letter b is greater than another letter a if b appears after a in the English alphabet.
     *
     * Input: s = "lEeTcOdE"
     * Output: "E"
     *
     * Input: s = "AbCdEfGhIjK"
     * Output: ""
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of lowercase and uppercase English letters.
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public String greatestLetter(String s) {
        boolean[] lowers = new boolean[26];
        boolean[] uppers = new boolean[26];

        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) uppers[c - 'A'] = true;
            else if (Character.isLowerCase(c)) lowers[c - 'a'] = true;
        }

        for (int i = 25; i >= 0; i--) {
            if (lowers[i] && uppers[i]) return (char)('A' + i) + "";
        }
        return "";
    }

    // S2:
    // time = O(n), space = O(1)
    public String greatestLetter2(String s) {
        HashSet<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) set.add(c);

        for (char c = 'Z'; c >= 'A'; c--) {
            if (set.contains(c) && set.contains(Character.toLowerCase(c))) return String.valueOf(c);
        }
        return "";
    }
}

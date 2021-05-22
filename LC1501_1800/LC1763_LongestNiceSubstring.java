package LC1501_1800;
import java.util.*;
public class LC1763_LongestNiceSubstring {
    /**
     * A string s is nice if, for every letter of the alphabet that s contains, it appears both in uppercase and
     * lowercase. For example, "abABB" is nice because 'A' and 'a' appear, and 'B' and 'b' appear. However, "abA" is
     * not because 'b' appears, but 'B' does not.
     *
     * Given a string s, return the longest substring of s that is nice. If there are multiple, return the substring of
     * the earliest occurrence. If there are none, return an empty string.
     *
     * Input: s = "YazaAay"
     * Output: "aAa"
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists of uppercase and lowercase English letters.
     *
     * @param s
     * @return
     */
    public static String longestNiceSubstring(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        HashSet<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) set.add(c);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (set.contains(Character.toLowerCase(c)) && set.contains(Character.toUpperCase(c))) continue;
            String sub1 = longestNiceSubstring(s.substring(0, i)), sub2 = longestNiceSubstring(s.substring(i + 1));
            return sub1.length() >= sub2.length() ? sub1 : sub2;
        }
        return s;
    }
}

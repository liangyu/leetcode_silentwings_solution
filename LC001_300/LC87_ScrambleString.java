package LC001_300;
import java.util.*;
public class LC87_ScrambleString {
    /**
     * We can scramble a string s to get a string t using the following algorithm:
     *
     * If the length of the string is 1, stop.
     * If the length of the string is > 1, do the following:
     * Split the string into two non-empty substrings at a random index, i.e., if the string is s, divide it to x and y
     * where s = x + y.
     * Randomly decide to swap the two substrings or to keep them in the same order. i.e., after this step, s may become
     * s = x + y or s = y + x.
     * Apply step 1 recursively on each of the two substrings x and y.
     * Given two strings s1 and s2 of the same length, return true if s2 is a scrambled string of s1, otherwise, return
     * false.
     *
     * Input: s1 = "great", s2 = "rgeat"
     * Output: true
     *
     * Constraints:
     *
     * s1.length == s2.length
     * 1 <= s1.length <= 30
     * s1 and s2 consist of lower-case English letters.
     * @param s1
     * @param s2
     * @return
     */
    // time = O(n!), space = O(n)
    HashMap<String, Boolean> map = new HashMap<>();
    public boolean isScramble(String s1, String s2) {
        // corner case
        if (s1 == null || s2 == null) return false;

        StringBuilder sb = new StringBuilder();
        sb.append(s1);
        sb.append(s2);
        String key = sb.toString();

        if (map.containsKey(key)) return map.get(key);

        if (s1.equals(s2)) {
            map.put(key, true);
            return true;
        }

        int[] letters = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            letters[s1.charAt(i) - 'a']++;
            letters[s2.charAt(i) - 'a']--;
        }

        for (int i = 0; i < 26; i++) {
            if (letters[i] != 0) {
                map.put(key, false);
                return false;
            }
        }
        int len = s1.length();
        for (int i = 1; i < s1.length(); i++) {
            if (isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) {
                map.put(key, true);
                return true;
            }
            if (isScramble(s1.substring(0, i), s2.substring(len - i)) && isScramble(s1.substring(i), s2.substring(0, len - i))) {
                map.put(key, true);
                return true;
            }
        }
        map.put(key, false);
        return false;
    }
}

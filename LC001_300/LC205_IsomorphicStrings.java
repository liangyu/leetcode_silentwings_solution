package LC001_300;
import java.util.*;
public class LC205_IsomorphicStrings {
    /**
     * Given two strings s and t, determine if they are isomorphic.
     *
     * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
     *
     * All occurrences of a character must be replaced with another character while preserving the order of characters.
     * No two characters may map to the same character, but a character may map to itself.
     *
     * Input: s = "egg", t = "add"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 5 * 10^4
     * t.length == s.length
     * s and t consist of any valid ascii character.
     * @param s
     * @param t
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;

        HashMap<Character, Character> map = new HashMap<>();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            char c1 = s.charAt(i), c2 = t.charAt(i);
            if (map.containsKey(c1)) {
                if (map.get(c1) != c2) return false;
            } else {
                if (!map.containsValue(c2)) map.put(c1, c2);
                else return false;
            }
        }
        return true;
    }

    // S2
    // time = O(n), space = O(1)
    public boolean isIsomorphic2(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] schar = new int[256];
        int[] tchar = new int[256];

        int n = s.length();
        for (int i = 0; i < n; i++) {
            if (schar[s.charAt(i)] != tchar[t.charAt(i)]) return false;
            else schar[s.charAt(i)] = tchar[t.charAt(i)] = t.charAt(i);
        }
        return true;
    }
}

package LC001_300;
import java.util.*;
public class LC290_WordPattern {
    /**
     * Given a pattern and a string s, find if s follows the same pattern.
     *
     * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word
     * in s.
     *
     * Input: pattern = "abba", s = "dog cat cat dog"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= pattern.length <= 300
     * pattern contains only lower-case English letters.
     * 1 <= s.length <= 3000
     * s contains only lower-case English letters and spaces ' '.
     * s does not contain any leading or trailing spaces.
     * All the words in s are separated by a single space.
     * @param pattern
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public boolean wordPattern(String pattern, String s) {
        // corner case
        if (pattern == null || pattern.length() == 0 || s == null || s.length() == 0) return false;

        String[] dict = s.split(" ");
        if (pattern.length() != dict.length) return false;

        HashMap<Character, String> map = new HashMap<>();
        for (int i = 0; i < dict.length; i++) {
            char ch = pattern.charAt(i);
            if (map.containsKey(ch)) {
                if (!map.get(ch).equals(dict[i])) return false;
            } else {
                if (map.containsValue(dict[i])) return false; // 注意加入新映射前要check是否value已经被其他char映射过了！
                map.put(ch, dict[i]);
            }
        }
        return true;
    }
}

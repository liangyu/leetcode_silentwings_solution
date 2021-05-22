package LC001_300;
import java.util.*;
public class LC3_LongestSubstringWithoutRepeatingCharacters {
    /**
     * Given a string s, find the length of the longest substring without repeating characters.
     *
     * Input: s = "abcabcbb"
     * Output: 3
     *
     * Constraints:
     *
     * 0 <= s.length <= 5 * 104
     * s consists of English letters, digits, symbols and spaces.
     *
     * @param s
     * @return
     */
    // S1: HashMap + Sliding Window
    // time = O(n), space = O(n)
    public int lengthOfLongestSubstring(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0, start = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                if (map.get(c) >= start) start = map.get(c) + 1;
            }
            map.put(c, i);
            max = Math.max(max, i - start + 1);
        }
        return max;
    }

    // S2: int[128] 最优解！！！
    // time = O(n), space = O(1)
    public int lengthOfLongestSubstring2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int[] index = new int[128]; // ASCII
        Arrays.fill(index, -1); // O(1), 初始化为一个invalid的值
        int max = 0, start = 0;

        for (int i = 0; i < s.length(); i++) { // O(n)
            char c = s.charAt(i);
            if (index[c] != -1) { // 如果invalid，表明之前没遇到和记录过该char
                if (index[c] >= start) start = index[c] + 1;
            }
            index[c] = i;
            max = Math.max(max, i - start + 1);
        }
        return max;
    }
}

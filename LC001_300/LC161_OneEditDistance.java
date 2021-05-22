package LC001_300;
import java.util.*;
public class LC161_OneEditDistance {
    /**
     * Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.
     *
     * A string s is said to be one distance apart from a string t if you can:
     *
     * Insert exactly one character into s to get t.
     * Delete exactly one character from s to get t.
     * Replace exactly one character of s with a different character to get t.
     *
     * Input: s = "ab", t = "acb"
     * Output: true
     *
     * Constraints:
     *
     * 0 <= s.length <= 10^4
     * 0 <= t.length <= 10^4
     * s and t consist of lower-case letters, upper-case letters and/or digits.
     *
     * @param s
     * @param t
     * @return
     */
    // time = O(max(m, n)), space = O(1)
    public boolean isOneEditDistance(String s, String t) {
        if (s.length() < t.length()) return isOneEditDistance(t, s); // 确保第一个string长度 >= 第2个string
        if (s.length() - t.length() > 1) return false; // 长度之差超过1，肯定是false

        int i = 0, j = 0;
        boolean flag = false; // flag用来标注是否已经出现one edit，如果出现则设置为true
        while (i < s.length() && j < t.length()) {
            char c1 = s.charAt(i), c2 = t.charAt(j);
            if (c1 != c2) {
                if (flag) return false; // flag = true表明之前已经出现过1个edit diff，这里再次出现直接返回false
                flag = true; // 之前没出现过edit distance，这次是首次出现，flag设为true
                if (s.length() == t.length()) j++; // 如果两者长度相等，则对应case 3，从而i和j都要向后移动一位
                i++; // 否则就是case 1或者case 2，无论哪种都等价于较长的string向后移动一位
            } else { // c1 == c2 ==> i，j同时向后移动一位
                i++;
                j++;
            }
        }
        // 遍历完后出loop，要满足one edit distance,那么就要分2种情况：
        // 1. 两字符串长度相等且符合case 3,此时i应该也刚好出界，同时之前在loop里出现1位不同，使用replace操作，flag = true
        // 2. 两字符串不等，较长的string比短的长1位，那么此时i应该指向string最后一位，且之前loop里字符都对应相等，即flag = false
        if (flag && i == s.length() || !flag && i == s.length() - 1) return true;
        return false;
    }
}

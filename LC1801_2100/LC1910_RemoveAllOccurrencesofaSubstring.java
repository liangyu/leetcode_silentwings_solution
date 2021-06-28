package LC1801_2100;

public class LC1910_RemoveAllOccurrencesofaSubstring {
    /**
     * Given two strings s and part, perform the following operation on s until all occurrences of the substring part
     * are removed:
     *
     * Find the leftmost occurrence of the substring part and remove it from s.
     * Return s after removing all occurrences of part.
     *
     * A substring is a contiguous sequence of characters in a string.
     *
     * Input: s = "daabcbaabcbc", part = "abc"
     * Output: "dab"
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * 1 <= part.length <= 1000
     * s and part consists of lowercase English letters.
     * @param s
     * @param part
     * @return
     */
    // time = O(n^2), space = O(1)
    public String removeOccurrences(String s, String part) {
        int n = part.length();
        while (s.indexOf(part) != -1) {
            int idx = s.indexOf(part);
            s = s.substring(0, idx) + s.substring(idx + n);
        }
        return s;
    }
}

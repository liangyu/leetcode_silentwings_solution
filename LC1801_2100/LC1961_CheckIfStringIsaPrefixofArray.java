package LC1801_2100;

public class LC1961_CheckIfStringIsaPrefixofArray {
    /**
     * Given a string s and an array of strings words, determine whether s is a prefix string of words.
     *
     * A string s is a prefix string of words if s can be made by concatenating the first k strings in words for some
     * positive k no larger than words.length.
     *
     * Return true if s is a prefix string of words, or false otherwise.
     *
     * Input: s = "iloveleetcode", words = ["i","love","leetcode","apples"]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 20
     * 1 <= s.length <= 1000
     * words[i] and s consist of only lowercase English letters.
     * @param s
     * @param words
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isPrefixString(String s, String[] words) {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
            if (sb.length() == s.length()) return s.equals(sb.toString());
            if (sb.length() > s.length()) return false;
        }
        return false;
    }
}

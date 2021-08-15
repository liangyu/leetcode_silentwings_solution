package LC1801_2100;

public class LC1957_DeleteCharacterstoMakeFancyString {
    /**
     * A fancy string is a string where no three consecutive characters are equal.
     *
     * Given a string s, delete the minimum possible number of characters from s to make it fancy.
     *
     * Return the final string after the deletion. It can be shown that the answer will always be unique.
     *
     * Input: s = "leeetcode"
     * Output: "leetcode"
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists only of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String makeFancyString(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        StringBuilder sb = new StringBuilder();
        int count = 0;
        char prev = '#';

        for (char c : s.toCharArray()) {
            if (c == prev) count++;
            else {
                count = 1;
                prev = c;
            }
            if (count <= 2) sb.append(c);
        }
        return sb.toString();
    }
}

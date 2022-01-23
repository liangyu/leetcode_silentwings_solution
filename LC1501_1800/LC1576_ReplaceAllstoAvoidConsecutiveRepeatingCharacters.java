package LC1501_1800;

public class LC1576_ReplaceAllstoAvoidConsecutiveRepeatingCharacters {
    /**
     * Given a string s containing only lowercase English letters and the '?' character, convert all the '?' characters
     * into lowercase letters such that the final string does not contain any consecutive repeating characters. You
     * cannot modify the non '?' characters.
     *
     * It is guaranteed that there are no consecutive repeating characters in the given string except for '?'.
     *
     * Return the final string after all the conversions (possibly zero) have been made. If there is more than one
     * solution, return any of them. It can be shown that an answer is always possible with the given constraints.
     *
     * Input: s = "?zs"
     * Output: "azs"
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consist of lowercase English letters and '?'.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public String modifyString(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        for (int i = 0 ;i < n; i++) {
            if (chars[i] == '?') {
                for (char c = 'a'; c <= 'z'; c++) {
                    if (i > 0 && chars[i - 1] == c || i < n - 1 && chars[i + 1] == c) continue;
                    chars[i] = c;
                }
            }
        }
        return String.valueOf(chars);
    }
}

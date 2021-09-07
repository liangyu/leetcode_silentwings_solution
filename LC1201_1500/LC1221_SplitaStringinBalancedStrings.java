package LC1201_1500;

public class LC1221_SplitaStringinBalancedStrings {
    /**
     * Balanced strings are those that have an equal quantity of 'L' and 'R' characters.
     *
     * Given a balanced string s, split it in the maximum amount of balanced strings.
     *
     * Return the maximum amount of split balanced strings.
     *
     * Input: s = "RLRRLLRLRL"
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s[i] is either 'L' or 'R'.
     * s is a balanced string.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int balancedStringSplit(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length(), delta = 0, count = 0;
        for (char c : s.toCharArray()) {
            if (c == 'L') delta++;
            else delta--;
            if (delta == 0) count++;
        }
        return count;
    }
}

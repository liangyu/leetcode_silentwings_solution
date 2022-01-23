package LC2100_2400;

public class LC2124_CheckifAllAsAppearsBeforeAllBs {
    /**
     * Given a string s consisting of only the characters 'a' and 'b', return true if every 'a' appears before every 'b'
     * in the string. Otherwise, return false.
     *
     * Input: s = "aaabbb"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s[i] is either 'a' or 'b'.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean checkString(String s) {
        boolean flag = false;
        for (char c : s.toCharArray()) {
            if (c == 'a') {
                if (flag) return false;
            } else {
                if (!flag) flag = true;
            }
        }
        return true;
    }
}

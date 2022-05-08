package LC601_900;

public class LC796_RotateString {
    /**
     * Given two strings s and goal, return true if and only if s can become goal after some number of shifts on s.
     *
     * A shift on s consists of moving the leftmost character of s to the rightmost position.
     *
     * For example, if s = "abcde", then it will be "bcdea" after one shift.
     *
     * Input: s = "abcde", goal = "cdeab"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length, goal.length <= 100
     * s and goal consist of lowercase English letters.
     * @param s
     * @param goal
     * @return
     */
    // time = O(n), space = O(1)
    public boolean rotateString(String s, String goal) {
        int m = s.length(), n = goal.length();
        if (m != n) return false;
        if (s.equals(goal)) return true;

        s += s;
        return s.indexOf(goal) != -1;
    }
}

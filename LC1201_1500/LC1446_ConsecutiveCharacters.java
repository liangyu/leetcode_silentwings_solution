package LC1201_1500;

public class LC1446_ConsecutiveCharacters {
    /**
     * The power of the string is the maximum length of a non-empty substring that contains only one unique character.
     *
     * Given a string s, return the power of s.
     *
     * Input: s = "leetcode"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * s consists of only lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int maxPower(String s) {
        int res = 1, n = s.length(), sum = 1;
        for (int i = 0; i < n; i++) {
            if (i > 0 && s.charAt(i) == s.charAt(i - 1)) {
                sum++;
                res = Math.max(res, sum);
            } else sum = 1;
        }
        return res;
    }
}

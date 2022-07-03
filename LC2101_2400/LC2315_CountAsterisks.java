package LC2101_2400;

public class LC2315_CountAsterisks {
    /**
     * You are given a string s, where every two consecutive vertical bars '|' are grouped into a pair. In other words,
     * the 1st and 2nd '|' make a pair, the 3rd and 4th '|' make a pair, and so forth.
     *
     * Return the number of '*' in s, excluding the '*' between each pair of '|'.
     *
     * Note that each '|' will belong to exactly one pair.
     *
     * Input: s = "l|*e*et|c**o|*de|"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of lowercase English letters, vertical bars '|', and asterisks '*'.
     * s contains an even number of vertical bars '|'.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int countAsterisks(String s) {
        boolean flag = false;
        int n = s.length(), count = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '|') flag = !flag;
            else if (c == '*') count += flag ? 0 : 1;
        }
        return count;
    }

    // S2
    // time = O(n), space = O(1)
    public int countAsterisks2(String s) {
        int res = 0, cnt = 0;
        for (char c : s.toCharArray()) {
            if (c == '|') cnt++;
            else if (c == '*' && cnt % 2 == 0) res++;
        }
        return res;
    }
}

package LC2101_2400;

public class LC2278_PercentageofLetterinString {
    /**
     * Given a string s and a character letter, return the percentage of characters in s that equal letter rounded down
     * to the nearest whole percent.
     *
     * Input: s = "foobar", letter = "o"
     * Output: 33
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists of lowercase English letters.
     * letter is a lowercase English letter.
     * @param s
     * @param letter
     * @return
     */
    // time = O(n), space = O(1)
    public int percentageLetter(String s, char letter) {
        int count = 0, n = s.length();
        for (char c : s.toCharArray()) {
            if (c == letter) count++;
        }
        return count * 100 / n;
    }
}

package LC2101_2400;

public class LC2330_ValidPalindromeIV {
    /**
     * You are given a 0-indexed string s consisting of only lowercase English letters. In one operation, you can
     * change any character of s to any other character.
     *
     * Return true if you can make s a palindrome after performing exactly one or two operations, or return false
     * otherwise.
     *
     * Input: s = "abcdba"
     * Output: true
     *
     * Input: s = "aa"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists only of lowercase English letters.
     * @param s
     * @return
     */
    public boolean makePalindrome(String s) {
        int n = s.length(), i = 0, j = n - 1, count = 0;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) count++;
            i++;
            j--;
        }
        return count <= 2;
    }
}

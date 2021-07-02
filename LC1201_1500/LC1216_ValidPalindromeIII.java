package LC1201_1500;

public class LC1216_ValidPalindromeIII {
    /**
     * Given a string s and an integer k, return true if s is a k-palindrome.
     *
     * A string is k-palindrome if it can be transformed into a palindrome by removing at most k characters from it.
     *
     * Input: s = "abcdeca", k = 2
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of only lowercase English letters.
     * 1 <= k <= s.length
     * @param s
     * @param k
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public boolean isValidPalindrome(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k < 0) return false;

        int n = s.length();
        StringBuilder sb = new StringBuilder(s);
        String t = sb.reverse().toString();

        s = "#" + s;
        t = "#" + t;

        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return n - dp[n][n] <= k;
    }
}
/**
 * 此题和1312题很相似的想法。想要将一个字符串s变成一个回文串（无论是通过增加还是删除），一个技巧就是构造另一个字符串t是s的逆序。
 * 于是，如果要求增加字符，那么s和t的shorted common supersequence就是需要增加的最少字符；如果要求删除字符，
 * 那么s和t的longest common subsequence就对应着需要删除的最少字符。
 *
 * 本题求出s和t的LCS后，只需要判断s的长度减去LCS的长度（即对于s而言最少需要删除的字符）是否小于等于k即可。
 */

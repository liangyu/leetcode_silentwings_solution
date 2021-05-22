package LC1801_2100;
import java.util.*;
public class LC1839_LongestSubstringOfAllVowelsinOrder {
    /**
     * A string is considered beautiful if it satisfies the following conditions:
     *
     * Each of the 5 English vowels ('a', 'e', 'i', 'o', 'u') must appear at least once in it.
     * The letters must be sorted in alphabetical order (i.e. all 'a's before 'e's, all 'e's before 'i's, etc.).
     * For example, strings "aeiou" and "aaaaaaeiiiioou" are considered beautiful, but "uaeio", "aeoiu", and "aaaeeeooo"
     * are not beautiful.
     *
     * Given a string word consisting of English vowels, return the length of the longest beautiful substring of word.
     * If no such substring exists, return 0.
     *
     * A substring is a contiguous sequence of characters in a string.
     *
     * Input: word = "aeiaaioaaaaeiiiiouuuooaauuaeiu"
     * Output: 13
     *
     * Constraints:
     *
     * 1 <= word.length <= 5 * 10^5
     * word consists of characters 'a', 'e', 'i', 'o', and 'u'.
     * @param word
     * @return
     */
    // S1: Greedy
    // time = O(n), space = O(1)
    public int longestBeautifulSubstring(String word) {
        // corner case
        if (word == null || word.length() == 0) return 0;

        int len = 1, vowel = 1;
        int res = 0;

        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) > word.charAt(i - 1)) {
                len++;
                vowel++;
            } else if (word.charAt(i) == word.charAt(i - 1)) len++;
            else {
                len = 1;
                vowel = 1;
            }
            if (vowel == 5) res = Math.max(res, len);
        }
        return res;
    }

    // S2: DP
    // time = O(n), space = O(n)
    public int longestBeautifulSubstring2(String word) {
        // corner case
        if (word == null || word.length() == 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        map.put('a', 1);
        map.put('e', 2);
        map.put('i', 3);
        map.put('o', 4);
        map.put('u', 5);

        int[] dp = new int[6];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0; // 表示空，什么都不取

        int[] dp_tmp = new int[6];
        Arrays.fill(dp_tmp, Integer.MIN_VALUE);
        int res = 0;

        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j <= 5; j++) dp_tmp[j] = dp[j];
            for (int j = 1; j <= 5; j++) {
                if (map.get(word.charAt(i)) == j) {
                    dp[j] = Math.max(dp_tmp[j], dp_tmp[j - 1]) + 1;
                } else dp[j] = Integer.MIN_VALUE;
            }
            res = Math.max(res, dp[5]);
        }
        return res;
    }
}
/**
 * dp[i][u] = max(dp[i - 1][u], dp[i - 1][o] + 1
 * dp[i][e] = max(dp[i - 1][a], dp[i - 1][e] + 1
 * dp[i][a] = max(dp[i - 1][_], dp[i - 1][a]) + 1
 *
 */

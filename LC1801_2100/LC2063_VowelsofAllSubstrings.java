package LC1801_2100;
import java.util.*;
public class LC2063_VowelsofAllSubstrings {
    /**
     * Given a string word, return the sum of the number of vowels ('a', 'e', 'i', 'o', and 'u') in every substring of
     * word.
     *
     * A substring is a contiguous (non-empty) sequence of characters within a string.
     *
     * Note: Due to the large constraints, the answer may not fit in a signed 32-bit integer. Please be careful during
     * the calculations.
     *
     * Input: word = "aba"
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= word.length <= 10^5
     * word consists of lowercase English letters.
     * @param word
     * @return
     */
    // time = O(n), space = O(1)
    public long countVowels(String word) {
        // corner case
        if (word == null || word.length() == 0) return 0;

        long n = word.length(), res = 0;
        for (int i = 0; i < n; i++) {
            char c = word.charAt(i);
            if ("aeiou".indexOf(c) != -1) res += (i + 1) * (n - i);
        }
        return res;
    }
}
/**
 * For each vowels s[i],
 * it could be in the substring starting at s[x] and ending at s[y],
 * where 0 <= x <= i and i <= y < n,
 * that is (i + 1) choices for x and (n - i) choices for y.
 * So there are (i + 1) * (n - i) substrings containing s[i].
 */

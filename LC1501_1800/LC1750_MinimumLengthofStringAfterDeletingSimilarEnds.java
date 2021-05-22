package LC1501_1800;
import java.util.*;
public class LC1750_MinimumLengthofStringAfterDeletingSimilarEnds {
    /**
     * Given a string s consisting only of characters 'a', 'b', and 'c'. You are asked to apply the following algorithm
     * on the string any number of times:
     *
     * Pick a non-empty prefix from the string s where all the characters in the prefix are equal.
     * Pick a non-empty suffix from the string s where all the characters in this suffix are equal.
     * The prefix and the suffix should not intersect at any index.
     * The characters from the prefix and suffix must be the same.
     * Delete both the prefix and the suffix.
     * Return the minimum length of s after performing the above operation any number of times (possibly zero times).
     *
     * Input: s = "cabaabac"
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= s.length <= 105
     * s only consists of characters 'a', 'b', and 'c'.
     *
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public static int minimumLength(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int i = 0, j = s.length() - 1;
        while (i < j && s.charAt(i) == s.charAt(j)) {
            char c = s.charAt(i++);
            while (i < s.length() && s.charAt(i) == c) i++;
            while (i < j && s.charAt(j) == c) j--;
        }
        if (i > j) return 0;
        return j - i + 1;
    }
}

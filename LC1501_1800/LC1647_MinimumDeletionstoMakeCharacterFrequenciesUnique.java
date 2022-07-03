package LC1501_1800;
import java.util.*;
public class LC1647_MinimumDeletionstoMakeCharacterFrequenciesUnique {
    /**
     * A string s is called good if there are no two different characters in s that have the same frequency.
     *
     * Given a string s, return the minimum number of characters you need to delete to make s good.
     *
     * The frequency of a character in a string is the number of times it appears in the string. For example, in the
     * string "aab", the frequency of 'a' is 2, while the frequency of 'b' is 1.
     *
     * Input: s = "aab"
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s contains only lowercase English letters.
     * @param s
     * @return
     */
    // time = O(1), space = O(1)
    public int minDeletions(String s) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) count[s.charAt(i) - 'a']++;

        HashSet<Integer> set = new HashSet<>();
        int res = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                while (!set.add(count[i]) && count[i] > 0) {
                    count[i]--;
                    res++;
                }
            }
        }
        return res;
    }
}

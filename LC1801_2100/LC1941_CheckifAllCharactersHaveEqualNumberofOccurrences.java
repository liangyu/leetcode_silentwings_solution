package LC1801_2100;

public class LC1941_CheckifAllCharactersHaveEqualNumberofOccurrences {
    /**
     * Given a string s, return true if s is a good string, or false otherwise.
     *
     * A string s is good if all the characters that appear in s have the same number of occurrences (i.e., the same
     * frequency).
     *
     * Input: s = "abacbc"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean areOccurrencesEqual(String s) {
        // corner case
        if (s == null || s.length() == 0) return false;

        int[] arr = new int[26];
        for (char c : s.toCharArray()) arr[c - 'a']++;

        int prev = 0;
        for (int n : arr) {
            if (n > 0) {
                if (prev != 0 && prev != n) return false;
                prev = n;
            }
        }
        return true;
    }
}

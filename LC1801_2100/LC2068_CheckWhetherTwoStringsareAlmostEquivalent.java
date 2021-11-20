package LC1801_2100;

public class LC2068_CheckWhetherTwoStringsareAlmostEquivalent {
    /**
     * Two strings word1 and word2 are considered almost equivalent if the differences between the frequencies of each
     * letter from 'a' to 'z' between word1 and word2 is at most 3.
     *
     * Given two strings word1 and word2, each of length n, return true if word1 and word2 are almost equivalent, or
     * false otherwise.
     *
     * The frequency of a letter x is the number of times it occurs in the string.
     *
     * Input: word1 = "aaaa", word2 = "bccb"
     * Output: false
     *
     * Constraints:
     *
     * n == word1.length == word2.length
     * 1 <= n <= 100
     * word1 and word2 consist only of lowercase English letters.
     * @param word1
     * @param word2
     * @return
     */
    // time = O(max(m, n)), space = O(1)
    public boolean checkAlmostEquivalent(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[] arr1 = new int[26];
        int[] arr2 = new int[26];

        int i = 0, j = 0;
        while (i < m || j < n) {
            if (i < m) {
                char c1 = word1.charAt(i);
                arr1[c1 - 'a']++;
                i++;
            }
            if (j < n) {
                char c2 = word2.charAt(j);
                arr2[c2 - 'a']++;
                j++;
            }
        }

        for (i = 0; i < 26; i++) {
            if (Math.abs(arr1[i] - arr2[i]) > 3) return false;
        }
        return true;
    }
}

package LC1801_2100;

public class LC1967_NumberofStringsThatAppearasSubstringsinWord {
    /**
     * Given an array of strings patterns and a string word, return the number of strings in patterns that exist as a
     * substring in word.
     *
     * A substring is a contiguous sequence of characters within a string.
     *
     * Input: patterns = ["a","abc","bc","d"], word = "abc"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= patterns.length <= 100
     * 1 <= patterns[i].length <= 100
     * 1 <= word.length <= 100
     * patterns[i] and word consist of lowercase English letters.
     * @param patterns
     * @param word
     * @return
     */
    // time = O(n * k), space = O(1)
    public int numOfStrings(String[] patterns, String word) {
        // corner case
        if (patterns == null || patterns.length == 0) return 0;

        int count = 0;
        for (String s : patterns) {
            if (word.indexOf(s) != -1) count++;
        }
        return count;
    }
}

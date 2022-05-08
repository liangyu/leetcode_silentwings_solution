package LC2101_2400;

public class LC2255_CountPrefixesofaGivenString {
    /**
     * You are given a string array words and a string s, where words[i] and s comprise only of lowercase English letters.
     *
     * Return the number of strings in words that are a prefix of s.
     *
     * A prefix of a string is a substring that occurs at the beginning of the string. A substring is a contiguous
     * sequence of characters within a string.
     *
     * Input: words = ["a","b","c","ab","bc","abc"], s = "abc"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= words.length <= 1000
     * 1 <= words[i].length, s.length <= 10
     * words[i] and s consist of lowercase English letters only.
     * @param words
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int countPrefixes(String[] words, String s) {
        int count = 0;
        for (String word : words) {
            if (s.indexOf(word) == 0) count++;
        }
        return count;
    }
}

package LC2101_2400;

public class LC2185_CountingWordsWithaGivenPrefix {
    /**
     * You are given an array of strings words and a string pref.
     *
     * Return the number of strings in words that contain pref as a prefix.
     *
     * A prefix of a string s is any leading contiguous substring of s.
     *
     * Input: words = ["pay","attention","practice","attend"], pref = "at"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length, pref.length <= 100
     * words[i] and pref consist of lowercase English letters.
     * @param words
     * @param pref
     * @return
     */
    // time = O(n), space = O(1)
    public int prefixCount(String[] words, String pref) {
        int count = 0;
        for (String word : words) {
            if (word.indexOf(pref) == 0) count++;
        }
        return count;
    }
}

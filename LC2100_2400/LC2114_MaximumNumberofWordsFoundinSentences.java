package LC2100_2400;

public class LC2114_MaximumNumberofWordsFoundinSentences {
    /**
     * A sentence is a list of words that are separated by a single space with no leading or trailing spaces.
     *
     * You are given an array of strings sentences, where each sentences[i] represents a single sentence.
     *
     * Return the maximum number of words that appear in a single sentence.
     *
     * Input: sentences = ["alice and bob love leetcode", "i think so too", "this is great thanks very much"]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= sentences.length <= 100
     * 1 <= sentences[i].length <= 100
     * sentences[i] consists only of lowercase English letters and ' ' only.
     * sentences[i] does not have leading or trailing spaces.
     * All the words in sentences[i] are separated by a single space.
     * @param sentences
     * @return
     */
    // time = O(n * k), space = O(1)
    public int mostWordsFound(String[] sentences) {
        int res = 0;
        for (String s : sentences) {
            int space = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') space++;
            }
            res = Math.max(res, space + 1);
        }
        return res;
    }
}

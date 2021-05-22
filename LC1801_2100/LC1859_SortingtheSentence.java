package LC1801_2100;
import java.util.*;
public class LC1859_SortingtheSentence {
    /**
     * A sentence is a list of words that are separated by a single space with no leading or trailing spaces. Each word
     * consists of lowercase and uppercase English letters.
     *
     * A sentence can be shuffled by appending the 1-indexed word position to each word then rearranging the words in
     * the sentence.
     *
     * For example, the sentence "This is a sentence" can be shuffled as "sentence4 a3 is2 This1" or "is2 sentence4
     * This1 a3".
     * Given a shuffled sentence s containing no more than 9 words, reconstruct and return the original sentence.
     *
     * Input: s = "is2 sentence4 This1 a3"
     * Output: "This is a sentence"
     *
     * Constraints:
     *
     * 2 <= s.length <= 200
     * s consists of lowercase and uppercase English letters, spaces, and digits from 1 to 9.
     * The number of words in s is between 1 and 9.
     * The words in s are separated by a single space.
     * s contains no leading or trailing spaces.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String sortSentence(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        String[] strs = s.split(" ");
        String[] res = new String[strs.length];

        for (int i = 0; i < strs.length; i++) {
            String word = strs[i];
            int idx = word.charAt(word.length() - 1) - '0';
            res[idx - 1] = word;

        }

        StringBuilder sb = new StringBuilder();
        for (String str : res) sb.append(str.substring(0, str.length() -1)).append(" ");
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}

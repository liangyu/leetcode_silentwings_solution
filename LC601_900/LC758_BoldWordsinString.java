package LC601_900;
import java.util.*;
public class LC758_BoldWordsinString {
    /**
     * Given an array of keywords words and a string s, make all appearances of all keywords words[i] in s bold. Any
     * letters between <b> and </b> tags become bold.
     *
     * Return s after adding the bold tags. The returned string should use the least number of tags possible, and the
     * tags should form a valid combination.
     *
     * Input: words = ["ab","bc"], s = "aabcd"
     * Output: "a<b>abc</b>d"
     *
     * Input: words = ["ab","cb"], s = "aabcd"
     * Output: "a<b>ab</b>cd"
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * 0 <= words.length <= 50
     * 1 <= words[i].length <= 10
     * s and words[i] consist of lowercase English letters.
     * @param words
     * @param s
     * @return
     */
    // time = O(m * n), space = O(n)
    public String boldWords(String[] words, String s) {
        int n = s.length();
        boolean[] bold = new boolean[n];
        int end = 0;
        for (int i = 0; i < n; i++) { // O(n)
            for (String word : words) { // O(m)
                if (s.startsWith(word, i)) {
                    end = Math.max(end, i + word.length());
                }
            }
            bold[i] = end > i;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (!bold[i]) {
                sb.append(s.charAt(i));
                continue;
            }
            int j = i;
            while (j < n && bold[j]) j++;
            sb.append("<b>").append(s.substring(i, j)).append("</b>");
            i = j - 1;
        }
        return sb.toString();
    }

    // S2:
    // time = O(m * n), space = O(n)
    public String boldWords2(String[] words, String s) {
        int bold = -1, n = s.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (String word : words) {
                if (s.startsWith(word, i)) {
                    if (bold < i) sb.append("<b>");
                    bold = Math.max(bold, i + word.length());
                }
            }
            if (i == bold) sb.append("</b>");
            sb.append(s.charAt(i));
        }
        if (bold == n) sb.append("</b>");
        return sb.toString();
    }
}

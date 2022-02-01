package LC601_900;
import java.util.*;
public class LC616_AddBoldTaginString {
    /**
     * You are given a string s and an array of strings words. You should add a closed pair of bold tag <b> and </b> to
     * wrap the substrings in s that exist in words. If two such substrings overlap, you should wrap them together with
     * only one pair of closed bold-tag. If two substrings wrapped by bold tags are consecutive, you should combine them.
     *
     * Return s after adding the bold tags.
     *
     * Input: s = "abcxyz123", words = ["abc","123"]
     * Output: "<b>abc</b>xyz<b>123</b>"
     *
     * Input: s = "aaabbcc", words = ["aaa","aab","bc"]
     * Output: "<b>aaabbc</b>c"
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * 0 <= words.length <= 100
     * 1 <= words[i].length <= 1000
     * s and words[i] consist of English letters and digits.
     * All the values of words are unique.
     *
     *
     * Note: This question is the same as 758: https://leetcode.com/problems/bold-words-in-string/
     * @param s
     * @param dict
     * @return
     */
    // time = O(m * n), space = O(n)
    public String addBoldTag(String s, String[] dict) {
        int n = s.length();
        boolean[] bold = new boolean[n];
        int end = 0;
        for (int i = 0; i < n; i++) {
            for (String word : dict) {
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
}

package LC1801_2100;
import java.util.*;
public class LC2062_CountVowelSubstringsofaString {
    /**
     * A substring is a contiguous (non-empty) sequence of characters within a string.
     *
     * A vowel substring is a substring that only consists of vowels ('a', 'e', 'i', 'o', and 'u') and has all five
     * vowels present in it.
     *
     * Given a string word, return the number of vowel substrings in word.
     *
     * Input: word = "aeiouu"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= word.length <= 100
     * word consists of lowercase English letters only.
     * @param word
     * @return
     */
    // time = O(n), space = O(1)
    public int countVowelSubstrings(String word) {
        // corner case
        if (word == null || word.length() == 0) return 0;

        int n = word.length(), count = 0, right = -1, left = -1;
        int[] arr = new int[26];
        Arrays.fill(arr, -1);
        for (int i = 0; i < n; i++){
            char c = word.charAt(i);
            boolean flag = false;
            if ("aeiou".indexOf(c) != -1) {
                flag = true;
                arr[c - 'a'] = i;
            }
            if (!flag) left = i;
            right = n;
            for (char ch : "aeiou".toCharArray()) {
                right = Math.min(right, arr[ch - 'a']);
            }
            count += Math.max(0, right - left);
        }
        return count;
    }
}

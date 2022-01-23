package LC2101_2400;
import java.util.*;
public class LC2131_LongestPalindromebyConcatenatingTwoLetterWords {
    /**
     * You are given an array of strings words. Each element of words consists of two lowercase English letters.
     *
     * Create the longest possible palindrome by selecting some elements from words and concatenating them in any order.
     * Each element can be selected at most once.
     *
     * Return the length of the longest palindrome that you can create. If it is impossible to create any palindrome,
     * return 0.
     *
     * A palindrome is a string that reads the same forward and backward.
     *
     * Input: words = ["lc","cl","gg"]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= words.length <= 10^5
     * words[i].length == 2
     * words[i] consists of lowercase English letters.
     * @param words
     * @return
     */
    // S1: HashMap
    // time = O(n), space = O(n)
    public int longestPalindrome(String[] words) {
        HashMap<String, Integer> map = new HashMap<>();
        HashSet<String> set = new HashSet<>();
        for (String word : words) map.put(word, map.getOrDefault(word, 0) + 1);

        int res = 0;
        for (String word : words) {
            if (!map.containsKey(word)) continue;
            if (word.charAt(0) == word.charAt(1)) {
                int count = map.get(word);
                if (count % 2 == 0) res += 2 * count;
                else {
                    res += 2 * (count - 1);
                    set.add(word);
                }
                map.remove(word);
            } else {
                String rev = reverse(word);
                if (map.containsKey(rev)) {
                    res += 2 * 2 * Math.min(map.get(word), map.get(rev));
                    map.remove(word);
                    map.remove(rev);
                }
            }
        }
        if (set.size() > 0) res += 2;
        return res;
    }

    private String reverse(String word) {
        StringBuilder sb = new StringBuilder();
        sb.append(word);
        return sb.reverse().toString();
    }
}

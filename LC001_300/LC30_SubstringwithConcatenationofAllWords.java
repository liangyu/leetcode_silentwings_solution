package LC001_300;
import java.util.*;
public class LC30_SubstringwithConcatenationofAllWords {
    /**
     * You are given a string s and an array of strings words of the same length. Return all starting indices of
     * substring(s) in s that is a concatenation of each word in words exactly once, in any order, and without any
     * intervening characters.
     *
     * You can return the answer in any order.
     *
     * Input: s = "barfoothefoobarman", words = ["foo","bar"]
     * Output: [0,9]
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^4
     * s consists of lower-case English letters.
     * 1 <= words.length <= 5000
     * 1 <= words[i].length <= 30
     * words[i] consists of lower-case English letters.
     * @param s
     * @param words
     * @return
     */
    // time = O(n * l * w), space = O(n * w)    l: length of string s, n: words.length
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0 || words == null || words.length == 0) return res;

        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) map.put(word, map.getOrDefault(word, 0) + 1);

        int w = words[0].length(), n = words.length;
        for (int i = 0; i <= s.length() - n * w; i++) { // O(l)
            // create a copy of hashmap
            HashMap<String, Integer> copy = new HashMap<>(map);
            int k = n, j = i;
            while (k > 0) { // O(n)
                String str = s.substring(j, j + w); // O(w)
                if (!copy.containsKey(str) || copy.get(str) < 1) break;
                copy.put(str, copy.get(str) - 1);
                k--;
                j += w;
            }
            if (k == 0) res.add(i);
        }
        return res;
    }
}

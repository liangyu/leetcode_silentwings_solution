package LC601_900;
import java.util.*;
public class LC890_FindandReplacePattern {
    /**
     * Given a list of strings words and a string pattern, return a list of words[i] that match pattern. You may return
     * the answer in any order.
     *
     * A word matches the pattern if there exists a permutation of letters p so that after replacing every letter x in
     * the pattern with p(x), we get the desired word.
     *
     * Recall that a permutation of letters is a bijection from letters to letters: every letter maps to another letter,
     * and no two letters map to the same letter.
     *
     * Input: words = ["abc","deq","mee","aqq","dkd","ccc"], pattern = "abb"
     * Output: ["mee","aqq"]
     *
     * Constraints:
     *
     * 1 <= pattern.length <= 20
     * 1 <= words.length <= 50
     * words[i].length == pattern.length
     * pattern and words[i] are lowercase English letters.
     * @param words
     * @param pattern
     * @return
     */
    // S1
    // time = O(n * k), space = O(1)  k: length of each word, only 26 chars -> the space of HashMap is constant
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> res = new ArrayList<>();
        // corner case
        if (words == null || words.length == 0 || pattern == null || pattern.length() == 0) return res;

        for (String word : words) {
            if (word.length() != pattern.length()) continue;
            if (isMatch(word, pattern)) res.add(word);
        }
        return res;
    }

    private boolean isMatch(String word, String pattern) {
        HashMap<Character, Character> map1 = new HashMap<>();
        HashMap<Character, Character> map2 = new HashMap<>();
        int n = word.length();
        for (int i = 0; i < n; i++) {
            char c1 = word.charAt(i), c2 = pattern.charAt(i);
            if (map1.containsKey(c1) && map1.get(c1) != c2) return false;
            if (map2.containsKey(c2) && map2.get(c2) != c1) return false;
            map1.putIfAbsent(c1, c2);
            map2.putIfAbsent(c2, c1);
        }
        return true;
    }

    // S2
    // time = O(n * k), space = O(1)
    public List<String> findAndReplacePattern2(String[] words, String pattern) {
        List<String> res = new ArrayList<>();
        // corner case
        if (words == null || words.length == 0 || pattern == null || pattern.length() == 0) return res;

        int[] p = helper(pattern);
        for (String word : words) {
            if (Arrays.equals(helper(word), p)) res.add(word);
        }
        return res;
    }

    private int[] helper(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int n = s.length();
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            map.putIfAbsent(s.charAt(i), map.size());
            res[i] = map.get(s.charAt(i));
        }
        return res;
    }
}

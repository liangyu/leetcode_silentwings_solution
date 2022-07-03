package LC2101_2400;
import java.util.*;
public class LC2273_FindResultantArrayAfterRemovingAnagrams {
    /**
     * You are given a 0-indexed string array words, where words[i] consists of lowercase English letters.
     *
     * In one operation, select any index i such that 0 < i < words.length and words[i - 1] and words[i] are anagrams,
     * and delete words[i] from words. Keep performing this operation as long as you can select an index that satisfies
     * the conditions.
     *
     * Return words after performing all operations. It can be shown that selecting the indices for each operation in
     * any arbitrary order will lead to the same result.
     *
     * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase using all the
     * original letters exactly once. For example, "dacb" is an anagram of "abdc".
     *
     * Input: words = ["abba","baba","bbaa","cd","cd"]
     * Output: ["abba","cd"]
     *
     * Constraints:
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 10
     * words[i] consists of lowercase English letters.
     * @param words
     * @return
     */
    // time = O(n), space = O(1)
    public List<String> removeAnagrams(String[] words) {
        List<String> res = new ArrayList<>();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            res.add(words[i]);
            int j = i + 1;
            while (j < n && isAnagram(words[i], words[j])) j++;
            i = j - 1;
        }
        return res;
    }

    private boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;
        for (char c : t.toCharArray()) {
            count[c - 'a']--;
            if (count[c - 'a'] < 0) return false;
        }

        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }
}

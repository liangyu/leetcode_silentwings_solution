package LC901_1200;

public class LC1160_FindWordsThatCanBeFormedbyCharacters {
    /**
     * You are given an array of strings words and a string chars.
     *
     * A string is good if it can be formed by characters from chars (each character can only be used once).
     *
     * Return the sum of lengths of all good strings in words.
     *
     * Input: words = ["cat","bt","hat","tree"], chars = "atach"
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= words.length <= 1000
     * 1 <= words[i].length, chars.length <= 100
     * words[i] and chars consist of lowercase English letters.
     * @param words
     * @param chars
     * @return
     */
    // time = O(n * (k + l)), space = O(1)
    public int countCharacters(String[] words, String chars) {
        int res = 0;
        for (String word : words) { // O(n)
            if (checkOK(word, chars)) res += word.length();
        }
        return res;
    }

    private boolean checkOK(String word, String s) {
        if (word.length() > s.length()) return false;

        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        for (char c : word.toCharArray()) {
            freq[c - 'a']--;
            if (freq[c - 'a'] < 0) return false;
        }
        return true;
    }
}

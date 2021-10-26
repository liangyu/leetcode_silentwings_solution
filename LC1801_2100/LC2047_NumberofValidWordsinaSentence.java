package LC1801_2100;

public class LC2047_NumberofValidWordsinaSentence {
    /**
     * A sentence consists of lowercase letters ('a' to 'z'), digits ('0' to '9'), hyphens ('-'), punctuation marks
     * ('!', '.', and ','), and spaces (' ') only. Each sentence can be broken down into one or more tokens separated
     * by one or more spaces ' '.
     *
     * A token is a valid word if:
     *
     * It only contains lowercase letters, hyphens, and/or punctuation (no digits).
     * There is at most one hyphen '-'. If present, it should be surrounded by lowercase characters ("a-b" is valid,
     * but "-ab" and "ab-" are not valid).
     * There is at most one punctuation mark. If present, it should be at the end of the token.
     * Examples of valid words include "a-b.", "afad", "ba-c", "a!", and "!".
     *
     * Given a string sentence, return the number of valid words in sentence.
     *
     * Input: sentence = "cat and  dog"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= sentence.length <= 1000
     * sentence only contains lowercase English letters, digits, ' ', '-', '!', '.', and ','.
     * There will be at least 1 token.
     * @param sentence
     * @return
     */
    // time = O(m * n), space = O(m + n)
    public int countValidWords(String sentence) {
        // corner case
        if (sentence == null || sentence.length() == 0) return 0;

        String[] strs = sentence.split("\\s+");
        int count = 0;
        for (String s : strs) {
            if (s.length() > 0 && isValid(s)) count++;
        }
        return count;
    }

    private boolean isValid(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length, count = 0;
        for (int i = 0; i < n; i++) {
            if (Character.isDigit(chars[i])) return false;
            if (chars[i] == '-') {
                if (i == 0 || i == n - 1) return false;
                if (!Character.isLowerCase(chars[i - 1]) || !Character.isLowerCase((chars[i + 1]))) return false;
                if (count > 0) return false;
                count++;
            }
            if (chars[i] == '.' || chars[i] == '!' || chars[i] == ',') {
                if (i != n - 1) return false;
            }
        }
        return true;
    }
}

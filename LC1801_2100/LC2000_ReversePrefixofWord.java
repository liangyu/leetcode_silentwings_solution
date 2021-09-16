package LC1801_2100;

public class LC2000_ReversePrefixofWord {
    /**
     * Given a 0-indexed string word and a character ch, reverse the segment of word that starts at index 0 and ends
     * at the index of the first occurrence of ch (inclusive). If the character ch does not exist in word, do nothing.
     *
     * For example, if word = "abcdefd" and ch = "d", then you should reverse the segment that starts at 0 and ends at
     * 3 (inclusive). The resulting string will be "dcbaefd".
     * Return the resulting string.
     *
     * Input: word = "abcdefd", ch = "d"
     * Output: "dcbaefd"
     *
     * Constraints:
     *
     * 1 <= word.length <= 250
     * word consists of lowercase English letters.
     * ch is a lowercase English letter.
     * @param word
     * @param ch
     * @return
     */
    // time = O(n), space = O(n)
    public String reversePrefix(String word, char ch) {
        // corner case
        if (word == null || word.length() == 0) return "";
        int idx = word.indexOf(ch);
        return idx == -1 ? word : reverse(word.substring(0, idx + 1)) + word.substring(idx + 1);
    }

    private String reverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }
}

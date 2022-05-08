package LC2101_2400;

public class LC2207_MaximizeNumberofSubsequencesinaString {
    /**
     * You are given a 0-indexed string text and another 0-indexed string pattern of length 2, both of which consist of
     * only lowercase English letters.
     *
     * You can add either pattern[0] or pattern[1] anywhere in text exactly once. Note that the character can be added
     * even at the beginning or at the end of text.
     *
     * Return the maximum number of times pattern can occur as a subsequence of the modified text.
     *
     * A subsequence is a string that can be derived from another string by deleting some or no characters without
     * changing the order of the remaining characters.
     *
     * Input: text = "abdcdbc", pattern = "ac"
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= text.length <= 10^5
     * pattern.length == 2
     * text and pattern consist only of lowercase English letters.
     * @param text
     * @param pattern
     * @return
     */
    // time = O(n), space = O(1)
    public long maximumSubsequenceCount(String text, String pattern) {
        int n = text.length(), count = 1;
        long option1 = 0, option2 = 0;

        for (char c : text.toCharArray()) {
            if (c == pattern.charAt(1)) option1 += count;
            if (c == pattern.charAt(0)) count++;
        }
        count = 1;
        for (int i = n - 1; i >= 0; i--) {
            char c = text.charAt(i);
            if (c == pattern.charAt(0)) option2 += count;
            if (c == pattern.charAt(1)) count++;
        }
        return Math.max(option1, option2);
    }
}

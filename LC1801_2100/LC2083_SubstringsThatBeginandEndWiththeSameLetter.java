package LC1801_2100;

public class LC2083_SubstringsThatBeginandEndWiththeSameLetter {
    /**
     * You are given a 0-indexed string s consisting of only lowercase English letters. Return the number of substrings
     * in s that begin and end with the same character.
     *
     * A substring is a contiguous non-empty sequence of characters within a string.
     *
     * Input: s = "abcba"
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists only of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public long numberOfSubstrings(String s) {
        int[] freq = new int[26];
        long count = 0;
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
            count += freq[c - 'a'];
        }
        return count;
    }
}
/**
 * Let's say n is a number of 'a' chars you have already seen.
 * Next time you see another 'a', you can make n + 1 substrings: n with previous 'a', and one with itself.
 * So, just keep counting each letter and keep adding.
 */
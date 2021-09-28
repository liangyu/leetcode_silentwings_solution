package LC1201_1500;

public class LC1328_BreakaPalindrome {
    /**
     * Given a palindromic string of lowercase English letters palindrome, replace exactly one character with any
     * lowercase English letter so that the resulting string is not a palindrome and that it is the lexicographically
     * smallest one possible.
     *
     * Return the resulting string. If there is no way to replace a character to make it not a palindrome, return an
     * empty string.
     *
     * A string a is lexicographically smaller than a string b (of the same length) if in the first position where a
     * and b differ, a has a character strictly smaller than the corresponding character in b. For example, "abcc"
     * is lexicographically smaller than "abcd" because the first position they differ is at the fourth character,
     * and 'c' is smaller than 'd'.
     *
     * Input: palindrome = "abccba"
     * Output: "aaccba"
     *
     * Constraints:
     *
     * 1 <= palindrome.length <= 1000
     * palindrome consists of only lowercase English letters.
     * @param palindrome
     * @return
     */
    // time = O(n), space = O(n)
    public String breakPalindrome(String palindrome) {
        // corner case
        if (palindrome == null || palindrome.length() <= 1) return ""; // 注意当len == 1时无论如何都是palin，只能返回""

        int n = palindrome.length();
        char[] chars = palindrome.toCharArray();
        for (int i = 0; i < n / 2; i++) {
            if (chars[i] != 'a') {
                chars[i] = 'a';
                return String.valueOf(chars);
            }
        }
        chars[n - 1] = (char)(chars[n - 1] + 1);
        return String.valueOf(chars);
    }
}

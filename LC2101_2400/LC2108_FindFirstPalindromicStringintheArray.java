package LC2101_2400;

public class LC2108_FindFirstPalindromicStringintheArray {
    /**
     * Given an array of strings words, return the first palindromic string in the array. If there is no such string,
     * return an empty string "".
     *
     * A string is palindromic if it reads the same forward and backward.
     *
     * Input: words = ["abc","car","ada","racecar","cool"]
     * Output: "ada"
     *
     * Constraints:
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 100
     * words[i] consists only of lowercase English letters.
     * @param words
     * @return
     */
    // time = O(n * k), space = O(1)
    public String firstPalindrome(String[] words) {
        for (String word : words) {
            if (isPalin(word)) return word;
        }
        return "";
    }

    private boolean isPalin(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}

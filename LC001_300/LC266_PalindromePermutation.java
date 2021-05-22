package LC001_300;
import java.util.*;
public class LC266_PalindromePermutation {
    /**
     * Given a string s, return true if a permutation of the string could form a palindrome.
     *
     * Input: s = "code"
     * Output: false
     *
     * Constraints:
     *
     * 1 <= s.length <= 5000
     * s consists of only lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean canPermutePalindrome(String s) {
        // corner case
        if (s == null || s.length() == 0) return true;

        int[] f = new int[26];
        for (char c : s.toCharArray()) {
            if (f[c - 'a'] > 0) f[c - 'a']--;
            else f[c - 'a']++;
        }
        int count = 0;
        for (int n : f) {
            if (n == 1) count++;
        }
        return count <= 1;
    }
}

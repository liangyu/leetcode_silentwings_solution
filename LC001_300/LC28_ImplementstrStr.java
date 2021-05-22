package LC001_300;
import java.util.*;
public class LC28_ImplementstrStr {
    /**
     * Implement strStr().
     *
     * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     *
     * Clarification:
     *
     * What should we return when needle is an empty string? This is a great question to ask during an interview.
     *
     * For the purpose of this problem, we will return 0 when needle is an empty string.
     * This is consistent to C's strstr() and Java's indexOf().
     *
     * Input: haystack = "hello", needle = "ll"
     * Output: 2
     *
     * Constraints:
     *
     * 0 <= haystack.length, needle.length <= 5 * 104
     * haystack and needle consist of only lower-case English characters.
     *
     * @param haystack
     * @param needle
     * @return
     */
    // S1: double for loop (prefer!!!)
    // time = O(m * n), space = O(1)
    public int strStr(String haystack, String needle) {
        // corner case
        if (haystack == null || needle == null) {
            return -1;
        }

        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            int j = 0;
            for (j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            if (j == needle.length()) return i;
        }
        return -1;
    }

    // S2: Rabin-Karp 常数复杂度
    // time = O(n), space = O(1)
    public int strStr2(String haystack, String needle) {
        // corner case
        if (haystack == null || needle == null) return -1;
        if (needle.length() == 0) return 0;

        int len1 = haystack.length(), len2 = needle.length();
        if (len1 < len2) return -1;

        // base value for the rolling hash function
        int a = 26;
        // modulus value for the rolling hash function to avoid overflow
        long modulus = (long)Math.pow(2, 31);

        // compute the hash of the strings haystack and needle
        long h = 0, ref_h = 0;
        for (int i = 0; i < len2; i++) {
            h = (h * a + charToInt(i, haystack)) % modulus;
            ref_h = (ref_h * a + charToInt(i, needle)) % modulus;
        }
        if (h == ref_h) return 0;

        long diff = (long)Math.pow(a, len2) % modulus;

        for (int i = 1; i <= len1 - len2; i++) {
            h = (h * a - charToInt(i - 1, haystack) * diff + charToInt(i + len2 - 1, haystack)) % modulus;
            if (h == ref_h) return i;
        }
        return -1;
    }

    private int charToInt(int i, String s) {
        return s.charAt(i) - 'a';
    }
}

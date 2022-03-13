package LC301_600;

public class LC567_PermutationinString {
    /**
     * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
     *
     * In other words, return true if one of s1's permutations is the substring of s2.
     *
     * Input: s1 = "ab", s2 = "eidbaooo"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s1.length, s2.length <= 10^4
     * s1 and s2 consist of lowercase English letters.
     * @param s1
     * @param s2
     * @return
     */
    // S1: brute-force
    // time = O(n * (m + n)), space = O(1)
    public boolean checkInclusion(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        if (m > n) return false;

        for (int i = 0; i <= n - m; i++) {
            if (helper(s2.substring(i, i + m), s1)) return true;
        }
        return false;
    }

    private boolean helper(String s1, String s2) {
        int[] count = new int[26];
        for (char c : s1.toCharArray()) count[c - 'a']++;
        for (char c : s2.toCharArray()) {
            count[c - 'a']--;
            if (count[c - 'a'] < 0) return false;
        }

        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }

    // S2: sliding window (optimal solution!!!)
    // time = O(n), space = O(1)
    public boolean checkInclusion2(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        if (m > n) return false;

        int[] count = new int[26];
        for (int i = 0; i < m; i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        if (helper(count)) return true;

        for (int i = m; i < n; i++) {
            count[s2.charAt(i) - 'a']--;
            count[s2.charAt(i - m) - 'a']++;
            if (helper(count)) return true;
        }
        return false;
    }

    private boolean helper(int[] count) {
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }
}

package LC2101_2400;

public class LC2186_MinimumNumberofStepstoMakeTwoStringsAnagramII {
    /**
     * You are given two strings s and t. In one step, you can append any character to either s or t.
     *
     * Return the minimum number of steps to make s and t anagrams of each other.
     *
     * An anagram of a string is a string that contains the same characters with a different (or the same) ordering.
     *
     * Input: s = "leetcode", t = "coats"
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= s.length, t.length <= 2 * 10^5
     * s and t consist of lowercase English letters.
     * @param s
     * @param t
     * @return
     */
    // time = O(n), space = O(1)
    public int minSteps(String s, String t) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;
        for (char c : t.toCharArray()) freq[c - 'a']--;

        int res = 0;
        for (int i = 0; i < 26; i++) {
            res += Math.abs(freq[i]);
        }
        return res;
    }
}

package LC301_600;

public class LC522_LongestUncommonSubsequenceII {
    /**
     * Given an array of strings strs, return the length of the longest uncommon subsequence between them. If the
     * longest uncommon subsequence does not exist, return -1.
     *
     * An uncommon subsequence between an array of strings is a string that is a subsequence of one string but not
     * the others.
     *
     * A subsequence of a string s is a string that can be obtained after deleting any number of characters from s.
     *
     * For example, "abc" is a subsequence of "aebdc" because you can delete the underlined characters in "aebdc" to
     * get "abc". Other subsequences of "aebdc" include "aebdc", "aeb", and "" (empty string).
     *
     * Input: strs = ["aba","cdc","eae"]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= strs.length <= 50
     * 1 <= strs[i].length <= 10
     * strs[i] consists of lowercase English letters.
     * @param strs
     * @return
     */
    // time = O(n^2 * k), space = O(1)  k: avg length of each string
    public int findLUSlength(String[] strs) {
        // corner case
        if (strs == null || strs.length <= 1) return 0;

        int n = strs.length, res = -1, j = 0;
        for (int i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j) continue;
                if (isSubsequence(strs[i], strs[j])) break;
            }
            if (j == n) res = Math.max(res, strs[i].length());
        }
        return res;
    }

    private boolean isSubsequence(String a, String b) {
        int i = 0, j = 0;
        while (i < a.length() && j < b.length()) {
            if (a.charAt(i) == b.charAt(j)) i++;
            j++;
        }
        return i == a.length();
    }
}

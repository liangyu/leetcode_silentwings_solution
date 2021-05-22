package LC001_300;
import java.util.*;
public class LC267_PalindromePermutationII {
    /**
     * Given a string s, return all the palindromic permutations (without duplicates) of it.
     *
     * You may return the answer in any order. If s has no palindromic permutation, return an empty list.
     *
     * Input: s = "aabb"
     * Output: ["abba","baab"]
     *
     * Constraints:
     *
     * 1 <= s.length <= 16
     * s consists of only lowercase English letters.
     * @param s
     * @return
     */
    // time = O((n/2 + 1)!), space = O(n)
    public List<String> generatePalindromes(String s) {
        List<String> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0) return res;

        int n = s.length();
        // count all chars
        int[] f = new int[128];
        for (char c : s.toCharArray()) f[c]++;

        // count all odd chars
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < f.length; i++) {
            if (f[i] % 2 != 0) sb.append((char)i);
        }

        // if there is more than 1 odd chars, then return empty res list
        if (sb.length() > 1) return res;

        dfs(f, n, sb, res);
        return res;
    }

    private void dfs(int[] f, int n, StringBuilder sb, List<String> res) {
        // base case
        if (sb.length() == n) {
            res.add(sb.toString());
            return;
        }

        for (int i = 0; i < f.length; i++) {
            if (f[i] > 1) {
                f[i] -= 2;
                sb.insert(0, (char)i);
                sb.append((char)i);

                dfs(f, n, sb, res);

                // setback
                sb.deleteCharAt(0);
                sb.deleteCharAt(sb.length() - 1);
                f[i] += 2;
            }
        }
    }
}

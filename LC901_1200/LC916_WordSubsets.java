package LC901_1200;
import java.util.*;
public class LC916_WordSubsets {
    /**
     * We are given two arrays A and B of words.  Each word is a string of lowercase letters.
     *
     * Now, say that word b is a subset of word a if every letter in b occurs in a, including multiplicity.
     * For example, "wrr" is a subset of "warrior", but is not a subset of "world".
     *
     * Now say a word a from A is universal if for every b in B, b is a subset of a.
     *
     * Return a list of all universal words in A.  You can return the words in any order.
     *
     * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["e","o"]
     * Output: ["facebook","google","leetcode"]
     *
     * Note:
     *
     * 1 <= A.length, B.length <= 10000
     * 1 <= A[i].length, B[i].length <= 10
     * A[i] and B[i] consist only of lowercase letters.
     * All words in A[i] are unique: there isn't i != j with A[i] == A[j].
     * @param A
     * @param B
     * @return
     */
    // time = O(m + n), space = O(1)
    public List<String> wordSubsets(String[] A, String[] B) {
        List<String> res = new ArrayList<>();
        // corner case
        if (A == null || A.length == 0 || B == null || B.length == 0) return res;

        int[] bmax = new int[26];
        for (String s : B) {
            int[] count = helper(s);
            for (int i = 0; i < 26; i++) {
                bmax[i] = Math.max(bmax[i], count[i]); // find the max count of each char from string of B
            }
        }

        for (String s : A) {
            int[] count = helper(s);
            boolean flag = false;
            for (int i = 0; i < 26; i++) {
                if (count[i] < bmax[i]) {
                    flag = true;
                    break;
                }
            }
            if (!flag) res.add(s);
        }
        return res;
    }

    private int[] helper(String s) {
        int[] res = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            res[c - 'a']++;
        }
        return res;
    }
}

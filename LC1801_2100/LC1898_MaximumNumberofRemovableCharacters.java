package LC1801_2100;
import java.util.*;
public class LC1898_MaximumNumberofRemovableCharacters {
    /**
     * You are given two strings s and p where p is a subsequence of s. You are also given a distinct 0-indexed integer
     * array removable containing a subset of indices of s (s is also 0-indexed).
     *
     * You want to choose an integer k (0 <= k <= removable.length) such that, after removing k characters from s using
     * the first k indices in removable, p is still a subsequence of s. More formally, you will mark the character at
     * s[removable[i]] for each 0 <= i < k, then remove all marked characters and check if p is still a subsequence.
     *
     * Return the maximum k you can choose such that p is still a subsequence of s after the removals.
     *
     * A subsequence of a string is a new string generated from the original string with some characters (can be none)
     * deleted without changing the relative order of the remaining characters.
     *
     * Input: s = "abcacb", p = "ab", removable = [3,1,0]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= p.length <= s.length <= 10^5
     * 0 <= removable.length < s.length
     * 0 <= removable[i] < s.length
     * p is a subsequence of s.
     * s and p both consist of lowercase English letters.
     * The elements in removable are distinct.
     * @param s
     * @param p
     * @param removable
     * @return
     */
    // S1: B.S
    // time = O(mlogn), space = O(1)
    public int maximumRemovals(String s, String p, int[] removable) {
        char[] chars = s.toCharArray();
        int left = 0, right = removable.length;
        while (left <= right) { // O(logn)
            int mid = left + (right - left) / 2;
            for (int i = 0; i < mid; i++) chars[removable[i]] = '#';
            if (isSubsequence(chars, p)) left = mid + 1;
            else {
                for (int i = 0; i < mid; i++) chars[removable[i]] = s.charAt(removable[i]); // setback;
                right = mid - 1;
            }
        }
        return right;
    }

    private boolean isSubsequence(char[] chars, String p) {
        int idx1 = 0, idx2= 0;
        while (idx1 < chars.length && idx2 < p.length()) { // O(m)
            char c1 = chars[idx1], c2 = p.charAt(idx2);
            if (c1 != '#' && c1 == c2) idx2++;
            idx1++;
        }
        if (idx2 == p.length()) return true;
        return false;
    }

    // S2: B.S.
    // time = O(mlogn), space = O(k)
    public int maximumRemovals2(String s, String p, int[] removable) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < removable.length; i++) {
            map.put(removable[i], i);
        }
        int left = 0, right = removable.length;
        while (left < right) {
            int mid = right - (right - left) / 2; // 用0，1去试， 是否死循环，不是left+就是right-
            if (checkOK(map, s, p, mid)) { // mid可能是个解，但可以向上继续试探
                left = mid;
            } else { // mid删多了
                right = mid - 1; // mid肯定不是一个解
            }
        }
        // 一定有解
        return left;
    }

    private boolean checkOK(HashMap<Integer, Integer> map, String s, String p, int k) {
        int i = 0, j = 0;
        while (j < p.length()) {
            while (i < s.length() && s.charAt(i) != p.charAt(j)) i++;
            if (i == s.length()) break;
            if (!map.containsKey(i) || map.get(i) >= k) j++;
            i++;
        }
        return j == p.length();
    }
}
/**
 * LCS(s, p) == p ??? LTE!
 * map: i in s -> idx in removable
 *
 * binary search for k:
 * for k = n to 1   => 不一定从大到小挨个找 -> 单调性
 *      check if p is a subsequence of s w/ a forbidden set {removable[0:k-1]}
 *      for j = ...
 *          find s[i] = p[j] && (i not in removable || map[i] >= k)
 * while (left < right) {
 *
 * }
 * // left == right
 * return left;
 */

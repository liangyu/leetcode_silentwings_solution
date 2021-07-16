package LC1801_2100;
import java.util.*;
public class LC1930_UniqueLength3PalindromicSubsequences {
    /**
     * Given a string s, return the number of unique palindromes of length three that are a subsequence of s.
     *
     * Note that even if there are multiple ways to obtain the same subsequence, it is still only counted once.
     *
     * A palindrome is a string that reads the same forwards and backwards.
     *
     * A subsequence of a string is a new string generated from the original string with some characters (can be none)
     * deleted without changing the relative order of the remaining characters.
     *
     * For example, "ace" is a subsequence of "abcde".
     *
     * Input: s = "aabca"
     * Output: 3
     *
     * Constraints:
     *
     * 3 <= s.length <= 10^5
     * s consists of only lowercase English letters.
     * @param s
     * @return
     */
    // S1: use built-in indexOf function (better solution!)
    // time = O(26n), space = O(26) = O(1)
    public int countPalindromicSubsequence(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int count = 0;
        for (int i = 0; i < 26; i++) {
            char c = (char)(i + 'a');
            int start = s.indexOf(c);
            int end = s.lastIndexOf(c);
            if (end == -1) continue;
            HashSet<Character> set = new HashSet<>();
            for (int j = start + 1; j < end; j++) {
                set.add(s.charAt(j));
            }
            count += set.size();
        }
        return count;
    }

    // S2: HashMap + TreeSet
    // time = O(26n), space = O(26) = O(n)
    public int countPalindromicSubsequence2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        HashMap<Character, TreeSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            map.putIfAbsent(c, new TreeSet<>());
            map.get(c).add(i);
        }

        int res = 0;
        HashSet<String> set = new HashSet<>();
        for (char key : map.keySet()) {
            if (map.get(key).size() > 1) {
                findPalin(map, key, s, set);
            }
        }
        return set.size();
    }

    private void findPalin(HashMap<Character, TreeSet<Integer>> map, char key, String s, HashSet<String> ans) {
        TreeSet<Integer> set = map.get(key);
        if (set.size() >= 3) ans.add(key + "" + key + key);
        int fk = set.first();
        set.remove(fk);
        while (set.size() > 0) {
            int sk = set.first();
            set.remove(sk);
            for (int i = fk + 1; i <= sk - 1; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(key).append(s.charAt(i)).append(key);
                ans.add(sb.toString());
            }
            fk = sk;
        }
    }
}

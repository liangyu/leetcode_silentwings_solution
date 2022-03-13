package LC901_1200;
import java.util.*;
public class LC1081_SmallestSubsequenceofDistinctCharacters {
    /**
     * Given a string s, return the lexicographically smallest subsequence of s that contains all the distinct
     * characters of s exactly once.
     *
     * Input: s = "bcabc"
     * Output: "abc"
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of lowercase English letters.
     *
     * Note: This question is the same as 316: https://leetcode.com/problems/remove-duplicate-letters/
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String smallestSubsequence(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        Stack<Character> stack = new Stack<>();
        HashSet<Character> set = new HashSet<>();

        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (set.contains(c)) freq[c - 'a']--;
            else {
                while (!stack.isEmpty() && stack.peek() > c && freq[stack.peek() - 'a'] > 0) {
                    set.remove(stack.pop());
                }
                stack.push(c);
                freq[c - 'a']--;
                set.add(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());
        return sb.reverse().toString();
    }
}
/**
 * same as LC316
 * 单调栈经典应用！！！
 */
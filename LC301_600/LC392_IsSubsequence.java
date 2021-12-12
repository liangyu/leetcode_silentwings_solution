package LC301_600;
import java.util.*;
public class LC392_IsSubsequence {
    /**
     * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
     *
     * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none)
     * of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a
     *  subsequence of "abcde" while "aec" is not).
     *
     * Input: s = "abc", t = "ahbgdc"
     * Output: true
     *
     * Constraints:
     *
     * 0 <= s.length <= 100
     * 0 <= t.length <= 10^4
     * s and t consist only of lowercase English letters.
     *
     * Follow up: Suppose there are lots of incoming s, say s1, s2, ..., sk where k >= 10^9, and you want to check one
     * by one to see if t has its subsequence. In this scenario, how would you change your code?
     * @param t
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else j++;
        }
        return i == s.length();
    }

    // follow-up
    // time = O(m * n), space = O(m)
    public boolean isSubsequence2(String s, String t) {
        HashMap<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            map.putIfAbsent(t.charAt(i), new ArrayList<>());
            map.get(t.charAt(i)).add(i);
        }

        int nextStart = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean flag = false;
            for (int j : map.getOrDefault(c, new ArrayList<>())) {
                if (j > nextStart) {
                    flag = true;
                    nextStart = j;
                    break;
                }
            }
            if (!flag) return false;
        }
        return true;
    }
}
/**
 * 考虑follow up的问题。显然，需要提前建立关于t的一些信息的存储，方便s来查阅，用空间换时间嘛。
 * 结合双指针解法的思路，如果在t[pos]找到了s[i]，则只需要在pos之后去寻找s[i+1]。
 * 所以提前构建映射：t的每个字符和该字符出现位置:
 * Map[s[i]].push_back(i);
 * 建立pos的初始位置-1. 然后遍历s[i]：在Map[s[i]]里找第一个大于pos的位置并更新pos，则之后对于s[i+1]的查找必须从t的新pos位置开始。
 */

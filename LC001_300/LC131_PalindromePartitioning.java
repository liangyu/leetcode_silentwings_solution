package LC001_300;
import java.util.*;
public class LC131_PalindromePartitioning {
    /**
     * Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible
     * palindrome partitioning of s.
     *
     * A palindrome string is a string that reads the same backward as forward.
     *
     * Input: s = "aab"
     * Output: [["a","a","b"],["aa","b"]]
     *
     * Constraints:
     *
     * 1 <= s.length <= 16
     * s contains only lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n * 2^n), space = O(n)
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0) return res;

        dfs(s, 0, new ArrayList<>(), res);
        return res;
    }

    private void dfs(String s, int idx, List<String> path, List<List<String>> res) {
        // base case - success
        if (idx == s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = idx; i < s.length(); i++) {
            String sub = s.substring(idx, i + 1);
            if (isPalin(sub)) {
                path.add(sub);
                dfs(s, i + 1, path, res);
                path.remove(path.size() - 1);
            }
        }
    }

    private boolean isPalin(String s) {
        if (s == null || s.length() == 0) return true;


        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }
}
/**
 * 大框架：dfs
 * 挨个向后找 -> 确定回文 how? 双指针 * n
 * dp[i][j] -> 区间型dp  s[i:j] is palindrome
 * s[i] = s[j] && dp[i+1][j-1] is palindrome
 * 大区间依赖小区间
 * 考查2点：
 * 1. dfs
 * 2. 预处理回文串，判断回文的时间不会叠加在dfs的时间上
 */

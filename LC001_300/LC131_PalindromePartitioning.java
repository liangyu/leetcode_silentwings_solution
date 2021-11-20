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
    // S1: dfs
    // time = O(n * 2^n), space = O(n^2)
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

    // S2: dfs + dp
    // time = O(n * 2^n), space = O(n^2)
    public List<List<String>> partition2(String s) {
        List<List<String>> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0) return res;

        int n = s.length();
        boolean[][] isPalin = new boolean[n][n];
        helper(s, isPalin);

        dfs(s, 0, new ArrayList<>(), res, isPalin);
        return res;
    }

    private void dfs(String s, int idx, List<String> path, List<List<String>> res, boolean[][] isPalin) {
        int n = s.length();
        // base case
        if (idx == n) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = idx; i < n; i++) {
            if (isPalin[idx][i]) {
                path.add(s.substring(idx, i + 1));
                dfs(s, i + 1, path, res, isPalin);
                path.remove(path.size() - 1);
            }
        }
    }

    private void helper(String s, boolean[][] isPalin) {
        int n = s.length();
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || isPalin[i + 1][j - 1])) {
                    isPalin[i][j] = true;
                }
            }
        }
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

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
    boolean[][] isPalin;
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();

        int n = s.length();
        isPalin = new boolean[n][n];

        // init
        // j - 1 = i + len - 2 >= i + 1  => len >= 3
        // len = 1 => j = i => isPalin[i][i] = true
        for (int i = 0; i < n; i++) isPalin[i][i] = true;
        // len = 2 -> j = i + 1 -> i + 1 > j - 1
        for (int i = 0; i < n - 1; i++) isPalin[i][i + 1] = s.charAt(i) == s.charAt(i + 1);

        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && isPalin[i + 1][j - 1]) {
                    isPalin[i][j] = true;
                } else isPalin[i][j] = false;
            }
        }

        dfs(s, 0, new ArrayList<>(), res);
        return res;
    }

    private void dfs(String s, int idx, List<String> path, List<List<String>> res) {
        // base case
        if (idx == s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = idx; i < s.length(); i++) {
            if (isPalin[idx][i]) {
                path.add(s.substring(idx, i + 1));
                dfs(s, i + 1, path, res);
                path.remove(path.size() - 1);
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

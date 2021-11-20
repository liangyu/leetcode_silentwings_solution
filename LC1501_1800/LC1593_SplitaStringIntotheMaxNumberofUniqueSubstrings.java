package LC1501_1800;
import java.util.*;
public class LC1593_SplitaStringIntotheMaxNumberofUniqueSubstrings {
    /**
     * Given a string s, return the maximum number of unique substrings that the given string can be split into.
     *
     * You can split string s into any list of non-empty substrings, where the concatenation of the substrings forms
     * the original string. However, you must split the substrings such that all of them are unique.
     *
     * A substring is a contiguous sequence of characters within a string.
     *
     * Input: s = "ababccc"
     * Output: 5
     *
     * Constraints:
     * 1 <= s.length <= 16
     * s contains only lower case English letters.
     * @param s
     * @return
     */
    HashSet<String> set;
    int res = 0;
    public int maxUniqueSplit(String s) {
        set = new HashSet<>();
        dfs(s, 0, 0);
        return res;
    }

    private void dfs(String s, int idx, int count) {
        int n = s.length();
        // base case
        if (idx == n) {
            res = Math.max(res, count);
            return;
        }

        if (count + n - idx <= res) return; // 剪枝，即使后面每个字符都切出一个来，也超越不了当前的res!

        for (int i = idx; i < n; i++) {
            String sub = s.substring(idx, i + 1);
            if (!set.contains(sub)) {
                set.add(sub);
                dfs(s, i + 1, count + 1);
                set.remove(sub);
            }
        }
    }
}
/**
 * 1 <= s.length <= 16
 * 暴力穷举
 * DFS来搜索分组的方案是比较可行的方法。
 * 如果遇到当前分组不满足条件时，就可以终止剩余字符的分组。
 * 注意，不要试图用状态压缩来暴力枚举判断。因为它无法实现DFS的提前剪枝。
 * 01101010101101 -> 01相间自动做了切分  -> 2^16
 * 没法做提前的剪枝
 *     010xxxxx  -> 不是一个合法的切分
 * s = aaxxxxxx
 */
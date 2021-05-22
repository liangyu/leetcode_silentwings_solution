package LC001_300;
import java.util.*;
public class LC22_GenerateParentheses {
    /**
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     *
     * Input: n = 3
     * Output: ["((()))","(()())","(())()","()(())","()()()"]
     *
     * Constraints:
     *
     * 1 <= n <= 8
     * @param n
     * @return
     */
    // time = O(n * 4^n), space = O(n)
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        // corner case
        if (n <= 0) return res;

        dfs(n, 0, 0, 0, new StringBuilder(), res);
        return res;
    }

    private void dfs(int n, int l, int r, int delta, StringBuilder path, List<String> res) {
        // base case - success
        if (l + r == 2 * n && delta == 0) {
            res.add(path.toString());
            return;
        }

        // base case - fail
        if (l + r == 2 * n || delta < 0) return;

        // + '('
        path.append('(');
        dfs(n, l + 1, r, delta + 1, path, res);
        path.setLength(path.length() - 1);

        // + ')'
        path.append(')');
        dfs(n, l, r + 1, delta - 1, path, res);
        path.setLength(path.length() - 1);
    }
}

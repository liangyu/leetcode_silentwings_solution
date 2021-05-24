package LC001_300;
import java.util.*;
public class LC282_ExpressionAddOperators {
    /**
     * Given a string num that contains only digits and an integer target, return all possibilities to add the binary
     * operators '+', '-', or '*' between the digits of num so that the resultant expression evaluates to the target value.
     *
     * Input: num = "123", target = 6
     * Output: ["1*2*3","1+2+3"]
     *
     * Constraints:
     *
     * 1 <= num.length <= 10
     * num consists of only digits.
     * -2^31 <= target <= 2^31 - 1
     * @param num
     * @param target
     * @return
     */
    // time = O(4^n), space = O(n)
    public List<String> addOperators(String num, int target) {
        List<String> res = new ArrayList<>();
        // corner case
        if (num == null || num.length() == 0) return res;

        dfs(num, 0, target, 0, 0, new StringBuilder(), res);
        return res;
    }

    private void dfs(String s, int idx, int target, long sum, long lastVal, StringBuilder path, List<String> res) {
        // base case - success
        if (idx == s.length() && sum == target) {
            res.add(path.toString());
            return;
        }

        // base case - fail
        if (idx == s.length()) return;

        long val = 0;
        int len = path.length();
        for (int i = idx; i < s.length(); i++) {
            val = val * 10 + s.charAt(i) - '0';
            if (path.length() == 0) {
                path.append(val);
                dfs(s, i + 1, target, val, val, path, res);
                path.setLength(len);
            } else {
                path.append("+" + val);
                dfs(s, i + 1, target, sum + val, val, path, res);
                path.setLength(len);

                path.append("-" + val);
                dfs(s, i + 1, target, sum - val, -val, path, res);
                path.setLength(len);

                path.append("*" + val);
                dfs(s, i + 1, target, sum - lastVal + lastVal * val, lastVal * val, path, res);
                path.setLength(len);
            }
            if (val == 0) break;
        }
    }
}

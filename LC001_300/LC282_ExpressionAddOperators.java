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
    // S1
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

    // S2: DFS
    // time = O(4^n), space = O(n)
    public List<String> addOperators2(String num, int target) {
        List<String> res = new ArrayList<>();
        dfs(num, target, 0, "", 0, 0, res);
        return res;
    }

    private void dfs(String num, int target, int idx, String path, long preVal, long lastVal, List<String> res) {
        // base case
        if (idx == num.length()) {
            if (preVal == target) res.add(path.toString());
            return;
        }

        for (int i = idx; i < num.length(); i++) {
            String curStr = num.substring(idx, i + 1);
            if (curStr.length() > 1 && curStr.charAt(0) == '0') continue;
            long curVal = Long.valueOf(curStr);
            if (idx == 0) {
                dfs(num, target, i + 1, curStr, curVal, curVal, res);
            } else {
                dfs(num, target, i + 1, path + "+" + curStr, preVal + curVal, curVal, res);
                dfs(num, target, i + 1, path + "-" + curStr, preVal - curVal, -curVal, res);
                dfs(num, target, i + 1, path + "*" + curStr, preVal - lastVal + lastVal * curVal, lastVal * curVal, res);
            }
        }
    }
}
/**
 * 靠的就是暴力搜索
 * [1 2 3] +/-/* 4 5 6 7 8 9 10
 * 穷举所有可能性
 * 1+23+4*dfs(5678910)
 * preVal - lastVal + lastVal * dfs(...)
 * lastVal
 * 先不考虑乘号，本质就是一个递归深度搜索
 * 如果加减号直接存preVal就没问题，可以一路算到底
 * 如果遇到乘号，显然不能直接把preVal直接乘上后面dfs的结果
 * 最后一个单项式需要和后面dfs的结果先乘
 * 得先把最后一个单项式lastVal存下来，我们就可以算出新的val出来
 */
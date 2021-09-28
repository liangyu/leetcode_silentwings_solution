package LC1801_2100;
import java.util.*;
public class LC2019_TheScoreofStudentsSolvingMathExpression {
    /**
     * ou are given a string s that contains digits 0-9, addition symbols '+', and multiplication symbols '*' only,
     * representing a valid math expression of single digit numbers (e.g., 3+5*2). This expression was given to n
     * elementary school students. The students were instructed to get the answer of the expression by following this
     * order of operations:
     *
     * Compute multiplication, reading from left to right; Then,
     * Compute addition, reading from left to right.
     * You are given an integer array answers of length n, which are the submitted answers of the students in no
     * particular order. You are asked to grade the answers, by following these rules:
     *
     * If an answer equals the correct answer of the expression, this student will be rewarded 5 points;
     * Otherwise, if the answer could be interpreted as if the student used the incorrect order of operations, once or
     * multiple times, this student will be rewarded 2 points;
     * Otherwise, this student will be rewarded 0 points.
     * Return the sum of the points of the students.
     *
     * Input: s = "7+3*1*2", answers = [20,13,42]
     * Output: 7
     *
     * Constraints:
     *
     * 3 <= s.length <= 31
     * s represents a valid expression that contains only digits 0-9, '+', and '*' only.
     * All the integer operands in the expression are in the inclusive range [0, 9].
     * 1 <= The count of all operators ('+' and '*') in the math expression <= 15
     * Test data are generated such that the correct answer of the expression is in the range of [0, 1000].
     * n == answers.length
     * 1 <= n <= 10^4
     * 0 <= answers[i] <= 1000
     * @param s
     * @param answers
     * @return
     */
    public int scoreOfStudents(String s, int[] answers) {
        int rightAns = calculate(s);
        HashSet<Integer> set = diffWaysToCompute(s);

        int res = 0;
        for (int ans : answers) {
            if (ans == rightAns) res += 5;
            else if (set.contains(ans)) res += 2;
        }
        return res;
    }

    private int calculate(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        s = s.trim().replaceAll(" +", "");
        int res = 0, prev = 0, i = 0;
        char sign = '+';

        while (i < s.length()) {
            int cur = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                cur = cur * 10 + s.charAt(i) - '0';
                i++;
            }

            if (sign == '+') {
                res += prev;
                prev = cur;
            } else if (sign == '-') {
                res += prev;
                prev = -cur;
            } else if (sign == '*') {
                prev = prev * cur;
            } else if (sign == '/') {
                prev = prev / cur;
            }

            if (i < s.length()) {
                sign = s.charAt(i);
                i++;
            }
        }
        res += prev;
        return res;
    }

    HashMap<String, HashSet<Integer>> map = new HashMap<>();
    private HashSet<Integer> diffWaysToCompute(String expression) {
        if (map.containsKey(expression)) return map.get(expression);
        HashSet<Integer> res = new HashSet<>();
        boolean flag = true;
        // corner case
        if (expression == null || expression.length() == 0) return res;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '+' || c == '*') {
                String a = expression.substring(0, i);
                String b = expression.substring(i + 1);
                HashSet<Integer> al = diffWaysToCompute(a);
                HashSet<Integer> bl = diffWaysToCompute(b);
                for (int x : al) {
                    for (int y : bl) {
                        if (c == '+') {
                            if (x + y <= 1000) res.add(x + y);
                            else flag = false;
                        } else if (c == '*') {
                            if (x * y <= 1000) res.add(x * y);
                            else flag = false;
                        }
                    }
                }
            }
        }
        if (res.size() == 0 && flag) res.add(Integer.valueOf(expression));
        map.put(expression, res);
        return res;
    }
}
/**
 * 10 * 2 = 20
 * 7 + 3 * 2 => 10 * 2 = 20
 * LC227 => 得到标准答案是多少
 * 如何能算出可能的partially correct的结果呢？
 * 任意的结合律 => 括号的添加
 * ((7 + 3) * (1 * 2))
 * LC241 => 添加任意括号来改变运算顺序
 * 基本思路就是递归，最后一步运算肯定就是这些运算符里的某一个
 * 拆成2部分，分别递归
 * 重要隐含条件：0 <= answers[i] <= 1000
 * 大于1000的都不需要保留
 */
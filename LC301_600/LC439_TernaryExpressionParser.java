package LC301_600;
import java.util.*;
public class LC439_TernaryExpressionParser {
    /**
     * Given a string expression representing arbitrarily nested ternary expressions, evaluate the expression, and
     * return the result of it.
     *
     * You can always assume that the given expression is valid and only contains digits, '?', ':', 'T', and 'F' where
     * 'T' is true and 'F' is false. All the numbers in the expression are one-digit numbers (i.e., in the range [0, 9]).
     *
     * The conditional expressions group right-to-left (as usual in most languages), and the result of the expression
     * will always evaluate to either a digit, 'T' or 'F'.
     *
     * Input: expression = "T?2:3"
     * Output: "2"
     *
     * Constraints:
     *
     * 5 <= expression.length <= 10^4
     * expression consists of digits, 'T', 'F', '?', and ':'.
     * It is guaranteed that expression is a valid ternary expression and that each number is a one-digit number.
     * @param expression
     * @return
     */
    // time = O(n), space = O(n)
    public String parseTernary(String expression) {
        Stack<String> stack = new Stack<>();

        int n = expression.length();
        String s = "";
        for (int i = 0; i < n; i++) {
            if (i + 1 < n && expression.charAt(i + 1) == '?') {
                stack.push(s);
                s = "" + expression.charAt(i);
            } else {
                s += expression.charAt(i);
                while (s.length() == 5) {
                    s = eval(s);
                    if (!stack.isEmpty()) s = stack.pop() + s;
                }
            }
        }
        return s;
    }

    private String eval(String s) {
        String res = "";
        if (s.charAt(0) == 'T') {
            res += s.charAt(2);
        } else res += s.charAt(4);
        return res;
    }
}
/**
 * 标准型永远是5个字符组成
 * 从右往左group -> 优先级
 * 若干个标准型交叠而来，不可能是若干个表达式的并行
 * 当我们从左往右看的时候，如果发现新的问号，有个新的标准型要解析，前面不可能有个完整的标准型
 * 之前的压入栈内
 * 如果凑齐5个字符，那就是标准型，evaluate
 */
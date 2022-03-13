package LC901_1200;
import java.util.*;
public class LC1106_ParsingABooleanExpression {
    /**
     * Return the result of evaluating a given boolean expression, represented as a string.
     *
     * An expression can either be:
     *
     * "t", evaluating to True;
     * "f", evaluating to False;
     * "!(expr)", evaluating to the logical NOT of the inner expression expr;
     * "&(expr1,expr2,...)", evaluating to the logical AND of 2 or more inner expressions expr1, expr2, ...;
     * "|(expr1,expr2,...)", evaluating to the logical OR of 2 or more inner expressions expr1, expr2, ...
     *
     * Input: expression = "!(f)"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= expression.length <= 2 * 10^4
     * expression[i] consists of characters in {'(', ')', '&', '|', '!', 't', 'f', ','}.
     * expression is a valid expression representing a boolean, as given in the description.
     * @param expression
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public boolean parseBoolExpr(String expression) {
        Stack<Character> stackOp = new Stack<>();
        Stack<List<Integer>> stackNums = new Stack<>();
        List<Integer> nums = new ArrayList<>();

        int n = expression.length(), res = 0;
        for (int i = 0; i < n; i++) {
            char c = expression.charAt(i);
            if (c == '!' || c == '&' || c == '|') {
                stackOp.push(c);
                stackNums.push(new ArrayList<>(nums));
                nums = new ArrayList<>();
                i++; // 跳过接下来的左括号
            } else if (c == ')') { // 出栈
                res = eval(stackOp.pop(), nums);
                nums = stackNums.pop();
                nums.add(res);
            } else if (c == 't' || c == 'f') {  // 逗号忽略不计
                nums.add(c == 't' ? 1 : 0);
            }
        }
        return res == 1 ? true : false;
    }

    private int eval(char op, List<Integer> nums) {
        int res = 0;
        if (op == '!') res = 1 - nums.get(0);
        else if (op == '&') {
            res = 1;
            for (int x : nums) res &= x;
        } else if (op == '|') {
            res = 0;
            for (int x : nums) res |= x;
        }
        return res;
    }

    // S2: dfs
    // time = O(n), space = O(n)
    public boolean parseBoolExpr2(String expression) {
        return helper(expression, 0, expression.length() - 1);
    }

    private boolean helper(String s, int a, int b) {
        List<Integer> nums = new ArrayList<>(); // 收集操作数
        for (int i = a + 2; i <= b - 1; i++) {
            // 可能嵌套其他布尔表达式
            char c = s.charAt(i);
            if (c == '!' || c == '&' || c == '|') {  // recursion -> measure the depth level
                int level = 1;
                int j = i + 2;
                while (level > 0 && j <= b - 1) {
                    level += s.charAt(j) == '(' ? 1 : 0;
                    level -= s.charAt(j) == ')' ? 1 : 0;
                    if (level == 0) break;
                    j++;
                }
                nums.add(helper(s, i, j) ? 1 : 0);
                i = j;
            } else if (c == 't' || c == 'f') {
                nums.add(c == 't' ? 1 : 0);
            }
        }
        return eval(s.charAt(a), nums) == 1 ? true : false;
    }
}
/**
 * Similar to LC1096
 * two stacks,一个存操作符，一个存操作数
 * 也可以递归调用，直到里面不再嵌套其他布尔表达式
 */
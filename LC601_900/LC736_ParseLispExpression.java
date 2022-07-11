package LC601_900;
import java.util.*;
public class LC736_ParseLispExpression {
    /**
     * You are given a string expression representing a Lisp-like expression to return the integer value of.
     *
     * The syntax for these expressions is given as follows.
     *
     * An expression is either an integer, let expression, add expression, mult expression, or an assigned variable.
     * Expressions always evaluate to a single integer.
     * (An integer could be positive or negative.)
     * A let expression takes the form "(let v1 e1 v2 e2 ... vn en expr)", where let is always the string "let", then
     * there are one or more pairs of alternating variables and expressions, meaning that the first variable v1 is
     * assigned the value of the expression e1, the second variable v2 is assigned the value of the expression e2,
     * and so on sequentially; and then the value of this let expression is the value of the expression expr.
     * An add expression takes the form "(add e1 e2)" where add is always the string "add", there are always two
     * expressions e1, e2 and the result is the addition of the evaluation of e1 and the evaluation of e2.
     * A mult expression takes the form "(mult e1 e2)" where mult is always the string "mult", there are always two
     * expressions e1, e2 and the result is the multiplication of the evaluation of e1 and the evaluation of e2.
     * For this question, we will use a smaller subset of variable names. A variable starts with a lowercase letter,
     * then zero or more lowercase letters or digits. Additionally, for your convenience, the names "add", "let", and
     * "mult" are protected and will never be used as variable names.
     * Finally, there is the concept of scope. When an expression of a variable name is evaluated, within the context
     * of that evaluation, the innermost scope (in terms of parentheses) is checked first for the value of that
     * variable, and then outer scopes are checked sequentially. It is guaranteed that every expression is legal.
     * Please see the examples for more details on the scope.
     *
     * Input: expression = "(let x 2 (mult x (let x 3 y 4 (add x y))))"
     * Output: 14
     *
     * Input: expression = "(let x 3 x 2 x)"
     * Output: 2
     *
     * Input: expression = "(let x 1 y 2 x (add x y) (add x y))"
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= expression.length <= 2000
     * There are no leading or trailing spaces in expression.
     * All tokens are separated by a single space in expression.
     * The answer and all intermediate calculations of that answer are guaranteed to fit in a 32-bit integer.
     * The expression is guaranteed to be legal and evaluate to an integer.
     * @param expression
     * @return
     */
    // time = O(n), space = O(n)
    public int evaluate(String expression) {
        HashMap<String, Integer> data = new HashMap<>();
        return helper(expression, 1, expression.length() - 2, data); // 第一层脱了括号
    }

    private int helper(String s, int a, int b, HashMap<String, Integer> data)  {
        if (a + 3 <= b && s.substring(a, a + 3).equals("add")) {
            List<Integer> nums = new ArrayList<>();
            for (int i = a + 4; i <= b; i++) {
                if (s.charAt(i) == '(') {
                    int j = getRightParenthesis(s, i);

                    int val = helper(s, i + 1, j - 1, clone(data));
                    nums.add(val);
                    i = j;
                } else if (Character.isLowerCase(s.charAt(i))) {
                    int j = getVariable(s, i);
                    String var = s.substring(i, j);
                    nums.add(data.get(var));
                    i = j - 1;
                } else if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '-') {
                    int j = getDigit(s, i);
                    nums.add(Integer.valueOf(s.substring(i, j)));
                    i = j - 1;
                }
            }
            return nums.get(0) + nums.get(1);
        } else if (a + 4 <= b && s.substring(a, a + 4).equals("mult")) {
            List<Integer> nums = new ArrayList<>();
            for (int i = a + 5; i <= b; i++) {
                if (s.charAt(i) == '(') {
                    int j = getRightParenthesis(s, i);
                    int val = helper(s, i + 1, j - 1, clone(data));
                    nums.add(val);
                    i = j;
                } else if (Character.isLowerCase(s.charAt(i))) {
                    int j = getVariable(s, i);
                    String var = s.substring(i, j);
                    nums.add(data.get(var));
                    i = j - 1;
                } else if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '-') {
                    int j = getDigit(s, i);
                    nums.add(Integer.valueOf(s.substring(i, j)));
                    i = j - 1;
                }
            }
            return nums.get(0) * nums.get(1);
        } else if (a + 3 <= b && s.substring(a, a + 3).equals("let")) {
            int flag = 0;
            String var = "";
            int val = 0;
            for (int i = a + 4; i <= b; i++) {
                if (s.charAt(i) == ' ') continue;
                if (flag == 0) {
                    if (s.charAt(i) == '(') {
                        int j = getRightParenthesis(s, i);
                        return helper(s, i + 1, j - 1, clone(data));
                    } else if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '-') {
                        int j = getDigit(s, i);
                       return Integer.valueOf(s.substring(i, j));
                    }

                    int j = getVariable(s, i);
                    var = s.substring(i, j);
                    i = j - 1;
                    if (j == b + 1) return data.get(var);
                } else if (flag == 1) {
                    if (s.charAt(i) == '(') {
                        int j = getRightParenthesis(s, i);
                        val = helper(s, i + 1, j - 1, clone(data));
                        i = j;
                    } else if (Character.isLowerCase(s.charAt(i))) {
                        int j = getVariable(s, i);
                        val = data.get(s.substring(i, j));
                        i = j - 1;
                    } else if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '-') {
                        int j = getDigit(s, i);
                        val = Integer.valueOf(s.substring(i, j));
                        i = j - 1;
                    }
                    data.put(var, val);
                }
                flag = 1 - flag;
            }
        }
        return -1;
    }

    private int getRightParenthesis(String s, int i) {
        i++;
        int level = 1;
        while (level > 0) {
            level += s.charAt(i) == '(' ? 1 : 0;
            level -= s.charAt(i) == ')' ? 1 : 0;
            if (level == 0) break;
            else i++;
        }
        return i;
    }

    private int getVariable(String s, int i) {
        i++;
        while (Character.isDigit(s.charAt(i)) || Character.isLowerCase(s.charAt(i))) i++;
        return i;
    }

    private int getDigit(String s, int i) {
        i++;
        while (Character.isDigit(s.charAt(i)) || s.charAt(i) == '-') i++;
        return i;
    }

    private HashMap<String, Integer> clone(HashMap<String, Integer> vars) {
        HashMap<String, Integer> copy = new HashMap<>();
        for (String key : vars.keySet()) {
            copy.put(key, vars.get(key));
        }
        return copy;
    }
}
/**
 * 函数式语言 => 递归的过程
 * (let 变量名 变量值 变量名 变量值 值的表达式)
 * (add 值 + 值)
 * (mul 值 * 值)
 * 递归求就可以
 */
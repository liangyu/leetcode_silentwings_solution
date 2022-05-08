package LC2101_2400;

public class LC2232_MinimizeResultbyAddingParenthesestoExpression {
    /**
     * You are given a 0-indexed string expression of the form "<num1>+<num2>" where <num1> and <num2> represent
     * positive integers.
     *
     * Add a pair of parentheses to expression such that after the addition of parentheses, expression is a valid
     * mathematical expression and evaluates to the smallest possible value. The left parenthesis must be added to the
     * left of '+' and the right parenthesis must be added to the right of '+'.
     *
     * Return expression after adding a pair of parentheses such that expression evaluates to the smallest possible
     * value. If there are multiple answers that yield the same result, return any of them.
     *
     * The input has been generated such that the original value of expression, and the value of expression after adding
     * any pair of parentheses that meets the requirements fits within a signed 32-bit integer.
     *
     * Input: expression = "247+38"
     * Output: "2(47+38)"
     *
     * Input: expression = "12+34"
     * Output: "1(2+3)4"
     *
     * Constraints:
     *
     * 3 <= expression.length <= 10
     * expression consists of digits from '1' to '9' and '+'.
     * expression starts and ends with digits.
     * expression contains exactly one '+'.
     * The original value of expression, and the value of expression after adding any pair of parentheses that meets the
     * requirements fits within a signed 32-bit integer.
     * @param expression
     * @return
     */
    // time = O(n^2), space = O(n)
    public String minimizeResult(String expression) {
        int idx = expression.indexOf("+");
        int pos1 = -1, pos2 = -1;
        int n = expression.length(), res =  Integer.MAX_VALUE;

        for (int i = 0; i < idx; i++) {  // pos of left bracket
            for (int j = idx + 1; j < n; j++) { // pos of right bracket
                int a = i == 0 ? 1 : Integer.parseInt(expression.substring(0, i));
                int b = Integer.parseInt(expression.substring(i, idx));
                int c = Integer.parseInt(expression.substring(idx + 1, j + 1));
                int d = j == n - 1 ? 1 : Integer.parseInt(expression.substring(j + 1));

                int val = a * (b + c) * d;
                if (val < res) {
                    res = val;
                    pos1 = i;
                    pos2 = j;
                }
            }
        }
        return expression.substring(0, pos1) + "(" + expression.substring(pos1, pos2 + 1) + ")" + expression.substring(pos2 + 1);
    }
}

package LC001_300;
import java.util.*;
public class LC241_DifferentWaystoAddParentheses {
    /**
     * Given a string expression of numbers and operators, return all possible results from computing all the different
     * possible ways to group numbers and operators. You may return the answer in any order.
     *
     * Input: expression = "2-1-1"
     * Output: [0,2]
     *
     * Constraints:
     *
     * 1 <= expression.length <= 20
     * expression consists of digits and the operator '+', '-', and '*'.
     * @param expression
     * @return
     */
    // time = O(2^n), space = O(2^n)
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (expression == null || expression.length() == 0) return res;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '-' || c == '+' || c == '*') {
                String a = expression.substring(0, i);
                String b = expression.substring(i + 1);
                List<Integer> al = diffWaysToCompute(a);
                List<Integer> bl = diffWaysToCompute(b);
                for (int x : al) {
                    for (int y : bl) {
                        if (c == '-') res.add(x - y);
                        else if (c == '+') res.add(x + y);
                        else if (c == '*') res.add(x * y);
                    }
                }
            }
        }
        if (res.size() == 0) res.add(Integer.valueOf(expression));
        return res;
    }
}
/**
 * ref: LC894 for time and space complexity, actually the same problem in essence
 */

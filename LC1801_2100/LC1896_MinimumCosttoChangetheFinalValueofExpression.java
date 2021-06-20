package LC1801_2100;
import java.util.*;
public class LC1896_MinimumCosttoChangetheFinalValueofExpression {
    /**
     * You are given a valid boolean expression as a string expression consisting of the characters '1','0','&'
     * (bitwise AND operator),'|' (bitwise OR operator),'(', and ')'.
     *
     * For example, "()1|1" and "(1)&()" are not valid while "1", "(((1))|(0))", and "1|(0&(1))" are valid expressions.
     * Return the minimum cost to change the final value of the expression.
     *
     * For example, if expression = "1|1|(0&0)&1", its value is 1|1|(0&0)&1 = 1|1|0&1 = 1|0&1 = 1&1 = 1. We want to
     * apply operations so that the new expression evaluates to 0.
     * The cost of changing the final value of an expression is the number of operations performed on the expression.
     * The types of operations are described as follows:
     *
     * Turn a '1' into a '0'.
     * Turn a '0' into a '1'.
     * Turn a '&' into a '|'.
     * Turn a '|' into a '&'.
     * Note: '&' does not take precedence over '|' in the order of calculation. Evaluate parentheses first, then in
     * left-to-right order.
     *
     * Input: expression = "1&(0|1)"
     * Output: 1
     *
     * Input: expression = "(0&0)&(0&0&0)"
     * Output: 3
     *
     * Input: expression = "(0|(1|0&1))"
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= expression.length <= 10^5
     * expression only contains '1','0','&','|','(', and ')'
     * All parentheses are properly matched.
     * There will be no empty parentheses (i.e: "()" is not a substring of expression).
     * @param expression
     * @return
     */
    // time = O(n), space = O(n)
    public int minOperationsToFlip(String expression) {
        // corner case
        if (expression == null || expression.length() == 0) return 0;

        Pair cur = new Pair(-1, -1);
        char op = '#';
        Stack<Pair> s1 = new Stack<>();
        Stack<Character> s2 = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (ch == '&' || ch == '|') op = ch;
            else if (ch == '0' || ch == '1') {
                Pair next = new Pair(ch - '0', 1); // flip the leaf node -> must be 1 operations -> flip = 1
                int val = evalVal(op, cur, next);
                int flip = evalFlip(op, cur, next);
                cur = new Pair(val, flip);
            } else if (ch == '(') {
                s1.push(cur);
                s2.push(op);
                cur = new Pair(-1, -1);
                op = '#';
            } else {
                Pair last = s1.pop();
                op = s2.pop();
                int val = evalVal(op, last, cur);
                int flip = evalFlip(op, last, cur);
                cur = new Pair(val, flip);
            }
        }
        return cur.flip;
    }

    private int evalVal(char op, Pair a, Pair b) {
        if (op == '#') return b.val; // -1 # 3 => 3
        if (op == '&') return a.val & b.val;
        else return a.val | b.val;
    }

    private int evalFlip(char op, Pair a, Pair b) {
        if (op == '#') return b.flip; // -1 # 3 => 3
        if (op == '|') {
            if (a.val + b.val == 0) return Math.min(a.flip, b.flip);
            else if (a.val + b.val == 1) return 1;
            else if (a.val + b.val == 2) return Math.min(a.flip, b.flip) + 1;
        }
        if (op == '&') {
            if (a.val + b.val == 0) return Math.min(a.flip, b.flip) + 1;
            else if (a.val + b.val == 1) return 1;
            else if (a.val + b.val == 2) return Math.min(a.flip, b.flip);
        }
        return 0;
    }

    private class Pair {
        private int val, flip;
        public Pair(int val, int flip) {
            this.val = val;
            this.flip = flip;
        }
    }
}
/**
 * 表达式 -> 逻辑二叉树 syntax tree
 * 每个节点的翻转
 * 二叉树 => recursion
 * 递归建树
 * (xxxxx) & (xxxxxx)  -> 扫一遍确定分割点
 * left        right
 * ((xxxxx) | (xx)) & ((xxxxx）| (xx)) -> O(n^2)
 * similar to calculator (ref: LC224) -> use stack
 * 对于每个节点而言，不但要评估它的值，还要算它的flip
 * => 能否把计算value和flip放在一起做，套用LC224的架构
 *
 * for LC224
 * 1. int cur, char op
 * 1 & 3 -> 还欠一个数 => cur = eval(cur, op, next);
 * 2 | 1
 *
 * 2. s1, s2 two stacks
 * 1 & (1 & 2...)
 *      cur = -1, op = '#'， next = 1 // 虚拟的，这是个"空"的状态
 *      cur = next;
 *
 * 3. 1 & cur (...)
 *        cur = eval(1, op, next);
 *
 *     node
 *  left    right
 *  0 | 0 -> 1 | 0
 *  0 | 1 -> 0 & 1
 *  1 | 1 -> 0 & 1  -> 0 & 1,  0 | 0
 *
 *  0 & 0 -> 1 | 0
 *  0 & 1 -> 0 | 1
 *  1 & 1 -> 0 & 1
 *
 *  int eval(node) {
 *      return evalExp(left.val, node.op, right.val);
 *  }
 *
 *
 *  int minFlip(node) {
 *      left.flip = minFlip(left);
 *      right.flip = minFlip(right);
 *
 *      if (node.op == '|' && flip.val + right.val == 0) return min(left.flip, right.flip); // flip one child val
 *      if (node.op == '|' && flip.val + right.val == 1) return 1; // flip operator
 *      if (node.op == '|' && flip.val + right.val == 2) return min(left.flip, right.flip) + 1; // flip one child val + flip operator
 *
 *      if (node.op == '&' && flip.val + right.val == 0) return min(left.flip, right.flip) + 1; // flip one child val + flip operator
 *      if (node.op == '&' && flip.val + right.val == 1) return 1; // flip operator
 *      if (node.op == '&' && flip.val + right.val == 2) return min(left.flip, right.flip); // flip one child val
 *  }
 *
 *  不会出现同时改2个数的情况，因为其总是不如改一个数+改一个符号来得好(贪心)
 */

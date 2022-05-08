package LC1501_1800;
import java.util.*;
public class LC1628_DesignanExpressionTreeWithEvaluateFunction {
    /**
     * Given the postfix tokens of an arithmetic expression, build and return the binary expression tree that represents
     * this expression.
     * <p>
     * Postfix notation is a notation for writing arithmetic expressions in which the operands (numbers) appear before
     * their operators. For example, the postfix tokens of the expression 4*(5-(7+2)) are represented in the array
     * postfix = ["4","5","7","2","+","-","*"].
     * <p>
     * The class Node is an interface you should use to implement the binary expression tree. The returned tree will be
     * tested using the evaluate function, which is supposed to evaluate the tree's value. You should not remove the
     * Node class; however, you can modify it as you wish, and you can define other classes to implement it if needed.
     * <p>
     * A binary expression tree is a kind of binary tree used to represent arithmetic expressions. Each node of a binary
     * expression tree has either zero or two children. Leaf nodes (nodes with 0 children) correspond to operands
     * (numbers), and internal nodes (nodes with two children) correspond to the operators '+' (addition),
     * '-' (subtraction), '*' (multiplication), and '/' (division).
     * <p>
     * It's guaranteed that no subtree will yield a value that exceeds 109 in absolute value, and all the operations are
     * valid (i.e., no division by zero).
     * <p>
     * Follow up: Could you design the expression tree such that it is more modular? For example, is your design able to
     * support additional operators without making changes to your existing evaluate implementation?
     * <p>
     * Input: s = ["3","4","+","2","*","7","/"]
     * Output: 2
     * <p>
     * Constraints:
     * <p>
     * 1 <= s.length < 100
     * s.length is odd.
     * s consists of numbers and the characters '+', '-', '*', and '/'.
     * If s[i] is a number, its integer representation is no more than 105.
     * It is guaranteed that s is a valid expression.
     * The absolute value of the result and intermediate values will not exceed 109.
     * It is guaranteed that no expression will include division by zero.
     */
    abstract class Node {
        public abstract int evaluate();
        // define your fields here
    }

    ;


    /**
     * This is the TreeBuilder class.
     * You can treat it as the driver code that takes the postinfix input
     * and returns the expression tree represnting it as a Node.
     */

    abstract class Node {
        public abstract int evaluate();
        // define your fields here
    }

    ;


    /**
     * This is the TreeBuilder class.
     * You can treat it as the driver code that takes the postinfix input
     * and returns the expression tree represnting it as a Node.
     */

    class TreeBuilder {
        String operators = "+-*/";
        Stack<TreeNode> stack;

        Node buildTree(String[] postfix) {
            if (postfix == null || postfix.length == 0) return null;

            stack = new Stack<>();
            for (String s : postfix) {
                if (operators.contains(s)) {
                    TreeNode cur = new TreeNode(s);
                    cur.right = stack.pop();
                    cur.left = stack.pop();
                    stack.push(cur);
                } else stack.push(new TreeNode(s));
            }
            return stack.peek();
        }
    }

    ;

    class TreeNode extends Node {
        String val;
        TreeNode left, right;

        public TreeNode(String val) {
            this.val = val;
        }

        public int evaluate() {
            return dfs(this);
        }

        private int dfs(TreeNode node) {
            if (node.left == null && node.right == null) {
                return Integer.parseInt(node.val);
            }

            int left = dfs(node.left);
            int right = dfs(node.right);

            String operator = node.val;
            int res = 0;
            if (operator.equals("+")) res = left + right;
            else if (operator.equals("-")) res = left - right;
            else if (operator.equals("*")) res = left * right;
            else if (operator.equals("/")) res = left / right;
            return res;
        }
    }
}

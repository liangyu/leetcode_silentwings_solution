package LC1501_1800;

public class LC1597_BuildBinaryExpressionTreeFromInfixExpression {
    /**
     * A binary expression tree is a kind of binary tree used to represent arithmetic expressions. Each node of a binary
     * expression tree has either zero or two children. Leaf nodes (nodes with 0 children) correspond to operands
     * (numbers), and internal nodes (nodes with 2 children) correspond to the operators '+' (addition),
     * '-' (subtraction), '*' (multiplication), and '/' (division).
     *
     * For each internal node with operator o, the infix expression that it represents is (A o B), where A is the
     * expression the left subtree represents and B is the expression the right subtree represents.
     *
     * You are given a string s, an infix expression containing operands, the operators described above, and
     * parentheses '(' and ')'.
     *
     * Return any valid binary expression tree, which its in-order traversal reproduces s after omitting the
     * parenthesis from it (see examples below).
     *
     * Please note that order of operations applies in s. That is, expressions in parentheses are evaluated first, and
     * multiplication and division happen before addition and subtraction.
     *
     * Operands must also appear in the same order in both s and the in-order traversal of the tree.
     *
     * Input: s = "3*4-2*5"
     * Output: [-,*,*,3,4,2,5]
     *
     * Input: s = "2-3/(5*2)+1"
     * Output: [+,-,1,2,/,null,null,null,null,3,*,null,null,5,2]
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consists of digits and the characters '+', '-', '*', and '/'.
     * Operands in s are exactly 1 digit.
     * It is guaranteed that s is a valid expression.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public Node expTree(String s) {
        int n = s.length();
        // base case
        if (n == 1) {
            Node root = new Node(s.charAt(0));
            return root;
        }

        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                Node root = new Node(s.charAt(i));
                root.left = expTree(s.substring(0, i));
                root.right = expTree(s.substring(i + 1));
                return root;
            } else if (s.charAt(i) == ')') {
                int j = i - 1, count = 1;
                while (j >= 0 && count > 0) {
                    if (s.charAt(j) == ')') count++;
                    else if (s.charAt(j) == '(') count--;
                    j--;
                }
                i = j + 1;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '*' || s.charAt(i) == '/') {
                Node root = new Node(s.charAt(i));
                root.left = expTree(s.substring(0, i));
                root.right = expTree(s.substring(i + 1));
                return root;
            } else if (s.charAt(i) == ')') {
                int j = i - 1, count = 1;
                while (j >= 0 && count > 0) {
                    if (s.charAt(j) == ')') count++;
                    else if (s.charAt(j) == '(') count--;
                    j--;
                }
                i = j + 1;
            }
        }

        if (s.charAt(0) == '(' && s.charAt(n - 1) == ')') {
            return expTree(s.substring(1, n - 1));
        }
        return null;
    }

    class Node {
        char val;
        Node left;
        Node right;
        Node() {this.val = ' ';}
        Node(char val) { this.val = val; }
        Node(char val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

package LC1501_1800;

public class LC1612_CheckIfTwoExpressionTreesareEquivalent {
    /**
     * A binary expression tree is a kind of binary tree used to represent arithmetic expressions. Each node of a binary
     * expression tree has either zero or two children. Leaf nodes (nodes with 0 children) correspond to
     * operands (variables), and internal nodes (nodes with two children) correspond to the operators. In this problem,
     * we only consider the '+' operator (i.e. addition).
     *
     * You are given the roots of two binary expression trees, root1 and root2. Return true if the two binary expression
     * trees are equivalent. Otherwise, return false.
     *
     * Two binary expression trees are equivalent if they evaluate to the same value regardless of what the variables
     * are set to.
     *
     * Input: root1 = [x], root2 = [x]
     * Output: true
     *
     * Input: root1 = [+,a,+,null,null,b,c], root2 = [+,+,a,b,c]
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in both trees are equal, odd and, in the range [1, 4999].
     * Node.val is '+' or a lower-case English letter.
     * It's guaranteed that the tree given is a valid binary expression tree.
     *
     *
     * Follow up: What will you change in your solution if the tree also supports the '-' operator (i.e. subtraction)?
     * @param root1
     * @param root2
     * @return
     */
    // time = O(n), space = O(n)
    public boolean checkEquivalence(Node root1, Node root2) {
        int[] count = new int[26];
        dfs(root1, count, 1, 1);
        dfs(root2, count, -1, 1);

        for (int x : count) {
            if (x != 0) return false;
        }
        return true;
    }

    private void dfs(Node node, int[] count, int flag, int sign) {
        if (node == null) return;

        if (Character.isLowerCase(node.val)) {
            count[node.val - 'a'] += flag * sign;
        }

        dfs(node.left, count, flag, sign);
        if (node.val == '-') sign *= -1;
        dfs(node.right, count, flag, sign);
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
/**
 * For followup we again need to make sure both tree have same elements.
 * But this time we need to also take into account the sign of elements (as subtraction is not commutative).
 * We can do this by adding another parameter i.e. sign to the addToMap function and reversing sign for right children
 * of negative nodes
 */

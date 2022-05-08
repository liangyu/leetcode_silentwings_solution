package LC601_900;

public class LC606_ConstructStringfromBinaryTree {
    /**
     * Given the root of a binary tree, construct a string consisting of parenthesis and integers from a binary tree
     * with the preorder traversal way, and return it.
     *
     * Omit all the empty parenthesis pairs that do not affect the one-to-one mapping relationship between the string
     * and the original binary tree.
     *
     * Input: root = [1,2,3,4]
     * Output: "1(2(4))(3)"
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * -1000 <= Node.val <= 1000
     * @param t
     * @return
     */
    // time = O(n), space = O(n)
    StringBuilder sb;
    public String tree2str(TreeNode t) {
        // corner case
        if (t == null) return "";

        sb = new StringBuilder();
        dfs(t);
        return sb.toString();
    }

    private void dfs(TreeNode node) {
        if (node == null) return;
        sb.append(node.val);
        if (node.left != null) {
            sb.append('(');
            dfs(node.left);
            sb.append(')');
        } else if (node.right != null) {
            sb.append('(').append(')');
        }

        if (node.right != null) {
            sb.append('(');
            dfs(node.right);
            sb.append(')');
        }
    }
}

package LC2101_2400;

public class LC2313_MinimumFlipsinBinaryTreetoGetResult {
    /**
     * You are given the root of a binary tree with the following properties:
     *
     * Leaf nodes have either the value 0 or 1, representing false and true respectively.
     * Non-leaf nodes have either the value 2, 3, 4, or 5, representing the boolean operations OR, AND, XOR, and NOT,
     * respectively.
     * You are also given a boolean result, which is the desired result of the evaluation of the root node.
     *
     * The evaluation of a node is as follows:
     *
     * If the node is a leaf node, the evaluation is the value of the node, i.e. true or false.
     * Otherwise, evaluate the node's children and apply the boolean operation of its value with the children's evaluations.
     * In one operation, you can flip a leaf node, which causes a false node to become true, and a true node to become
     * false.
     *
     * Return the minimum number of operations that need to be performed such that the evaluation of root yields result.
     * It can be shown that there is always a way to achieve result.
     *
     * A leaf node is a node that has zero children.
     *
     * Note: NOT nodes have either a left child or a right child, but other non-leaf nodes have both a left child and a
     * right child.
     *
     * Input: root = [3,5,4,2,null,1,1,1,0], result = true
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^5].
     * 0 <= Node.val <= 5
     * OR, AND, and XOR nodes have 2 children.
     * NOT nodes have 1 child.
     * Leaf nodes have a value of 0 or 1.
     * Non-leaf nodes have a value of 2, 3, 4, or 5.
     * @param root
     * @param result
     * @return
     */
    // time = O(n), space = O(n)
    public int minimumFlips(TreeNode root, boolean result) {
        if (root == null) return 0;

        int[] res = dfs(root);
        return result ? res[1] : res[0];
    }

    private int[] dfs(TreeNode node) {
        if (node == null) return null;
        if (node.left == null && node.right == null) {
            return node.val == 0 ? new int[]{0, 1} : new int[]{1, 0};
        }

        int[] l = dfs(node.left);
        int[] r = dfs(node.right);

        int[] res = new int[2];
        if (node.val == 2) {
            res[0] = l[0] + r[0];
            res[1] = Math.min(l[1] + r[1], Math.min(l[0] + r[1], l[1] + r[0]));
        } else if (node.val == 3) {
            res[0] = Math.min(l[0] + r[0], Math.min(l[0] + r[1], l[1] + r[0]));
            res[1] = l[1] + r[1];
        } else if (node.val == 4) {
            res[0] = Math.min(l[0] + r[0], l[1] + r[1]);
            res[1] = Math.min(l[0] + r[1], l[1] + r[0]);
        } else {
            res[0] = l == null ? r[1] : l[1];
            res[1] = l == null ? r[0] : l[0];
        }
        return res;
    }
}

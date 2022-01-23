package LC301_600;
import java.util.*;
public class LC545_BoundaryofBinaryTree {
    /**
     * The boundary of a binary tree is the concatenation of the root, the left boundary, the leaves ordered from
     * left-to-right, and the reverse order of the right boundary.
     *
     * The left boundary is the set of nodes defined by the following:
     *
     * The root node's left child is in the left boundary. If the root does not have a left child, then the left
     * boundary is empty.
     * If a node in the left boundary and has a left child, then the left child is in the left boundary.
     * If a node is in the left boundary, has no left child, but has a right child, then the right child is in the left
     * boundary.
     * The leftmost leaf is not in the left boundary.
     * The right boundary is similar to the left boundary, except it is the right side of the root's right subtree.
     * Again, the leaf is not part of the right boundary, and the right boundary is empty if the root does not have a
     * right child.
     *
     * The leaves are nodes that do not have any children. For this problem, the root is not a leaf.
     *
     * Given the root of a binary tree, return the values of its boundary.
     *
     * Input: root = [1,null,2,3,4]
     * Output: [1,3,4,2]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    List<Integer> res;
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        res.add(root.val);
        leftBound(root.left);
        findLeaves(root.left); // prevent the case when root is a leave!
        findLeaves(root.right);
        rightBound(root.right);
        return res;
    }

    private void leftBound(TreeNode node) {
        if (node == null || node.left == null && node.right == null) return;

        res.add(node.val);
        if (node.left == null) leftBound(node.right);
        else leftBound(node.left);
    }

    private void rightBound(TreeNode node) {
        if (node == null || node.left == null && node.right == null) return;

        if (node.right == null) rightBound(node.left);
        else rightBound(node.right);
        res.add(node.val); // add after visiting all children (reverse)
    }

    private void findLeaves(TreeNode node) {
        if (node == null) return;
        if (node.left == null && node.right == null) res.add(node.val);
        findLeaves(node.left);
        findLeaves(node.right);
    }
}

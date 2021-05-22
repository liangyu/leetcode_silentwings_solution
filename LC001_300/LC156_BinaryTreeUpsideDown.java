package LC001_300;
import java.util.*;
public class LC156_BinaryTreeUpsideDown {
    /**
     * Given the root of a binary tree, turn the tree upside down and return the new root.
     *
     * You can turn a binary tree upside down with the following steps:
     *
     * The original left child becomes the new root.
     * The original root becomes the new right child.
     * The original right child becomes the new left child.
     *
     * The mentioned steps are done level by level, it is guaranteed that every node in the given tree has either 0
     * or 2 children.
     *
     * Input: root = [1,2,3,4,5]
     * Output: [4,5,2,null,null,3,1]
     *
     * Constraints:
     *
     * The number of nodes in the tree will be in the range [0, 10].
     * 1 <= Node.val <= 10
     * Every node has either 0 or 2 children.
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        // corner case
        if (root == null || root.left == null && root.right == null) return root;

        TreeNode newRoot = upsideDownBinaryTree(root.left);
        root.left.left = root.right;
        root.left.right = root;

        root.left = null;
        root.right = null;
        return newRoot;
    }
}

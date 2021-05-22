package LC001_300;
import java.util.*;
public class LC106_ConstructBinaryTreefromInorderandPostorderTraversal {
    /**
     * Given two integer arrays inorder and postorder where inorder is the inorder traversal of a binary tree and
     * postorder is the postorder traversal of the same tree, construct and return the binary tree.
     *
     * Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
     * Output: [3,9,20,null,null,15,7]
     *
     * Constraints:
     *
     * 1 <= inorder.length <= 3000
     * postorder.length == inorder.length
     * -3000 <= inorder[i], postorder[i] <= 3000
     * inorder and postorder consist of unique values.
     * Each value of postorder also appears in inorder.
     * inorder is guaranteed to be the inorder traversal of the tree.
     * postorder is guaranteed to be the postorder traversal of the tree.
     *
     * @param inorder
     * @param postorder
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // corner case
        if (inorder == null || inorder.length == 0 || postorder == null || postorder.length == 0) return null;

        return helper(inorder, 0, inorder.length, postorder, 0, postorder.length);
    }

    private TreeNode helper(int[] inorder, int is, int ie, int[] postorder, int ps, int pe) {
        if (ps == pe) return null;

        int root_val = postorder[pe - 1];
        TreeNode root = new TreeNode(root_val);

        int idx = 0;
        for (int i = is; i < ie; i++) {
            if (inorder[i] == root_val) {
                idx = i;
                break;
            }
        }

        int dist = idx - is;
        root.left = helper(inorder, is, idx, postorder, ps, ps + dist);
        root.right = helper(inorder, idx + 1, ie, postorder, ps + dist, pe - 1);
        return root;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}

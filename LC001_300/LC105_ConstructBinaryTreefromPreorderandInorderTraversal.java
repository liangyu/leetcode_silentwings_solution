package LC001_300;
import java.util.*;
public class LC105_ConstructBinaryTreefromPreorderandInorderTraversal {
    /**
     * Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and
     * inorder is the inorder traversal of the same tree, construct and return the binary tree.
     *
     * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * Output: [3,9,20,null,null,15,7]
     *
     * Constraints:
     *
     * 1 <= preorder.length <= 3000
     * inorder.length == preorder.length
     * -3000 <= preorder[i], inorder[i] <= 3000
     * preorder and inorder consist of unique values.
     * Each value of inorder also appears in preorder.
     * preorder is guaranteed to be the preorder traversal of the tree.
     * inorder is guaranteed to be the inorder traversal of the tree.
     *
     * @param preorder
     * @param inorder
     * @return
     */
    // time = O(n), space - O(n)
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // corner case
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) return null;

        return helper(preorder, 0, preorder.length, inorder, 0, inorder.length);
    }

    private TreeNode helper(int[] preorder, int ps, int pe, int[] inorder, int is, int ie) {
        if (ps == pe) return null;

        int root_val = preorder[ps];
        TreeNode root = new TreeNode(root_val);

        int idx = 0;
        for (int i = is; i < ie; i++) {
            if (inorder[i] == root_val) {
                idx = i;
                break;
            }
        }

        int dist = idx - is;
        root.left = helper(preorder, ps + 1, ps + dist + 1, inorder, is, idx);
        root.right = helper(preorder, ps + dist + 1, pe, inorder, idx + 1, ie);
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

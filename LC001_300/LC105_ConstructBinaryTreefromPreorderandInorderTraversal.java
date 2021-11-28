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
    HashMap<Integer, Integer> map;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return helper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode helper(int[] preorder, int ps, int pe, int[] inorder, int is, int ie) {
        // base case
        if (ps > pe) return null;

        TreeNode root = new TreeNode(preorder[ps]);
        int idx = map.get(preorder[ps]);

        int dist = idx - is;
        root.left = helper(preorder, ps + 1, ps + dist, inorder, is, idx - 1);
        root.right = helper(preorder, ps + dist + 1, pe, inorder, idx + 1, ie);
        return root;
    }
}

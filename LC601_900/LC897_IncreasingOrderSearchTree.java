package LC601_900;

public class LC897_IncreasingOrderSearchTree {
    /**
     * Given the root of a binary search tree, rearrange the tree in in-order so that the leftmost node in the tree is
     * now the root of the tree, and every node has no left child and only one right child.
     *
     * Input: root = [5,3,6,2,4,null,8,1,null,null,null,7,9]
     * Output: [1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
     *
     * Constraints:
     *
     * The number of nodes in the given tree will be in the range [1, 100].
     * 0 <= Node.val <= 1000
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode increasingBST(TreeNode root) {
        if (root == null) return root;

        if (root.left != null) {
            TreeNode head = increasingBST(root.left);
            TreeNode cur = head;
            while (cur.right != null) cur = cur.right;
            cur.right = root;
            root.left = null;
            root.right = increasingBST(root.right);
            return head;
        } else {
            root.right = increasingBST(root.right);
            return root;
        }
    }
}

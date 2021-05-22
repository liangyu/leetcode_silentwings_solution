package LC601_900;
import java.util.*;
public class LC623_AddOneRowtoTree {
    /**
     * Given the root of a binary tree, then value v and depth d, you need to add a row of nodes with value v at the
     * given depth d. The root node is at depth 1.
     *
     * The adding rule is: given a positive integer depth d, for each NOT null tree nodes N in depth d-1, create two
     * tree nodes with value v as N's left subtree root and right subtree root. And N's original left subtree should be
     * the left subtree of the new left subtree root, its original right subtree should be the right subtree of the new
     * right subtree root. If depth d is 1 that means there is no depth d-1 at all, then create a tree node with value
     * v as the new root of the whole original tree, and the original tree is the new root's left subtree.
     *
     * Input:
     * A binary tree as following:
     *        4
     *      /   \
     *     2     6
     *    / \   /
     *   3   1 5
     *
     * v = 1
     *
     * d = 2
     *
     * Output:
     *        4
     *       / \
     *      1   1
     *     /     \
     *    2       6
     *   / \     /
     *  3   1   5
     *
     *  Note:
     * The given d is in range [1, maximum depth of the given tree + 1].
     * The given binary tree has at least one tree node.
     *
     * @param root
     * @param v
     * @param d
     * @return
     */
    // time = O(n), space = O(k) k: the number of maximum number of nodes at any level in the given tree
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        // corner case
        if (root == null) return root;

        if (d == 1) {
            TreeNode node = new TreeNode(v);
            node.left = root;
            return node;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int minLen = 1;

        while (!queue.isEmpty() && minLen < d - 1) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            minLen++;
        }

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            TreeNode temp = cur.left;
            cur.left = new TreeNode(v);
            cur.left.left = temp;
            temp = cur.right;
            cur.right = new TreeNode(v);
            cur.right.right = temp;
        }
        return root;
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}

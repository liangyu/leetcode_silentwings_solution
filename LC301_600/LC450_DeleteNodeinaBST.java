package LC301_600;
import LC001_300.LC106_ConstructBinaryTreefromInorderandPostorderTraversal;

import java.util.*;
public class LC450_DeleteNodeinaBST {
    /**
     * Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root
     * node reference (possibly updated) of the BST.
     *
     * Basically, the deletion can be divided into two stages:
     *
     * Search for a node to remove.
     * If the node is found, delete the node.
     * Follow up: Can you solve it with time complexity O(height of tree)?
     *
     * Input: root = [5,3,6,2,4,null,7], key = 3
     * Output: [5,4,6,2,null,null,7]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 10^4].
     * -10^5 <= Node.val <= 10^5
     * Each node has a unique value.
     * root is a valid binary search tree.
     * -10^5 <= key <= 10^5
     *
     * @param root
     * @param key
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode deleteNode(TreeNode root, int key) {
        // corner case
        if (root == null) return null;

        if (root.val == key) {
            if (root.left != null && root.right != null) {
                root.val = findMin(root.right).val;
                root.right = deleteNode(root.right, root.val);
            } else if (root.left == null) root = root.right;
            else root = root.left;
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else root.left = deleteNode(root.left, key);
        return root;
    }

    private TreeNode findMin(TreeNode cur) {
        if (cur == null) return cur;

        while (cur.left != null) cur = cur.left;
        return cur;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}

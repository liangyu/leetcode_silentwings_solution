package LC301_600;
import java.util.*;
public class LC538_ConvertBSTtoGreaterTree {
    /**
     * Given the root of a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original
     * BST is changed to the original key plus sum of all keys greater than the original key in BST.
     *
     * As a reminder, a binary search tree is a tree that satisfies these constraints:
     *
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     * Input: root = [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
     * Output: [30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 104].
     * -104 <= Node.val <= 104
     * All the values in the tree are unique.
     * root is guaranteed to be a valid binary search tree.
     *
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        // corner case
        if (root == null) return root;

        // 反向中序遍历累加即可
        convertBST(root.right);
        sum += root.val;
        root.val = sum;
        convertBST(root.left);
        return root;
    }
}

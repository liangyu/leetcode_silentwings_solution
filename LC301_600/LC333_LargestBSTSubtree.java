package LC301_600;
import java.util.*;
public class LC333_LargestBSTSubtree {
    /**
     * Given the root of a binary tree, find the largest subtree, which is also a Binary Search Tree (BST), where the
     * largest means subtree has the largest number of nodes.
     *
     * A Binary Search Tree (BST) is a tree in which all the nodes follow the below-mentioned properties:
     *
     * The left subtree values are less than the value of their parent (root) node's value.
     * The right subtree values are greater than the value of their parent (root) node's value.
     * Note: A subtree must include all of its descendants.
     *
     * Follow up: Can you figure out ways to solve it with O(n) time complexity?
     *
     * Input: root = [10,5,15,1,8,null,7]
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 10^4].
     * -10^4 <= Node.val <= 10^4
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int res = 0;
    public int largestBSTSubtree(TreeNode root) {
        // corner case
        if (root == null) return 0;

        helper(root);
        return res;
    }

    private int helper(TreeNode root) {
        if (root == null) return 0;

        boolean flag = true;

        if (root.left != null) {
            TreeNode cur = root.left;
            while (cur.right != null) cur = cur.right;
            if (cur.val >= root.val) flag = false;
        }

        if (root.right != null) {
            TreeNode cur = root.right;
            while (cur.left != null) cur = cur.left;
            if (cur.val <= root.val) flag = false;
        }

        int left = helper(root.left);
        int right = helper(root.right);

        if (!flag || left == -1 || right == -1) return -1;
        res = Math.max(res, left + right + 1);
        return left + right + 1;
    }
}
/**
 * 要判断一个root是否代表了一颗BST，有三个条件：
 *
 * 其左子树也是BST（或者NULL）
 * 其右子树也是BST（或者NULL）
 * 其根节点小于左子树的最大值，并且根节点大于右子树的最小值。
 *
 * 考虑这个问题其实包括了两个任务：判断BST，以及它有多少个节点数目。
 * 容易想到这样的设计：当root是BST时返回其包含的节点数目，当root不是BST时则返回-1. 这个-1的返回值可以帮助上一级的根节点确定非BST，一举两得。
 * 对于非NULL的节点，我们就可以放心地为左右子树分别递归调用
 * leftNum和rightNum有任何一个为-1，都表明root都不是BST。
 * 此外，判断root为BST的第三个条件，等价于这样的操作：将左子树的右下节点与根节点比较、右子树的左下节点与根节点比较。
 * 设计一个while语句就可以迅速定位所需要的边界节点
 * 以上三个条件都判断后，如果root不是BST，返回-1；如果是BST，返回leftNum+rightNum+1.
 */
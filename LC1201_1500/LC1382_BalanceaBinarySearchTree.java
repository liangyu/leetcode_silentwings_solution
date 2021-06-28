package LC1201_1500;
import java.util.*;
public class LC1382_BalanceaBinarySearchTree {
    /**
     * Given a binary search tree, return a balanced binary search tree with the same node values.
     *
     * A binary search tree is balanced if and only if the depth of the two subtrees of every node never differ by more
     * than 1.
     *
     * If there is more than one answer, return any of them.
     *
     * Input: root = [1,null,2,null,3,null,4,null,null]
     * Output: [2,1,3,null,null,null,4]
     *
     * Constraints:
     *
     * The number of nodes in the tree is between 1 and 10^4.
     * The tree nodes will have distinct values between 1 and 10^5.
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode balanceBST(TreeNode root) {
        // corner case
        if (root == null) return root;

        List<TreeNode> list = new ArrayList<>();
        inorder(root, list);
        return arrToBst(list, 0, list.size() - 1);
    }

    private void inorder(TreeNode root, List<TreeNode> list) {
        if (root == null) return;

        inorder(root.left, list);
        list.add(root);
        inorder(root.right, list);
    }

    private TreeNode arrToBst(List<TreeNode> list, int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;
        TreeNode root = list.get(mid);
        root.left = arrToBst(list, left, mid - 1);
        root.right = arrToBst(list, mid + 1, right);
        return root;
    }
}

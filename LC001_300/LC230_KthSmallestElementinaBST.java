package LC001_300;
import java.util.*;
public class LC230_KthSmallestElementinaBST {
    /**
     * Given the root of a binary search tree, and an integer k, return the kth (1-indexed) smallest element in the tree.
     *
     * Input: root = [3,1,4,null,2], k = 1
     * Output: 1
     *
     * Constraints:
     *
     * The number of nodes in the tree is n.
     * 1 <= k <= n <= 10^4
     * 0 <= Node.val <= 10^4
     *
     *
     * Follow up: If the BST is modified often (i.e., we can do insert and delete operations) and you need to find
     * the kth smallest frequently, how would you optimize?
     * @param root
     * @param k
     * @return
     */
    // S1: dfs
    // time = O(n), space = O(n)
    private int count = 0;
    private int res = 0;
    public int kthSmallest(TreeNode root, int k) {
        count = k;
        helper(root);
        return res;
    }

    private void helper(TreeNode root) {
        if (root == null) return;

        helper(root.left);
        count--;
        if (count == 0) res = root.val;
        helper(root.right);
    }

    // S2: bfs
    // time = O(n), space = O(n)
    public int kthSmallest2(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;

        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                k--;
                if (k == 0) return cur.val;
                cur = cur.right;
            }
        }
        return -1;
    }
}

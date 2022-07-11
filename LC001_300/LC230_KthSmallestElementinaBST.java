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
    int k, res;
    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        res = 0;
        dfs(root);
        return res;
    }

    private boolean dfs(TreeNode node) {
        if (node == null) return false;

        if (dfs(node.left)) return true;
        k--;
        if (k == 0) {
            res = node.val;
            return true;
        }
        return dfs(node.right);
    }

    // S2: stack
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
/**
 * 需要额外记录一个值，维护下以这个结点为根的子树里一共有多少个结点
 * 可以在插入删除过程中维护的，就可以用logn的复杂度来做
 * lc < k -> dfs 左子树
 * lc == k + 1 -> root
 * lc > k -> dfs 右子树
 * 必须手写平衡树
 */
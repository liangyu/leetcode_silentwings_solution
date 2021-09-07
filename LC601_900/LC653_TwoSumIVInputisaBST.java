package LC601_900;
import java.util.*;
public class LC653_TwoSumIVInputisaBST {
    /**
     * Given the root of a Binary Search Tree and a target number k, return true if there exist two elements in the BST
     * such that their sum is equal to the given target.
     *
     * Input: root = [5,3,6,2,4,null,7], k = 9
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * -10^4 <= Node.val <= 10^4
     * root is guaranteed to be a valid binary search tree.
     * -10^5 <= k <= 10^5
     * @param root
     * @param k
     * @return
     */
    // S1: BST
    // time = O(n), space = O(n)
    public boolean findTarget(TreeNode root, int k) {
        // corner case
        if (root == null) return false;

        List<Integer> list = new ArrayList<>();
        inorder(root, list);

        int left = 0, right = list.size() - 1;
        while (left < right) {
            int sum = list.get(left) + list.get(right);
            if (sum == k) return true;
            if (sum < k) left++;
            else right--;
        }
        return false;
    }

    private void inorder(TreeNode root, List<Integer> list) {
        if (root == null) return;

        inorder(root.left, list);
        list.add(root.val);
        inorder(root.right, list);
    }

    // S2: Stack
    // time = O(n), space = O(n)
    public boolean findTarget2(TreeNode root, int k) {
        // corner case
        if (root == null) return false;

        Stack<TreeNode> ls = new Stack<>();
        Stack<TreeNode> rs = new Stack<>();

        // step 1:  push the left subtree into the left stack
        TreeNode cur = root;
        while (cur != null) {
            ls.push(cur);
            cur = cur.left;
        }

        // step 2: push the right subtree into the right stack
        cur = root;
        while (cur != null) {
            rs.push(cur);
            cur = cur.right;
        }

        // step 3: left++, right--
        while (!ls.isEmpty() && !rs.isEmpty()) {
            int left = ls.peek().val, right = rs.peek().val;
            if (left == right) return false; // no same value in the bst except for the root
            if (left + right == k) return true;
            if (left + right < k) leftPlusPlus(ls);
            else rightMinusMinus(rs);
        }
        return false;
    }

    private void leftPlusPlus(Stack<TreeNode> ls) {
        TreeNode top = ls.pop(), cur = top.right;
        while (cur != null) {
            ls.push(cur);
            cur = cur.left;
        }
    }

    private void rightMinusMinus(Stack<TreeNode> rs) {
        TreeNode top = rs.pop(), cur = top.left;
        while (cur != null) {
            rs.push(cur);
            cur = cur.right;
        }
    }
}

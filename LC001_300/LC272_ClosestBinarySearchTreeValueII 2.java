package LC001_300;
import java.util.*;
public class LC272_ClosestBinarySearchTreeValueII {
    /**
     * Given the root of a binary search tree, a target value, and an integer k, return the k values in the BST that ar
     * closest to the target. You may return the answer in any order.
     *
     * You are guaranteed to have only one unique set of k values in the BST that are closest to the target.
     *
     * Input: root = [4,2,5,1,3], target = 3.714286, k = 2
     * Output: [4,3]
     *
     * Constraints:
     *
     * The number of nodes in the tree is n.
     * 1 <= k <= n <= 10^4.
     * 0 <= Node.val <= 10^9
     * -109 <= target <= 10^9
     *
     *
     * Follow up: Assume that the BST is balanced. Could you solve it in less than O(n) runtime (where n = total nodes)?
     * @param root
     * @param target
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        // init
        Stack<TreeNode> ls = new Stack<>();
        Stack<TreeNode> rs = new Stack<>();
        TreeNode cur = root;

        // traverse -> smaller ones to ls, bigger ones to rs
        while (cur != null) {
            if (cur.val <= target) {
                ls.push(cur);
                cur = cur.right;
            } else {
                rs.push(cur);
                cur = cur.left;
            }
        }

        // find k
        while (k-- > 0) {
            if (!ls.isEmpty() && !rs.isEmpty()) {
                double l = Math.abs(ls.peek().val - target);
                double r = Math.abs(rs.peek().val - target);
                if (l < r) {
                    res.add(ls.peek().val);
                    leftMinusMinus(ls);
                } else {
                    res.add(rs.peek().val);
                    rightPlusPlus(rs);
                }
            } else if (!ls.isEmpty()) {
                res.add(ls.peek().val);
                leftMinusMinus(ls);
            } else {
                res.add(rs.peek().val);
                rightPlusPlus(rs);
            }
        }
        return res;
    }

    private void leftMinusMinus(Stack<TreeNode> ls) {
        TreeNode top = ls.pop();
        TreeNode cur = top.left;
        while (cur != null) {
            ls.push(cur);
            cur = cur.right;
        }
    }

    private void rightPlusPlus(Stack<TreeNode> rs) {
        TreeNode top = rs.pop();
        TreeNode cur = top.right;
        while (cur != null) {
            rs.push(cur);
            cur = cur.left;
        }
    }
}

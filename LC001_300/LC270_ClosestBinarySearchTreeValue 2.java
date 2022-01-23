package LC001_300;
import java.util.*;
public class LC270_ClosestBinarySearchTreeValue {
    /**
     * Given the root of a binary search tree and a target value, return the value in the BST that is closest to the
     * target.
     *
     * Input: root = [4,2,5,1,3], target = 3.714286
     * Output: 4
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * 0 <= Node.val <= 10^9
     * -10^9 <= target <= 10^9
     * @param root
     * @param target
     * @return
     */
    // time = O(logn), space = O(1)
    public int closestValue(TreeNode root, double target) {
        // corner case
        if (root == null) throw new IllegalArgumentException("Invalid input!");

        int res = root.val;
        TreeNode cur = root;
        while (cur != null) {
            if (Math.abs(cur.val - target) < Math.abs(res - target)) res = cur.val;
            if (cur.val < target) cur = cur.right;
            else if (cur.val > target) cur = cur.left;
            else return cur.val;
        }
        return res;
    }
}

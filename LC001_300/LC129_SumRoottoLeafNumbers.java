package LC001_300;
import java.util.*;
public class LC129_SumRoottoLeafNumbers {
    /**
     * You are given the root of a binary tree containing digits from 0 to 9 only.
     *
     * Each root-to-leaf path in the tree represents a number.
     *
     * For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
     * Return the total sum of all root-to-leaf numbers.
     *
     * A leaf node is a node with no children.
     *
     * Input: root = [1,2,3]
     * Output: 25
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 1000].
     * 0 <= Node.val <= 9
     * The depth of the tree will not exceed 10.
     * @param root
     * @return
     */
    //S1: recursion
    // time = O(n), space = O(n)
    public int sumNumbers(TreeNode root) {
        // corner case
        if (root == null) return 0;

        return helper(root, 0);
    }

    private int helper(TreeNode root, int num) {
        if (root == null) return 0;

        if (root.left == null && root.right == null) return num * 10 + root.val;

        return helper(root.left, num * 10 + root.val) + helper(root.right, num * 10 + root.val);
    }
}

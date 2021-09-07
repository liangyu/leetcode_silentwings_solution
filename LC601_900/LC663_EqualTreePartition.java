package LC601_900;
import java.util.*;
public class LC663_EqualTreePartition {
    /**
     * Given the root of a binary tree, return true if you can partition the tree into two trees with equal sums of
     * values after removing exactly one edge on the original tree.
     *
     * Input: root = [5,10,10,null,null,2,3]
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * -10^5 <= Node.val <= 10^5
     * @param root
     * @return
     */
    // S1: HashMap
    // time = O(n), space = O(n)
    private HashMap<Integer, Integer> map;
    public boolean checkEqualTree(TreeNode root) {
        // corner case
        if (root == null) return false;

        map = new HashMap<>();
        int total = calSum(root);
        if (total == 0) return map.getOrDefault(total, 0) > 1;
        return total % 2 == 0 && map.containsKey(total / 2);
    }

    private int calSum(TreeNode root) {
        if (root == null) return 0;
        int sum = calSum(root.left) + calSum(root.right) + root.val;
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        return sum;
    }

    // S2: DFS
    // time = O(n), space = O(n)
    private boolean flag;
    public boolean checkEqualTree2(TreeNode root) {
        // corner case
        if (root == null) return false;

        int total = getSum(root);
        if (total % 2 != 0) return false;

        int target = total / 2;
        flag = false;
        helper(root.left, target);
        helper(root.right, target);
        return flag;
    }

    private int helper(TreeNode root, int target) {
        if (root == null || flag) return 0;

        int left = helper(root.left, target);
        int right = helper(root.right, target);
        int sum = left + right + root.val;
        if (sum == target) flag = true;
        return sum;
    }

    private int getSum(TreeNode root) {
        if (root == null) return 0;
        return getSum(root.left) + root.val + getSum(root.right);
    }
}

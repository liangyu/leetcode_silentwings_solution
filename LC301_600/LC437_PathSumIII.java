package LC301_600;
import java.util.*;
public class LC437_PathSumIII {
    /**
     * Given the root of a binary tree and an integer targetSum, return the number of paths where the sum of the values
     * along the path equals targetSum.
     *
     * The path does not need to start or end at the root or a leaf, but it must go downwards (i.e., traveling only
     * from parent nodes to child nodes).
     *
     * Input: root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 1000].
     * -10^9 <= Node.val <= 10^9
     * -1000 <= targetSum <= 1000
     * @param root
     * @param targetSum
     * @return
     */
    // S1: DFS
    // time = O(n^2), space = O(n)
    public int pathSum(TreeNode root, int targetSum) {
        // corner case
        if (root == null) return 0;

        return helper(root, targetSum) + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
    }

    private int helper(TreeNode root, int sum) {
        int res = 0;
        if (root == null) return 0;
        if (root.val == sum) res++;

        res += helper(root.left, sum - root.val) + helper(root.right, sum - root.val);
        return res;
    }

    // S2: HashMap + prefix
    // time = O(n), space = O(n)
    public int pathSum2(TreeNode root, int targetSum) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        return helper(root, 0, targetSum, map);
    }

    private int helper(TreeNode root, int curSum, int sum, HashMap<Integer, Integer> map) {
        if (root == null) return 0;
        curSum += root.val;
        int res = map.getOrDefault(curSum - sum, 0); // prefix sum -> curSum - sum = diff
        map.put(curSum, map.getOrDefault(curSum, 0) + 1);

        res += helper(root.left, curSum, sum, map) + helper(root.right, curSum, sum, map);
        map.put(curSum, map.get(curSum) - 1); // setback
        return res;
    }
}

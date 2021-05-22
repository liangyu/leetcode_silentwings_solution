package LC001_300;
import java.util.*;
public class LC113_PathSumII {
    /**
     * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where each path's sum
     * equals targetSum.
     *
     * A leaf is a node with no children.
     *
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 5000].
     * -1000 <= Node.val <= 1000
     * -1000 <= targetSum <= 1000
     *
     * @param root
     * @param targetSum
     * @return
     */
    // time = O(n^2), space = O(n)
    // In the worst case, we could have a complete binary tree and if that is the case, then there would be N/2 leafs.
    // For every leaf, we perform a potential O(N) operation of copying over the pathNodes nodes to a new list to be
    // added to the final pathsList. Hence, the complexity in the worst case could be O(N^2)
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        List<Integer> path = new ArrayList<>();
        path.add(root.val);
        dfs(root, targetSum - root.val, path, res);
        return res;
    }

    private void dfs(TreeNode cur, int curSum, List<Integer> path, List<List<Integer>> res) {
        // base case
        if (cur.left == null && cur.right == null) {
            if (curSum == 0) res.add(new ArrayList<>(path)); // O(n)
            return;
        }

        if (cur.left != null) {
            path.add(cur.left.val);
            dfs(cur.left, curSum - cur.left.val, path, res); // O(n)
            path.remove(path.size() - 1);
        }
        if (cur.right != null) {
            path.add(cur.right.val);
            dfs(cur.right, curSum - cur.right.val, path, res); // O(n)
            path.remove(path.size() - 1);
        }
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}

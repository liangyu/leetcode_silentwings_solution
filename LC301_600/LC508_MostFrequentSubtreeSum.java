package LC301_600;
import java.util.*;
public class LC508_MostFrequentSubtreeSum {
    /**
     * Given the root of a binary tree, return the most frequent subtree sum. If there is a tie, return all the values
     * with the highest frequency in any order.
     *
     * The subtree sum of a node is defined as the sum of all the node values formed by the subtree rooted at that node
     * (including the node itself).
     *
     * Input: root = [5,2,-3]
     * Output: [2,-3,4]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 104].
     * -10^5 <= Node.val <= 10^5
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    HashMap<Integer, Integer> map;
    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) return new int[0];

        map = new HashMap<>();
        dfs(root);

        int max = 0;
        List<Integer> res = new ArrayList<>();
        for (int x : map.keySet()) {
            if (map.get(x) >= max) {
                if (map.get(x) > max) {
                    res = new ArrayList<>();
                    max = map.get(x);
                }
                res.add(x);
            }
        }

        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int l = dfs(node.left);
        int r = dfs(node.right);
        int sum = l + r + node.val;
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        return sum;
    }
}

package LC001_300;
import java.util.*;
public class LC95_UniqueBinarySearchTreesII {
    /**
     * Given an integer n, return all the structurally unique BST's (binary search trees), which has exactly n nodes of
     * unique values from 1 to n. Return the answer in any order.
     *
     * Input: n = 3
     * Output: [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
     *
     * Constraints:
     *
     * 1 <= n <= 8
     * @param n
     * @return
     */
    // time = O(n * G(n)), space = O(nG(n))  G(n): Catalan number = 4^n / n^(1/2)
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> res = new ArrayList<>();
        // corner case
        if (n <= 0) return res;
        return dfs(1, n);
    }

    private List<TreeNode> dfs(int start, int end) {
        List<TreeNode> res = new ArrayList<>();
        // base case
        if (start > end) {
            res.add(null);
            return res;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> ls = dfs(start, i - 1); // set i as the root
            List<TreeNode> rs = dfs(i + 1, end);
            for (TreeNode l : ls) {
                for (TreeNode r : rs) {
                    TreeNode root = new TreeNode(i);
                    root.left = l;
                    root.right = r;
                    res.add(root);
                }
            }
        }
        return res;
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}

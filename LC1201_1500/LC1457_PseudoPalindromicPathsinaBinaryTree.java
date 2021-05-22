package LC1201_1500;
import java.util.*;
public class LC1457_PseudoPalindromicPathsinaBinaryTree {
    // S1: DFS
    // time = O(n), space = O(h) = O(logn)
    private int count = 0;
    public int pseudoPalindromicPaths (TreeNode root) {
        // corner case
        if (root == null) return 0;

        preOrder(root, 0);
        return count;
    }

    private void preOrder(TreeNode cur, int path) {
        path ^= (1 << cur.val); // val为多少就左移多少位,比如1 -> 10, 2 -> 100，然后
        if (cur.left == null && cur.right == null) {
            if (path == 0 || (path & (path - 1)) == 0) count++; // even 1221 or odd 121， n & (n - 1) 消除右边开始第一个1
        } // 异或后，要么1221 -> 0，要么121 -> 100，即1的个数为奇数的数字
        if (cur.left != null) preOrder(cur.left, path);
        if (cur.right != null) preOrder(cur.right, path);
    }

    // S2: DFS
    public int pseudoPalindromicPaths2 (TreeNode root) {
        // corner case
        if (root == null) return 0;

        List<List<TreeNode>> res = new ArrayList<>();
        List<TreeNode> path = new ArrayList<>();
        path.add(root);
        dfs(root, path, res);

        int count = 0;
        for (List<TreeNode> list : res) {
            if (isPalin(list)) count++;
        }
        return count;
    }

    private void dfs(TreeNode cur, List<TreeNode> path, List<List<TreeNode>> res) {
        if (cur == null) {
            res.add(new ArrayList<>(path));
            return;
        }

        if (cur.left != null) {
            path.add(cur.left);
            dfs(cur.left, path, res);
            path.remove(path.size() - 1);
        }

        if (cur.right != null) {
            path.add(cur.right);
            dfs(cur.right, path, res);
            path.remove(path.size() - 1);
        }
    }

    private boolean isPalin(List<TreeNode> list) {
        int[] counter = new int[9];
        for (TreeNode node : list) {
            counter[node.val - 1]++;
        }

        int odd = 0, even = 0;
        for (int i = 0; i < 9; i++) {
            if (counter[i] != 0) {
                if (counter[i] % 2 != 0) odd++;
                else even++;
            }
        }
        if (odd > 1) return false;
        return true;
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}

package LC001_300;
import java.util.*;
public class LC257_BinaryTreePaths {
    /**
     * Given the root of a binary tree, return all root-to-leaf paths in any order.
     *
     * A leaf is a node with no children.
     *
     * Input: root = [1,2,3,null,5]
     * Output: ["1->2->5","1->3"]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 100].
     * -100 <= Node.val <= 100
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        path.append(root.val);
        dfs(root, path, res);
        return res;
    }

    private void dfs(TreeNode cur, StringBuilder path, List<String> res) {
        // base case
        if (cur.left == null && cur.right == null) {
            res.add(path.toString());
            return;
        }

        int len = path.length();
        if (cur.left != null) {
            path.append("->" + cur.left.val);
            dfs(cur.left, path, res);
            path.setLength(len);
        }

        if (cur.right != null) {
            path.append("->" + cur.right.val);
            dfs(cur.right, path, res);
            path.setLength(len);
        }
    }
}

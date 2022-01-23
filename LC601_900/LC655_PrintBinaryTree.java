package LC601_900;
import java.util.*;
public class LC655_PrintBinaryTree {
    /**
     * Given the root of a binary tree, construct a 0-indexed m x n string matrix res that represents a formatted layout
     * of the tree. The formatted layout matrix should be constructed using the following rules:
     *
     * The height of the tree is height and the number of rows m should be equal to height + 1.
     * The number of columns n should be equal to 2height+1 - 1.
     * Place the root node in the middle of the top row (more formally, at location res[0][(n-1)/2]).
     * For each node that has been placed in the matrix at position res[r][c], place its left child at
     * res[r+1][c-2height-r-1] and its right child at res[r+1][c+2height-r-1].
     * Continue this process until all the nodes in the tree have been placed.
     * Any empty cells should contain the empty string "".
     * Return the constructed matrix res.
     *
     * Input: root = [1,2]
     * Output:
     * [["","1",""],
     *  ["2","",""]]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 2^10].
     * -99 <= Node.val <= 99
     * The depth of the tree will be in the range [1, 10].
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<List<String>> printTree(TreeNode root) {
        List<List<String>> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        int height = getHeight(root);
        int width = (int) Math.pow(2, height) - 1;

        for (int i = 0; i < height; i++) {
            res.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                res.get(i).add("");
            }
        }

        dfs(root, 0, 0, width - 1, res);
        return res;
    }

    private void dfs(TreeNode node, int depth, int start, int end, List<List<String>> res) {
        if (node == null) return;

        int mid = (start + end) / 2;
        res.get(depth).set(mid, String.valueOf(node.val));

        dfs(node.left, depth + 1, start, mid - 1, res);
        dfs(node.right, depth + 1, mid + 1, end, res);
    }

    private int getHeight(TreeNode node) {
        if (node == null) return 0;

        int lh = getHeight(node.left);
        int rh = getHeight(node.right);
        return Math.max(lh, rh) + 1;
    }
}

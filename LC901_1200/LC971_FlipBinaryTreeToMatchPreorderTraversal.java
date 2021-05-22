package LC901_1200;
import java.util.*;
public class LC971_FlipBinaryTreeToMatchPreorderTraversal {
    /**
     * You are given the root of a binary tree with n nodes, where each node is uniquely assigned a value from 1 to n.
     * You are also given a sequence of n values voyage, which is the desired pre-order traversal of the binary tree.
     *
     * Any node in the binary tree can be flipped by swapping its left and right subtrees. For example, flipping node 1
     * will have the following effect:
     *
     * Flip the smallest number of nodes so that the pre-order traversal of the tree matches voyage.
     *
     * Return a list of the values of all flipped nodes. You may return the answer in any order. If it is impossible to
     * flip the nodes in the tree to make the pre-order traversal match voyage, return the list [-1].
     *
     * Input: root = [1,2], voyage = [2,1]
     * Output: [-1]
     *
     * Constraints:
     *
     * The number of nodes in the tree is n.
     * n == voyage.length
     * 1 <= n <= 100
     * 1 <= Node.val, voyage[i] <= n
     * All the values in the tree are unique.
     * All the values in voyage are unique.
     * @param root
     * @param voyage
     * @return
     */
    // time = O(n), space = O(n)
    private int cur = 0;
    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null || voyage == null || voyage.length == 0) {
            res.add(-1);
            return res;
        }

        preOrder(root, voyage, res);

        if (res.size() != 0 && res.get(0) == -1) {
            res.clear();
            res.add(-1);
        }
        return res;
    }

    private void preOrder(TreeNode root, int[] voyage, List<Integer> res) {
        if (root == null) return;

        if (root.val != voyage[cur]) {
            res.clear();
            res.add(-1);
            return;
        }

        cur++;
        if (cur < voyage.length && root.left != null && root.left.val != voyage[cur]) {
            res.add(root.val);
            preOrder(root.right, voyage, res);
            preOrder(root.left, voyage, res);
        } else {
            preOrder(root.left, voyage, res);
            preOrder(root.right, voyage, res);
        }
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
    }
}

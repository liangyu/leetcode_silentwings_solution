package LC301_600;
import java.util.*;
public class LC501_FindModeinBinarySearchTree {
    /**
     * Given the root of a binary search tree (BST) with duplicates, return all the mode(s) (i.e., the most frequently
     * occurred element) in it.
     *
     * If the tree has more than one mode, return them in any order.
     *
     * Assume a BST is defined as follows:
     *
     * The left subtree of a node contains only nodes with keys less than or equal to the node's key.
     * The right subtree of a node contains only nodes with keys greater than or equal to the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     * Input: root = [1,null,2,2]
     * Output: [2]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * -105 <= Node.val <= 105
     *
     *
     * Follow up: Could you do that without using any extra space? (Assume that the implicit stack space incurred due to
     * recursion does not count).
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    int max = 0, count = 1;
    List<Integer> list;
    TreeNode prev = null;
    public int[] findMode(TreeNode root) {
        // corner case
        if (root == null) return new int[0];

        list = new ArrayList<>();
        inorder(root);
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) res[i] = list.get(i);
        return res;
    }

    private void inorder(TreeNode node) {
        if (node == null) return;

        inorder(node.left);
        if (prev == null) {
            list.add(node.val);
            max = 1;
            count = 1;
        } else {
            if (prev.val == node.val) count++;
            else count = 1;
            if (count >= max) {
                if (count > max) {
                    max = count;
                    list.clear();
                }
                list.add(node.val);
            }
        }
        prev = node;
        inorder(node.right);
    }
}

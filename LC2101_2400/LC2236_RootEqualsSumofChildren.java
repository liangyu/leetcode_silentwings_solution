package LC2101_2400;

public class LC2236_RootEqualsSumofChildren {
    /**
     * You are given the root of a binary tree that consists of exactly 3 nodes: the root, its left child, and its right
     * child.
     *
     * Return true if the value of the root is equal to the sum of the values of its two children, or false otherwise.
     *
     * Input: root = [10,4,6]
     * Output: true
     *
     * Constraints:
     *
     * The tree consists only of the root, its left child, and its right child.
     * -100 <= Node.val <= 100
     * @param root
     * @return
     */
    // time = O(1), space = O(1)
    public boolean checkTree(TreeNode root) {
        return root.val == root.left.val + root.right.val;
    }
}

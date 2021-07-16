package LC601_900;
import java.util.*;
public class LC617_MergeTwoBinaryTrees {
    /**
     * You are given two binary trees root1 and root2.
     *
     * Imagine that when you put one of them to cover the other, some nodes of the two trees are overlapped while the
     * others are not. You need to merge the two trees into a new binary tree. The merge rule is that if two nodes
     * overlap, then sum node values up as the new value of the merged node. Otherwise, the NOT null node will be used
     * as the node of the new tree.
     *
     * Return the merged tree.
     *
     * Note: The merging process must start from the root nodes of both trees.
     *
     * Input: root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
     * Output: [3,4,5,5,4,null,7]
     *
     * Constraints:
     *
     * The number of nodes in both trees is in the range [0, 2000].
     * -10^4 <= Node.val <= 10^4
     * @param root1
     * @param root2
     * @return
     */
    // S1: recursion
    // time = O(m), space = O(m)  n: the minimum number of nodes from the two given trees.
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;
        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    // S2: iteration
    // time = O(m), space = O(m)  n: the minimum number of nodes from the two given trees.
    public TreeNode mergeTrees2(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;

        Stack<TreeNode[]> stack = new Stack<>();
        stack.push(new TreeNode[]{root1, root2});

        while (!stack.isEmpty()) {
            TreeNode[] cur = stack.pop();
            if (cur[0] == null || cur[1] == null) continue;
            cur[0].val += cur[1].val;

            if (cur[0].left == null) cur[0].left = cur[1].left;
            else stack.push(new TreeNode[]{cur[0].left, cur[1].left});

            if (cur[0].right == null) cur[0].right = cur[1].right;
            else stack.push(new TreeNode[]{cur[0].right, cur[1].right});
        }
        return root1;
    }
}

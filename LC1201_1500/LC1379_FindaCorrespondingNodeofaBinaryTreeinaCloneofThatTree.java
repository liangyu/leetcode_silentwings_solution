package LC1201_1500;
import java.util.*;
public class LC1379_FindaCorrespondingNodeofaBinaryTreeinaCloneofThatTree {
    /**
     * Given two binary trees original and cloned and given a reference to a node target in the original tree.
     *
     * The cloned tree is a copy of the original tree.
     *
     * Return a reference to the same node in the cloned tree.
     *
     * Note that you are not allowed to change any of the two trees or the target node and the answer must be a
     * reference to a node in the cloned tree.
     *
     * Input: tree = [7,4,3,null,null,6,19], target = 3
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * The values of the nodes of the tree are unique.
     * target node is a node from the original tree and is not null.
     *
     *
     * Follow up: Could you solve the problem if repeated values on the tree are allowed?
     * @param original
     * @param cloned
     * @param target
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if (original == target) return cloned;

        if (original.left != null) {
            TreeNode res = getTargetCopy(original.left, cloned.left, target);
            if (res != null) return res;
        }

        if (original.right != null) {
            TreeNode res = getTargetCopy(original.right, cloned.right, target);
            if (res != null) return res;
        }
        return null;
    }

    // S2
    // time = O(n), space = O(n)
    HashMap<TreeNode, TreeNode> map;
    public final TreeNode getTargetCopy2(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        map = new HashMap<>();

        dfs(original, cloned);
        return map.get(target);
    }

    private void dfs(TreeNode node1, TreeNode node2) {
        if (node1 == null) return;

        map.put(node1, node2);

        dfs(node1.left, node2.left);
        dfs(node1.right, node2.right);
    }
}

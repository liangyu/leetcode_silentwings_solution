package LC1501_1800;
import java.util.*;
public class LC1676_LowestCommonAncestorofaBinaryTreeIV {
    /**
     * Given the root of a binary tree and an array of TreeNode objects nodes, return the lowest common ancestor (LCA)
     * of all the nodes in nodes. All the nodes will exist in the tree, and all values of the tree's nodes are unique.
     *
     * Extending the definition of LCA on Wikipedia: "The lowest common ancestor of n nodes p1, p2, ..., pn in a binary
     * tree T is the lowest node that has every pi as a descendant (where we allow a node to be a descendant of itself)
     * for every valid i". A descendant of a node x is a node y that is on the path from node x to some leaf node.
     *
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [4,7]
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 104].
     * -10^9 <= Node.val <= 10^9
     * All Node.val are unique.
     * All nodes[i] will exist in the tree.
     * All nodes[i] are distinct.
     * @param root
     * @param nodes
     * @return
     */
    // time = O(n), space = O(n)
    TreeNode res = null;
    HashSet<TreeNode> set;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
        if (root == null) return null;

        set = new HashSet<>();
        for (TreeNode x : nodes) set.add(x);
        dfs(root);
        return res;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int count = dfs(node.left) + dfs(node.right) + (set.contains(node) ? 1 : 0);
        if (count == set.size() && res == null) res = node;
        return count;
    }
}
/**
 * ref: LC236
 * 反过来想，判断是不是最小公共祖先，因为这个判断可以用递归来处理
 * 最大特点是每个结点的属性都是递归的
 * 几乎所有结点的属性都是有递归性的
 * 千方百计设计递归的函数来体现递归性
 * 如何判断呢？
 * int dfs(node): how many of nodes in the set that the subtree of node contains
 */
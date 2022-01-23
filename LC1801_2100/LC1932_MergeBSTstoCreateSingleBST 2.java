package LC1801_2100;
import java.util.*;
public class LC1932_MergeBSTstoCreateSingleBST {
    /**
     * You are given n BST (binary search tree) root nodes for n separate BSTs stored in an array trees (0-indexed).
     * Each BST in trees has at most 3 nodes, and no two roots have the same value. In one operation, you can:
     *
     * Select two distinct indices i and j such that the value stored at one of the leaves of trees[i] is equal to the
     * root value of trees[j].
     * Replace the leaf node in trees[i] with trees[j].
     * Remove trees[j] from trees.
     * Return the root of the resulting BST if it is possible to form a valid BST after performing n - 1 operations, or
     * null if it is impossible to create a valid BST.
     *
     * A BST (binary search tree) is a binary tree where each node satisfies the following property:
     *
     * Every node in the node's left subtree has a value strictly less than the node's value.
     * Every node in the node's right subtree has a value strictly greater than the node's value.
     * A leaf is a node that has no children.
     *
     * Input: trees = [[2,1],[3,2,5],[5,4]]
     * Output: [3,2,5,1,null,4]
     *
     * Constraints:
     *
     * n == trees.length
     * 1 <= n <= 5 * 10^4
     * The number of nodes in each tree is in the range [1, 3].
     * No two roots of trees have the same value.
     * All the trees in the input are valid BSTs.
     * 1 <= TreeNode.val <= 5 * 10^4.
     * @param trees
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode canMerge(List<TreeNode> trees) {
        HashSet<Integer> blacklist = new HashSet<>();
        HashMap<Integer, TreeNode> map = new HashMap<>();
        HashSet<Integer> used = new HashSet<>();
        for (TreeNode root : trees) {
            findLeaves(root.left, blacklist);
            findLeaves(root.right, blacklist);
            map.put(root.val, root);
        }
        int count = 0;
        int n = trees.size();
        TreeNode root = null;
        for (int i = 0; i < n; i++) {
            if (!blacklist.contains(trees.get(i).val)) {
                count++;
                root = trees.get(i);
            }
        }
        if (count != 1) return null;
        used.add(root.val);
        boolean ok = build(root, map, used, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return (ok && used.size() == n) ? root : null;
    }

    private boolean build(TreeNode node, HashMap<Integer, TreeNode> map, HashSet<Integer> used, int min, int max) {
        if (node == null) return true;
        int val = node.val;
        if (val < min || val > max) return false;

        if (node.left != null || node.right != null) {
            return build(node.left, map, used, min, val - 1) && build(node.right, map, used,val + 1, max);
        }
        else if (map.containsKey(val)) {
            TreeNode childRoot = map.get(val);
            node.left = childRoot.left;
            node.right = childRoot.right;
            used.add(val);
            return build(node.left, map, used, min, val - 1) && build(node.right, map, used, val + 1, max);
        } else return true;
    }

    private void findLeaves(TreeNode node, HashSet<Integer> blacklist) {
        if (node == null) return;
        blacklist.add(node.val);
        findLeaves(node.left, blacklist);
        findLeaves(node.right, blacklist);
    }
}
/**
 * root不会有相同元素
 * 根在哪里？取某一个作为大bst的根
 * 根的选择是unique的，为什么呢？
 * 因为要构造成大bst，小bst的叶子结点不会作为大bst的根结点
 * blacklist [1 2 5 4]
 * whitelist [2 3 5] => 3
 * 1. small bst leaf nodes cannot ber the root of the final big bst
 * 要构造成功，只能有一个小bst的根作为大bst的根，否则超过1个的话必定有一个无法merge，就不符合条件了。所以是有且只有一个，很容易找到。
 * 以这棵bst为根，把其他bst merge进来即可。
 * 一旦发现可以拼了，要不要拼呢？
 * 2. shall we merge if we find a leaf node of big bst == root node of a small bst
 * 一定要merge => 因为最终bst里的所有元素都不相同，所以一旦遇到一个2，其他地方不会再有机会再merge进来
 * yes, we have to.
 * 3. keep checking range vs node val while constructing big bst
 * 4. check total number of merged small bst
 */

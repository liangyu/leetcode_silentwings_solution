package LC001_300;
import java.util.*;
public class LC99_RecoverBinarySearchTree {
    /**
     * You are given the root of a binary search tree (BST), where exactly two nodes of the tree were swapped by
     * mistake. Recover the tree without changing its structure.
     *
     * Follow up: A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?
     *
     * Input: root = [1,3,null,null,2]
     * Output: [3,1,null,null,2]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [2, 1000].
     * -2^31 <= Node.val <= 2^31 - 1
     * @param root
     */
    // S1
    // time = O(n), space = O(n)
    private TreeNode prev = null;
    public void recoverTree(TreeNode root) {
        // corner case
        if (root == null) return;

        TreeNode[] mistakes = new TreeNode[2];
        dfs(root, mistakes);
        swap(mistakes);
    }

    private void dfs(TreeNode cur, TreeNode[] mistakes) {
        // base case
        if (cur == null) return;

        dfs(cur.left, mistakes);
        if (prev != null && prev.val > cur.val) {
            mistakes[1] = cur;
            if (mistakes[0] == null) mistakes[0] = prev;
        }
        prev = cur;
        dfs(cur.right, mistakes);
    }

    private void swap(TreeNode[] mistakes) {
        int temp = mistakes[0].val;
        mistakes[0].val = mistakes[1].val;
        mistakes[1].val = temp;
    }

    // S2: dfs
    // time = O(n), space = O(n)
    TreeNode lastSeen = new TreeNode(Integer.MIN_VALUE), first = null, second = null;
    public void recoverTree2(TreeNode root) {
        dfs(root);

        int val = first.val;
        first.val = second.val;
        second.val = val;
    }

    private void dfs(TreeNode node) {
        if (node == null) return;

        dfs(node.left);

        if (node.val < lastSeen.val) {
            if (first == null) {
                first = lastSeen;
                second = node;
            } else {
                second = node;
            }
        }
        lastSeen = node;

        dfs(node.right);
    }
}
/**
 * 中序遍历得到一个递增的序列
 * 交换2个node -> 不和谐 -> 递增顺序没有了
 * 扫一遍定位peak和valley
 * 如果被交换的2个是相邻的 => 1处不和谐
 * 把所有元素抽出来转化成数组
 * 中序遍历，一边走一边看，看相邻2个是否是递增的
 */
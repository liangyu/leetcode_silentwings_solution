package LC301_600;

public class LC543_DiameterofBinaryTree {
    /**
     * Given the root of a binary tree, return the length of the diameter of the tree.
     *
     * The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may
     * or may not pass through the root.
     *
     * The length of a path between two nodes is represented by the number of edges between them.
     *
     * Input: root = [1,2,3,4,5]
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * -100 <= Node.val <= 100
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int res = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        // corner case
        if (root == null) return 0;

        longestPathToLeaf(root);
        return res - 1; // 求的是边数 = 点数 - 1
    }

    private int longestPathToLeaf(TreeNode node) {
        if (node == null) return 0;

        int leftPath = longestPathToLeaf(node.left);
        int rightPath = longestPathToLeaf(node.right);
        res = Math.max(res, leftPath + 1 + rightPath);
        return Math.max(leftPath, rightPath) + 1; // + node自身
    }
}
/**
 * 各点连通，且没有环，无向无环连通图
 * 最关键的一点：对diameter有个奇特的审查角度
 * 不管路径如何分布，肯定会有一个拐点
 * 递归 -> 把每个结点到达最远的距离计算出来，均摊为1
 * 问的虽然是最长路径，但是我递归算的是每个结点到叶子节点的距离，间接来求
 * 以node为拐点的最长路径我们也知道，就是leftPath + node + rightPath
 * 所以所有的path都不重复不遗漏的遍历了一遍
 * 遇到树，递归函数 -> 求的是很特别的，结点和自身的最远距离，间接去求
 */

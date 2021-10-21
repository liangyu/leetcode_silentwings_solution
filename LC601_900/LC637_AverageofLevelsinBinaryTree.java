package LC601_900;
import java.util.*;
public class LC637_AverageofLevelsinBinaryTree {
    /**
     * Given a non-empty binary tree, return the average value of the nodes on each level in the form of an array.
     *
     * Input:
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * Output: [3, 14.5, 11]
     *
     * Note:
     * The range of node's value is in the range of 32-bit signed integer.
     * @param root
     * @return
     */
    // time = O(n), space = O(k) k: the maximum number of nodes at any level in the input tree
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            double levelSum = 0; // 注意这里要用double, 用int到后面再转很可能会越界！！！
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                levelSum += cur.val;
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            res.add(levelSum / size);
        }
        return res;
    }
}
/**
 * 大部分tree的题都用dfs来做，只有少数涉及到层级遍历的，我们用bfs。
 */

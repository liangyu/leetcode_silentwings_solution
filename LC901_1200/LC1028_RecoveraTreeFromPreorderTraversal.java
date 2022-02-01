package LC901_1200;
import java.util.*;
public class LC1028_RecoveraTreeFromPreorderTraversal {
    /**
     * We run a preorder depth-first search (DFS) on the root of a binary tree.
     *
     * At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value
     * of this node.  If the depth of a node is D, the depth of its immediate child is D + 1.  The depth of the root
     * node is 0.
     *
     * If a node has only one child, that child is guaranteed to be the left child.
     *
     * Given the output traversal of this traversal, recover the tree and return its root.
     *
     * Input: traversal = "1-2--3--4-5--6--7"
     * Output: [1,2,5,3,4,6,7]
     *
     * Constraints:
     *
     * The number of nodes in the original tree is in the range [1, 1000].
     * 1 <= Node.val <= 10^9
     * @param traversal
     * @return
     */
    // time = O(n), space = O(n)
    List<int[]> nodes;
    public TreeNode recoverFromPreorder(String traversal) {
        nodes = new ArrayList<>();
        int n = traversal.length();
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && traversal.charAt(j) == '-') j++;
            int depth = j - i;
            i = j;
            while (j < n && Character.isDigit(traversal.charAt(j))) j++;
            int val = Integer.valueOf(traversal.substring(i, j));
            i = j - 1; // j的位置是下一个dash
            nodes.add(new int[]{val, depth});
        }
        int[] num = new int[1];
        return dfs(0, num);
    }

    private TreeNode dfs(int cur, int[] num) {
        TreeNode root = new TreeNode(nodes.get(cur)[0]);
        int[] leftNum = new int[1], rightNum = new int[1]; // 默认值是0，到达叶子节点下面的空后直接返回，不会走下面if语句

        if (cur + 1 < nodes.size() && nodes.get(cur)[1] + 1 == nodes.get(cur + 1)[1]) {
            root.left = dfs(cur + 1, leftNum);
        }

        if (cur + leftNum[0] + 1 < nodes.size() && nodes.get(cur)[1] + 1 == nodes.get(cur + leftNum[0] + 1)[1]) {
            root.right = dfs(cur + leftNum[0] + 1, rightNum);
        }

        num[0] = leftNum[0] + rightNum[0] + 1; // 叶子节点左右都是0，返回节点个数为1
        return root;
    }
}
/**
 * tree的题目基本都尽量用递归解决
 * 先序遍历有个好的特性 -> 它可以唯一重构出这棵树来。
 * 把空节点也要写进序列化里，这样才能唯一重构出一棵树来
 * 但这里并不包括空节点
 * nodes[i] => val, depth
 * 1 0 <- cur
 * 2 1
 * 3 2
 * 4 2
 * 5 1
 * 6 2
 * 7 2
 * return dfs(nodes[0])
 */

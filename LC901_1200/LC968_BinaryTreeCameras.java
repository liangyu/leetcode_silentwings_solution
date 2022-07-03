package LC901_1200;
import java.util.*;
public class LC968_BinaryTreeCameras {
    /**
     * Given a binary tree, we install cameras on the nodes of the tree.
     *
     * Each camera at a node can monitor its parent, itself, and its immediate children.
     *
     * Calculate the minimum number of cameras needed to monitor all nodes of the tree.
     *
     * Input: [0,0,null,0,0]
     * Output: 1
     * Note:
     *
     * The number of nodes in the given tree will be in the range [1, 1000].
     * Every node has value 0.
     *
     * @param root
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    int res = 0;
    public int minCameraCover(TreeNode root) {
        int temp = dfs(root);
        if (temp == 0) res++; // if temp = 0，表明root没被覆盖到，来自left == 2 && right == 2 => 必须在root加一个cam
        return res;
    }

    private int dfs(TreeNode cur) {
        if (cur == null) return 2; // -> 空结点 要处理成covered without camera!!!

        int left = dfs(cur.left);
        int right = dfs(cur.right);

        if (left == 0 || right == 0) {
            res++;
            return 1;
        }
        if (left == 2 && right == 2) return 0;
        return 2; // case: (1, 2) or (1, 1) -> 2
    }

    // S2: 树形dp
    // time = O(n), space = O(n)
    public int minCameraCover2(TreeNode root) {
        int[] res = helper(root);
        return Math.min(res[1], res[2]);
    }

    private int[] helper(TreeNode node) {
        if (node == null) return new int[]{0, 0, Integer.MAX_VALUE / 2};

        int[] l = helper(node.left);
        int[] r = helper(node.right);

        // case 1: node 被父节点看 -> 子节点a,b只能被子节点和自己看
        int v1 = Math.min(l[1], l[2]) + Math.min(r[1], r[2]);
        // case 2: node 被子节点看 -> 子节点a自己看自己，b被自己或子节点看；b自己看自己，a被自己或者子节点看
        int v2 = Math.min(l[2] + Math.min(r[1], r[2]), r[2] + Math.min(l[1], l[2]));
        // case 3: node 被自己看 -> 子节点被父节点，子节点或者自己看都可以
        int v3 = Math.min(l[0], Math.min(l[1], l[2])) + Math.min(r[0], Math.min(r[1], r[2])) + 1;
        return new int[]{v1, v2, v3};
    }
}
/**
 * f(u, 0): u 被父节点看
 * f(u, 1): u 被子节点看
 * f(u, 2): u 被自己看
 * f(u,0) = min{f(a,1), f(a,2)} + min{f(b,1), f(b,2)}
 * f(u,1) = min{f(a,2) + min{f(b, 1~2)}， f(b,2) + min{f(a, 1~2)}}
 * f(u,2) = min{f(a, 0~2) + min{f(b, 0~2)}}
 * 0: uncovered
 * 1: covered with camera
 * 2: covered without camera
 *
 * Intuition:
 * Consider a node in the tree.
 * It can be covered by its parent, itself, its two children.
 * Four options.
 *
 * Consider the root of the tree.
 * It can be covered by left child, or right child, or itself.
 * Three options.
 *
 * Consider one leaf of the tree.
 * It can be covered by its parent or by itself.
 * Two options.
 *
 * If we set a camera at the leaf, the camera can cover the leaf and its parent.
 * If we set a camera at its parent, the camera can cover the leaf, its parent and its sibling.
 *
 * We can see that the second plan is always better than the first.
 * Now we have only one option, set up camera to all leaves' parent.
 *
 * Here is our greedy solution:
 *
 * Set cameras on all leaves' parents, then remove all covered nodes.
 * Repeat step 1 until all nodes are covered.
 * Explanation:
 * Apply a recusion function dfs.
 * Return 0 if it's a leaf.
 * Return 1 if it's a parent of a leaf, with a camera on this node.
 * Return 2 if it's coverd, without a camera on this node.
 *
 * For each node,
 * if it has a child, which is leaf (node 0), then it needs camera.
 * if it has a child, which is the parent of a leaf (node 1), then it's covered.
 *
 * If it needs camera, then res++ and we return 1.
 * If it's covered, we return 2.
 * Otherwise, we return 0.
 */
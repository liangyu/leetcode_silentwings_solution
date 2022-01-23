package LC1501_1800;
import java.util.*;
public class LC1660_CorrectaBinaryTree {
    /**
     * You have a binary tree with a small defect. There is exactly one invalid node where its right child incorrectly
     * points to another node at the same depth but to the invalid node's right.
     *
     * Given the root of the binary tree with this defect, root, return the root of the binary tree after removing this
     * invalid node and every node underneath it (minus the node it incorrectly points to).
     *
     * Custom testing:
     *
     * The test input is read as 3 lines:
     *
     * TreeNode root
     * int fromNode (not available to correctBinaryTree)
     * int toNode (not available to correctBinaryTree)
     * After the binary tree rooted at root is parsed, the TreeNode with value of fromNode will have its right child
     * pointer pointing to the TreeNode with a value of toNode. Then, root is passed to correctBinaryTree.
     *
     * Input: root = [1,2,3], fromNode = 2, toNode = 3
     * Output: [1,null,3]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [3, 10^4].
     * -10^9 <= Node.val <= 10^9
     * All Node.val are unique.
     * fromNode != toNode
     * fromNode and toNode will exist in the tree and will be on the same depth.
     * toNode is to the right of fromNode.
     * fromNode.right is null in the initial tree from the test data.
     * @param root
     * @return
     */
    // S1: dfs
    // time = O(n), space = O(n)
    HashSet<Integer> visited;
    public TreeNode correctBinaryTree(TreeNode root) {
        visited = new HashSet<>();
        return dfs(root);
    }

    private TreeNode dfs(TreeNode node) {
        if (node == null) return node;
        if (node.right != null && visited.contains(node.right.val)) return null;

        visited.add(node.val);
        node.right = dfs(node.right); // 一定要先右后左，因为出问题的node连接的是右边的node!!!
        node.left = dfs(node.left);
        return node;
    }

    // S2: bfs
    // time = O(n), space = O(n)
    public TreeNode correctBinaryTree2(TreeNode root) {
        if (root == null) return root;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        HashSet<Integer> set = new HashSet<>();
        set.add(root.val);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if (cur.right != null) {
                    TreeNode node = cur.right;
                    if (node.right != null && set.contains(node.right.val)) {
                        cur.right = null;
                        return root;
                    }
                    queue.offer(node);
                    set.add(node.val);
                }
                if (cur.left != null) {
                    TreeNode node = cur.left;
                    if (node.right != null && set.contains(node.right.val)) {
                        cur.left = null;
                        return root;
                    }
                    queue.offer(node);
                    set.add(node.val);
                }
            }
        }
        return root;
    }
}

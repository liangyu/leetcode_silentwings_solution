package LC1501_1800;

public class LC1666_ChangetheRootofaBinaryTree {
    /**
     * Given the root of a binary tree and a leaf node, reroot the tree so that the leaf is the new root.
     *
     * You can reroot the tree with the following steps for each node cur on the path starting from the leaf up to the
     * root excluding the root:
     *
     * If cur has a left child, then that child becomes cur's right child.
     * cur's original parent becomes cur's left child. Note that in this process the original parent's pointer to cur
     * becomes null, making it have at most one child.
     * Return the new root of the rerooted tree.
     *
     * Note: Ensure that your solution sets the Node.parent pointers correctly after rerooting or you will receive
     * "Wrong Answer".
     *
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], leaf = 7
     * Output: [7,2,null,5,4,3,6,null,null,null,1,null,null,0,8]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [2, 100].
     * -10^9 <= Node.val <= 10^9
     * All Node.val are unique.
     * leaf exist in the tree.
     * @param root
     * @param leaf
     * @return
     */
    // time = O(n), space = O(n)
    public Node flipBinaryTree(Node root, Node leaf) {
        dfs(null, leaf); // dfs(prev, cur)
        return leaf;
    }

    private void dfs(Node prev, Node node) {
        if (node.parent == null) { // root -> no parent
            if (node.left == prev) node.left = null;
            else node.right = null;
            node.parent = prev; // prev is current node's new parent
            return;
        }

        if (node.right == prev) node.right = node.left; // if new parent comes from the right side
        Node p = node.parent; // if new parent comes from the left side
        node.parent = prev; // prev is current node's new parent
        node.left = p; // original parent now becomes current node's left child; if no original parent, see root case above!
        dfs(node, p); // recursive treatment from leaf to root, exit is when node = root
    }

    // Definition for a Node.
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
}

package LC301_600;
import java.util.*;
public class LC510_InorderSuccessorinBSTII {
    /**
     * Given a node in a binary search tree, return the in-order successor of that node in the BST. If that node has no
     * in-order successor, return null.
     *
     * The successor of a node is the node with the smallest key greater than node.val.
     *
     * You will have direct access to the node but not to the root of the tree. Each node will have a reference to its
     * parent node. Below is the definition for Node:
     *
     * class Node {
     *     public int val;
     *     public Node left;
     *     public Node right;
     *     public Node parent;
     * }
     *
     * Input: tree = [2,1,3], node = 1
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * -10^5 <= Node.val <= 10^5
     * All Nodes will have unique values.
     *
     *
     * Follow up: Could you solve it without looking up any of the node's values?
     *
     * @param node
     * @return
     */
    // time = O(n), space = O(1)
    public Node inorderSuccessor(Node node) {
        // corner case
        if (node == null) return null;

        if (node.right != null) return leftMostNode(node.right);

        while (node.parent != null && node.parent.right == node) node = node.parent;
        return node.parent;
    }

    private Node leftMostNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
}

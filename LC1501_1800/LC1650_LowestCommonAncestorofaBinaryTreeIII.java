package LC1501_1800;
import java.util.*;
public class LC1650_LowestCommonAncestorofaBinaryTreeIII {
    /**
     * Given two nodes of a binary tree p and q, return their lowest common ancestor (LCA).
     *
     * Each node will have a reference to its parent node. The definition for Node is below:
     *
     * class Node {
     *     public int val;
     *     public Node left;
     *     public Node right;
     *     public Node parent;
     * }
     * According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a tree T is
     * the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself)."
     *
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [2, 10^5].
     * -10^9 <= Node.val <= 10^9
     * All Node.val are unique.
     * p != q
     * p and q exist in the tree.
     * @param p
     * @param q
     * @return
     */
    // S1: HashSet
    // time = O(n), space = O(n)
    public Node lowestCommonAncestor(Node p, Node q) {
        // corner case
        if (p == null || q == null) return null;

        HashSet<Node> set = new HashSet<>();
        set.add(p);
        while (p.parent != null) {
            p = p.parent;
            set.add(p);
        }

        while (q != null) {
            if (set.contains(q)) return q;
            q = q.parent;
        }
        return null;
    }

    // S2: Ref LC160
    // time = O(n), space = O(1)
    public Node lowestCommonAncestor2(Node p, Node q) {
        // corner case
        if (p == null || q == null) return null;

        Node curA = p, curB = q;
        while (curA != curB) {
            curA = curA == null ? q : curA.parent;
            curB = curB == null ? p : curB.parent;
        }
        return curA;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
}

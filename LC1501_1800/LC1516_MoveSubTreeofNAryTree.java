package LC1501_1800;
import java.util.*;
public class LC1516_MoveSubTreeofNAryTree {
    /**
     * Given the root of an N-ary tree of unique values, and two nodes of the tree p and q.
     *
     * You should move the subtree of the node p to become a direct child of node q. If p is already a direct child of
     * q, do not change anything. Node p must be the last child in the children list of node q.
     *
     * Return the root of the tree after adjusting it.
     *
     * There are 3 cases for nodes p and q:
     *
     * Node q is in the sub-tree of node p.
     * Node p is in the sub-tree of node q.
     * Neither node p is in the sub-tree of node q nor node q is in the sub-tree of node p.
     * In cases 2 and 3, you just need to move p (with its sub-tree) to be a child of q, but in case 1 the tree may be
     * disconnected, thus you need to reconnect the tree again. Please read the examples carefully before solving this
     * problem.
     *
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated
     * by the null value (See examples).
     *
     * For example, the above tree is serialized as [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,
     * null,13,null,null,14].

     * Input: root = [1,null,2,3,null,4,5,null,6,null,7,8], p = 4, q = 1
     * Output: [1,null,2,3,4,null,5,null,6,null,7,8]
     *
     * Constraints:
     *
     * The total number of nodes is between [2, 1000].
     * Each node has a unique value.
     * p != null
     * q != null
     * p and q are two different nodes (i.e. p != q).
     * @param root
     * @param p
     * @param q
     * @return
     */
    // time = O(n), space = O(n)
    HashMap<Node, Node> map;
    public Node moveSubTree(Node root, Node p, Node q) {
        map = new HashMap<>();
        findParent(root, null);

        // case 1: q is already direct parent of p
        if (map.get(p) == q) return root;

        // case 2: if p is not the ancestor of q, disconnect p from its parent and add itself to q
        if (!isAncestor(p, q)) {
            disconnect(p, map.get(p));

            q.children.add(p);
            return root;
        }

        // case 3: if p is the ancestor of q, disconnect both p and q from their own parents
        // append q to p's parent and append p to q
        disconnect(q, map.get(q));

        // case 3.1: if p is the root, then q will be the new root and append p to q
        if (map.get(p) == null) {
            q.children.add(p);
            return q; // q has been disconnected with its parent, so no loop here
        }

        // case 3.2: p is not the root, so simply disconnect p with its parent and append to q, and
        // append q to p's ORIGINAL position!!! i.e., q must replace p's ORIGINAL position!!!
        //
        for (int i = 0; i < map.get(p).children.size(); i++) {
            if (map.get(p).children.get(i) == p) {
                map.get(p).children.set(i, q);
                map.put(q, map.get(p));
            }
        }
        disconnect(p, map.get(p));
        q.children.add(p);
        return root;
    }

    private void disconnect(Node cur, Node parent) {
        if (parent == null) return;

        parent.children.remove(cur);
        map.remove(cur);
    }

    private boolean isAncestor(Node parent, Node cur) {
        while (cur != null) {
            if (cur == parent) return true;
            cur = map.get(cur);
        }
        return false;
    }

    private void findParent(Node cur, Node parent) {
        if (cur == null) return;
        map.put(cur, parent);

        for (Node child : cur.children) findParent(child, cur);
    }

    // Definition for a Node.
    class Node {
        public int val;
        public List<Node> children;

        public Node() {
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val,ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }
    }
}
/**
 * 这道题的复杂之处在于需要考虑以下4种情景，有不同的操作策略。
 * 1. p是q的直系child。需要的操作：直接返回root。
 * 2. p不是q的祖先。需要的操作：p与其父节点断开；将p子树接在q的子节点；答案返回root。
 * 3.1 p是q的祖先且p是root。需要的操作：q与其父节点断开；p接为q的子节点；答案返回q。
 * 3.2 p是q的祖先。需要的操作：q与其父节点断开；将p子树与其父节点断开，改换为q子树；将p子树接在q的子节点；答案返回root。
 */

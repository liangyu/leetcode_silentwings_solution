package LC301_600;

public class LC558_LogicalORofTwoBinaryGridsRepresentedasQuadTrees {
    /**
     * A Binary Matrix is a matrix in which all the elements are either 0 or 1.
     *
     * Given quadTree1 and quadTree2. quadTree1 represents a n * n binary matrix and quadTree2 represents another n * n
     * binary matrix.
     *
     * Return a Quad-Tree representing the n * n binary matrix which is the result of logical bitwise OR of the two
     * binary matrixes represented by quadTree1 and quadTree2.
     *
     * Notice that you can assign the value of a node to True or False when isLeaf is False, and both are accepted in
     * the answer.
     *
     * A Quad-Tree is a tree data structure in which each internal node has exactly four children. Besides, each node
     * has two attributes:
     *
     * val: True if the node represents a grid of 1's or False if the node represents a grid of 0's.
     * isLeaf: True if the node is leaf node on the tree or False if the node has the four children.
     * class Node {
     *     public boolean val;
     *     public boolean isLeaf;
     *     public Node topLeft;
     *     public Node topRight;
     *     public Node bottomLeft;
     *     public Node bottomRight;
     * }
     * We can construct a Quad-Tree from a two-dimensional area using the following steps:
     *
     * If the current grid has the same value (i.e all 1's or all 0's) set isLeaf True and set val to the value of the
     * grid and set the four children to Null and stop.
     * If the current grid has different values, set isLeaf to False and set val to any value and divide the current
     * grid into four sub-grids as shown in the photo.
     * Recurse for each of the children with the proper sub-grid.
     *
     * If you want to know more about the Quad-Tree, you can refer to the wiki.
     *
     * Quad-Tree format:
     *
     * The input/output represents the serialized format of a Quad-Tree using level order traversal, where null
     * signifies a path terminator where no node exists below.
     *
     * It is very similar to the serialization of the binary tree. The only difference is that the node is represented
     * as a list [isLeaf, val].
     *
     * If the value of isLeaf or val is True we represent it as 1 in the list [isLeaf, val] and if the value of isLeaf
     * or val is False we represent it as 0.
     *
     * Input: quadTree1 = [[0,1],[1,1],[1,1],[1,0],[1,0]]
     * , quadTree2 = [[0,1],[1,1],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]
     * Output: [[0,0],[1,1],[1,1],[1,1],[1,0]]
     *
     * Constraints:
     *
     * quadTree1 and quadTree2 are both valid Quad-Trees each representing a n * n grid.
     * n == 2x where 0 <= x <= 9.
     * @param quadTree1
     * @param quadTree2
     * @return
     */
    // time = O(n), space = O(n)
    public Node intersect(Node quadTree1, Node quadTree2) {
        if (quadTree1.isLeaf && quadTree2.isLeaf) {
            Node node = new Node(quadTree1.val || quadTree2.val, true, null, null, null, null);
            return node;
        }

        if (quadTree1.isLeaf && quadTree1.val || quadTree2.isLeaf && quadTree2.val) {
            Node node = new Node(true, true, null, null, null, null);
            return node;
        }

        Node a = quadTree1.isLeaf ? quadTree1 : quadTree1.topLeft;
        Node b = quadTree2.isLeaf ? quadTree2 : quadTree2.topLeft;
        Node topLeft = intersect(a, b);

        a = quadTree1.isLeaf ? quadTree1 : quadTree1.topRight;
        b = quadTree2.isLeaf ? quadTree2 : quadTree2.topRight;
        Node topRight = intersect(a, b);

        a = quadTree1.isLeaf ? quadTree1 : quadTree1.bottomLeft;
        b = quadTree2.isLeaf ? quadTree2 : quadTree2.bottomLeft;
        Node bottomLeft = intersect(a, b);

        a = quadTree1.isLeaf ? quadTree1 : quadTree1.bottomRight;
        b = quadTree2.isLeaf ? quadTree2 : quadTree2.bottomRight;
        Node bottomRight = intersect(a, b);

        boolean val1 = topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf;
        boolean val2 = topLeft.val == topRight.val && topRight.val == bottomLeft.val && bottomLeft.val == bottomRight.val;
        if (val1 || val2) {
            Node node = new Node(topLeft.val, true, null, null, null, null);
            return node;
        } else {
            Node node = new Node(false, false, null, null, null, null);
            node.topLeft = topLeft;
            node.topRight = topRight;
            node.bottomLeft = bottomLeft;
            node.bottomRight = bottomRight;
            return node;
        }
    }

    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {}

        public Node(boolean _val,boolean _isLeaf,Node _topLeft,Node _topRight,Node _bottomLeft,Node _bottomRight) {
            val = _val;
            isLeaf = _isLeaf;
            topLeft = _topLeft;
            topRight = _topRight;
            bottomLeft = _bottomLeft;
            bottomRight = _bottomRight;
        }
    }
}

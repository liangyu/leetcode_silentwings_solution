package LC301_600;

public class LC427_ConstructQuadTree {
    /**
     * Given a n * n matrix grid of 0's and 1's only. We want to represent the grid with a Quad-Tree.
     *
     * Return the root of the Quad-Tree representing the grid.
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
     * The output represents the serialized format of a Quad-Tree using level order traversal, where null signifies a
     * path terminator where no node exists below.
     *
     * It is very similar to the serialization of the binary tree. The only difference is that the node is represented
     * as a list [isLeaf, val].
     *
     * If the value of isLeaf or val is True we represent it as 1 in the list [isLeaf, val] and if the value of isLeaf
     * or val is False we represent it as 0.
     *
     * Input: grid = [[0,1],[1,0]]
     * Output: [[0,1],[1,0],[1,1],[1,1],[1,0]]
     *
     * Input: grid = [[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1],[1,1,1,1,0,0,0,0],
     * [1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0],[1,1,1,1,0,0,0,0]]
     * Output: [[0,1],[1,1],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]
     *
     * Constraints:
     *
     * n == grid.length == grid[i].length
     * n == 2x where 0 <= x <= 6
     * @param grid
     * @return
     */
    // S1
    // time = O(n^2), space = O(n^2)
    int[][] presum;
    public Node construct(int[][] grid) {
        int n = grid.length;
        presum = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                presum[i][j] = presum[i - 1][j] + presum[i][j - 1] - presum[i - 1][j - 1] + grid[i - 1][j - 1];
            }
        }

        return dfs(1, 1, n, n);
    }

    private Node dfs(int x1, int y1, int x2, int y2) {
        int n = x2 - x1 + 1;
        int sum = presum[x2][y2] - presum[x2][y1 - 1] - presum[x1 - 1][y2] + presum[x1 - 1][y1 - 1];
        if (sum == 0 || sum == n * n) return new Node(sum == 0 ? false : true, true);

        Node node = new Node(false, false);
        int m = n / 2;
        node.topLeft = dfs(x1, y1, x1 + m - 1, y1 + m - 1);
        node.topRight = dfs(x1, y1 + m, x1 + m - 1, y2);
        node.bottomLeft = dfs(x1 + m, y1, x2, y1 + m - 1);
        node.bottomRight = dfs(x1 + m, y1 + m, x2, y2);
        return node;
    }

    // S2:
    // time = O(n^2), space = O(logn)
    public Node construct2(int[][] grid) {
        int n = grid.length;
        return helper(grid, 0, 0, n);
    }

    private Node helper(int[][] grid, int x, int y, int k) {
        if (k == 1) return new Node(grid[x][y] == 0 ? false : true, true);

        Node node = new Node(false, false);
        Node tl = helper(grid, x, y, k / 2);
        Node tr = helper(grid, x, y + k / 2, k / 2);
        Node bl = helper(grid, x + k / 2, y, k / 2);
        Node br = helper(grid, x + k / 2, y + k / 2, k / 2);

        if (tl.isLeaf &&  tr.isLeaf && bl.isLeaf && br.isLeaf && tl.val == tr.val && tr.val == bl.val && bl.val == br.val) {
            node.isLeaf = true;
            node.val = tl.val;
        } else {
            node.topLeft = tl;
            node.topRight = tr;
            node.bottomLeft = bl;
            node.bottomRight = br;
        }
        return node;
    }

    // Definition for a QuadTree node.
    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;


        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    };
}
/**
 * 二维前缀和 -> 求是否矩阵里全是0或者1 -> O(1) 快速判断
 * time = n^2 + n^2/4 + ... = n^2(1+1/4+...) <= 2 * n^2 => O(n^2)
 */

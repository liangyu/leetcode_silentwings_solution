package LC301_600;

public class LC370_RangeAddition {
    /**
     * You are given an integer length and an array updates where updates[i] = [startIdxi, endIdxi, inci].
     *
     * You have an array arr of length length with all zeros, and you have some operation to apply on arr. In the ith
     * operation, you should increment all the elements arr[startIdxi], arr[startIdxi + 1], ..., arr[endIdxi] by inci.
     *
     * Return arr after applying all the updates.
     *
     * Input: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
     * Output: [-2,0,3,5,3]
     *
     * Constraints:
     *
     * 1 <= length <= 10^5
     * 0 <= updates.length <= 10^4
     * 0 <= startIdxi <= endIdxi < length
     * -1000 <= inci <= 1000
     * @param length
     * @param updates
     * @return
     */
    // S1: burte-force
    // time = O(n * k), space = O(1)
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] res = new int[length];
        for (int[] update : updates) {
            int a = update[0], b = update[1];
            for (int i = a; i <= b; i++) res[i] += update[2];
        }
        return res;
    }

    // S2: range caching
    // time = O(n + k), space = O(1)
    public int[] getModifiedArray2(int length, int[][] updates) {
        int[] res = new int[length];
        for (int[] update : updates) {
            int start = update[0], end = update[1], val = update[2];
            res[start] += val;
            if (end < length - 1) res[end + 1] -= val;
        }

        int sum = 0;
        for (int i = 0 ; i < length; i++) {
            sum += res[i];
            res[i] = sum;
        }
        return res;
    }

    // S3: Diff Array
    // time = O(n), space = O(n)
    public int[] getModifiedArray3(int length, int[][] updates) {
        int[] diff = new int[length + 1];
        for (int[] x : updates) {
            diff[x[0]] += x[2];
            diff[x[1] + 1] -= x[2];
        }

        int val = 0;
        int[] res = new int[length];
        for (int i = 0; i < length; i++) {
            val += diff[i];
            res[i] = val;
        }
        return res;
    }

    // S4: Segment Tree
    // time = O(nlogn), space = O(n)
    public int[] getModifiedArray4(int length, int[][] updates) {
        SegTreeNode root = new SegTreeNode(0, length - 1);
        init(root, 0, length - 1);

        for (int[] update : updates) {
            updateRange(root, update[0], update[1], update[2]);
        }

        int[] res = new int[length];
        for (int i = 0; i < length; i++) {
//            res[i] = queryRange(root, i, i);
            res[i] = querySingle(root, i);
        }
        return res;
    }

    private class SegTreeNode {
        private SegTreeNode left, right;
        int start, end;
        int info, tag;
        public SegTreeNode(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private void init(SegTreeNode node, int a, int b) {
        // base case -> single point
        if (a == b) {
            node.info = 0; // 单点值初始化为0
            return;
        }

        int mid = (a + b) / 2;
        if (node.left == null) {
            node.left = new SegTreeNode(a, mid);
            node.right = new SegTreeNode(mid + 1, b);
        }
        init(node.left, a, mid);
        init(node.right, mid + 1, b);
        // info
        node.info = 0;
    }

    private void updateRange(SegTreeNode node, int a, int b, int val) {
        if (b < node.start || a > node.end) return;
        if (a <= node.start && node.end <= b) {
            node.info += val * (node.end - node.start + 1);
            node.tag += val; // 表示从这个结点往后，每个叶子节点都要increase by val
            return;
        }

        // recursion
        pushdown(node); // erase lazy tag and propagate down
        updateRange(node.left, a, b, val);
        updateRange(node.right, a, b, val);
        node.info = node.left.info + node.right.info; // node.info需要更新，因为左右子树的info可能更新了
    }

    private void pushdown(SegTreeNode node) {
        if (node.tag != 0) {
            node.left.tag += node.tag; // tag表示在我这个区间里，每个叶子节点都要增加的量
            node.right.tag += node.tag;
            node.left.info += node.tag * (node.left.end - node.left.start + 1); // update info
            node.right.info += node.tag * (node.right.end - node.right.start + 1);
            node.tag = 0; // node本身tag消除掉
        }
    }

    private int queryRange(SegTreeNode node, int a, int b) {
        if (b < node.start || a > node.end) return 0;
        if (a <= node.start && node.end <= b) return node.info;
        // recursion
        pushdown(node); // 一旦下放，必须要pushdown！
        return queryRange(node.left, a, b) + queryRange(node.right, a, b);
    }

    private int querySingle(SegTreeNode node, int id) {
        if (id < node.start || id > node.end) return Integer.MAX_VALUE;
        if (node.start == id && node.end == id) return node.info;

        pushdown(node);
        int a = querySingle(node.left, id);
        int b = querySingle(node.right, id);
        if (a != Integer.MAX_VALUE) return a;
        if (b != Integer.MAX_VALUE) return b;
        return Integer.MAX_VALUE;
    }
}
/**
 * 常规做法：差分数组
 * Segment Tree: LazyTag
 * [3,7] increase by 2
 *      3 4 5 6 7 8 9 10
 *      x x x x x x x x x x
 * diff +2       -2
 *         -1        +1
 *    0 2 2 1 1 1-1-1 0 0 0
 * [s,e] increase by d
 * diff[s] += d
 * diff[e+1] -= d
 * Ref: LC307
 * updateSingle()
 * updateRange()
 * querySingle()
 * update[2,7]
 * [4,7]是[2.7]的真子集，完全被包括
 * 但只有当所有query都结束了，我才会去看叶子结点真正变成什么样
 * 之前所有update过程中根本不用看 => + lazy tag
 * 只有真正去考察下面结点的时候，才会去更新
 */

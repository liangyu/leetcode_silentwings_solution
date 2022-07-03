package LC601_900;
import java.util.*;
public class LC699_FallingSquares {
    /**
     * There are several squares being dropped onto the X-axis of a 2D plane.
     *
     * You are given a 2D integer array positions where positions[i] = [lefti, sideLengthi] represents the ith square
     * with a side length of sideLengthi that is dropped with its left edge aligned with X-coordinate lefti.
     *
     * Each square is dropped one at a time from a height above any landed squares. It then falls downward
     * (negative Y direction) until it either lands on the top side of another square or on the X-axis.
     * A square brushing the left/right side of another square does not count as landing on it. Once it lands,
     * it freezes in place and cannot be moved.
     *
     * After each square is dropped, you must record the height of the current tallest stack of squares.
     *
     * Return an integer array ans where ans[i] represents the height described above after dropping the ith square.
     *
     * Input: positions = [[1,2],[2,3],[6,1]]
     * Output: [2,5,5]
     *
     * Constraints:
     *
     * 1 <= positions.length <= 1000
     * 1 <= lefti <= 10^8
     * 1 <= sideLengthi <= 10^6
     * @param positions
     * @return
     */
    // time = O(nlogn), space = O(n)
    public List<Integer> fallingSquares(int[][] positions) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int[] rect : positions) {
            set.add(rect[0]);
            set.add(rect[0] + rect[1]);
        }
        int idx = 0;
        HashMap<Integer, Integer> pos2idx = new HashMap<>();
        for (int x : set) {
            pos2idx.put(x, idx);
            idx++;
        }
        int n = pos2idx.size();

        SegTreeNode root = new SegTreeNode(0, n - 1);
        init(root, 0, n - 1);

        int maxH = 0;
        List<Integer> res = new ArrayList<>();
        for (int[] rect : positions) {
            int a = pos2idx.get(rect[0]);
            int b = pos2idx.get(rect[0] + rect[1]);
            int h = queryRange(root, a, b - 1); // [ ) 左闭右开区间
            updateRange(root, a, b - 1, h + rect[1]);
            maxH = Math.max(maxH, h + rect[1]);
            res.add(maxH);
        }
        return res;
    }

    private class SegTreeNode {
        private SegTreeNode left, right;
        int start, end;
        int info; // the max height of the range
        boolean tag;
        public SegTreeNode(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private void init(SegTreeNode node, int a, int b) {
        // base case -> single point
        if (a == b) {
            node.info = 0;
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
        if (b < node.start || a > node.end) return; // no intersection
        if (a <= node.start && node.end <= b) {
            node.info = val;
            node.tag = true; // 做个lazyTag
            return;
        }

        // recursion
        pushdown(node); // erase lazy tag and propagate down
        updateRange(node.left, a, b, val);
        updateRange(node.right, a, b, val);
        // update info
        node.info = Math.max(node.left.info, node.right.info);
    }

    private void pushdown(SegTreeNode node) {
        if (node.tag) {
            node.left.info = node.info;
            node.right.info = node.info;
            node.left.tag = true;
            node.right.tag = true;
            node.tag = false;
        }
    }

    private int queryRange(SegTreeNode node, int a, int b) { // 一个区间上的最大值
        if (b < node.start || a > node.end) return 0;
        if (a <= node.start && node.end <= b) return node.info;
        // recursion
        pushdown(node); // 一旦下放，必须要pushdown！
        node.info = Math.max(queryRange(node.left, a, b) , queryRange(node.right, a, b));
        return node.info;
    }

    // S2:
    // time = O(n^2), space = O(n)
    public List<Integer> fallingSquares2(int[][] pos) {
        List<Integer> res = new ArrayList<>();
        int n = pos.length;
        int[] h = new int[n];
        for (int i = 0; i < n; i++) {
            int a = pos[i][0], b = pos[i][1];
            h[i] += b;
            for (int j = i + 1; j < n; j++) {
                int c = pos[j][0], d = pos[j][1];
                if (isIntersect(a, a + b, c, c + d)) h[j] = Math.max(h[j], h[i]);
            }
        }
        int cur = 0;
        for (int x : h) {
            cur = Math.max(cur, x);
            res.add(cur);
        }
        return res;
    }

    private boolean isIntersect(int l1, int r1, int l2, int r2) {
        return r2 > l1 && l2 < r1;
    }
}
/**
 * 共同点，叶子结点一开始都是知道的，整体什么样是可以提前建立起来的，而LC715形状一直在变，容易TLE，效率不高，需要动态开辟空间。
 * h = queryRange(a, b)
 * updateRange(a, b, h + d)
 * 线段树来做比较直观，无脑套模板
 * 用一个左闭右开区间来表示区间的高度
 * 不管是update还是query,一旦你要越过这个node到达下面去的时候，特别要小心
 * node本身可能截留住一些信息，如果是被lazyTag过，它下面的left, right信息都是不准确的
 * 所以在访问前要把lazyTag清理掉，同时把一些信息提前下放下去
 *
 * 本质上2个操作：
 * 1. 求区间最大值
 * 2. 将[l,r]变为c
 * => 经典线段树的问题
 * 区间查询，区间修改 => 懒标记
 * 10^8 => 需要离散化，三个点 => 左右端点+中点
 */
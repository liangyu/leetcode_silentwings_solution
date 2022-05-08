package LC601_900;

public class LC715_RangeModule {
    /**
     * A Range Module is a module that tracks ranges of numbers. Design a data structure to track the ranges represented
     * as half-open intervals and query about them.
     *
     * A half-open interval [left, right) denotes all the real numbers x where left <= x < right.
     *
     * Implement the RangeModule class:
     *
     * RangeModule() Initializes the object of the data structure.
     * void addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that
     * interval. Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the
     * interval [left, right) that are not already tracked.
     * boolean queryRange(int left, int right) Returns true if every real number in the interval [left, right) is
     * currently being tracked, and false otherwise.
     * void removeRange(int left, int right) Stops tracking every real number currently being tracked in the half-open
     * interval [left, right).
     *
     * Input
     * ["RangeModule", "addRange", "removeRange", "queryRange", "queryRange", "queryRange"]
     * [[], [10, 20], [14, 16], [10, 14], [13, 15], [16, 17]]
     * Output
     * [null, null, null, true, false, true]
     *
     * Constraints:
     *
     * 1 <= left < right <= 10^9
     * At most 10^4 calls will be made to addRange, queryRange, and removeRange.
     */
    // time = O(logn), space = O(n)
    private SegTreeNode root;
    public LC715_RangeModule() {
        root = new SegTreeNode(0, (int) 1e9, false);
    }

    public void addRange(int left, int right) {
        setStatus(root, left, right - 1, true);
    }

    public boolean queryRange(int left, int right) {
        return getStatus(root, left, right - 1);
    }

    public void removeRange(int left, int right) {
        setStatus(root, left, right - 1, false);
    }

    private class SegTreeNode {
        private int start, end;
        private SegTreeNode left, right;
        private boolean status;
        public SegTreeNode(int start, int end, boolean status) {
            this.start = start;
            this.end = end;
            this.status = status;
        }
    }

    private void setStatus(SegTreeNode node, int a, int b, boolean status) {
        if (node.start == a && node.end == b) { // single point
            node.status = status;
            node.left = null;
            node.right = null;
            return;
        }

        int mid = node.start + (node.end - node.start) / 2;

        // if left and right subtree doesn't exist
        if (node.left == null) {
            node.left = new SegTreeNode(node.start, mid, node.status);
            node.right = new SegTreeNode(mid + 1, node.end, node.status);
        }

        if (a <= mid) setStatus(node.left, a, Math.min(b, mid), status);
        if (b >= mid + 1) setStatus(node.right, Math.max(a, mid + 1), b, status);
        node.status = node.left.status && node.right.status;
    }

    private boolean getStatus(SegTreeNode node, int a, int b) {
        // not splits in between
        if (node.left == null) return node.status;
        if (node.start == a && node.end == b) return node.status;

        int mid = node.start + (node.end - node.start) / 2;
        boolean L = true, R = true;
        if (a <= mid) L = getStatus(node.left, a, Math.min(b, mid));
        if (b >= mid + 1) R = getStatus(node.right, Math.max(mid + 1, a), b);
        return L && R;
    }
}
/**
 * 线段树最大的好处，
 * [3,5] -> 二分 => {3} {[4,5]}
 */

package LC601_900;
import com.sun.source.tree.Tree;

import java.util.*;
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
    // S1: Segment Tree
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

    // S2: TreeMap
    // time = O(logn), space = O(n)
    class RangeModule {
        TreeMap<Integer, Integer> map; // {left -> right}
        public RangeModule() {
            map = new TreeMap<>();
        }

        public void addRange(int left, int right) {
            Integer start = map.floorKey(left); // 找比left还靠左露头的区间
            if (start == null) start = map.ceilingKey(left); // 如果没有左边露头的区间，那么找>= left的第一个区间
            // 寻找所有[left, right)里的原有区间,注意当有区间刚好是right作为start的时候，可以和[left, right)合并成大区间！！！
            while (start != null && start <= right) {
                int end = map.get(start);
                if (end >= left) { // [start, end)与[left, right)有overlap或者可以合并
                    map.remove(start); // delete [start, end) interval and merge with [left, start)
                    if (start < left) left = start;
                    if (end > right) right = end;
                }
                start = map.ceilingKey(end); // 因为end是个开区间端点，所以下一个interval可以从>= end的位置开始！
            }
            map.put(left, right); // 加入[left, right)
        }

        public boolean queryRange(int left, int right) {
            Integer fk = map.floorKey(left);
            return fk != null && map.get(fk) >= right;
        }

        public void removeRange(int left, int right) {
            Integer start = map.floorKey(left);
            if (start == null) start = map.ceilingKey(left);
            while (start != null && start < right) {  // delete all intervals within
                int end = map.get(start);
                if (end >= left) {
                    map.remove(start);
                    if (start < left) map.put(start, left); // 分割出左半边
                    if (end > right) map.put(right, end);
                }
                start = map.ceilingKey(end);
            }
        }
    }
}
/**
 * 线段树最大的好处，
 * [3,5] -> 二分 => {3} {[4,5]}
 * 线段树并不推荐，因为这道题的难点在于所有这些问题都是动态的，你事先并不知道总共会有多少个点，有多少个区间
 * 一旦线段树开辟好了，每个结点上的值，性质做些改变，线段树大小本身有变化，需要一个高级的支持动态开点和删点的模板。
 * map<int, int> -> TreeMap
 *    head  tail
 */

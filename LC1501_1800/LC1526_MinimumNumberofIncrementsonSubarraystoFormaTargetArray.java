package LC1501_1800;

public class LC1526_MinimumNumberofIncrementsonSubarraystoFormaTargetArray {
    /**
     * Given an array of positive integers target and an array initial of same size with all zeros.
     *
     * Return the minimum number of operations to form a target array from initial if you are allowed to do the
     * following operation:
     *
     * Choose any subarray from initial and increment each value by one.
     * The answer is guaranteed to fit within the range of a 32-bit signed integer.
     *
     * Input: target = [1,2,3,2,1]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= target.length <= 10^5
     * 1 <= target[i] <= 10^5
     * @param target
     * @return
     */
    // S1: Segment Tree
    // time = O(nlogn), space = O(n)
    private int[] target;
    private SegTreeNode root;
    public int minNumberOperations(int[] target) {
        int n = target.length;
        this.target = target;
        root = new SegTreeNode(0, n - 1);
        init(root, 0, n - 1);
        return dfs(0, n - 1, 0); // 在[0,n-1]里base是0
    }

    private int dfs(int a, int b, int base) {
        // base case
        if (a > b) return 0;
        if (a == b) { // single point
            return target[a] - base; // 涨的幅度
        }
        // find min val
        int[] cur = queryRangeMin(root, a, b);
        int val = cur[0], pos = cur[1];
        int sum = val - base;
        sum += dfs(a, pos - 1, val); // base增长到val
        sum += dfs(pos + 1, b, val);
        return sum;
    }

    private class SegTreeNode {
        private SegTreeNode left, right;
        int start, end;
        int val, pos; // val -> min, pos -> idx
        public SegTreeNode(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private void init(SegTreeNode node, int a, int b) {
        // base case -> single point
        if (a == b) {
            node.val = target[a];
            node.pos = a;
            return;
        }

        int mid = (a + b) / 2;
        if (node.left == null) {
            node.left = new SegTreeNode(a, mid);
            node.right = new SegTreeNode(mid + 1, b);
        }
        init(node.left, a, mid);
        init(node.right, mid + 1, b);
        // info -> 后序遍历
        if (node.left.val < node.right.val) {
            node.val = node.left.val;
            node.pos = node.left.pos;
        } else {
            node.val = node.right.val;
            node.pos = node.right.pos;
        }
    }

    // 本题不需要单点更新，不需要对一棵建成的树做任何改动

    private int[] queryRangeMin(SegTreeNode node, int a, int b) {
        if (b < node.start || a > node.end) return new int[]{Integer.MAX_VALUE, 0}; // 完全不相交
        if (a <= node.start && node.end <= b) return new int[]{node.val, node.pos};
        // recursion
        int[] L = queryRangeMin(node.left, a, b);
        int[] R = queryRangeMin(node.right, a, b);
        if (L[0] < R[0]) return L;
        else return R;
    }

    // S2: Greedy
    // time = O(n), space = O(1)
    public int minNumberOperations2(int[] target) {
        int curHeight = 0, res = 0;
        for (int i = 0; i < target.length; i++) {
            if (target[i] > curHeight) {
                res += target[i] - curHeight;
            }
            curHeight = target[i]; // 下坡不用更新res,但也要更新curHight
        }
        return res;
    }
}
/**
 * S1: Segment Tree
 * 尽量减少次数，选择一个较大的区间，把整个区间+1，直到到达目标数组中的最小值
 * 然后递归处理
 * 递归思想最重要的一个实现：找最小点
 * BIT没法做，因为要求一个区间里的最小值 => segment tree
 * query任何一个区间，都可以从大区间里往下走
 *
 * S2: Greedy
 * 肯定有个subarray带着+1
 * 1 3 5 7 6
 * 1 [0 ...]
 * 2 [0...]
 * 3 [0...]
 * 4 [0...]
 * 5 [0...]
 * 6 [0...4]
 * 7 [0]
 * 每次下坡的任何点对应着上坡的某个区间，对于这些区间都不用处理了
 * 只要处理上坡的时候做了多少次increase by 1
 * 上坡走的每步都为下坡做了准备 => 所有的下坡都不用管
 * 只要找到每个上坡，看提升多少即可
 */
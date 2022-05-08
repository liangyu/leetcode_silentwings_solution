package LC301_600;
import java.util.*;
public class LC307_RangeSumQueryMutable {
    /**
     * Given an integer array nums, handle multiple queries of the following types:
     *
     * Update the value of an element in nums.
     * Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
     * Implement the NumArray class:
     *
     * NumArray(int[] nums) Initializes the object with the integer array nums.
     * void update(int index, int val) Updates the value of nums[index] to be val.
     * int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right
     * inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
     *
     * Input
     * ["NumArray", "sumRange", "update", "sumRange"]
     * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
     * Output
     * [null, 9, null, 8]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 10^4
     * -100 <= nums[i] <= 100
     * 0 <= index < nums.length
     * -100 <= val <= 100
     * 0 <= left <= right < nums.length
     * At most 3 * 10^4 calls will be made to update and sumRange.
     * @param nums
     */
    // S1: Segment Tree
    // time = O(logn), space = O(n)
    private SegTreeNode root;
    private int[] nums;
    public LC307_RangeSumQueryMutable(int[] nums) {
        int n = nums.length;
        this.nums = nums;
        root = new SegTreeNode(0, n - 1);
        init(root, 0, n - 1);
    }

    public void update(int index, int val) {
        updateSingle(root, index, val);
    }

    public int sumRange(int left, int right) {
        return queryRange(root, left, right);
    }

    private class SegTreeNode {
        private SegTreeNode left, right;
        int start, end;
        int info; // 区间和
        public SegTreeNode(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private void init(SegTreeNode node, int a, int b) { // 满二叉树
        // base case -> single point
        if (a == b) {
            node.info = nums[a];
            return;
        }

        int mid = (a + b) / 2;
        if (node.left == null) { // 要开一起开，不会出现某个结点只有单个子节点的情况，这里不需要，但留着做模板！
            node.left = new SegTreeNode(a, mid);
            node.right = new SegTreeNode(mid + 1, b);
        }
        init(node.left, a, mid);
        init(node.right, mid + 1, b);
        // info
        node.info = node.left.info + node.right.info;
    }

    private void updateSingle(SegTreeNode node, int idx, int val) { // 单点更新
        if (idx < node.start || idx > node.end) return; // 不在区间内，直接return
        if (node.start == node.end) { // 本身就是要找的单点
            node.info = val;
            return;
        }
        // 拆分 -> recursion
        updateSingle(node.left, idx, val);
        updateSingle(node.right, idx, val);
        // update info
        node.info = node.left.info + node.right.info;
    }

    private int queryRange(SegTreeNode node, int a, int b) { // 本题信息就是区间和
        if (b < node.start || a > node.end) return 0;
        if (a <= node.start && node.end <= b) return node.info; // 结点本身是query的一个子集的话，直接返回info
        // recursion
        return queryRange(node.left, a, b) + queryRange(node.right, a, b);
    }

    // S2: BIT
    // time = O(nlogn), space = O(n)
    class NumArray {
        BIT bit;
        int[] nums;
        public NumArray(int[] nums) {
            this.nums = nums;
            int n = nums.length;
            bit = new BIT(n);

            for (int i = 0; i < n; i++) bit.update(i + 1, nums[i]);
        }

        public void update(int index, int val) {
            int delta = val - nums[index];
            bit.update(index + 1, delta);
            nums[index] = val;
        }

        public int sumRange(int left, int right) {
            return bit.sumRange(left + 1, right + 1);
        }

        private class BIT {
            int n;
            int[] bitree;
            public BIT(int n) {
                this.n = n;
                this.bitree = new int[n + 1];
            }

            private void update(int x, int delta) {
                for (int i = x; i <= n; i += i & (-i)) {
                    bitree[i] += delta;
                }
            }

            private int query(int x) {
                int res = 0;
                for (int i = x; i > 0; i -= i & (-i)) {
                    res += bitree[i];
                }
                return res;
            }

            private int sumRange(int i, int j) {
                return query(j) - query(i - 1);
            }
        }
    }
}
/**
 * Segment Tree
 * query是动态的，支持单点修改
 * 线段树：本质就是二叉树，每个节点代表一段区间，从根节点开始代表一个最大的区间
 *                        [0,9]
 *            [0,4]                    [5,9]
 *       [0,2]    [3,4]          [5,7]      [8,9]
 *    [0,1] [2,2] [3,3] [4,4] [5,6][7,7] [8,8] [9,9]
 * [0,0] [1,1]              [5,5] [6,6]
 *
 * 如果区间和都知道，这样求某段区间，比如[5,7]，不需要加起来，而是已知的，直接读节点数就可以了。
 * 如果[4,9]区间和怎么算？=> 拆分[4,4] + [5,9] -> 只要求单点4的和
 * 最大特点：不需要O(n) -> only O(logn)
 * 最多logn层，每层最多只会query 2个，只会一分二 => 2*logn 有其独特的优势
 * 还支持单点修改 -> 不是O(1),也是O(logn)
 * singlePointUpdate: log(n)
 * queryRange: log(n)
 * 基础版本：模板题
 * 区间更新：lazy tag
 * BIT 比较局限，segment tree威力更大一些
 * 把模板存下来，以后直接修改模板即可 -> 换一下info，比如区间最大值，基本框架不变
 */

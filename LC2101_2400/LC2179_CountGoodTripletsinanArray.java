package LC2101_2400;
import java.util.*;
public class LC2179_CountGoodTripletsinanArray {
    /**
     * You are given two 0-indexed arrays nums1 and nums2 of length n, both of which are permutations of [0, 1, ..., n - 1].
     *
     * A good triplet is a set of 3 distinct values which are present in increasing order by position both in nums1 and
     * nums2. In other words, if we consider pos1v as the index of the value v in nums1 and pos2v as the index of the
     * value v in nums2, then a good triplet will be a set (x, y, z) where 0 <= x, y, z <= n - 1, such that
     * pos1x < pos1y < pos1z and pos2x < pos2y < pos2z.
     *
     * Return the total number of good triplets.
     *
     * Input: nums1 = [2,0,1,3], nums2 = [0,1,2,3]
     * Output: 1
     *
     * Input: nums1 = [4,0,1,3,2], nums2 = [4,1,0,2,3]
     * Output: 4
     *
     * Constraints:
     *
     * n == nums1.length == nums2.length
     * 3 <= n <= 10^5
     * 0 <= nums1[i], nums2[i] <= n - 1
     * nums1 and nums2 are permutations of [0, 1, ..., n - 1].
     * @param nums1
     * @param nums2
     * @return
     */
    // S1: BIT
    // time = O(nlogn), space = O(n)
    int[] bitree;
    int n;
    public long goodTriplets(int[] nums1, int[] nums2) {
        this.n = nums1.length;
        this.bitree = new int[n + 1];
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[nums1[i]] = i;

        for (int i = 0; i < n; i++) nums2[i] = arr[nums2[i]];

        // find all triplet increasing pairs
        long res = 0;
        for (int i = 0; i < n; i++) {
            int ls = query(nums2[i]);
            int lb = i - ls;
            int rb = (n - 1 - nums2[i]) - lb;
            add(nums2[i], 1);
            res += (long) ls * rb;
        }
        return res;
    }

    private int lowbit(int x) {
        return x & -x;
    }

    private void add(int x, int val) {
        x++;
        for (int i = x; i <= n; i += lowbit(i)) bitree[i] += val;
    }

    private int query(int x) {
        x++;
        int res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) res += bitree[i];
        return res;
    }

    // S2: BIT
    public long goodTriplets2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.put(nums1[i], i);

        for (int i = 0; i < n; i++) {
            nums2[i] = map.get(nums2[i]) + 1; // 1-index
        }

        long[] smallerBefore = new long[n];
        long[] largerAfter = new long[n];

        BIT bit1 = new BIT(100005); // 按照val来开
        for (int i = 0; i < n; i++) {
            smallerBefore[i] = bit1.sumRange(1, nums2[i]);
            bit1.updateDelta(nums2[i], 1);
        }


        BIT bit2 = new BIT(100005); // 按照val来开
        for (int i = n - 1; i >= 0; i--) {
            largerAfter[i] = bit2.sumRange(nums2[i], n);
            bit2.updateDelta(nums2[i], 1);
        }

        long res = 0;
        for (int j = 0; j < n; j++) {
            res += (long) smallerBefore[j] * (long) largerAfter[j];
        }
        return res;
    }

    private class BIT {
        int n;
        long[] bitArr; // Note: all arrays are 1-index
        long[] nums;
        long M = (long)(1e9 + 7);

        public BIT(int n) {
            this.n = n;
            bitArr = new long[n + 1];
            nums = new long[n + 1];
        }

        // increase nums[i] by delta  (1-index)
        private void updateDelta(int i, long delta) {
            int idx = i;
            while (idx <= n) {
                bitArr[idx] += delta;
                bitArr[idx] %= M;
                idx += idx & (-idx);
            }
        }

        // sum of a range nums[1:j] inclusively, 1-index
        private long queryPreSum(int idx) {
            long res = 0;
            while (idx > 0) {
                res += bitArr[idx];
                res %= M;
                idx -= idx & (-idx);
            }
            return res;
        }

        // sum of a range nums[i:j] inclusively
        private long sumRange(int i, int j) {
            return queryPreSum(j) - queryPreSum(i - 1);
        }
    }
}
/**
 * 固定x，遍历y
 * (x,y,z) -> 遍历中间一个
 * X X X i [X X X X X X X]
 * X X X X X X j [X X X X]
 * nums1[i] = nums2[j] = y
 * 看有多少个数在i后面出现过，也在j后面出现过
 * 除了暴力之外，似乎没有更好的办法
 * 所有元素互不相同
 * 忽略了数值的特性
 * nums1: 0 i [2 3 4 5 6 7 8 9]
 * nums2: 3 4 5 1 [2 7 8 9 6 0]
 * countLargerElementAfterSelf[j]
 * counterSmallerElementsBeforeSelf[j]
 * res = sum{counterSmallerElementsBeforeSelf[j] * countLargerElementAfterSelf[j]}
 * 遍历下中间这个pivot
 * 树状数组：3 <= n <= 10^5
 * 想象成basket
 * 类似桶排序
 * use BIT -> 模板题，区间和
 * 所有查询，区间求和都是O(logn)
 */
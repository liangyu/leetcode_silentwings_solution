package LC301_600;
import java.util.*;
public class LC493_ReversePairs {
    /**
     * Given an integer array nums, return the number of reverse pairs in the array.
     *
     * A reverse pair is a pair (i, j) where 0 <= i < j < nums.length and nums[i] > 2 * nums[j].
     *
     * Input: nums = [1,3,2,3,1]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     * @param nums
     * @return
     */
    // S1: merge sort + bs
    // time = O(nlogn * logn), space = O(n)
    private int[] sorted;
    private int res;
    public int reversePairs(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        sorted = nums.clone();
        res = 0;

        helper(nums, 0, nums.length - 1);
        return res;
    }

    private void helper(int[] nums, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        helper(nums, left, mid);
        helper(nums, mid + 1, right);

        for (int j = mid + 1; j <= right; j++) {
            int idx = upperBound(sorted, left, mid, 2 * (long)nums[j]);
            res += mid - idx + 1;
        }

//        Arrays.sort(sorted, left, right + 1);
        int i = left, j = mid + 1, p = 0;
        int[] temp = new int[right - left + 1];
        while (i <= mid && j <= right) {
            if (sorted[i] < sorted[j]) temp[p++] = sorted[i++];
            else temp[p++] = sorted[j++];
        }
        while (i <= mid) temp[p++] = sorted[i++];
        while (j <= right) temp[p++] = sorted[j++];

        for (i = 0; i < temp.length; i++) {
            sorted[i + left] = temp[i];
        }
    }

    private int upperBound(int[] sorted, int left, int right, long target) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (sorted[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return sorted[left] > target ? left : left + 1;
    }

    // S2: divide conquer + merge sort
    // time = O(nlogn), space = O(n)
    private int ret;
    public int reversePairs2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        sorted = nums.clone();
        ret = 0;

        partition(nums, 0, nums.length - 1);
        return ret;
    }

    private void partition(int[] nums, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        partition(nums, left, mid);
        partition(nums, mid + 1, right);

        int i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (2 * (long)nums[j] < nums[i]) {
                ret += mid - i + 1;
                j++;
            } else i++;
        }
        i = left;
        j = mid + 1;
        int p = 0;
        int[] temp = new int[right - left + 1];

        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) temp[p++] = nums[i++];
            else temp[p++] = nums[j++];
        }
        while (i <= mid) temp[p++] = nums[i++];
        while (j <= right) temp[p++] = nums[j++];

        for (i = 0; i < temp.length; i++) {
            nums[i + left] = temp[i];
        }
    }

    // S3: BIT
    // time = O(nlogn), space = O(n)
    public int reversePairs3(int[] nums) {
        TreeSet<Long> set = new TreeSet<>();
        for (int x : nums) {
            set.add((long) x);
            set.add((long) x * 2);
        }

        HashMap<Long, Integer> map = new HashMap<>();
        for (long x : set) map.put(x, map.size());

        int res = 0, n = nums.length;
        BIT bit = new BIT(map.size());
        for (int i = 0; i < n; i++) {
            int left = map.get((long) nums[i] * 2) + 1, right = map.size() - 1;
            res += bit.sumRange(left + 1, right + 1);
            bit.update(map.get((long) nums[i]) + 1, 1);
        }
        return res;
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
/**
 * A：[y y y y y z z z z]
 * B: [y y y y y] C: [z z z z z] + ...
 * 分治法和归并排序在一起用 => 返回的时候排个序
 * 变有序有什么帮助：用二分法(logn)找出逆序对，方便我们在做additional work时就变得非常高效
 * 拆分最多是logn层
 *
 * i,j都在左边 -> 递归
 * i,j都在右边 -> 递归
 * i在左，j在右
 */


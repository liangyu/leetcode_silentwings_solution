package LC001_300;
import java.util.*;
public class LC215_KthLargestElementinanArray {
    /**
     * Given an integer array nums and an integer k, return the kth largest element in the array.
     *
     * Note that it is the kth largest element in the sorted order, not the kth distinct element.
     *
     * Input: nums = [3,2,1,5,6,4], k = 2
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 10^4
     * -10^4 <= nums[i] <= 10^4
     * @param nums
     * @param k
     * @return
     */
    // S1: minHeap
    // time = O(nlogk), space = O(k)
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) minHeap.poll();
        }
        return minHeap.poll();
    }

    // S2: quick select
    // time = O(n) on average, O(n^2) worst case, space = O(1)
    public int findKthLargest2(int[] nums, int k) {
        int n = nums.length;
        quickSelect(nums, 0, n - 1, k);
        return nums[n - k];
    }

    private void quickSelect(int[] nums, int left, int right, int k) {
        int n = nums.length;
        // base case
        if (left == right) return;

        int pivot = partition(nums, left, right);
        if (pivot == n - k) return;
        if (pivot < n - k) quickSelect(nums, pivot + 1, right, k);
        else quickSelect(nums, left, pivot - 1, k);
    }

    private int partition(int[] nums, int left, int right) {
        Random random = new Random();
        int pivot = random.nextInt(right - left + 1) + left;
        swap(nums, right, pivot);
        int l = left, r = right - 1;

        while (l <= r) {
            if (nums[l] > nums[right] && nums[r] <= nums[right]) swap(nums, l++, r--);
            if (nums[l] <= nums[right]) l++;
            if (nums[r] > nums[right]) r--;
        }
        swap(nums, l, right);
        return l;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // S2.1: Quick Select
    // time = O(n) on average, O(n^2) worst case, space = O(1)
    public int findKthLargest21(int[] nums, int k) {
        return quickselect(nums, 0, nums.length - 1, k);
    }

    private int quickselect(int[] nums, int a, int b, int k) {
        int pivot = nums[a + (b - a) / 2];
        int i = a, j = b, t = a;
        while (t <= j) {
            if (nums[t] < pivot) {
                swap(nums, i, t);
                i++;
                t++;
            } else if (nums[t] > pivot) {
                swap(nums, t, j);
                j--;
            } else t++;
        }

        if (b - j >= k) return quickselect(nums, j + 1, b, k);
        if (b - i + 1 >= k) return pivot;
        return quickselect(nums, a, i - 1, k - (b - i + 1));
    }

    // S3: quick select
    // time = O(n * logC), space = O(1)
    public int findKthLargest3(int[] nums, int k) {
        int left = Integer.MIN_VALUE / 2, right = Integer.MAX_VALUE / 2;
        while (left < right) { // left = 0, right = 1  最多分32次，就一定能找到任何整数 => logC
            int mid = right - (right - left) / 2;
            if (count(nums, mid) >= k) left = mid; // mid might be the answer
            else right = mid - 1;
        }
        return left;
    }

    private int count(int[] nums, int t) { // O(n)
        int res = 0;
        for (int x : nums) {
            if (x >= t) res++;
        }
        return res;
    }
}
/**
 * 1. sort the whole array: O(nlogn)
 * 2. PQ: rolling k max elements: O(nlogk)
 * 3. binary search by value
 * guess t:
 * if countNumber(>=t) >= k => adjust bigger
 *                     < k  => adjust smaller
 * 一定有解 => 收敛解就是最终解
 * 我怎么保证收敛解一定是数组里的元素
 * binary search => the largest t  s.t. satisfy condition
 * array => the kth largest element is the largest t  s.t.  satisfy condition
 *
 * Quick Select: k-th element => O(n) on average
 *      pivot
 * x x x o x x x x x
 * s s s o o o l l l
 * ^ ^         ^
 * i t         j
 * 三指针 ref: LC75 sort colors
 * t之前就是small + equal, 而i之前都是small, i 始终指向的都是equal的元素, j 指向large前面的一个元素
 * 处理到t与j相遇为止
 * 作用是什么呢？看有多少个元素small, equal or large
 * s s s o o o l l l
 *  a     b      c
 * if (c >= k) => find k-th largest in [l]
 * else if (b + c >= k) => k-th largest is pivot
 * else => k-(b+c)th largest in [s]
 * recursion
 *
 */

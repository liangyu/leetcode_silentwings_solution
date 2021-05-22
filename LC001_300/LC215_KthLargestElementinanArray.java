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
}

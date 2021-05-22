package LC301_600;
import java.util.*;
public class LC462_MinimumMovestoEqualArrayElementsII {
    /**
     * Given an integer array nums of size n, return the minimum number of moves required to make all array elements equal.
     *
     * In one move, you can increment or decrement an element of the array by 1.
     *
     * Input: nums = [1,2,3]
     * Output: 2
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: without finding median
    // time = O(nlogn), space = O(1)
    public int minMoves2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums);

        int left = 0, right = nums.length - 1;
        int res = 0;
        while (left < right) {
            res += nums[right--] - nums[left++];
        }
        return res;
    }

    // S2: quick sort
    // time = O(nlogn), space = O(1)
    public int minMoves22(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int res = 0;
        int median = findKthLargest(nums, 0, nums.length - 1, nums.length / 2);
        for (int num : nums) {
            res += Math.abs(num - median);
        }
        return res;
    }

    private int findKthLargest(int[] nums, int left, int right, int k) {
        if (left == right) return nums[left];

        int pos = partition(nums, left, right);
        if (pos == k) return nums[pos];
        if (pos < k) return findKthLargest(nums, pos + 1, right, k);
        else return findKthLargest(nums, left, pos - 1, k);
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int l = left, r = right - 1;
        while (l <= r) {
            if (nums[l] > pivot && nums[r] < pivot) swap(nums, l++, r--);
            if (nums[l] <= pivot) l++;
            if (nums[r] >= pivot) r--;
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
/**
 * ref: LC296
 */

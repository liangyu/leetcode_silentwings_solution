package LC001_300;
import java.util.*;
public class LC287_FindtheDuplicateNumber {
    /**
     * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
     *
     * There is only one repeated number in nums, return this repeated number.
     *
     * You must solve the problem without modifying the array nums and uses only constant extra space.
     *
     * Input: nums = [1,3,4,2,2]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= n <= 10^5
     * nums.length == n + 1
     * 1 <= nums[i] <= n
     * All the integers in nums appear only once except for precisely one integer which appears two or more times.
     *
     *
     * Follow up:
     *
     * How can we prove that at least one duplicate number must exist in nums?
     * Can you solve the problem in linear runtime complexity?
     * @param nums
     * @return
     */
    // S1: B.S.
    // time = O(nlogn), space = O(1)
    public int findDuplicate(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int left = 0, right = n - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int count = 0;
            for (int x : nums) {
                if (x <= mid) count++;
            }

            if (count > mid) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    // S2: Floyd's Tortoise and Hare (Cycle Detection)
    // time = O(n), space = O(1)
    public int findDuplicate2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int slow = nums[0], fast = nums[nums[0]];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        fast = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    // S3: indexing sort
    public int findDuplicate3(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) return nums[i];
        }
        return -1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
/**
 * indexing sort
 * ref: LC41, LC442, LC448
 * 可能repeating number不止repeating 1 次
 * B.S.
 * if the duplicated number = k => count(<= k) > k
 * 0 -> 1 -> 3 -> 2 -> 4 -> 2
 */

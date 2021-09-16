package LC601_900;
import java.util.*;
public class LC719_FindKthSmallestPairDistance {
    /**
     * The distance of a pair of integers a and b is defined as the absolute difference between a and b.
     *
     * Given an integer array nums and an integer k, return the kth smallest distance among all the pairs nums[i] and
     * nums[j] where 0 <= i < j < nums.length.
     *
     * Input: nums = [1,3,1], k = 1
     * Output: 0
     *
     * Constraints:
     *
     * n == nums.length
     * 2 <= n <= 10^4
     * 0 <= nums[i] <= 10^6
     * 1 <= k <= n * (n - 1) / 2
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int smallestDistancePair(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return 0;

        Arrays.sort(nums);

        int n = nums.length;
        int right = nums[n - 1] - nums[0];
        int left = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) left = Math.min(left, nums[i] - nums[i -1]);

        while (left < right) {
            int mid = left + (right - left) / 2;
            int count = 0;
            for (int i = 0; i < n; i++) {
                int idx = upperBound(nums, nums[i] + mid); // 1st larger than nums[i] + mid
                count += idx - i - 1; // pair -> idx - i - 1
            }
            if (count < k) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private int upperBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return nums[left] > target ? left : left + 1;
    }

    // S1.2: BS (最优解！！！)
    // time = O(nlogn), space = O(1)
    public int smallestDistancePair2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k < 0) return 0;

        Arrays.sort(nums);
        int n = nums.length;
        int left = 0, right = nums[n - 1] - nums[0];

        while (left < right) {
            int mid = left + (right - left) / 2;
            int j = 0, count = 0;
            for (int i = 0; i < n; i++) {
                while (j < n && nums[j] <= nums[i] + mid) j++;
                count += j - i - 1;
            }
            if (count < k) left = mid + 1;
            else right = mid;
        }
        return left;
    }
}
/**
 * 正常情况下没有比O(n^2)更优的
 * 10^4 => O(nlogn)
 * BS => search by value
 * 直接猜测第k小的val
 * 如何判断这个val是取大了还是取小了呢？
 * 如果取大了，所有pair里 <= val的大于k个
 * nlogn: how many pairs w/ distance < val
 * 1 2 3 4 5 6 7
 * 用二分最多猜32次
 */
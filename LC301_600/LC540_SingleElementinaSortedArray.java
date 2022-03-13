package LC301_600;

public class LC540_SingleElementinaSortedArray {
    /**
     * You are given a sorted array consisting of only integers where every element appears exactly twice, except for
     * one element which appears exactly once.
     *
     * Return the single element that appears only once.
     *
     * Your solution must run in O(log n) time and O(1) space.
     *
     * Input: nums = [1,1,2,3,3,4,4,8,8]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(logn), space = O(1)
    public int singleNonDuplicate(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (mid % 2 == 0) {
                if (nums[mid] == nums[mid - 1]) right = mid - 2;
                else left = mid;
            } else {
                if (nums[mid] == nums[mid + 1]) right = mid - 1;
                else left = mid + 1;
            }
        }
        return nums[left];
    }
}
/**
 * corner case:
 * nums只有1个元素，那么不进入while loop, return nums[0]
 * nums不能只有2个元素，不满足题意
 * nums至少有3个元素，那么掉入else，right = mid - 1之后就会出现left == right，没问题
 * nums有4个元素，依然可以正常进入else
 * nums有5个元素，mid = 2 -> right = mid - 2之后left == right，也没问题！
 */
package LC1801_2100;

public class LC1966_BinarySearchableNumbersinanUnsortedArray {
    /**
     * Consider a function that implements an algorithm similar to Binary Search. The function has two input parameters:
     * sequence is a sequence of integers, and target is an integer value. The purpose of the function is to find if
     * the target exists in the sequence.
     *
     * The pseudocode of the function is as follows:
     *
     * func(sequence, target)
     *   while sequence is not empty
     *     randomly choose an element from sequence as the pivot
     *     if pivot = target, return true
     *     else if pivot < target, remove pivot and all elements to its left from the sequence
     *     else, remove pivot and all elements to its right from the sequence
     *   end while
     *   return false
     * When the sequence is sorted, the function works correctly for all values. When the sequence is not sorted, the
     * function does not work for all values, but may still work for some values.
     *
     * Given an integer array nums, representing the sequence, that contains unique numbers and may or may not be
     * sorted, return the number of values that are guaranteed to be found using the function, for every possible
     * pivot selection.
     *
     * Input: nums = [7]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^5 <= nums[i] <= 10^5
     * All the values of nums are unique.
     *
     *
     * Follow-up: If nums has duplicates, would you modify your algorithm? If so, how?
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int binarySearchableNumbers(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return -1;

        int n = nums.length, res = 0;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[] maxLeft = new int[n];

        for (int i = 0; i < n; i++) {
            max = Math.max(max, nums[i]);
            maxLeft[i] = max;
        }

        for (int i = n - 1; i >= 0; i--) {
            min = Math.min(min, nums[i]);
            if (min == nums[i] && maxLeft[i] == nums[i]) res++;
        }
        return res;
    }
}
/**
 * For an element to be found, it should be greater that all elements before it, and smaller than all element after it.
 * We can first check whether each element is greater by going left-to-right and tracking the max_l element so far.
 * Then, we can go right-to-left, and check if each element is smaller of the min_r element so far.
 * If both the above conditions are true, the element can be found.
 */

package LC1501_1800;

public class LC1664_WaystoMakeaFairArray {
    /**
     * You are given an integer array nums. You can choose exactly one index (0-indexed) and remove the element. Notice
     * that the index of the elements may change after the removal.
     *
     * For example, if nums = [6,1,7,4,1]:
     *
     * Choosing to remove index 1 results in nums = [6,7,4,1].
     * Choosing to remove index 2 results in nums = [6,1,4,1].
     * Choosing to remove index 4 results in nums = [6,1,7,4].
     * An array is fair if the sum of the odd-indexed values equals the sum of the even-indexed values.
     *
     * Return the number of indices that you could choose such that after the removal, nums is fair.
     *
     * Input: nums = [2,1,6,4]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int waysToMakeFair(int[] nums) {
        int n = nums.length;
        int[] leftEven = new int[n], leftOdd = new int[n];
        int oddSum = 0, evenSum = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) evenSum += nums[i];
            else oddSum += nums[i];
            leftEven[i] = evenSum;
            leftOdd[i] = oddSum;
        }

        int rightEven = 0, rightOdd = 0, res = 0;
        for (int i = n - 1; i >= 0; i--) {
            if ((i > 0 ? leftEven[i - 1] : 0) + rightOdd == (i > 0 ? leftOdd[i - 1] : 0) + rightEven) res++;
            if (i % 2 == 0) rightEven += nums[i];
            else rightOdd += nums[i];
        }
        return res;
    }
}
/**
 * 去掉元素后，
 * 原来偶数位的变成奇数位，原来奇数位的变成偶数位
 * if we delete i-th element
 * leftEven[i-1] + rightOdd[i+1] == leftOdd[i-1] + rightEven[i+1]
 */
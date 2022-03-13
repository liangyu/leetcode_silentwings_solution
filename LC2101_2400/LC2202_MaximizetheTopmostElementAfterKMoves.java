package LC2101_2400;

public class LC2202_MaximizetheTopmostElementAfterKMoves {
    /**
     * You are given a 0-indexed integer array nums representing the contents of a pile, where nums[0] is the topmost
     * element of the pile.
     *
     * In one move, you can perform either of the following:
     *
     * If the pile is not empty, remove the topmost element of the pile.
     * If there are one or more removed elements, add any one of them back onto the pile. This element becomes the new
     * topmost element.
     * You are also given an integer k, which denotes the total number of moves to be made.
     *
     * Return the maximum value of the topmost element of the pile possible after exactly k moves. In case it is not
     * possible to obtain a non-empty pile after k moves, return -1.
     *
     * Input: nums = [5,2,2,4,0,6], k = 4
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i], k <= 10^9
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int maximumTop(int[] nums, int k) {
        int n = nums.length;
        if (n == 1 && k % 2 == 1) return -1;

        int max = -1;
        // 注意：这里是到k-1，留1次要么把max放到top，要么就把第k-1个移走使第k个成为top
        for (int i = 0; i < Math.min(k - 1, n); i++) {
            max = Math.max(max, nums[i]);
        }
        if (k < n) max = Math.max(max, nums[k]);
        return max;
    }
}

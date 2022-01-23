package LC2100_2400;

public class LC2134_MinimumSwapstoGroupAll1sTogetherII {
    /**
     * A swap is defined as taking two distinct positions in an array and swapping the values in them.
     *
     * A circular array is defined as an array where we consider the first element and the last element to be adjacent.
     *
     * Given a binary circular array nums, return the minimum number of swaps required to group all 1's present in the
     * array together at any location.
     *
     * Input: nums = [0,1,0,1,1,0,0]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * nums[i] is either 0 or 1.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int minSwaps(int[] nums) {
        int n = nums.length, ones = 0;
        for (int x : nums) {
            if (x == 1) ones++;
        }

        int res = Integer.MAX_VALUE, start = 0, end = 0, zeros = 0;
        while (end < n + ones) {
            if (nums[end % n] == 0) zeros++;
            if (end - start + 1 > ones) {
                if (nums[start] == 0) zeros--;
                start++;
            }
            if (end - start + 1 == ones) res = Math.min(res, zeros);
            end++;
        }
        return res;
    }
}

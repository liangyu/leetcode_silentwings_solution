package LC2101_2400;

public class LC2239_FindClosestNumbertoZero {
    /**
     * Given an integer array nums of size n, return the number with the value closest to 0 in nums. If there are
     * multiple answers, return the number with the largest value.
     *
     * Input: nums = [-4,-2,1,4,8]
     * Output: 1
     *
     * Input: nums = [2,-1,1]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= n <= 1000
     * -10^5 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int findClosestNumber(int[] nums) {
        int res = Integer.MAX_VALUE;
        for (int x : nums) {
            if (Math.abs(x) < Math.abs(res)) res = x;
            else if (Math.abs(x) == Math.abs(res)) {
                if (x > res) res = x;
            }
        }
        return res;
    }
}

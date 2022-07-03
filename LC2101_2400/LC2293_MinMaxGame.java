package LC2101_2400;

public class LC2293_MinMaxGame {
    /**
     * You are given a 0-indexed integer array nums whose length is a power of 2.
     *
     * Apply the following algorithm on nums:
     *
     * Let n be the length of nums. If n == 1, end the process. Otherwise, create a new 0-indexed integer array newNums
     * of length n / 2.
     * For every even index i where 0 <= i < n / 2, assign the value of newNums[i] as min(nums[2 * i], nums[2 * i + 1]).
     * For every odd index i where 0 <= i < n / 2, assign the value of newNums[i] as max(nums[2 * i], nums[2 * i + 1]).
     * Replace the array nums with newNums.
     * Repeat the entire process starting from step 1.
     * Return the last number that remains in nums after applying the algorithm.
     *
     * Input: nums = [1,3,5,2,4,8,2,2]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1024
     * 1 <= nums[i] <= 10^9
     * nums.length is a power of 2.
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int minMaxGame(int[] nums) {
        int n = nums.length;
        while (n > 1) {
            int idx = 0;
            for (int i = 0; i < n; i++) {
                if (i + 1 < n) nums[idx++] = Math.min(nums[i], nums[i + 1]);
                if (i + 3 < n) nums[idx++] = Math.max(nums[i + 2], nums[i + 3]);
                i += 3;
            }
            n /= 2;
        }
        return nums[0];
    }
}

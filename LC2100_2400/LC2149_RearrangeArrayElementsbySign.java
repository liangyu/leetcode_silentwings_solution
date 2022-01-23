package LC2100_2400;

public class LC2149_RearrangeArrayElementsbySign {
    /**
     * You are given a 0-indexed integer array nums of even length consisting of an equal number of positive and
     * negative integers.
     *
     * You should rearrange the elements of nums such that the modified array follows the given conditions:
     *
     * Every consecutive pair of integers have opposite signs.
     * For all integers with the same sign, the order in which they were present in nums is preserved.
     * The rearranged array begins with a positive integer.
     * Return the modified array after rearranging the elements to satisfy the aforementioned conditions.
     *
     * Input: nums = [3,1,-2,-5,2,-4]
     * Output: [3,-2,1,-5,2,-4]
     *
     * Constraints:
     *
     * 2 <= nums.length <= 2 * 10^5
     * nums.length is even
     * 1 <= |nums[i]| <= 10^5
     * nums consists of equal number of positive and negative integers.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] rearrangeArray(int[] nums) {
        int n = nums.length, pos = 0, neg = 1;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                res[pos] = nums[i];
                pos += 2;
            } else {
                res[neg] = nums[i];
                neg += 2;
            }
        }
        return res;
    }
}

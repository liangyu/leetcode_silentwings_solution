package LC2101_2400;

public class LC2317_MaximumXORAfterOperations {
    /**
     * You are given a 0-indexed integer array nums. In one operation, select any non-negative integer x and an index i,
     * then update nums[i] to be equal to nums[i] AND (nums[i] XOR x).
     *
     * Note that AND is the bitwise AND operation and XOR is the bitwise XOR operation.
     *
     * Return the maximum possible bitwise XOR of all elements of nums after applying the operation any number of times.
     *
     * Input: nums = [3,2,4,6]
     * Output: 7
     *
     * Input: nums = [1,2,3,9,2]
     * Output: 11
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^8
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int maximumXOR(int[] nums) {
        int res = 0;
        for (int x : nums) res |= x;
        return res;
    }
}
/**
 * ai -> ai & (ai ^ x)
 * 0 -> 0 & (ai ^ x) = 0
 * 1 -> 1 & (1 ^ x) = 0 (x = 1) / 1 (x = 0)
 * => 全部 | 即可
 */
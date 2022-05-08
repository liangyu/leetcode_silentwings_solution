package LC2101_2400;
import java.util.*;
public class LC2206_DivideArrayIntoEqualPairs {
    /**
     * You are given an integer array nums consisting of 2 * n integers.
     *
     * You need to divide nums into n pairs such that:
     *
     * Each element belongs to exactly one pair.
     * The elements present in a pair are equal.
     * Return true if nums can be divided into n pairs, otherwise return false.
     *
     * Input: nums = [3,2,3,2,2,2]
     * Output: true
     *
     * Constraints:
     *
     * nums.length == 2 * n
     * 1 <= n <= 500
     * 1 <= nums[i] <= 500
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public boolean divideArray(int[] nums) {
        int[] freq = new int[501];
        for (int x : nums) freq[x]++;
        for (int x : freq) {
            if (x > 0 && x % 2 != 0) return false;
        }
        return true;
    }
}

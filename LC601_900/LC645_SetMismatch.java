package LC601_900;
import java.util.*;
public class LC645_SetMismatch {
    /**
     * You have a set of integers s, which originally contains all the numbers from 1 to n. Unfortunately, due to some
     * error, one of the numbers in s got duplicated to another number in the set, which results in repetition of one
     * number and loss of another number.
     *
     * You are given an integer array nums representing the data status of this set after the error.
     *
     * Find the number that occurs twice and the number that is missing and return them in the form of an array.
     *
     * Input: nums = [1,2,2,4]
     * Output: [2,3]
     *
     * Constraints:
     *
     * 2 <= nums.length <= 10^4
     * 1 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int[] findErrorNums(int[] nums) {
        int[] res = new int[2];
        // corner case
        if (nums == null || nums.length == 0) return res;

        int n = nums.length;
        int[] count = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (count[nums[i]] != 0) res[0] = nums[i];
            count[nums[i]]++;
        }
        for (int i = 1; i < n + 1; i++) {
            if (count[i] == 0) {
                res[1] = i;
                break;
            }
        }
        return res;
    }
}

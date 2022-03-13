package LC2101_2400;
import java.util.*;
public class LC2160_MinimumSumofFourDigitNumberAfterSplittingDigits {
    /**
     * You are given a positive integer num consisting of exactly four digits. Split num into two new integers new1 and
     * new2 by using the digits found in num. Leading zeros are allowed in new1 and new2, and all the digits found in
     * num must be used.
     *
     * For example, given num = 2932, you have the following digits: two 2's, one 9 and one 3. Some of the possible
     * pairs [new1, new2] are [22, 93], [23, 92], [223, 9] and [2, 329].
     * Return the minimum possible sum of new1 and new2.
     *
     * Input: num = 2932
     * Output: 52
     *
     * Input: num = 4009
     * Output: 13
     *
     * Constraints:
     *
     * 1000 <= num <= 9999
     * @param num
     * @return
     */
    // time = O(1), space = O(1)
    public int minimumSum(int num) {
        int[] nums = new int[4];
        int idx = 0;
        while (num > 0) {
            nums[idx++] = num % 10;
            num /= 10;
        }
        Arrays.sort(nums);
        int val1 = nums[0] * 10 + nums[2];
        int val2 = nums[1] * 10 + nums[3];
        return val1 + val2;
    }
}

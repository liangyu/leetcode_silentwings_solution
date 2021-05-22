package LC001_300;
import java.util.*;
public class LC66_PlusOne {
    /**
     * Given a non-empty array of decimal digits representing a non-negative integer, increment one to the integer.
     *
     * The digits are stored such that the most significant digit is at the head of the list, and each element in the
     * array contains a single digit.
     *
     * You may assume the integer does not contain any leading zero, except the number 0 itself.
     *
     * Input: digits = [1,2,3]
     * Output: [1,2,4]
     *
     * Constraints:
     *
     * 1 <= digits.length <= 100
     * 0 <= digits[i] <= 9
     * @param digits
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public int[] plusOne(int[] digits) {
        // corner case
        if (digits == null || digits.length == 0) return new int[0];

        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits; // if not all the digits are 9, then + 1 and return
            } else digits[i] = 0;
        }

        int[] res = new int[digits.length + 1];
        res[0] = 1; // otherwise add a new digit 1 at the beginning, and the other digits are all 0
        return res;
    }

    // S2
    // time = O(n), space = O(n)
    public int[] plusOne2(int[] digits) {
        // corner case
        if (digits == null || digits.length == 0) return new int[0];

        List<Integer> list = new LinkedList<>();
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + carry;
            list.add(0, sum % 10);
            carry = sum / 10;
        }
        if (carry != 0) list.add(0, carry);
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) res[i] = list.get(i);
        return res;
    }
}

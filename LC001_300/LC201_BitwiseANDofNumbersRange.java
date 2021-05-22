package LC001_300;
import java.util.*;
public class LC201_BitwiseANDofNumbersRange {
    /**
     * Given two integers left and right that represent the range [left, right], return the bitwise AND of all numbers
     * in this range, inclusive.
     *
     * Input: left = 5, right = 7
     * Output: 4
     *
     * Constraints:
     *
     * 0 <= left <= right <= 2^31 - 1
     * @param left
     * @param right
     * @return
     */
    // S1
    // time = O(1), space = O(1)
    public int rangeBitwiseAnd(int left, int right) {
        int shift = 0;
        while (left != right) {
            left >>= 1;
            right >>= 1;
            shift++;
        }
        return left << shift;
    }

    // S2
    // time = O(1), space = O(1)
    public int rangeBitwiseAnd2(int left, int right) {
        while (right > left) right = right & (right - 1);
        return right;
    }

}

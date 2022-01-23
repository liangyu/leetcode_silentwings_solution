package LC2100_2400;

public class LC2119_ANumberAfteraDoubleReversal {
    /**
     * Reversing an integer means to reverse all its digits.
     *
     * For example, reversing 2021 gives 1202. Reversing 12300 gives 321 as the leading zeros are not retained.
     * Given an integer num, reverse num to get reversed1, then reverse reversed1 to get reversed2. Return true if
     * reversed2 equals num. Otherwise return false.
     *
     * Input: num = 526
     * Output: true
     *
     * Constraints:
     *
     * 0 <= num <= 10^6
     * @param num
     * @return
     */
    // time = O(1), space = O(1)
    public boolean isSameAfterReversals(int num) {
        return num == 0 || num % 10 != 0;
    }
}

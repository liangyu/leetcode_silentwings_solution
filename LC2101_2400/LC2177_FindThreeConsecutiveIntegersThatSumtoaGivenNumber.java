package LC2101_2400;

public class LC2177_FindThreeConsecutiveIntegersThatSumtoaGivenNumber {
    /**
     * Given an integer num, return three consecutive integers (as a sorted array) that sum to num. If num cannot be
     * expressed as the sum of three consecutive integers, return an empty array.
     *
     * Input: num = 33
     * Output: [10,11,12]
     *
     * Constraints:
     *
     * 0 <= num <= 1015
     * @param num
     * @return
     */
    // time = O(1), space = O(1)
    public long[] sumOfThree(long num) {
        return num % 3 != 0 ? new long[0] : new long[]{num / 3 - 1, num / 3, num / 3 + 1};
    }
}

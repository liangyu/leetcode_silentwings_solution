package LC2101_2400;

public class LC2310_SumofNumbersWithUnitsDigitK {
    /**
     * Given two integers num and k, consider a set of positive integers with the following properties:
     *
     * The units digit of each integer is k.
     * The sum of the integers is num.
     * Return the minimum possible size of such a set, or -1 if no such set exists.
     *
     * Note:
     *
     * The set can contain multiple instances of the same integer, and the sum of an empty set is considered 0.
     * The units digit of a number is the rightmost digit of the number.
     *
     * Input: num = 58, k = 9
     * Output: 2
     *
     * Input: num = 37, k = 2
     * Output: -1
     *
     * Input: num = 0, k = 7
     * Output: 0
     *
     * Constraints:
     *
     * 0 <= num <= 3000
     * 0 <= k <= 9
     * @param num
     * @param k
     * @return
     */
    // time = O(1), space = O(1)
    public int minimumNumbers(int num, int k) {
        if (num == 0) return 0;

        for (int i = 1; i <= 10; i++) {
            if (k * i % 10 == num % 10 && k * i <= num) return i;
        }
        return -1;
    }
}

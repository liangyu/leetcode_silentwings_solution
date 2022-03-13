package LC2101_2400;

public class LC2180_CountIntegersWithEvenDigitSum {
    /**
     * Given a positive integer num, return the number of positive integers less than or equal to num whose digit sums
     * are even.
     *
     * The digit sum of a positive integer is the sum of all its digits.
     *
     * Input: num = 4
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= num <= 1000
     * @param num
     * @return
     */
    // time = O(n), space = O(1)
    public int countEven(int num) {
        int count = 0;
        for (int i = 1; i <= num; i++) {
            if (helper(i)) count++;
        }
        return count;
    }

    private boolean helper(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum % 2 == 0;
    }
}

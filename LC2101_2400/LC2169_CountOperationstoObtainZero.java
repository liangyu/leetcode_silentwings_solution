package LC2101_2400;

public class LC2169_CountOperationstoObtainZero {
    /**
     * You are given two non-negative integers num1 and num2.
     *
     * In one operation, if num1 >= num2, you must subtract num2 from num1, otherwise subtract num1 from num2.
     *
     * For example, if num1 = 5 and num2 = 4, subtract num2 from num1, thus obtaining num1 = 1 and num2 = 4. However,
     * if num1 = 4 and num2 = 5, after one operation, num1 = 4 and num2 = 1.
     * Return the number of operations required to make either num1 = 0 or num2 = 0.
     *
     * Input: num1 = 2, num2 = 3
     * Output: 3
     *
     * Constraints:
     *
     * 0 <= num1, num2 <= 10^5
     * @param num1
     * @param num2
     * @return
     */
    // time = O(n), space = O(1)
    public int countOperations(int num1, int num2) {
        int count = 0;
        while (num1 != 0 && num2 != 0) {
            if (num1 > num2) num1 -= num2;
            else num2 -= num1;
            count++;
        }
        return count;
    }
}

package LC2101_2400;

public class LC2283_CheckifNumberHasEqualDigitCountandDigitValue {
    /**
     * You are given a 0-indexed string num of length n consisting of digits.
     *
     * Return true if for every index i in the range 0 <= i < n, the digit i occurs num[i] times in num, otherwise
     * return false.
     *
     * Input: num = "1210"
     * Output: true
     *
     * Constraints:
     *
     * n == num.length
     * 1 <= n <= 10
     * num consists of digits.
     * @param num
     * @return
     */
    // time = O(n), space = O(1)
    public boolean digitCount(String num) {
        int[] count = new int[10];
        int n = num.length();
        for (int i = 0; i < n; i++) {
             count[num.charAt(i) - '0']++;
        }

        for (int i = 0; i < n; i++) {
            if (count[i] != num.charAt(i) - '0') return false;
        }
        return true;
    }
}

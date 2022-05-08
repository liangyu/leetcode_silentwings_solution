package LC2101_2400;

public class LC2259_RemoveDigitFromNumbertoMaximizeResult {
    /**
     * You are given a string number representing a positive integer and a character digit.
     *
     * Return the resulting string after removing exactly one occurrence of digit from number such that the value of the
     * resulting string in decimal form is maximized. The test cases are generated such that digit occurs at least once
     * in number.
     *
     * Input: number = "123", digit = "3"
     * Output: "12"
     *
     * Constraints:
     *
     * 2 <= number.length <= 100
     * number consists of digits from '1' to '9'.
     * digit is a digit from '1' to '9'.
     * digit occurs at least once in number.
     * @param number
     * @param digit
     * @return
     */
    // time = O(n), space = O(n)
    public String removeDigit(String number, char digit) {
        int n = number.length();
        String res = "";
        for (int i = 0; i < n; i++) {
            char c = number.charAt(i);
            if (c == digit) {
                String s = number.substring(0, i) + number.substring(i + 1);
                res = res.compareTo(s) < 0 ? s : res;
            }
        }
        return res;
    }
}

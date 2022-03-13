package LC301_600;

public class LC504_Base7 {
    /**
     * Given an integer num, return a string of its base 7 representation.
     *
     * Input: num = 100
     * Output: "202"
     *
     * Input: num = -7
     * Output: "-10"
     *
     * Constraints:
     *
     * -10^7 <= num <= 10^7
     * @param num
     * @return
     */
    public String convertToBase7(int num) {
        StringBuilder sb = new StringBuilder();
        if (num == 0) return "0";
        int temp = num;
        if (num < 0) num = -num;
        while (num >= 7) {
            sb.append(num % 7);
            num /= 7;
        }
        sb.append(num);
        if (temp < 0) sb.append('-');
        return sb.reverse().toString();
    }
}

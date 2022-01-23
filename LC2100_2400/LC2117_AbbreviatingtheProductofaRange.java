package LC2100_2400;

public class LC2117_AbbreviatingtheProductofaRange {
    /**
     * You are given two positive integers left and right with left <= right. Calculate the product of all integers in
     * the inclusive range [left, right].
     *
     * Since the product may be very large, you will abbreviate it following these steps:
     *
     * Count all trailing zeros in the product and remove them. Let us denote this count as C.
     * For example, there are 3 trailing zeros in 1000, and there are 0 trailing zeros in 546.
     * Denote the remaining number of digits in the product as d. If d > 10, then express the product as <pre>...<suf>
     *     where <pre> denotes the first 5 digits of the product, and <suf> denotes the last 5 digits of the product after removing all trailing zeros. If d <= 10, we keep it unchanged.
     * For example, we express 1234567654321 as 12345...54321, but 1234567 is represented as 1234567.
     * Finally, represent the product as a string "<pre>...<suf>eC".
     * For example, 12345678987600000 will be represented as "12345...89876e5".
     * Return a string denoting the abbreviated product of all integers in the inclusive range [left, right].
     *
     * Input: left = 1, right = 4
     * Output: "24e0"
     *
     * Constraints:
     *
     * 1 <= left <= right <= 10^6
     * @param left
     * @param right
     * @return
     */
    public String abbreviateProduct(int left, int right) {
        long limit = 10000000000L, num = 1;
        int c = 0;
        boolean flag = false;

        for (int i = left; i <= right; i++) {
            num *= i;
            while (num % 10 == 0) {
                num /= 10;
                c++;
            }
            if (num >= limit) flag = true;
            if (flag) num %= limit;
        }
        if (!flag) return String.valueOf(num) + "e" + c;
        num %= 100000;
        long first = 1;
        for (int i = left; i <= right; i++) {
            first *= i;
            while (first > (long) 1e12) first /= 10;
        }
        while (first >= 100000) first /= 10;
        String s = String.valueOf(num);
        while (s.length() < 5) s = "0" + s;
        return String.valueOf(first) + "..." + s + "e" + c;
    }
}

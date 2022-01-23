package LC1501_1800;

public class LC1716_CalculateMoneyinLeetcodeBank {
    /**
     * Hercy wants to save money for his first car. He puts money in the Leetcode bank every day.
     *
     * He starts by putting in $1 on Monday, the first day. Every day from Tuesday to Sunday, he will put in $1 more
     * than the day before. On every subsequent Monday, he will put in $1 more than the previous Monday.
     * Given n, return the total amount of money he will have in the Leetcode bank at the end of the nth day.
     *
     * Input: n = 4
     * Output: 10
     *
     * Constraints:
     *
     * 1 <= n <= 1000
     * @param n
     * @return
     */
    // time = O(1), space = O(1)
    public int totalMoney(int n) {
        int weeks = n / 7, days = n % 7;
        int weekTotal = 1 + 2 + 3 + 4 + 5 + 6 + 7;
        int res = weeks > 0 ? weekTotal : 0;
        for (int i = 1; i < weeks; i++) {
            weekTotal += 7;
            res += weekTotal;
        }

        for (int i = 0; i < days; i++) res += weeks + i + 1;
        return res;
    }
}

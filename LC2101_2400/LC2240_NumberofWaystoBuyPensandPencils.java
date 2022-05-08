package LC2101_2400;

public class LC2240_NumberofWaystoBuyPensandPencils {
    /**
     * You are given an integer total indicating the amount of money you have. You are also given two integers cost1 and cost2 indicating the price of a pen and pencil respectively. You can spend part or all of your money to buy multiple quantities (or none) of each kind of writing utensil.
     *
     * Return the number of distinct ways you can buy some number of pens and pencils.
     *
     * Input: total = 20, cost1 = 10, cost2 = 5
     * Output: 9
     *
     * Constraints:
     *
     * 1 <= total, cost1, cost2 <= 10^6
     * @param total
     * @param cost1
     * @param cost2
     * @return
     */
    // time = O(n), space = O(1)
    public long waysToBuyPensPencils(int total, int cost1, int cost2) {
        long x = total / cost1, res = 0;
        for (long i = 0; i <= x; i++) {
            long remain = total - i * cost1;
            res += remain / cost2 + 1;
        }
        return res;
    }
}

package LC2100_2400;
import java.util.*;
public class LC2144_MinimumCostofBuyingCandiesWithDiscount {
    /**
     * A shop is selling candies at a discount. For every two candies sold, the shop gives a third candy for free.
     *
     * The customer can choose any candy to take away for free as long as the cost of the chosen candy is less than or
     * equal to the minimum cost of the two candies bought.
     *
     * For example, if there are 4 candies with costs 1, 2, 3, and 4, and the customer buys candies with costs 2 and 3,
     * they can take the candy with cost 1 for free, but not the candy with cost 4.
     * Given a 0-indexed integer array cost, where cost[i] denotes the cost of the ith candy, return the minimum cost of
     * buying all the candies.
     *
     * Input: cost = [1,2,3]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= cost.length <= 100
     * 1 <= cost[i] <= 100
     * @param cost
     * @return
     */
    // time = O(n), space = O(1)
    public int minimumCost(int[] cost) {
        int n = cost.length;
        Arrays.sort(cost);
        int i = n - 1, res = 0;
        while (i >= 0) {
            res += cost[i] + (i - 1 >= 0 ? cost[i - 1] : 0);
            i -= 3;
        }
        return res;
    }
}

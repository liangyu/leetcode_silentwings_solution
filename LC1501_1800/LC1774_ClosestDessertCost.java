package LC1501_1800;
import java.util.*;
public class LC1774_ClosestDessertCost {
    /**
     * You would like to make dessert and are preparing to buy the ingredients. You have n ice cream base flavors and
     * m types of toppings to choose from. You must follow these rules when making your dessert:
     *
     * There must be exactly one ice cream base.
     * You can add one or more types of topping or have no toppings at all.
     * There are at most two of each type of topping.
     * You are given three inputs:
     *
     * baseCosts, an integer array of length n, where each baseCosts[i] represents the price of the ith ice cream base
     * flavor.
     * toppingCosts, an integer array of length m, where each toppingCosts[i] is the price of one of the ith topping.
     * target, an integer representing your target price for dessert.
     * You want to make a dessert with a total cost as close to target as possible.
     *
     * Return the closest possible cost of the dessert to target. If there are multiple, return the lower one.
     *
     * Input: baseCosts = [1,7], toppingCosts = [3,4], target = 10
     * Output: 10
     *
     * Input: baseCosts = [2,3], toppingCosts = [4,5,100], target = 18
     * Output: 17
     *
     * Constraints:
     *
     * n == baseCosts.length
     * m == toppingCosts.length
     * 1 <= n, m <= 10
     * 1 <= baseCosts[i], toppingCosts[i] <= 10^4
     * 1 <= target <= 10^4
     *
     * @param baseCosts
     * @param toppingCosts
     * @param target
     * @return
     */
    // time = O(m * n * 4^n), space = O(1)
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int cost = 0;
        int diff = Integer.MAX_VALUE;
        int n = toppingCosts.length;

        for (int baseCost : baseCosts) { // O(m)
            for (int state = 0; state < (1 << 2 * n); state++) { // bit mask缺点：不能剪枝！！！
                int sum = baseCost;
                for (int i = 0; i < n; i++) {
                    if (((state >> i * 2) & 1) > 0) sum += toppingCosts[i];
                    if (((state >> (i * 2 + 1)) & 1) > 0) sum += toppingCosts[i];
                }

                if (Math.abs(sum - target) < diff || Math.abs(sum - target) == diff && sum < cost) {
                    diff = Math.abs(sum - target);
                    cost = sum;
                }
            }
        }
        return cost;
    }
}

/**
 * 暴力枚举 => 10*3^10
 * DFS
 * bit mask 更方便 => 2^(2n) = 2^20  (6个0 => 可以接受)
 * state = 00 00 10 11 01 00 00   (00 - 1个都不选，01 / 10 - 选1个， 11 - 选2个)
 */

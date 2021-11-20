package LC601_900;
import java.util.*;
public class LC638_ShoppingOffers {
    /**
     * In LeetCode Store, there are n items to sell. Each item has a price. However, there are some special offers, and
     * a special offer consists of one or more different kinds of items with a sale price.
     *
     * You are given an integer array price where price[i] is the price of the ith item, and an integer array needs
     * where needs[i] is the number of pieces of the ith item you want to buy.
     *
     * You are also given an array special where special[i] is of size n + 1 where special[i][j] is the number of pieces
     * of the jth item in the ith offer and special[i][n] (i.e., the last integer in the array) is the price of the ith
     * offer.
     *
     * Return the lowest price you have to pay for exactly certain items as given, where you could make optimal use of
     * the special offers. You are not allowed to buy more items than you want, even if that would lower the overall
     * price. You could use any of the special offers as many times as you want.
     *
     * Input: price = [2,5], special = [[3,0,5],[1,2,10]], needs = [3,2]
     * Output: 14
     *
     * Constraints:
     *
     * n == price.length
     * n == needs.length
     * 1 <= n <= 6
     * 0 <= price[i] <= 10
     * 0 <= needs[i] <= 10
     * 1 <= special.length <= 100
     * special[i].length == n + 1
     * 0 <= special[i][j] <= 50
     * @param price
     * @param special
     * @param needs
     * @return
     */
    // S1: dfs
    // time = O(n * k * m^n), space = O(n * m^n)
    HashMap<String, Integer> map;
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int n = price.size();
        map = new HashMap<>();
        // filter unncessary special deals
        List<List<Integer>> fs = new ArrayList<>();
        for (int i = 0; i < special.size(); i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += special.get(i).get(j) * price.get(j);
            }
            if (sum > special.get(i).get(n)) fs.add(special.get(i));
        }

        return dfs(price, fs, needs);
    }

    private int dfs(List<Integer> price, List<List<Integer>> fs, List<Integer> needs) {
        int n = price.size();
        String key = getKey(needs);
        if (map.containsKey(key)) return map.get(key);

        int minPrice = 0;
        for (int i = 0; i < n; i++) {
            minPrice += needs.get(i) * price.get(i); // do not use any special deals
        }

        for (List<Integer> x : fs) {
            int speicalPrice = x.get(n);
            List<Integer> nxtNeeds = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (x.get(i) > needs.get(i)) break;
                nxtNeeds.add(needs.get(i) - x.get(i));
            }
            if (nxtNeeds.size() == n) {
                minPrice = Math.min(minPrice, dfs(price, fs, nxtNeeds) + speicalPrice);
            }
        }
        map.put(key, minPrice);
        return minPrice;
    }

    private String getKey(List<Integer> needs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < needs.size(); i++) {
            sb.append(needs.get(i)).append('#');
        }
        return sb.toString();
    }

    // S2: bitmask
    // time = O(n * k * m^n), space = O(n * m^n)
    public int shoppingOffers2(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int n = price.size();
        int[] memo = new int[1 << 24];
        List<List<Integer>> special2 = new ArrayList<>();
        for (int i = 0; i < special.size(); i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += price.get(j) * special.get(i).get(j);
            }
            if (sum > special.get(i).get(special.get(i).size() - 1)) {
                special2.add(special.get(i));
            }
        }
        int state = 0;
        for (int i = 0; i < n; i++) {
            state += needs.get(i) << (4 * i);
        }
        return dfs(state, special2, price, memo);
    }

    private int dfs(int state, List<List<Integer>> special2, List<Integer> price, int[] memo) {
        int n = price.size();
        if (state == 0) return 0;
        if (memo[state] != 0) return memo[state];

        int res = 0;
        for (int i = 0; i < n; i++) {
            res += (state >> (i * 4)) % 16 * price.get(i);
        }

        for (List<Integer> comb : special2) {
            boolean flag = true;
            for (int i = 0; i < n; i++) {
                if ((state >> (i * 4)) % 16 < comb.get(i)) {
                    flag = false;
                    break;
                }
            }
            if (!flag) continue;

            int state2 = state;
            for (int i = 0; i < n; i++) {
                state2 -= comb.get(i) << (i * 4);
            }
            res = Math.min(res, comb.get(n) + dfs(state2, special2, price, memo));
        }
        memo[state] = res;
        return res;
    }
}
/**
 * 考虑到最多只有6件物品，每个物品最多6个。所以我们用18个bit的二进制数来表示状态。每3个bit可以表示该种物品的数量（从0到7）。
 * 在dfs(state)里，为我们尝试每一种合法的offer，将state更新后递归处理。我们取所有尝试中代价最小的结果，并记录在memo[state]里面。
 * PS: 最新的题目里把每种物品的数量放宽到了10件，所以需要4*6=24个bit的二进制数来表示状态。
 * dfs(2,3,1) -> key 编码成一个正数 -> 状态压缩 ref.LC1815
 * [4,3,2,1,2,5]
 * xxx xxx xxx xxx xxx xxx 18bit
 *             100     011
 * 注意：bundle价不一定更便宜！提前special处理下
 * return dfs(int state)
 */


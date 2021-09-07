package LC301_600;

import java.util.TreeSet;

public class LC475_Heaters {
    /**
     * Winter is coming! During the contest, your first job is to design a standard heater with a fixed warm radius to
     * warm all the houses.
     *
     * Every house can be warmed, as long as the house is within the heater's warm radius range.
     *
     * Given the positions of houses and heaters on a horizontal line, return the minimum radius standard of heaters so
     * that those heaters could cover all houses.
     *
     * Notice that all the heaters follow your radius standard, and the warm radius will the same.
     *
     * Input: houses = [1,2,3], heaters = [2]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= houses.length, heaters.length <= 3 * 10^4
     * 1 <= houses[i], heaters[i] <= 10^9
     * @param houses
     * @param heaters
     * @return
     */
    // time = O((m + n) * logn), space = O(n)
    public int findRadius(int[] houses, int[] heaters) {
        TreeSet<Integer> set = new TreeSet<>();
        int res = 0;
        for (int heater : heaters) set.add(heater); // O(nlogn)
        for (int house : houses) { // O(m)
            int r = 0;
            Integer fk = set.floor(house);
            Integer ck = set.ceiling(house);
            if (fk != null && ck != null) r = Math.min(house - fk, ck - house);
            else if (fk != null) r = house - fk;
            else r = ck - house;
            res = Math.max(res, r);
        }
        return res;
    }
}
/**
 * 一维分布的heater，离房子左边最接近，右边最接近的Heaters，找一个最小的距离
 * 所有house都有一个最小值，然后找其中的最大值即可。
 */

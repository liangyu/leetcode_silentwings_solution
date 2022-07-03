package LC2101_2400;
import java.util.*;
public class LC2274_MaximumConsecutiveFloorsWithoutSpecialFloors {
    /**
     * Alice manages a company and has rented some floors of a building as office space. Alice has decided some of these
     * floors should be special floors, used for relaxation only.
     *
     * You are given two integers bottom and top, which denote that Alice has rented all the floors from bottom to top
     * (inclusive). You are also given the integer array special, where special[i] denotes a special floor that Alice
     * has designated for relaxation.
     *
     * Return the maximum number of consecutive floors without a special floor.
     *
     * Input: bottom = 2, top = 9, special = [4,6]
     * Output: 3
     *
     * Input: bottom = 6, top = 8, special = [7,6,8]
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= special.length <= 10^5
     * 1 <= bottom <= special[i] <= top <= 10^9
     * All the values of special are unique.
     * @param bottom
     * @param top
     * @param special
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int maxConsecutive(int bottom, int top, int[] special) {
        Arrays.sort(special);
        int i = bottom, res = 0;
        for (int x : special) {
            res = Math.max(res, x - i);
            i = x + 1;
        }
        res = Math.max(res, top - i + 1);
        return res;
    }
}

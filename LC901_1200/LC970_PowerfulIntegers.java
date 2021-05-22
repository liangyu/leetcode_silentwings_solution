package LC901_1200;
import java.util.*;
public class LC970_PowerfulIntegers {
    /**
     * Given three integers x, y, and bound, return a list of all the powerful integers that have a value less than
     * or equal to bound.
     *
     * An integer is powerful if it can be represented as xi + yj for some integers i >= 0 and j >= 0.
     *
     * You may return the answer in any order. In your answer, each value should occur at most once.
     *
     * Input: x = 2, y = 3, bound = 10
     * Output: [2,3,4,5,7,9,10]
     *
     * Constraints:
     *
     * 1 <= x, y <= 100
     * 0 <= bound <= 10^6
     * @param x
     * @param y
     * @param bound
     * @return
     */
    // time = O(m * n), space = O(m * n)  m: logx(bound), n: logy(bound) bound
    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        HashSet<Integer> set = new HashSet<>();

        for (int i = 1; i < bound; i *= x) {
            for (int j = 1; i + j <= bound; j*= y) {
                set.add(i + j);
                if (y == 1) break;
            }
            if (x == 1) break;
        }
        return new ArrayList<>(set);
    }
}

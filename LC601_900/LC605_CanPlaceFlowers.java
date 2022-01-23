package LC601_900;

public class LC605_CanPlaceFlowers {
    /**
     * You have a long flowerbed in which some of the plots are planted, and some are not. However, flowers cannot be
     * planted in adjacent plots.
     *
     * Given an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty, and an
     * integer n, return if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule.
     *
     * Input: flowerbed = [1,0,0,0,1], n = 1
     * Output: true
     *
     * Input: flowerbed = [1,0,0,0,1], n = 2
     * Output: false
     *
     * Constraints:
     *
     * 1 <= flowerbed.length <= 2 * 10^4
     * flowerbed[i] is 0 or 1.
     * There are no two adjacent flowers in flowerbed.
     * 0 <= n <= flowerbed.length
     * @param flowerbed
     * @param n
     * @return
     */
    // S1: brute-force
    // time = O(n), space = O(1)
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int m = flowerbed.length, count = 0;
        if (n == 0) return true;
        if (m == 1) {
            if (n > 1) return false;
            return flowerbed[0] == 0;
        }
        for (int i = 0; i < m; i++) {
            if (flowerbed[i] == 0) {
                if (i == 0) {
                    if (flowerbed[i + 1] == 0) {
                        flowerbed[i] = 1;
                        count++;
                    } else continue;
                } else if (i == m - 1) {
                    if (flowerbed[m - 2] == 0) {
                        flowerbed[m - 2] = 1;
                        count++;
                    } else continue;
                } else {
                    if (flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                        flowerbed[i] = 1;
                        count++;
                    }
                }
            }
        }
        return count >= n;
    }

    // S2
    // time = O(n), space = O(1)
    public boolean canPlaceFlowers2(int[] flowerbed, int n) {
        int m = flowerbed.length, count = 0;
        for (int i = 0; i < m; i++) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == m - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i] = 1;
                count++;
            }
        }
        return count >= n;
    }
}

package LC1801_2100;

public class LC1954_MinimumGardenPerimetertoCollectEnoughApples {
    /**
     * In a garden represented as an infinite 2D grid, there is an apple tree planted at every integer coordinate.
     * The apple tree planted at an integer coordinate (i, j) has |i| + |j| apples growing on it.
     *
     * You will buy an axis-aligned square plot of land that is centered at (0, 0).
     *
     * Given an integer neededApples, return the minimum perimeter of a plot such that at least neededApples apples are
     * inside or on the perimeter of that plot.
     *
     * The value of |x| is defined as:
     *
     * x if x >= 0
     * -x if x < 0
     *
     * Input: neededApples = 1
     * Output: 8
     *
     * Constraints:
     *
     * 1 <= neededApples <= 10^15
     * @param neededApples
     * @return
     */
    // S1: Cardano's formula + BS
    // time = O(logn), space = O(1)
    public long minimumPerimeter(long neededApples) {
        long left = 1, right = (long)(1e5);
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (2 * mid * (mid + 1) * (2 * mid + 1) < neededApples) left = mid + 1;
            else right = mid;
        }
        return 8 * left;
    }

    // S2: Enumeration
    // time = O(n^(1/2)), space = O(1)
    public long minimumPerimeter2(long neededApples) {
        long r = 1, total = 0;
        while (total < neededApples) {
            total += 12 * r * r;
            r++;
        }
        return 8 * (r - 1);
    }
}
/**
 * total apples = (i * 4 + 2 * i * 4) + ((i + 1) + (i + 2) + ... + (i + i - 1)) * 8
 * = 12i + (i + 1 + 2i - 1) * (i - 1) / 2 * 8 = 12i + 3i * (i - 1) * 4 = 12i^2
 * square sum = 12 * (1^2 + 2^2 + ... + r^2) = 2 * n * (n + 1) * (2n + 1)  ~ n^3 => n ~ 10^5
 */

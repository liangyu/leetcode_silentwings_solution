package LC301_600;
import java.util.*;
public class LC497_RandomPointinNonoverlappingRectangles {
    /**
     * You are given an array of non-overlapping axis-aligned rectangles rects where rects[i] = [ai, bi, xi, yi]
     * indicates that (ai, bi) is the bottom-left corner point of the ith rectangle and (xi, yi) is the top-right corner
     * point of the ith rectangle. Design an algorithm to pick a random integer point inside the space covered by one of
     * the given rectangles. A point on the perimeter of a rectangle is included in the space covered by the rectangle.
     *
     * Any integer point inside the space covered by one of the given rectangles should be equally likely to be returned.
     *
     * Note that an integer point is a point that has integer coordinates.
     *
     * Implement the Solution class:
     *
     * Solution(int[][] rects) Initializes the object with the given rectangles rects.
     * int[] pick() Returns a random integer point [u, v] inside the space covered by one of the given rectangles.
     *
     * Input
     * ["Solution", "pick", "pick", "pick", "pick", "pick"]
     * [[[[-2, -2, 1, 1], [2, 2, 4, 6]]], [], [], [], [], []]
     * Output
     * [null, [1, -2], [1, -1], [-1, -2], [-2, -2], [0, 0]]
     *
     * Constraints:
     *
     * 1 <= rects.length <= 100
     * rects[i].length == 4
     * -109 <= ai < xi <= 10^9
     * -109 <= bi < yi <= 10^9
     * xi - ai <= 2000
     * yi - bi <= 2000
     * All the rectangles do not overlap.
     * At most 10^4 calls will be made to pick.
     * @param rects
     */
    int[][] rects;
    int[] sum;
    Random random;
    public LC497_RandomPointinNonoverlappingRectangles(int[][] rects) {
        this.rects = rects;
        int n = rects.length;
        sum = new int[n + 1];
        random = new Random();

        for (int i = 1; i <= n; i++) {
            int a = rects[i - 1][2] - rects[i - 1][0] + 1;
            int b = rects[i - 1][3] - rects[i - 1][1] + 1;
            sum[i] = sum[i - 1] + a * b;
        }
    }

    public int[] pick() {
        int n = sum.length;
        int k = random.nextInt(sum[n - 1]) + 1;
        int left = 1, right = n - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (sum[mid] < k) left = mid + 1;
            else right = mid;
        }
        int[] r = rects[left - 1];
        int a = r[2] - r[0] + 1;
        int b = r[3] - r[1] + 1;
        return new int[]{random.nextInt(a) + r[0], random.nextInt(b) + r[1]};
    }
}
/**
 * rand() % M => [0, M-1]
 * ______|____|______
 */
package LC2101_2400;
import java.util.*;
public class LC2249_CountLatticePointsInsideaCircle {
    /**
     * Given a 2D integer array circles where circles[i] = [xi, yi, ri] represents the center (xi, yi) and radius ri
     * of the ith circle drawn on a grid, return the number of lattice points that are present inside at least one circle.
     *
     * Note:
     *
     * A lattice point is a point with integer coordinates.
     * Points that lie on the circumference of a circle are also considered to be inside it.
     *
     * Input: circles = [[2,2,1]]
     * Output: 5
     *
     * Input: circles = [[2,2,2],[3,4,1]]
     * Output: 16
     *
     * Constraints:
     *
     * 1 <= circles.length <= 200
     * circles[i].length == 3
     * 1 <= xi, yi <= 100
     * 1 <= ri <= min(xi, yi)
     * @param circles
     * @return
     */
    // S1: hash string
    // time = O(n^3), space = O(n)
    public int countLatticePoints(int[][] circles) {
        HashSet<String> set = new HashSet<>();
        for (int[] c : circles) {
            int x = c[0], y = c[1], r = c[2];
            for (int i = x - r; i <= x + r; i++) {
                for (int j = y - r; j <= y + r; j++) {
                    int dx = i - x, dy = j - y;
                    if (dx * dx + dy * dy <= r * r) {
                        set.add(i + "#" + j);
                    }
                }
            }
        }
        return set.size();
    }

    // S2: hash string
    // time = O(n^3), space = O(n)
    public int countLatticePoints2(int[][] circles) {
        HashSet<Integer> set = new HashSet<>();
        int n = 201;
        for (int[] c : circles) {
            int x = c[0], y = c[1], r = c[2];
            for (int i = x - r; i <= x + r; i++) {
                for (int j = y - r; j <= y + r; j++) {
                    int dx = i - x, dy = j - y;
                    if (dx * dx + dy * dy <= r * r) {
                        set.add(i * n + j);
                    }
                }
            }
        }
        return set.size();
    }
}

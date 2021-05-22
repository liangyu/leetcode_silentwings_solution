package LC001_300;
import java.util.*;
public class LC149_MaxPointsonaLine {
    /**
     * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane, return the maximum
     * number of points that lie on the same straight line.
     *
     * Input: points = [[1,1],[2,2],[3,3]]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= points.length <= 300
     * points[i].length == 2
     * -10^4 <= xi, yi <= 10^4
     * All the points are unique.
     * @param points
     * @return
     */
    // time = O(n^2), space = O(n)
    public int maxPoints(int[][] points) {
        // corner case
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0) return 0;
        if (points.length < 2) return points.length;

        int res = 0;
        for (int i = 0; i < points.length; i++) {
            HashMap<String, Integer> map = new HashMap<>();
            int samePoint = 0, verticle = 1;
            for (int j = 0; j < points.length; j++) {
                if (i != j) {
                    if (points[i] == points[j]) samePoint++;
                    if (points[i][0] == points[j][0]) {
                        verticle++;
                        continue;
                    }
                    int deltaY = points[j][1] - points[i][1];
                    int deltaX = points[j][0] - points[i][0];
                    int b = deltaY / gcd(deltaY, deltaX);
                    int a = deltaX / gcd(deltaY, deltaX);
                    String str = b + "/" + a;
                    map.put(str, map.getOrDefault(str, 1) + 1);
                    res = Math.max(res, map.get(str) + samePoint);
                }
            }
            res = Math.max(res, verticle);
        }
        return res;
    }

    private int gcd(int a, int b) {
        if (a == 0) return b;
        return gcd(b % a, a);
    }
}
/**
 * o(n^2) 根据斜率去看哪些点在一条直线上 => 是0怎么办，90度怎么办
 * dy/dx => a/b
 * double slope = dy * 1.0 / dx; // 可能精度会不够，非常危险！！！ -> 不提倡
 */
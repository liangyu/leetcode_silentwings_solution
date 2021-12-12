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
        int res = 0;
        for (int i = 0; i < points.length; i++) {
            HashMap<String, Integer> map = new HashMap<>();
            int verticle = 0, same = 0;
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                if (points[i] == points[j]) {
                    same++;
                    continue;
                }
                int dy = points[j][1] - points[i][1];
                int dx = points[j][0] - points[i][0];

                if (dx == 0) {
                    verticle++;
                    continue;
                }

                int a = dy / gcd(dy, dx);
                int b = dx / gcd(dy, dx);

                String key = a + "#" + b;
                map.put(key, map.getOrDefault(key, 0) + 1);
            }

            res = Math.max(res, verticle + same + 1);
            for (String key : map.keySet()) {
                res = Math.max(res, map.get(key) + 1 + same); // + 1 is for i itself
            }
        }
        return res;
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}
/**
 * o(n^2) 根据斜率去看哪些点在一条直线上 => 是0怎么办，90度怎么办
 * dy/dx => a/b 化简成一个最简分数
 * double slope = dy * 1.0 / dx; // 可能精度会不够，非常危险！！！ -> 不提倡
 */
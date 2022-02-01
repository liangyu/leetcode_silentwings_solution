package LC2101_2400;
import java.util.*;
public class LC2152_MinimumNumberofLinestoCoverPoints {
    /**
     * You are given an array points where points[i] = [xi, yi] represents a point on an X-Y plane.
     *
     * Straight lines are going to be added to the X-Y plane, such that every point is covered by at least one line.
     *
     * Return the minimum number of straight lines needed to cover all the points.
     *
     * Input: points = [[0,1],[2,3],[4,5],[4,3]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= points.length <= 10
     * points[i].length == 2
     * -100 <= xi, yi <= 100
     * All the points are unique.
     * @param points
     * @return
     */
    // time = O(n^2 * 2^n), space = O(2^n)
    public int minimumLines(int[][] points) {
        int n = points.length;
        if (n == 1 || n == 2) return 1;

        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                int dx = x2 - x1, dy = y2 - y1, dg = gcd(dx, dy);
                dx /= dg;
                dy /= dg;
                int state = (1 << i) | (1 << j);

                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;
                    int x = points[k][0] - points[i][0];
                    int y = points[k][1] - points[i][1];
                    int g = gcd(x, y);
                    x /= g;
                    y /= g;
                    if (x == dx && y == dy) state |= (1 << k);
                }
                dp[state] = 1;
                // 注意：subset不包含state本身！subset:(0, state)
                for (int subset = (state - 1) & state; subset > 0; subset = (subset - 1) & state) {
                    dp[subset] = 1;
                }
            }
        }

        for (int state = 3; state < (1 << n); state++) {
            if (dp[state] == Integer.MAX_VALUE) {
                for (int subset = (state - 1) & state; subset > 0; subset = (subset - 1) & state) {
                    dp[state] = Math.min(dp[state], dp[subset] + dp[state - subset]);
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}

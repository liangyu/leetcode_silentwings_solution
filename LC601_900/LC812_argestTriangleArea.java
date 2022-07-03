package LC601_900;

public class LC812_argestTriangleArea {
    /**
     * Given an array of points on the X-Y plane points where points[i] = [xi, yi], return the area of the largest
     * triangle that can be formed by any three different points. Answers within 10-5 of the actual answer will be
     * accepted.
     *
     * Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
     * Output: 2.00000
     *
     * Input: points = [[1,0],[0,0],[0,1]]
     * Output: 0.50000
     *
     * Constraints:
     *
     * 3 <= points.length <= 50
     * -50 <= xi, yi <= 50
     * All the given points are unique.
     * @param points
     * @return
     */
    // time = O(n^3), space = O(1)
    public double largestTriangleArea(int[][] points) {
        double res = 0;
        for (int[] a : points) {
            for (int[] b : points) {
                for (int[] c : points) {
                    res = Math.max(res, Math.abs(area(a, b, c)));
                }
            }
        }
        return res / 2.0;
    }

    private int cross(int x1, int y1, int x2, int y2) {
        return x1 * y2 - x2 * y1;
    }

    private int area(int[] a, int[] b, int[] c) {
        return cross(b[0] - a[0], b[1] - a[1], c[0] - a[0], c[1] - a[1]);
    }
}
/**
 * 用叉积公式
 */

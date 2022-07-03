package LC901_1200;

public class LC1037_ValidBoomerang {
    /**
     * Given an array points where points[i] = [xi, yi] represents a point on the X-Y plane, return true if these points
     * are a boomerang.
     *
     * A boomerang is a set of three points that are all distinct and not in a straight line.
     *
     * Input: points = [[1,1],[2,3],[3,2]]
     * Output: true
     *
     * Constraints:
     *
     * points.length == 3
     * points[i].length == 2
     * 0 <= xi, yi <= 100
     * @param points
     * @return
     */
    // time = O(1), space = O(1)
    public boolean isBoomerang(int[][] points) {
        int[] a = points[0], b = points[1], c = points[2];
        return cross(b[0] - a[0], b[1] - a[1], c[0] - a[0], c[1] - a[1]) != 0;
    }

    private int cross(int x1, int y1, int x2, int y2) {
        return x1 * y2 - x2 * y1;
    }
}

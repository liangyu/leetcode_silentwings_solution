package LC901_1200;
import java.util.*;
public class LC963_MinimumAreaRectangleII {
    /**
     * You are given an array of points in the X-Y plane points where points[i] = [xi, yi].
     *
     * Return the minimum area of any rectangle formed from these points, with sides not necessarily parallel to the X
     * and Y axes. If there is not any such rectangle, return 0.
     *
     * Answers within 10-5 of the actual answer will be accepted.
     *
     * Input: points = [[1,2],[2,1],[1,0],[0,1]]
     * Output: 2.00000
     *
     * Input: points = [[0,1],[2,1],[1,1],[1,0],[2,0]]
     * Output: 1.00000
     *
     * Constraints:
     *
     * 1 <= points.length <= 50
     * points[i].length == 2
     * 0 <= xi, yi <= 4 * 10^4
     * All the given points are unique.
     * @param points
     * @return
     */
    // time = O(n^2logn), space = O(n^2)
    public double minAreaFreeRect(int[][] points) {
        int n = points.length;
        for (int[] p : points) {
            p[0] *= 2;
            p[1] *= 2;
        }

        List<long[]> q = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                int cx = (x1 + x2) / 2, cy = (y1 + y2) / 2;
                q.add(new long[]{cx, cy, get_dist(points[i], points[j]), i, j});
            }
        }

        Collections.sort(q, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                if (o1[0] == o2[0] && o1[1] == o2[1]) {
                    return o1[2] <= o2[2] ? -1 : 1;
                } else if (o1[0] == o2[0]) {
                    return o1[1] <= o2[1] ? -1 : 1;
                }
                return o1[0] <= o2[0] ? -1 : 1;
            }
        });

        double res = Double.MAX_VALUE;
        int m = q.size();
        for (int i = 0; i < m; i++) {
            int j = i + 1;
            while (j < m && q.get(i)[0] == q.get(j)[0] && q.get(i)[1] == q.get(j)[1] && q.get(i)[2] == q.get(j)[2]) j++;
            for (int a = i; a < j; a++) {
                for (int b = i; b < a; b++) {
                    int[] a1 = points[(int) q.get(a)[3]], a2 = points[(int) q.get(a)[4]];
                    int[] b1 = points[(int) q.get(b)[3]], b2 = points[(int) q.get(b)[4]];
                    double area = Math.sqrt(get_dist(a1, b1)) * Math.sqrt(get_dist(a1, b2));
                    if (area > 0) res = Math.min(res, area);
                }
            }
            i = j - 1;
        }
        return res == Double.MAX_VALUE ? 0 : res / 4;
    }

    private long get_dist(int[] a, int[] b) {
        long dx = a[0] - b[0];
        long dy = a[1] - b[1];
        return dx * dx + dy * dy;
    }
}
/**
 * C(n,4)
 * 已知三个点，可以直接求出第四个点 -> n^4 -> n^3
 * 两个对角线的中点重合,并且长度要相同 => 必定是矩形
 * C(n,2) -> n^2
 * 分类，每个集合内的对角线中点重合，且长度相同
 * 两条对角线必然属于一个集合，只要枚举集合内的两条对角线，不能重合
 * 按照中点和长度分类
 */

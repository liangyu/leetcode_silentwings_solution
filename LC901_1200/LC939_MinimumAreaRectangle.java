package LC901_1200;
import java.util.*;
public class LC939_MinimumAreaRectangle {
    /**
     * You are given an array of points in the X-Y plane points where points[i] = [xi, yi].
     *
     * Return the minimum area of a rectangle formed from these points, with sides parallel to the X and Y axes.
     * If there is not any such rectangle, return 0.
     *
     * Input: points = [[1,1],[1,3],[3,1],[3,3],[2,2]]
     * Output: 4
     *
     * Input: points = [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= points.length <= 500
     * points[i].length == 2
     * 0 <= xi, yi <= 4 * 10^4
     * All the given points are unique.
     * @param points
     * @return
     */
    // time = O(n^2), space = O(n)
    public int minAreaRect(int[][] points) {
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();

        for (int[] p : points) {
            map.putIfAbsent(p[0], new HashSet<>());
            map.get(p[0]).add(p[1]);
        }

        int n = points.length, res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];

                // check if p1 and p2 can make a diagonal
                if (x1 == x2 || y1 == y2) continue;

                // check p3 and p4
                int x3 = x2, y3 = y1;
                int x4 = x1, y4 = y2;
                if (!map.getOrDefault(x3, new HashSet<>()).contains(y3)) continue;
                if (!map.getOrDefault(x4, new HashSet<>()).contains(y4)) continue;

                res = Math.min(res, Math.abs(x2 - x1) * Math.abs(y2 - y1));
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    // S2
    // time = O(n^2), space = O(n)
    public int minAreaRect2(int[][] points) {
        HashSet<Integer> set = new HashSet<>();
        for (int[] p : points) set.add(helper(p));

        int n = points.length, res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int[] p = points[i], q = points[j];
                if (p[0] != q[0] && p[1] != q[1] && set.contains(helper(new int[]{p[0], q[1]})) && set.contains(helper(new int[]{q[0], p[1]}))) {
                    int area = Math.abs(p[0] - q[0]) * Math.abs(p[1] - q[1]);
                    res = Math.min(res, area);
                }
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    private int helper(int[] x) {
        return x[0] * 50000 + x[1];
    }
}
/**
 * 矩形都是"横平竖直"
 * 最少几个点确定一个矩形呢？
 * 只要2个点，对角线！！！
 * 比较直观的想法：这些点里面，两重遍历，知道A和B，就能求出C和D，看C，D是否in the set
 * 然后根据ABCD求出area，然后找到一个最小值
 * time complexity = O(n^2),但事实上不是特别高效
 * 但你怎么快速在一个set里找到C和D呢？
 * HashSet -> 可hash -> use String (Cx,Cy) -> Cx#Cy
 * TreeSet -> 可sorted -> int[] 直接看C, D坐标是否在set里 => O(logn)
 * 优化方法：C和D不需要在整个set里找
 * 如果我们确定A和B后，C的X和D的Y我们都知道了，那么其实我们只需要看C在以该X的坐标点里找，同理在以Y为纵坐标里的点去找D
 * HashSet<Integer> setB; // 装的都是所有与B横坐标相同的点的纵坐标
 * Cy?
 * HashSet<Integer> setA;
 * Dy?
 * y
 * ^    A       C
 * |
 * |    D       B
 * ------------------> x
 */
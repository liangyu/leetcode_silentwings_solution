package LC1801_2100;
import java.util.*;
public class LC2013_DetectSquares {
    /**
     * You are given a stream of points on the X-Y plane. Design an algorithm that:
     *
     * Adds new points from the stream into a data structure. Duplicate points are allowed and should be treated as
     * different points.
     * Given a query point, counts the number of ways to choose three points from the data structure such that the
     * three points and the query point form an axis-aligned square with positive area.
     * An axis-aligned square is a square whose edges are all the same length and are either parallel or perpendicular
     * to the x-axis and y-axis.
     *
     * Implement the DetectSquares class:
     *
     * DetectSquares() Initializes the object with an empty data structure.
     * void add(int[] point) Adds a new point point = [x, y] to the data structure.
     * int count(int[] point) Counts the number of ways to form axis-aligned squares with point point = [x, y] as
     * described above.
     *
     * Input
     * ["DetectSquares", "add", "add", "add", "count", "count", "add", "count"]
     * [[], [[3, 10]], [[11, 2]], [[3, 2]], [[11, 10]], [[14, 8]], [[11, 2]], [[11, 10]]]
     * Output
     * [null, null, null, null, 1, 0, null, 2]
     *
     * Constraints:
     *
     * point.length == 2
     * 0 <= x, y <= 1000
     * At most 5000 calls in total will be made to add and count.
     */
    // S1: HashMap
//    List<int[]> coordinates;
//    HashMap<String, Integer> map;
//    public LC2013_DetectSquares() {
//        coordinates = new ArrayList<>();
//        map = new HashMap<>();
//    }
//
//    // time = O(1), space = O(n)
//    public void add(int[] point) {
//        coordinates.add(point);
//        String key = point[0] + "@" + point[1];
//        map.put(key, map.getOrDefault(key, 0) + 1);
//    }
//
//    // time = O(n), space = O(n)
//    public int count(int[] point) {
//        int count = 0, px = point[0], py = point[1];
//        for (int[] coordinate : coordinates) {
//            int x = coordinate[0], y = coordinate[1];
//            if (px == x || py == y || Math.abs(px - x) != Math.abs(py - y)) continue; // check square diagonal
//            count += map.getOrDefault(x + "@" + py, 0) * map.getOrDefault(px + "@" + y, 0);
//        }
//        return count;
//    }

    // S2: Ennumeration
    private int[][] counts;
    public LC2013_DetectSquares() {
        counts = new int[1005][1005];
    }

    public void add(int[] point) {
        counts[point[0]][point[1]]++;
    }

    public int count(int[] point) {
        int x = point[0], y = point[1];
        int res = 0;

        for (int i = 0; i <= 1000; i++) {
            int d = Math.abs(x - i);
            if (d == 0) continue; // 边长不能为0，i与x不能重合
            int j = y + d;
            if (j >= 0 && j <= 1000) {
                res += counts[i][j] * counts[x][j] * counts[i][y];
            }
            j = y - d;
            if (j >= 0 && j <= 1000) {
                res += counts[i][j] * counts[x][j] * counts[i][y];
            }
        }
        return res;
    }
}
/**
 * 如何通过unique变量确定一个正方形
 * 最多5000次操作
 * 0 <= x, y <= 1000   => 只要遍历横坐标即可
 * 对角点只有2种可能性，只要遍历i轴即可，就是1000
 * 对于固定的i，就是要找y + d, y - d两个位置 O(1000)
 * 如果这是几何题，给出一条边，遍历对角点是最有效的方法
 * 因为一旦这两个角确定了，这个正方形或者长方形就确定了，比较高效。
 */
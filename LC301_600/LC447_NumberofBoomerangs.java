package LC301_600;
import java.util.*;
public class LC447_NumberofBoomerangs {
    /**
     * You are given n points in the plane that are all distinct, where points[i] = [xi, yi]. A boomerang is a tuple
     * of points (i, j, k) such that the distance between i and j equals the distance between i and k (the order of the
     * tuple matters).
     *
     * Return the number of boomerangs.
     *
     * Input: points = [[0,0],[1,0],[2,0]]
     * Output: 2
     *
     * Constraints:
     *
     * n == points.length
     * 1 <= n <= 500
     * points[i].length == 2
     * -10^4 <= xi, yi <= 10^4
     * All the points are unique.
     * @param points
     * @return
     */
    // time = O(n^2), space = O(n)
    public int numberOfBoomerangs(int[][] points) {
        int res = 0, n = points.length;
        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int j = 0; j < n; j++) {
                int val1 = (points[i][0] - points[j][0]) * (points[i][0] - points[j][0]);
                int val2 = (points[i][1] - points[j][1]) * (points[i][1] - points[j][1]);
                int dist = val1 + val2;
                map.put(dist, map.getOrDefault(dist, 0) + 1);
            }

            for (int key : map.keySet()) {
                int k = map.get(key);
                res += k * (k - 1);
            }
        }
        return res;
    }
}
/**
 * 固定一个点i，查找其他所有点到该点的距离，存进一个Map。
 * 假设到i距离为d的点的数目有n个，说明有n*(n-1)个(i,j,k)配对。
 * 因为(i,j,k)的次序有关系，遍历其他点的时候，要考察所有点而不是仅仅从i+1开始。
 */

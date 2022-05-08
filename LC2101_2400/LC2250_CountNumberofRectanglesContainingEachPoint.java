package LC2101_2400;
import java.util.*;
public class LC2250_CountNumberofRectanglesContainingEachPoint {
    /**
     * You are given a 2D integer array rectangles where rectangles[i] = [li, hi] indicates that ith rectangle has a
     * length of li and a height of hi. You are also given a 2D integer array points where points[j] = [xj, yj] is a
     * point with coordinates (xj, yj).
     *
     * The ith rectangle has its bottom-left corner point at the coordinates (0, 0) and its top-right corner point at
     * (li, hi).
     *
     * Return an integer array count of length points.length where count[j] is the number of rectangles that contain the
     * jth point.
     *
     * The ith rectangle contains the jth point if 0 <= xj <= li and 0 <= yj <= hi. Note that points that lie on the
     * edges of a rectangle are also considered to be contained by that rectangle.
     *
     * Input: rectangles = [[1,2],[2,3],[2,5]], points = [[2,1],[1,4]]
     * Output: [2,1]
     *
     * Input: rectangles = [[1,1],[2,2],[3,3]], points = [[1,3],[1,1]]
     * Output: [1,3]
     *
     * Constraints:
     *
     * 1 <= rectangles.length, points.length <= 5 * 10^4
     * rectangles[i].length == points[j].length == 2
     * 1 <= li, xj <= 10^9
     * 1 <= hi, yj <= 100
     * All the rectangles are unique.
     * All the points are unique.
     * @param rectangles
     * @param points
     * @return
     */
    // S1: Sort + Bucket
    // time = O(mlogm + nlogn), space = O(n)
    public int[] countRectangles(int[][] rectangles, int[][] points) {
        int m = rectangles.length, n = points.length;
        Arrays.sort(rectangles, (o1, o2) -> o1[0] - o2[0]);

        int[][] p = new int[n][3];
        for (int i = 0; i < n; i++) {
            p[i][0] = points[i][0];
            p[i][1] = points[i][1];
            p[i][2] = i;
        }
        Arrays.sort(p, ((o1, o2) -> o1[0] - o2[0]));

        int j = m - 1;
        int[] count = new int[101], res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (j >= 0 && rectangles[j][0] >= p[i][0]) {
                count[rectangles[j][1]]++;
                j--;
            }
            int total = 0;
            for (int h = 100; h >= p[i][1]; h--) total += count[h];
            res[p[i][2]] = total;
        }
        return res;
    }

    // S2
    // time = O((m + n) * logm), space = O(m)
    public int[] countRectangles2(int[][] rectangles, int[][] points) {
        int m = rectangles.length, n = points.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < m; i++) { // O(m)
            int x = rectangles[i][0], y = rectangles[i][1];
            map.putIfAbsent(y, new ArrayList<>());
            map.get(y).add(x);
        }
        for (int key : map.keySet()) Collections.sort(map.get(key)); // 最多sort 100次 O(h * mlogm)

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {  // O(n)
            int x = points[i][0], y = points[i][1];
            int count = 0;
            for (int h = 100; h >= 1; h--) {
                if (h < y) break;
                if (!map.containsKey(h)) continue;
                count += helper(map.get(h), x); // O(logm)
            }
            res[i] = count;
        }
        return res;
    }

    private int helper(List<Integer> nums, int t) {
        int left = 0, right = nums.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums.get(mid) < t) left = mid + 1;
            else right = mid - 1;
        }
        int idx = nums.get(left) >= t ? left : left + 1;
        return nums.size() - idx;
    }
}
/**
 * 2个属性 -> 将其中一个属性排序
 * 在高度方向上，pool里面的这些矩阵的高度值是参差不齐的，无法快速定位有多少矩阵的高度大于等y。
 * 常规的红黑树虽然支持二分定位这些高度值里第一个大于等于y的位置，但是它无法告诉大于等于y的值总共有多少个。
 * 1 <= hi, yj <= 100
 * 数下100个格子 -> 桶排序 -> 更新直方图
 * 最多遍历100次
 * 时间复杂度 = O(100*P)
 */
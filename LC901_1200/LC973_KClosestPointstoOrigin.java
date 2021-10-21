package LC901_1200;
import java.util.*;
public class LC973_KClosestPointstoOrigin {
    /**
     * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return
     * the k closest points to the origin (0, 0).
     *
     * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
     *
     * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
     *
     * Input: points = [[1,3],[-2,2]], k = 1
     * Output: [[-2,2]]
     *
     * Constraints:
     *
     * 1 <= k <= points.length <= 10^4
     * -10^4 < xi, yi < 10^4
     * @param points
     * @param k
     * @return
     */
    // S1: quick select
    // time = O(n), space = O(n)
    public int[][] kClosest(int[][] points, int k) {
        // corner case
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0 || k <= 0) {
            return new int[0][0];
        }

        List<long[]> arr = new ArrayList<>(); // {dist, idx}
        for (int i = 0; i < points.length; i++) {
            long d = (points[i][0]) * points[i][0] + points[i][1] * points[i][1];
            arr.add(new long[]{d, i});
        }

        long dist = quickselect(arr, 0, arr.size() - 1, k);

        List<int[]> res = new ArrayList<>();
        for (long[] x : arr) {
            if (x[0] <= dist) res.add(points[(int)x[1]]);
        }
        int[][] ans = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }

    private long quickselect(List<long[]> arr, int a, int b, int k) {
        long pivot = arr.get(a + (b - a) / 2)[0];

        int i = a, j = b, t = a;
        while (t <= j) {
            if (arr.get(t)[0] < pivot) swap(arr, t++, i++);
            else if (arr.get(t)[0] > pivot) swap(arr, t, j--);
            else t++;
        }

        if (i - a >= k) return quickselect(arr, a, i - 1, k);
        if (j - a + 1 >= k) return pivot;
        else return quickselect(arr, i + 1, b, k - (j - a + 1));
    }

    private void swap(List<long[]> nums, int i, int j) {
        long[] temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }

    // S2: PQ
    // time = O(nlogk), space = O(k)
    public int[][] kClosest2(int[][] points, int k) {
        // corner case
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0 || k <= 0) {
            return new int[0][0];
        }

        PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> (int) (o2[0] - o1[0]));
        for (int i = 0; i < points.length; i++) {
            long d = (points[i][0]) * points[i][0] + points[i][1] * points[i][1];
            pq.offer(new long[]{d, i});
            if (pq.size() > k) pq.poll();
        }

        int[][] res = new int[k][2];
        int idx = 0;
        while (!pq.isEmpty()) {
            int id = (int) pq.poll()[1];
            res[idx++] = points[id];
        }
        return res;
    }
}
/**
 * pq -> O(nlogk)
 * 单独考你的话 -> quick select 专门用来解决在一个无序数组里面解决第k大或者第k小的元素
 * 时间复杂度期望是O(n), worst case是O(n^2)
 */

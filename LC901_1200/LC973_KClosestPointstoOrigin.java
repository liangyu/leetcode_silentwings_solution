package LC901_1200;
import java.util.*;
public class LC973_KClosestPointstoOrigin {
    /**
     * We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
     *
     * (Here, the distance between two points on a plane is the Euclidean distance.)
     *
     * You may return the answer in any order.
     * The answer is guaranteed to be unique (except for the order that it is in.)
     *
     * Input: points = [[1,3],[-2,2]], K = 1
     * Output: [[-2,2]]
     *
     * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
     * Output: [[3,3],[-2,4]]
     *
     * Note:
     *
     * 1 <= K <= points.length <= 10000
     * -10000 < points[i][0] < 10000
     * -10000 < points[i][1] < 10000
     *
     * @param points
     * @param K
     * @return
     */
    // S1: maxHeap
    // time = O(nlogk), space = O(k)
    public int[][] kClosest(int[][] points, int K) {
        // corner case
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0 || K <= 0) {
            return null;
        }

        int[][] res = new int[K][2];
        PriorityQueue<Pair> maxHeap = new PriorityQueue<>(K, (o1, o2) -> o2.dist - o1.dist);

        // 10^10 = 10 * 10^9 = 10 * 2^30 > 2^32
        for(int[] p : points) { // O(n)
            Pair pair = new Pair(p[0], p[1]);
            if (maxHeap.size() >= K) {
                if (maxHeap.peek().dist > pair.dist) { // O(logk)
                    maxHeap.poll();
                }
            }
            maxHeap.offer(pair); // O(logk)
        }

        int i = 0;
        while (!maxHeap.isEmpty()) { // O(k)
            Pair p = maxHeap.poll();
            res[i][0] = p.x;
            res[i++][1] = p.y;
        }
        return res;
    }

    private class Pair {
        private int x;
        private int y;
        private int dist;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
            dist = x * x + y * y;
        }
    }

    // S2: Divide & Conquer
    // time = O(n), space = O(n)
    public int[][] kClosest2(int[][] points, int K) {
        // corner case
        if (points == null || points.length == 0 || points[0] == null || points[0].length == 0 || K <= 0) return new int[0][0];

        return quickSelect(points, 0, points.length - 1, K - 1);
    }

    private int[][] quickSelect(int[][] points, int start, int end, int idx) {
        if (start > end) return new int[0][0];

        int pivot = partition(points, start, end); // O(n)
        if (pivot == idx) return Arrays.copyOf(points, idx + 1); // O(k)
        return pivot < idx ? quickSelect(points, pivot + 1, end, idx) : quickSelect(points, start, pivot - 1, idx); // O(n)
    }

    private int partition(int[][] points, int start, int end) { // O(n)
        int[] p = points[start];
        int dist = p[0] * p[0] + p[1] * p[1];
        int i = start + 1, j = end; // start选定作为起始比较对象和最后被swap的对象，左边就从start + 1开始向内遍历

        while (true) {
            while (i <= j && points[i][0] * points[i][0] + points[i][1] * points[i][1] <= dist) i++; // == 既可以并入左边，也可以并入右边，都没有关系
            while (i <= j && points[j][0] * points[j][0] + points[j][1] * points[j][1] > dist) j--;

            if (i > j) break; // i, j 越过表明所有元素已经遍历完，可以跳出loop

            // points[i]与points[j]交换，保证比points[start]小的都在左边，而比points[start]大的都在右边
            int[] temp = points[i];
            points[i] = points[j];
            points[j] = temp;
        }

        // 最后break出loop，肯定就是i, j越过，由于是和最左边的points[start]进行swap，所以对应的swap对象是j，而不是i
        // 因为此刻j指向比points[start]小的那片区域，而i指向比points[start]大的那片区域，所以要和j进行swap
        points[start] = points[j];
        points[j] = p;
        return j;
    }
}

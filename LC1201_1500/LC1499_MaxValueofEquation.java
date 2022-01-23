package LC1201_1500;
import java.util.*;
public class LC1499_MaxValueofEquation {
    /**
     * You are given an array points containing the coordinates of points on a 2D plane, sorted by the x-values,
     * where points[i] = [xi, yi] such that xi < xj for all 1 <= i < j <= points.length. You are also given an integer k.
     *
     * Return the maximum value of the equation yi + yj + |xi - xj| where |xi - xj| <= k and 1 <= i < j <= points.length.
     *
     * It is guaranteed that there exists at least one pair of points that satisfy the constraint |xi - xj| <= k.
     *
     * Input: points = [[1,3],[2,0],[5,10],[6,-10]], k = 1
     * Output: 4
     *
     * Constraints:
     *
     * 2 <= points.length <= 10^5
     * points[i].length == 2
     * -108 <= xi, yi <= 10^8
     * 0 <= k <= 2 * 10^8
     * xi < xj for all 1 <= i < j <= points.length
     * xi form a strictly increasing sequence.
     * @param points
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int findMaxValueOfEquation(int[][] points, int k) {
        Deque<Integer> deque = new LinkedList<>();

        int n = points.length, res = Integer.MIN_VALUE;
        for (int j = 0; j < n; j++) {
            while (!deque.isEmpty() && points[j][0] - points[deque.peekFirst()][0] > k) deque.pollFirst();
            if (!deque.isEmpty()) {
                int xi = points[deque.peekFirst()][0], yi = points[deque.peekFirst()][1];
                int xj = points[j][0], yj = points[j][1];
                res = Math.max(res, yi + yj - xi + xj);
            }
            while (!deque.isEmpty() && points[deque.peekLast()][1] - points[deque.peekLast()][0] <= points[j][1] - points[j][0]) {
                deque.pollLast();
            }
            deque.offer(j);
        }
        return res;
    }
}
/**
 * xi < xj => max {yi + yj - xi + xj}
 * => xj + yj + max{-xi + yi}  for |xi - xj| <= k
 * 固定一个j点，找到-xi + yi的一个最大值
 * sliding window maximum => deque, O(n)
 * 队首的坐标和新来的坐标是否相差太远
 * x x x y j
 * if j > y，poll y out 因为我们要求最大值，y永无出头之日了
 */

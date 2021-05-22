package LC001_300;
import java.util.*;
public class LC218_TheSkylineProblem {
    /**
     * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed
     * from a distance. Given the locations and heights of all the buildings, return the skyline formed by these
     * buildings collectively.
     *
     * The geometric information of each building is given in the array buildings where
     * buildings[i] = [lefti, righti, heighti]:
     *
     * lefti is the x coordinate of the left edge of the ith building.
     * righti is the x coordinate of the right edge of the ith building.
     * heighti is the height of the ith building.
     * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
     *
     * The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form
     * [[x1,y1],[x2,y2],...]. Each key point is the left endpoint of some horizontal segment in the skyline except the
     * last point in the list, which always has a y-coordinate 0 and is used to mark the skyline's termination where the
     * rightmost building ends. Any ground between the leftmost and rightmost buildings should be part of
     * the skyline's contour.
     *
     * Note: There must be no consecutive horizontal lines of equal height in the output skyline. For instance,
     * [...,[2 3],[4 5],[7 5],[11 5],[12 7],...] is not acceptable; the three lines of height 5 should be merged into
     * one in the final output as such: [...,[2 3],[4 5],[12 7],...]
     *
     * Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
     * Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
     *
     * Constraints:
     *
     * 1 <= buildings.length <= 10^4
     * 0 <= lefti < righti <= 2^31 - 1
     * 1 <= heighti <= 2^31 - 1
     * buildings is sorted by lefti in non-decreasing order.
     * @param buildings
     * @return
     */
    // S1: Sweep Line
    // time = O(n^2), space = O(n)
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (buildings == null || buildings.length == 0 || buildings[0] == null || buildings[0].length == 0) {
            return res;
        }

        List<int[]> heights = new ArrayList<>();
        for (int[] b : buildings) {
            heights.add(new int[]{b[0], -b[2]});
            heights.add(new int[]{b[1], b[2]});
        }

        Collections.sort(heights, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        pq.offer(0);

        int prev = 0;
        for (int[] h : heights) {
            if (h[1] < 0) pq.offer(-h[1]);
            else pq.remove(h[1]);
            int cur = pq.peek();
            if (prev != cur) {
                res.add(Arrays.asList(h[0], cur));
                prev = cur;
            }
        }
        return res;
    }

    // S2: Interval
    // time = O(n^2), space = O(n)
    public List<List<Integer>> getSkyline2(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (buildings == null || buildings.length == 0 || buildings[0] == null || buildings[0].length == 0) {
            return res;
        }

        List<EndPoint> eps = new ArrayList<>();
        for (int[] b : buildings) {
            eps.add(new EndPoint(b[0], b[2], true));
            eps.add(new EndPoint(b[1], b[2], false));
        }

        Collections.sort(eps);
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

        for (EndPoint ep : eps) {
            if (ep.isStart) {
                int maxHeight = maxHeap.isEmpty() ? 0 : maxHeap.peek();
                if (maxHeight < ep.height) {
                    res.add(Arrays.asList(ep.val, ep.height));
                }
                maxHeap.offer(ep.height);
            } else {
                maxHeap.remove(ep.height);
                int maxHeight = maxHeap.isEmpty() ? 0 : maxHeap.peek();
                if (maxHeight < ep.height) {
                    res.add(Arrays.asList(ep.val, maxHeight));
                }
            }
        }
        return res;
    }

    private class EndPoint implements Comparable<EndPoint> {
        private int val;
        private int height;
        private boolean isStart;
        public EndPoint(int val, int height, boolean isStart) {
            this.val = val;
            this.height = height;
            this.isStart = isStart;
        }

        @Override
        public int compareTo(EndPoint that) {
            if (this.val != that.val) {
                return this.val - that.val;
            } else {
                // case 1: both are left endpoints -> h l
                if (this.isStart && that.isStart) {
                    return that.height - this.height;
                } else if (!this.isStart && !that.isStart) { // case 2: both are right endpoint -> l h
                    return this.height - that.height;
                } else { // case 3 & 4: one left, one right -> left -> 1st
                    return this.isStart ? -1 : 1;
                }
            }
        }
    }
}
/**
 * sweep line 扫描线算法
 * 所有点：起始点 -> 最高点; 终止点 -> 第二高点
 */

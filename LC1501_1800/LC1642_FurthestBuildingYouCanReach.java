package LC1501_1800;
import java.util.*;
public class LC1642_FurthestBuildingYouCanReach {
    /**
     * You are given an integer array heights representing the heights of buildings, some bricks, and some ladders.
     *
     * You start your journey from building 0 and move to the next building by possibly using bricks or ladders.
     *
     * While moving from building i to building i+1 (0-indexed),
     *
     * If the current building's height is greater than or equal to the next building's height, you do not need a
     * ladder or bricks.
     * If the current building's height is less than the next building's height, you can either use one ladder or
     * (h[i+1] - h[i]) bricks.
     * Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.
     *
     * Input: heights = [4,2,7,6,9,14,12], bricks = 5, ladders = 1
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= heights.length <= 10^5
     * 1 <= heights[i] <= 10^6
     * 0 <= bricks <= 10^9
     * 0 <= ladders <= heights.length
     * @param heights
     * @param bricks
     * @param ladders
     * @return
     */
    // time = O(nlogk), space = O(k)  k: the size of heap
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // corner case
        if (heights == null || heights.length == 0) return 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < heights.length - 1; i++) {
            int diff = heights[i + 1] - heights[i];
            if (diff > 0) pq.offer(diff);
            if (pq.size() > ladders) bricks -= pq.poll();
            if (bricks < 0) return i;
        }
        return heights.length - 1;
    }
}

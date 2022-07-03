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
    // time = O(nlogn), space = O(n)
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

    // S2: TreeMap
    // time = O(nlogn), space = O(n)
    public int furthestBuilding2(int[] heights, int bricks, int ladders) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int n = heights.length, count = 0;

        for (int i = 1; i < n; i++) {
            int diff = heights[i] - heights[i - 1];
            if (diff <= 0) continue;
            map.put(diff, map.getOrDefault(diff, 0) + 1);
            if (count < ladders) count++;
            else {
                if (bricks < map.firstKey()) return i - 1;
                diff = map.firstKey();
                bricks -= diff;
                map.put(diff, map.get(diff) - 1);
                if (map.get(diff) == 0) map.remove(diff);
            }
        }
        return n - 1;
    }
}
/**
 * 只考虑要爬升的这些楼
 * ladders 要用在刀刃上
 * brick 要用在跨度比较小的地方
 * 如果我们要爬升的楼 <= ladders，肯定能爬过去
 * 如果要爬升ladders + 1个楼，ladders肯定不够用，肯定至少一栋楼的爬升需要用到bricks
 * 选择哪栋楼用bricks呢？肯定是跨度差中最小的。
 * ladders = 6
 * x x x B x x x | Y Y Y Y Y
 * 确定的决策不管以后如何变化，都不会改变
 * 永远不可能是跨度最大的top 6，永远是个明智的选择
 * B x x B x x x Y | Y Y Y Y  -> 接下来里跨度最小的用brick
 * 同理，以此往复
 * 如果发现brick不够了，这栋楼是进不来的
 * 构造性问题，贪心法
 */
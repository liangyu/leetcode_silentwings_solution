package LC601_900;
import java.util.*;
public class LC815_BusRoutes {
    /**
     * You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats
     * forever.
     *
     * For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence
     * 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
     * You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop
     * target. You can travel between bus stops by buses only.
     *
     * Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.
     *
     * Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= routes.length <= 500.
     * 1 <= routes[i].length <= 10^5
     * All the values of routes[i] are unique.
     * sum(routes[i].length) <= 10^5
     * 0 <= routes[i][j] < 10^6
     * 0 <= source, target < 10^6
     * @param routes
     * @param source
     * @param target
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) return 0;
        HashMap<Integer, List<Integer>> map = new HashMap<>(); // stop2bus
        for (int i = 0; i < routes.length; i++) {
            for (int j : routes[i]) {
                map.putIfAbsent(j, new ArrayList<>());
                map.get(j).add(i);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        HashSet<Integer> stopSet = new HashSet<>();
        HashSet<Integer> busSet = new HashSet<>();
        stopSet.add(source);


        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                for (int bus : map.get(cur)) {
                    if (busSet.add(bus)) {
                        for (int next : routes[bus]) {
                            if (next == target) return step + 1;
                            if (stopSet.add(next)) queue.offer(next);
                        }
                    }
                }
            }
            step++;
        }
        return -1; // cannot reach target
    }
}
/**
 * bus2stop
 * stop2bus
 * 最小，起点和终点给了, 路径 => bfs
 * 本质：中间经历几个stop
 * 如何从该stop到达下一个stop
 * stop => bus1 => stop1
 *      => bus2 => stop2
 *              => stop3
 * 双重审核去重，去重：
 * 1. 乘过的bus
 * 2. 经过的stop
 */

package LC901_1200;
import java.util.*;
public class LC1059_AllPathsfromSourceLeadtoDestination {
    /**
     * Given the edges of a directed graph where edges[i] = [ai, bi] indicates there is an edge between nodes ai and bi,
     * and two nodes source and destination of this graph, determine whether or not all paths starting from source
     * eventually, end at destination, that is:
     *
     * At least one path exists from the source node to the destination node
     * If a path exists from the source node to a node with no outgoing edges, then that node is equal to destination.
     * The number of possible paths from source to destination is a finite number.
     * Return true if and only if all roads from source lead to destination.
     *
     * Input: n = 3, edges = [[0,1],[0,2]], source = 0, destination = 2
     * Output: false
     *
     * Constraints:
     *
     * 1 <= n <= 10^4
     * 0 <= edges.length <= 10^4
     * edges.length == 2
     * 0 <= ai, bi <= n - 1
     * 0 <= source <= n - 1
     * 0 <= destination <= n - 1
     * The given graph may have self-loops and parallel edges.
     * @param n
     * @param edges
     * @param source
     * @param destination
     * @return
     */
    // S1
    // time = O(V + E), space = O(V + E)
    public boolean leadsToDestination(int n, int[][] edges, int source, int destination) {
        // corner case
        if (edges == null || edges.length == 0) {
            if (source == destination) return true;
            return false;
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.putIfAbsent(edge[0], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
        }

        int[] status = new int[n];

        for (int key : map.keySet()) {
            if (containsCycle(map, status, source, destination)) return false;
        }
        return true;
    }

    private boolean containsCycle(HashMap<Integer, List<Integer>> map, int[] status, int cur, int destination) {
        if (status[cur] == 1) return true;
        if (status[cur] == 2) return false;

        status[cur] = 1;

        if (map.containsKey(cur)) {
            for (int next : map.get(cur)) {
                if (containsCycle(map, status, next, destination)) return true;
            }
        } else {
            if (cur == destination) {
                status[cur] = 2;
                return false;
            }
            return true;
        }
        status[cur] = 2;
        return false;
    }

    // S2:
    // time = O(V + E), space = O(V + E)
    public boolean leadsToDestination2(int n, int[][] edges, int source, int destination) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.putIfAbsent(edge[0], new ArrayList<>());
            map.get(edge[0]).add(edge[1]);
        }
        int[] status = new int[n];
        return dfs(map, status, source, destination);
    }

    private boolean dfs(HashMap<Integer, List<Integer>> map, int[] status, int cur, int dest) {
        status[cur] = 2;
        if (!map.containsKey(cur)) {
            if (cur != dest) return false;
            else {
                status[cur] = 1;
                return true;
            }
        }

        for (int next : map.get(cur)) {
            if (status[next] == 1) continue; // 注意这里是continue，而不是直接返回true!!!
            if (status[next] == 2) return false;
            if (!dfs(map, status, next, dest)) return false;
        }
        status[cur] = 1;
        return true;
    }
}

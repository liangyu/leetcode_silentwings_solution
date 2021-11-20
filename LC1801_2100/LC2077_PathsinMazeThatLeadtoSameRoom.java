package LC1801_2100;
import java.util.*;
public class LC2077_PathsinMazeThatLeadtoSameRoom {
    /**
     * A maze consists of n rooms numbered from 1 to n, and some rooms are connected by corridors. You are given a 2D
     * integer array corridors where corridors[i] = [room1i, room2i] indicates that there is a corridor connecting
     * room1i and room2i, allowing a person in the maze to go from room1i to room2i and vice versa.
     *
     * The designer of the maze wants to know how confusing the maze is. The confusion score of the maze is the number
     * of different cycles of length 3.
     *
     * For example, 1 → 2 → 3 → 1 is a cycle of length 3, but 1 → 2 → 3 → 4 and 1 → 2 → 3 → 2 → 1 are not.
     * Two cycles are considered to be different if one or more of the rooms visited in the first cycle is not in the
     * second cycle.
     *
     * Return the confusion score of the maze.
     *
     * Input: n = 5, corridors = [[1,2],[5,2],[4,1],[2,4],[3,1],[3,4]]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= n <= 1000
     * 1 <= corridors.length <= 5 * 10^4
     * corridors[i].length == 2
     * 1 <= room1i, room2i <= n
     * room1i != room2i
     * There are no duplicate corridors.
     * @param n
     * @param corridors
     * @return
     */
    // time = O(n^2), space = O(n)
    private int res = 0;
    public int numberOfPaths(int n, int[][] corridors) {
        HashSet<Integer>[] graph = new HashSet[n + 1];
        for (int i = 0; i <= n; i++) graph[i] = new HashSet<>();
        for (int[] c : corridors) {
            int a = c[0], b = c[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        for (int i = 0; i < n; i++) {
            dfs(graph, i, i, 1);
        }
        return res;
    }

    private void dfs(HashSet<Integer>[] graph, int cur, int start, int count) {
        // base case
        if (count == 3) {
            if (graph[cur].contains(start)) res++;
            return;
        }

        for (int next : graph[cur]) {
            if (next < cur) continue;
            dfs(graph, next, start, count + 1);
        }
    }
}

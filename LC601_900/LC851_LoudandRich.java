package LC601_900;
import java.util.*;
public class LC851_LoudandRich {
    /**
     * There is a group of n people labeled from 0 to n - 1 where each person has a different amount of money and a
     * different level of quietness.
     *
     * You are given an array richer where richer[i] = [ai, bi] indicates that ai has more money than bi and an integer
     * array quiet where quiet[i] is the quietness of the ith person. All the given data in richer are logically correct
     * (i.e., the data will not lead you to a situation where x is richer than y and y is richer than x at the same time).
     *
     * Return an integer array answer where answer[x] = y if y is the least quiet person (that is, the person y with
     * the smallest value of quiet[y]) among all people who definitely have equal to or more money than the person x.
     *
     * Input: richer = [[1,0],[2,1],[3,1],[3,7],[4,3],[5,3],[6,3]], quiet = [3,2,5,4,6,1,7,0]
     * Output: [5,5,2,5,4,5,6,7]
     *
     * Constraints:
     *
     * n == quiet.length
     * 1 <= n <= 500
     * 0 <= quiet[i] < n
     * All the values of quiet are unique.
     * 0 <= richer.length <= n * (n - 1) / 2
     * 0 <= ai, bi < n
     * ai != bi
     * All the pairs of richer are unique.
     * The observations in richer are all logically consistent.
     * @param richer
     * @param quiet
     * @return
     */
    // S1
    // time = O(n^2), space = O(n^2)
    int min = 0, minIdx = -1;
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int n = quiet.length;
        if (n == 1) return quiet;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int[] x : richer) graph[x[1]].add(x[0]);

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[n];
            min = quiet[i];
            minIdx = -1;
            visited[i] = true;
            dfs(graph, i, quiet, visited);
            res[i] = minIdx == -1 ? i : minIdx;
        }
        return res;
    }

    private void dfs(List<Integer>[] graph, int cur, int[] quiet, boolean[] visited) {
        for (int next : graph[cur]) {
            if (visited[next]) continue;
            visited[next] = true;
            if (quiet[next] < min) {
                min = quiet[next];
                minIdx = next;
            }
            dfs(graph, next, quiet, visited);
        }
    }

    // S2: dfs (最优解!)
    // time = O(n^2), space = O(n^2)
    public int[] loudAndRich2(int[][] richer, int[] quiet) {
        int n = quiet.length;

        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] x : richer) graph[x[1]].add(x[0]);

        int[] res = new int[n];
        Arrays.fill(res, -1);

        for (int i = 0; i < n; i++) {
            dfs(graph, i, quiet, res);
        }
        return res;
    }

    private int dfs(List<Integer>[] graph, int cur, int[] quiet, int[] res) {
        if (res[cur] == -1) { // if not visited, then go to dfs; otherwise can work as memo to directly return
            res[cur] = cur;
            for (int next : graph[cur]) {
                int candidate = dfs(graph, next, quiet, res);
                if (quiet[res[cur]] > quiet[candidate]) {
                    res[cur] = candidate;
                }
            }
        }
        return res[cur];
    }
}

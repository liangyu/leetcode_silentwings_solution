package LC1501_1800;
import java.util.*;
public class LC1697_CheckingExistenceofEdgeLengthLimitedPaths {
    /**
     * An undirected graph of n nodes is defined by edgeList, where edgeList[i] = [ui, vi, disi] denotes an edge
     * between nodes ui and vi with distance disi. Note that there may be multiple edges between two nodes.
     *
     * Given an array queries, where queries[j] = [pj, qj, limitj], your task is to determine for each queries[j]
     * whether there is a path between pj and qj such that each edge on the path has a distance strictly less than
     * limitj .
     *
     * Return a boolean array answer, where answer.length == queries.length and the jth value of answer is true if
     * there is a path for queries[j] is true, and false otherwise.
     *
     * Input: n = 3, edgeList = [[0,1,2],[1,2,4],[2,0,8],[1,0,16]], queries = [[0,1,2],[0,2,5]]
     * Output: [false,true]
     *
     * Constraints:
     *
     * 2 <= n <= 10^5
     * 1 <= edgeList.length, queries.length <= 10^5
     * edgeList[i].length == 3
     * queries[j].length == 3
     * 0 <= ui, vi, pj, qj <= n - 1
     * ui != vi
     * pj != qj
     * 1 <= disi, limitj <= 10^9
     * There may be multiple edges between two nodes.
     * @param n
     * @param edgeList
     * @param queries
     * @return
     */
    // time = O(nlogn), space = O(n)
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        boolean[] res = new boolean[queries.length];

        for (int i = 0; i < n; i++) parent[i] = i;

        // modify queries with index
        int[][] que = new int[queries.length][4];
        for (int i = 0; i < queries.length; i++) {
            que[i][0] = queries[i][0];
            que[i][1] = queries[i][1];
            que[i][2] = queries[i][2];
            que[i][3] = i;
        }
        // step 1: sort by limit weight of edges and queries
        Arrays.sort(edgeList, (o1, o2) -> o1[2] - o2[2]);
        Arrays.sort(que, (o1, o2) -> o1[2] - o2[2]);

        int i = 0;
        for (int[] q : que) {
            while (i < edgeList.length && edgeList[i][2] < q[2]) {
                int a = edgeList[i][0];
                int b = edgeList[i][1];
                if (find(a) != find(b)) union(a, b);
                i++;
            }
            res[q[3]] = (find(q[0]) == find(q[1]));
        }
        return res;
    }

    // Union Find
    private int[] parent = new int[100001];
    private int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[x] = y;
        else parent[y] = x;
    }
}

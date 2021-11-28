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
    int[] parent;
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        boolean[] res = new boolean[queries.length];

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
/**
 * 我们考虑一下如果只有一个query的话我们会怎么做？
 * 显然，我们只要考虑所有权重小于limit的那些edges，看看这些edges能否把query的两个node连接在一起。
 * 这是一个典型的Union Find的解法，时间复杂度近似为O(E).
 * 但是本题有多个queries，如果对于每个query都独立解决的话，时间复杂度大致是O(QE)，这样来看是会TLE的。
 * limit越小，要处理的边的数目就越少。
 * 解决方案很简单。我们优先解决limit低的query，因为它所能用到的edge数目更少，我们只需要查看少量的edges看它是否能够成query两点的连通图。
 * 然后我们再解决limit较高的query，此时只需要在已经构建的图里面再添加若干个新edge即可（因为放宽了对权重的要求），再判断query的两点是否联通.
 * 可见，如果我们将所有的queries按照limit从小到大排列进行处理，那么相应地我们只需要按照权重从小到大地添加边就行了。
 * 在构建联通图的过程中，每条边只需要处理一遍。
 * 所以本题的时间复杂度可以近似认为是O(ElogE)，瓶颈在于对所有edges的排序。
 */
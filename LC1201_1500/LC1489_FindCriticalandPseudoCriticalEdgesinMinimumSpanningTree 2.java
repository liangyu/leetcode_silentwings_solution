package LC1201_1500;
import java.util.*;
public class LC1489_FindCriticalandPseudoCriticalEdgesinMinimumSpanningTree {
    /**
     * Given a weighted undirected connected graph with n vertices numbered from 0 to n - 1, and an array edges where
     * edges[i] = [ai, bi, weighti] represents a bidirectional and weighted edge between nodes ai and bi. A minimum
     * spanning tree (MST) is a subset of the graph's edges that connects all vertices without cycles and with the minimum possible total edge weight.
     *
     * Find all the critical and pseudo-critical edges in the given graph's minimum spanning tree (MST). An MST edge
     * whose deletion from the graph would cause the MST weight to increase is called a critical edge. On the other
     * hand, a pseudo-critical edge is that which can appear in some MSTs but not all.
     *
     * Note that you can return the indices of the edges in any order.
     *
     * Input: n = 5, edges = [[0,1,1],[1,2,1],[2,3,2],[0,3,2],[0,4,3],[3,4,3],[1,4,6]]
     * Output: [[0,1],[2,3,4,5]]
     *
     * Constraints:
     *
     * 2 <= n <= 100
     * 1 <= edges.length <= min(200, n * (n - 1) / 2)
     * edges[i].length == 3
     * 0 <= ai < bi < n
     * 1 <= weighti <= 1000
     * All pairs (ai, bi) are distinct.
     * @param n
     * @param edges
     * @return
     */
    // S1: Kruskal
    // time = O(m^2 * a(n)), space = O(m + n)
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        List<int[]> edge = new LinkedList<>();
        for (int i = 0; i < edges.length; i++) {
            edge.add(new int[]{edges[i][0], edges[i][1], edges[i][2], i});
        }
        Collections.sort(edge, ((o1, o2) -> o1[2] - o2[2])); // O(mlogm)
        int minMST = kruskal(edge, -1, n);
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        for (int i = 0; i < edge.size(); i++) {
            int mst = kruskal(edge, i, n);
            if (mst > minMST) set1.add(edge.get(i)[3]);
        }

        for (int i = 0; i < edge.size(); i++) {
            if (set1.contains(edge.get(i)[3])) continue;
            int[] x = edge.get(i);
            edge.add(0, x);
            int mst = kruskal(edge, -1, n);
            if (mst == minMST) set2.add(x[3]);
            edge.remove(0);
        }

        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>(set1));
        res.add(new ArrayList<>(set2));
        return res;
    }

    private int[] parent;
    private int kruskal(List<int[]> edge, int k, int n) {  // O(m * a(n))
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        int res = 0;
        for (int i = 0; i < edge.size(); i++) {
            if (i == k) continue;
            int[] x = edge.get(i);
            int a = x[0], b = x[1];
            if (findParent(a) != findParent(b)) {
                union(a, b);
                res += x[2];
            }
        }

        for (int i = 0; i < n; i++) {
            if (findParent(i) != findParent(0)) return Integer.MAX_VALUE; // 所有点没有都连起来，是伪树
        }
        return res;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
/**
 * 根据关键边的定义，如果删除了它，那么无法生成最初数值的MST。
 * 所以我们挨个试每条边，满足这个条件的就是关键边。我们将他们放入Set1.
 * 伪关键边的定义是，如果删除了它，那么依然可以生成一棵最小数值的MST；但是如果如果用了它，也能生成一棵最小数值的MST。
 * 前者很好验证，就是Set1的补集。后者怎么验证呢？我们可以强迫在构建MST的时候使用这条边。
 * 如何最小程度地改动原来的MST生成代码来实现这个功能呢？
 * 考虑到排序后的edges数组的第一条边总是会被用到的，我们就把待考察的这条边放在edges数组的第一个，那么就可以保证在构建MST的时候用上它。
 * 如果生成的MST与之前的最小输出的相同，那么就符合后者的条件，放入Set2. 因此，Set2中那些不属于Set1的边，就是所求的第二类边。
 * set1: delete it and you will get a larger MST
 * set2: delete it and you will get the same-valued MST
 *       force to use it, and you will get the same-valued MST
 * Kruskal
 * {edges} sorted by weight
 * [edge, edge0, edge1, edge2, ..., edgek,...
 */
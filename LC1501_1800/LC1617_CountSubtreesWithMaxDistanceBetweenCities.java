package LC1501_1800;
import java.util.*;
public class LC1617_CountSubtreesWithMaxDistanceBetweenCities {
    /**
     * There are n cities numbered from 1 to n. You are given an array edges of size n-1, where edges[i] = [ui, vi]
     * represents a bidirectional edge between cities ui and vi. There exists a unique path between each pair of cities.
     * In other words, the cities form a tree.
     *
     * A subtree is a subset of cities where every city is reachable from every other city in the subset, where the path
     * between each pair passes through only the cities from the subset. Two subtrees are different if there is a city
     * in one subtree that is not present in the other.
     *
     * For each d from 1 to n-1, find the number of subtrees in which the maximum distance between any two cities in
     * the subtree is equal to d.
     *
     * Return an array of size n-1 where the dth element (1-indexed) is the number of subtrees in which the maximum
     * distance between any two cities is equal to d.
     *
     * Notice that the distance between the two cities is the number of edges in the path between them.
     *
     * Input: n = 4, edges = [[1,2],[2,3],[2,4]]
     * Output: [3,4,0]
     *
     * Constraints:
     *
     * 2 <= n <= 15
     * edges.length == n-1
     * edges[i].length == 2
     * 1 <= ui, vi <= n
     * All pairs (ui, vi) are distinct.
     * @param n
     * @param edges
     * @return
     */
    // time = O(n * 2^n), space = O(n)
    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        // build graph
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int a = edge[0] - 1, b = edge[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        boolean[] allow = new boolean[n];
        int[] dist = new int[n];
        int[] count = new int[n];
        for (int state = 1; state < (1 << n); state++) {
            Arrays.fill(allow, false);
            int start = -1, nodeNum = 0;
            for (int i = 0; i < n; i++) {
                if (((state >> i) & 1) == 1) {
                    allow[i] = true;
                    start = i;
                    nodeNum++;
                }
            }

            Arrays.fill(dist, -1);
            int[] b = bfs(graph, start, dist, allow); // [maxDist, id]
            int countVisited = 0;
            for (int x : dist) countVisited += (x != -1 ? 1 : 0);
            if (countVisited != nodeNum) continue;

            Arrays.fill(dist, -1);
            int[] c = bfs(graph, b[1], dist, allow);
            int maxDist = c[0];
            count[maxDist]++;
        }

        int[] res = new int[n - 1];
        for (int i = 0; i < n - 1; i++) res[i] = count[i + 1];
        return res;
    }

    private int[] bfs(List<Integer>[] graph, int start, int[] dist, boolean[] allow) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        dist[start] = 0;
        int maxDist = 0, id = start;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int next : graph[cur]) {
                if (!allow[next]) continue;
                if (dist[next] == -1) {
                    queue.offer(next);
                    dist[next] = dist[cur] + 1;
                    if (dist[next] > maxDist) {
                        maxDist = dist[next];
                        id = next;
                    }
                }
            }
        }
        return new int[]{maxDist, id};
    }
}
/**
 * max diameter = d, what are the subtrees? => constructive
 * 构造性问题，比较有难度
 * Given a subtree, what is its max diameter? => deterministic
 * 这个题目中节点数目小于等于15，穷举所有subtree（包括非法的不连通的图），也就是2^15=32768种，这是可以接受的。
 * 可以用bit mask来枚举所有的tree
 * (1,0,1...,1,0)
 * 很大概率subtree并不合法，因为要求是连通的
 * loop这些所有可能，大部分都是非法的
 * 接下来算下最大直径
 * 给定了一棵树的拓扑结构，如何计算它的最长直径呢？
 * 这是一个经典的问题，有着经典的O(n)的解法，ref: LC1245
 * 1. start with any node A, find the farthest point B w.r.t A  => bfs
 * we claim B is one of the two points of the max diameter
 * 2. start with B, find the farthest point C w.r.t. B
 * the max diameter is B -> C
 * i.e.如果我们从任意一个点出发，找到距离它最远的一个点B,这个点一定是最大直径的一个端点，同理从B出发找到距离B最远的一个端点C的话，
 * C也是最大直径的一个端点，所以BC就是最大直径的2个端点。
 * 如何证明呢？
 * 反证法：
 * assume B is the farthest point away from A
 * assume S -> T is the max diameter
 * 1. AB与ST完全不相交 non-crossed
 *  A --X-- B
 *      |
 *  S --Y-- T
 *  我们从A找一条能够到达ST的路径，并令分叉点是X和Y。因为AB是从A起始的最长路径，那么AB>AX+XY+YT，即BX>XY+YT.
 *  那么我们观察路径S->Y->X->B，其距离SY+YX+XB > SY+2XY+YT = ST+2XY > ST，这就与ST是全局“最长的点到点距离”矛盾。
 *  2. AB与ST相交于X crossed
 *      A
 *      |
 *  S ==X== T
 *      |
 *      B
 * 因为AB是从A起始的最长路径，那么AX+XB>AX+XS，即XB>XS.
 * 我们观察路径B->X->T，其距离BX+XT > XS+XT = ST，这就与ST是全局“最长的点到点距离”矛盾。
 * 还有一些corner cases，都容易结合图形分析。
 * 由此我们证明了，只要用"两次"BFS找到两个“最远距离”，就可以确定一棵树的最大直径。
 * O(n)就能判断。
 *
 * 本题的基本思路就是：
 * 枚举节点的组合尝试构成一棵树
 * 如果这棵树是联通的，那么就求它的最长直径。
 * 对于该直径的subtree统计就加1。
 * 如何快速判断树是联通的呢？很简单，在BFS的时候判断是否经过了所有这个树的节点就行了。
 * 不是连通的代表有孤悬的点
 * for subtree = ...
 *      if (subtree is not connected) continue
 *      compute max diameter for this subtree
 *      count[max_diameter] += 1
 *
 * 总结：考察2个知识点
 * 1. 构造性问题转化成一个确定性问题
 * 2. 对于确定性问题，这是一个经典老问题
 * n <= 15 => bit mask
 * 把方法记一下
 */
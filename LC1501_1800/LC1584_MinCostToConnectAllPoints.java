package LC1501_1800;
import java.util.*;
public class LC1584_MinCostToConnectAllPoints {
    /**
     * You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] =
     * [xi, yi].
     *
     * The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them:
     * |xi - xj| + |yi - yj|, where |val| denotes the absolute value of val.
     *
     * Return the minimum cost to make all points connected. All points are connected if there is exactly one simple
     * path between any two points.
     *
     * Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
     * Output: 20
     *
     * Constraints:
     *
     * 1 <= points.length <= 1000
     * -106 <= xi, yi <= 10^6
     * All pairs (xi, yi) are distinct.
     * @param points
     * @return
     */
    // S1: Kruskal
    // time = O(ElogE) = O(n^2 * logn), space = O(n^2)
    private int[] parent;
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int dis = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{dis, i, j});
            }
        }

        Collections.sort(edges, ((o1, o2) -> o1[0] - o2[0]));

        int count = 0, res = 0;
        for (int[] edge : edges) {
            int dis = edge[0], a = edge[1], b = edge[2];
            if (findParent(a) != findParent(b)) {
                union(a, b);
                count++;
                res += dis;
            }
            if (count == n - 1) break;
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

    // S2: Prim
    // time = O(n^2), space = O(n^2)
    public int minCostConnectPoints2(int[][] points) {
        int n = points.length;
        // corner case
        if (n == 1) return 0;

        List<int[]>[] edges = new List[n];
        for (int i = 0; i < n; i++) edges[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int dis = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                edges[i].add(new int[]{dis, j});
                edges[j].add(new int[]{dis, i});
            }
        }

        boolean[] visited = new boolean[n];
        visited[0] = true;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        for (int[] x : edges[0]) pq.offer(x);

        int count = 0, res = 0;
        while (true) {
            while (!pq.isEmpty() && visited[pq.peek()[1]]) pq.poll();
            int[] edge = pq.poll();
            int dis = edge[0];
            int next = edge[1];

            visited[next] = true;
            res += dis;
            count++;

            for (int[] x : edges[next]) pq.offer(x);

            if (count == n - 1) break;
        }
        return res;
    }
}
/**
 * MST:最基础版，给你很多条边，让你选权重最少的边把制定的n个点都连起来，连起来的图一定是棵树
 * 没有点割裂，都是连通图
 * 1. Kruskal: E(logE) => n^2*logn
 *      sort all edges by weight
 *      edge1, edge2, edge3 ... edgek
 *      1-2,2-3,1-3(x) 跳过那些已经被连通的2点，该跳就跳 => Union Find
 * 2. Prim: O(n^2)
 * 稠密图：Prim有优势
 * 0      pick min of {edges0}
 * [0,2]  pick min of {edges0, edges2}
 * [0,2,4] pick min of {edges0,edges2,edges4}
 * 从0开始作为种子，不断向外扩散，一个连通区域
 */

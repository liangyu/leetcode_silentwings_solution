package LC1501_1800;
import java.util.*;
public class LC1724_CheckingExistenceofEdgeLengthLimitedPathsII {
    /**
     * An undirected graph of n nodes is defined by edgeList, where edgeList[i] = [ui, vi, disi] denotes an edge between
     * nodes ui and vi with distance disi. Note that there may be multiple edges between two nodes, and the graph may
     * not be connected.
     *
     * Implement the DistanceLimitedPathsExist class:
     *
     * DistanceLimitedPathsExist(int n, int[][] edgeList) Initializes the class with an undirected graph.
     * boolean query(int p, int q, int limit) Returns true if there exists a path from p to q such that each edge on
     * the path has a distance strictly less than limit, and otherwise false.
     *
     * Input
     * ["DistanceLimitedPathsExist", "query", "query", "query", "query"]
     * [[6, [[0, 2, 4], [0, 3, 2], [1, 2, 3], [2, 3, 1], [4, 5, 5]]], [2, 3, 2], [1, 3, 3], [2, 0, 3], [0, 5, 6]]
     * Output
     * [null, true, false, true, false]
     *
     * Constraints:
     *
     * 2 <= n <= 10^4
     * 0 <= edgeList.length <= 10^4
     * edgeList[i].length == 3
     * 0 <= ui, vi, p, q <= n-1
     * ui != vi
     * p != q
     * 1 <= disi, limit <= 10^9
     * At most 104 calls will be made to query.
     * @param n
     * @param edgeList
     */
    // S1: Union Find
    // time = O(nlogn), space = O(n)
    TreeMap<Integer, int[]> map;
    int[] parent;
    public LC1724_CheckingExistenceofEdgeLengthLimitedPathsII(int n, int[][] edgeList) {
        map = new TreeMap<>();
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        Arrays.sort(edgeList, (o1, o2) -> o1[2] - o2[2]);

        for (int[] edge : edgeList) {
            int a = edge[0], b = edge[1], c = edge[2];
            if (findParent(a) != findParent(b)) union(a, b);
            map.put(c, parent.clone()); // 存一下此时此刻parent的snapshot
        }
    }

    public boolean query(int p, int q, int limit) {
        Integer lk = map.lowerKey(limit); // 找到离limit最近的snapshot
        if (lk == null) return false; // 没有找到snapshot，意味着都是各自为主的初始状态，那么p,q一定是互相独立不相连的
        parent = map.get(lk); // 找出对应最近的snapshot
        return findParent(p) == findParent(q);
    }

    private int findParent(int x) {
        if (parent[x] != x) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = findParent(x);
        y = findParent(y);
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }

    // S2: Union Find
    // time = O(nlogn), space = O(n)
//    List<int[]>[] snaps;
//    int[] parent;
//    int snapId = 0;
//    HashSet<Integer> changed; // the nodes whose parent has been modified since last snapshot
//    List<Integer> dist;
//    int[] rank;
//    public LC1724_CheckingExistenceofEdgeLengthLimitedPathsII(int n, int[][] edgeList) {
//        snaps = new List[n];
//        parent = new int[n];
//        changed = new HashSet<>();
//        dist = new ArrayList<>();
//        rank = new int[n];
//
//        for (int i = 0; i < n; i++) {
//            parent[i] = i;
//            snaps[i] = new ArrayList<>();
//            snaps[i].add(new int[]{-1, i}); // 对应初始并没有用到任和边时的snapshot
//        }
//
//        // construct snaps
//        Arrays.sort(edgeList, (o1, o2) -> o1[2] - o2[2]);
//        int curLen = 0;
//        for (int[] e : edgeList) {
//            if (e[2] > curLen) {
//                dist.add(curLen);
//                for (int node : changed) {
//                    snaps[node].add(new int[]{snapId, parent[node]});
//                }
//                changed.clear();
//                snapId++;
//                curLen = e[2];
//            }
//            union(e[0], e[1]);
//        }
//    }
//
//    public boolean query(int p, int q, int limit) {
//        int id = lowerBound(dist, limit);
//        return findSnapRoot(p, id) == findSnapRoot(q, id);
//    }
//
//    private int findParent(int x) {
//        if (parent[x] != x) parent[x] = findParent(parent[x]);
//        return parent[x];
//    }
//
//    private void union(int a, int b) {
//        int fa = findParent(a);
//        int fb = findParent(b);
//        if (fa != fb) {
//            if (rank[fa] < rank[fb]) {
//                parent[fa] = fb;
//                rank[fb] = Math.max(rank[fb], rank[fa] + 1);
//                changed.add(fa);
//            } else {
//                parent[fb] = fa;
//                rank[fa] = Math.max(rank[fa], rank[fb] + 1);
//                changed.add(fb);
//            }
//        }
//    }
//
//    private int findSnapRoot(int node, int snap_id) {
//        int idx = upperBound(snaps[node], snap_id);
//        int p = snaps[node].get(idx)[1];
//        if (p == node) return p;
//        return findSnapRoot(p, snap_id); // 一直向上找，找到顶
//    }
//
//    private int upperBound(List<int[]> nums, int t) {
//        int left = 0, right = nums.size() - 1;
//        while (left < right) {
//            int mid = left + (right - left) / 2;
//            if (nums.get(mid)[0] <= t) left = mid + 1;
//            else right = mid;
//        }
//        return nums.get(left)[0] <= t ? left : left - 1;
//    }
//
//    private int lowerBound(List<Integer> nums, int t) {
//        int left = 0, right = nums.size() - 1;
//        while (left < right) {
//            int mid = right - (right - left) / 2;
//            if (nums.get(mid) < t) left = mid;
//            else right = mid - 1;
//        }
//        return nums.get(left) < t ? left : left - 1;
//    }
}
/**
 * 与LC1697区别：query一个个蹦出来，事先不知道所有的query
 * 难点：dis-union??? 就算能做，效率也很低
 * ref: LC1146 -> snapshot
 * 我们要记录历史上连通图的状态！！！
 * 利用历史上的连通图状态来解决问题。
 * limit = 1 => complete isolated
 * father[0] = 0, father[1] = 1, father[2] = 2, father[3] = 3, father[4] = 4, father[5] = 5
 * limit = 2 => 2 <=> 3
 * father[0] = 0, father[1] = 1, father[2] = 2, father[3] = 2, father[4] = 4, father[5] = 5
 * limit = 3 => 0 <=> 3
 * father[0] = 0, father[1] = 1, father[2] = 0, father[3] = 0, father[4] = 4, father[5] = 5
 * limit = 4 => 2 <=> 1
 * father[0] = 0, father[1] = 0, father[2] = 0, father[3] = 0, father[4] = 4, father[5] = 5
 * limit = 5 => 2 <=> 0
 * father[0] = 0, father[1] = 0, father[2] = 0, father[3] = 0, father[4] = 4, father[5] = 5
 * limit = 6 => 4 <=> 5
 * father[0] = 0, father[1] = 0, father[2] = 0, father[3] = 0, father[4] = 4, father[5] = 4
 * 随着边不断增加，每加一条边就记录下snapshot，就可以解决这个问题
 * 那如何存连通图的状态呢？
 * 存张图还是挺麻烦的。
 * 在这题里，有个巧妙的办法：只要存每个历史时期的父节点，即每个node的parent
 * 连接2点一般都是指定一个点是另一个结点的parent
 * 所有结点的parent就代表一个连通图
 * snaps[node] = {{snapId1, father1}, {snapId2, father2}, {snapId3, father3}, ...}
 * snaps[1] = {{0,1},{1,1},{2,1},{3,0},{4,0},{5,0}} =>
 * snaps[1] = {{0,1},{3,0}}
 * snap_id <= limit
 * return findSnapFather(p, snap_id) == findSnapFather(q, snap_id)
 * 浪费空间 => 按秩归并
 * 选新族长的时候，要选秩比较大的作为根
 * 秩：每个结点到它最远的叶子结点的距离 => 0: 0,  2: 2
 * 做union find的时候，维护一个rank
 */

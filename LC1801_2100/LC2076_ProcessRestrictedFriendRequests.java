package LC1801_2100;
import java.util.*;
public class LC2076_ProcessRestrictedFriendRequests {
    /**
     * You are given an integer n indicating the number of people in a network. Each person is labeled from 0 to n - 1.
     *
     * You are also given a 0-indexed 2D integer array restrictions, where restrictions[i] = [xi, yi] means that person
     * xi and person yi cannot become friends, either directly or indirectly through other people.
     *
     * Initially, no one is friends with each other. You are given a list of friend requests as a 0-indexed 2D integer
     * array requests, where requests[j] = [uj, vj] is a friend request between person uj and person vj.
     *
     * A friend request is successful if uj and vj can be friends. Each friend request is processed in the given order
     * (i.e., requests[j] occurs before requests[j + 1]), and upon a successful request, uj and vj become direct friends
     * for all future friend requests.
     *
     * Return a boolean array result, where each result[j] is true if the jth friend request is successful or false if
     * it is not.
     *
     * Note: If uj and vj are already direct friends, the request is still successful.
     *
     * Input: n = 3, restrictions = [[0,1]], requests = [[0,2],[2,1]]
     * Output: [true,false]
     *
     * Constraints:
     *
     * 2 <= n <= 1000
     * 0 <= restrictions.length <= 1000
     * restrictions[i].length == 2
     * 0 <= xi, yi <= n - 1
     * xi != yi
     * 1 <= requests.length <= 1000
     * requests[j].length == 2
     * 0 <= uj, vj <= n - 1
     * uj != vj
     * @param n
     * @param restrictions
     * @param requests
     * @return
     */
    // S1: Union Find
    // time = O(m * n *alpah(n)), space = O(n)   alpha: 反阿克曼函数，表示在路径压缩和按秩合并优化下的并查集的单次操作时间复杂度
    private int[] parent;
    public boolean[] friendRequests(int n, int[][] restrictions, int[][] requests) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        boolean[] ans = new boolean[requests.length];
        int idx = 0;
        for (int[] req : requests) {
            int u = req[0], v = req[1];
            int fu = findParent(u), fv = findParent(v);

            if (fu == fv) {
                ans[idx++] = true;
                continue;
            }
            boolean flag = true;
            for (int[] res : restrictions) {
                int x = res[0], y = res[1];
                int fx = findParent(x), fy = findParent(y);
                if (fu == fx && fv == fy || fu == fy && fv == fx) {
                    flag = false;
                    break;
                }
            }
            ans[idx++] = flag;
            if (flag) union(u, v);
        }
        return ans;
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


    // S2: Union Find
    // time = O(m * n *alpah(n)), space = O(n)
//    private int[] parent;
//    public boolean[] friendRequests2(int n, int[][] restrictions, int[][] requests) {
//        parent = new int[n];
//        for (int i = 0; i < n; i++) parent[i] = i;
//
//        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
//        for (int[] r : restrictions) {
//            int a = r[0], b = r[1];
//            map.putIfAbsent(a, new HashSet<>());
//            map.putIfAbsent(b, new HashSet<>());
//            map.get(a).add(b);
//            map.get(b).add(a);
//        }
//
//        boolean[] res = new boolean[requests.length];
//        for (int i = 0; i < requests.length; i++) {
//            int a = requests[i][0], b = requests[i][1];
//            int p1 = findParent(a), p2 = findParent(b);
//            if (p1 == p2) {
//                res[i] = true;
//                continue;
//            } else {
//                if (map.containsKey(a) && map.get(a).contains(b)) {
//                    res[i] = false;
//                    continue;
//                }
//                if (helper(map, p1, p2)) {
//                    res[i] = false;
//                    continue;
//                }
//                union(p1, p2);
//                res[i] = true;
//            }
//        }
//        return res;
//    }
//
//    private boolean helper(HashMap<Integer, HashSet<Integer>> map, int p1, int p2) {
//        for (int key : map.keySet()) {
//            int p = findParent(key);
//            if (p == p1) {
//                for (int x : map.get(key)) {
//                    if (findParent(x) == p2) return false;
//                }
//            }
//            if (p == p2) {
//                for (int x : map.get(key)) {
//                    if (findParent(x) == p1) return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    private int findParent(int x) {
//        if (x != parent[x]) parent[x] = findParent(parent[x]);
//        return parent[x];
//    }
//
//    private void union(int x, int y) {
//        x = parent[x];
//        y = parent[y];
//        if (x < y) parent[y] = x;
//        else parent[x] = y;
//    }
}
/**
 * 我们想判断x和y是否能成为朋友，取决于x所在的家族X、y所在的家族Y，不能有任何一对是敌人。
 * 换句话说，如果家族X、家族Y中有存在任何一对是restriction，那么x和y就不能归并到一起。
 * 显然，我们不可能穷举X家族、Y家族的配对。
 * 但是反过来，我们可以穷举所有的restriction，查看每对敌人的双方是否分别存在这两个家族之中。
 * 这样，时间复杂度就是request的数目乘以restriction的数目，恰好符合题意。
 * 每次find和union的操作可以近似为O(1)的操作
 * {a,b} friend
 * {x,y} enemy
 * 给一堆query，看有没有矛盾？
 */

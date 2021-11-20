package LC1501_1800;
import java.util.*;
public class LC1766_TreeofCoprimes {
    /**
     * There is a tree (i.e., a connected, undirected graph that has no cycles) consisting of n nodes numbered from 0 to
     * n - 1 and exactly n - 1 edges. Each node has a value associated with it, and the root of the tree is node 0.
     *
     * To represent this tree, you are given an integer array nums and a 2D array edges. Each nums[i] represents the ith
     * node's value, and each edges[j] = [uj, vj] represents an edge between nodes uj and vj in the tree.
     *
     * Two values x and y are coprime if gcd(x, y) == 1 where gcd(x, y) is the greatest common divisor of x and y.
     *
     * An ancestor of a node i is any other node on the shortest path from node i to the root. A node is not considered
     * an ancestor of itself.
     *
     * Return an array ans of size n, where ans[i] is the closest ancestor to node i such that nums[i] and nums[ans[i]]
     * are coprime, or -1 if there is no such ancestor.
     *
     * Input: nums = [2,3,3,2], edges = [[0,1],[1,2],[1,3]]
     * Output: [-1,0,0,1]
     *
     * Constraints:
     *
     * nums.length == n
     * 1 <= nums[i] <= 50
     * 1 <= n <= 10^5
     * edges.length == n - 1
     * edges[j].length == 2
     * 0 <= uj, vj < n
     * uj != vj
     * @param nums
     * @param edges
     * @return
     */
    // time = O(n), space = O(n)
    public int[] getCoprimes(int[] nums, int[][] edges) {
        List<Integer> path = new ArrayList<>(); // path[i] is the i-th node idx along the dfs path
        List<Integer>[] records = new List[51]; // records[i] contains the depth of all the nodes whose num = i;
        for (int i = 0; i < 51; i++) records[i] = new ArrayList<>();
        int n = nums.length;
        int[] res = new int[n];
        List<Integer>[] next = new List[100000];
        boolean[] visited = new boolean[100000];
        for (int i = 0; i < 100000; i++) next[i] = new ArrayList<>();
        for (int[] edge : edges) {
            next[edge[0]].add(edge[1]);
            next[edge[1]].add(edge[0]);
        }
        visited[0] = true;
        dfs(0, 0, nums, path, records, next, res, visited);
        return res;
    }

    private void dfs(int curIdx, int depth, int[] nums, List<Integer> path, List<Integer>[] records, List<Integer>[] next, int[] res, boolean[] visited) {
        int i = -1;
        for (int d = 1; d <= 50; d++) {
            if (records[d].size() > 0 && gcd(d, nums[curIdx]) == 1) {
                i = Math.max(i, records[d].get(records[d].size() - 1));
            }
        }
        res[curIdx] = (i == -1 ? -1 : path.get(i));
        path.add(curIdx);
        records[nums[curIdx]].add(depth);

        for (int nextId : next[curIdx]) {
            if (visited[nextId]) continue;
            visited[nextId] = true;
            dfs(nextId, depth + 1, nums, path, records, next, res, visited);
            visited[nextId] = false;
        }
        path.remove(path.size() - 1);
        records[nums[curIdx]].remove(records[nums[curIdx]].size() - 1);
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}
/**
 * 1 <= nums[i] <= 50 => 不需要回看所有支路上的祖先
 * 本题本质就是DFS。当我们考察某个节点curIdx时，在该DFS路径path上的所有节点都是它的祖先。
 * 我们需要从中找一个深度最大的、与nums[curIdx]互质的节点。理论上我们需要逆序遍历一遍path，总体复杂度是o(N^2)。
 * 本题特殊之处在于数据范围限制了所有节点的“数值”不超过50，于是我们可以不遍历节点、转而遍历“数值”来更高效的找到互质的节点。
 * 需要在维护path的同时，维护一个哈希表records，其中records[d]存储的就是path里所有数值是d的节点的深度。
 * 我们在1到50里面寻找那些与nums[curIdx]互质的d，其中最大的records[d].back()就是离curIdx最近的互质节点的深度。
 * 再根据这个深度，直接从path里面读取那个节点的idx。
 */
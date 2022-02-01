package LC301_600;
import java.util.*;
public class LC310_MinimumHeightTrees {
    /**
     * A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any
     * connected graph without simple cycles is a tree.
     *
     * Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] = [ai, bi]
     * indicates that there is an undirected edge between the two nodes ai and bi in the tree, you can choose any node
     * of the tree as the root. When you select a node x as the root, the result tree has height h. Among all possible
     * rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).
     *
     * Return a list of all MHTs' root labels. You can return the answer in any order.
     *
     * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
     *
     * Input: n = 4, edges = [[1,0],[1,2],[1,3]]
     * Output: [1]
     *
     * Constraints:
     *
     * 1 <= n <= 2 * 10^4
     * edges.length == n - 1
     * 0 <= ai, bi < n
     * ai != bi
     * All the pairs (ai, bi) are distinct.
     * The given input is guaranteed to be a tree and there will be no repeated edges.
     * @param n
     * @param edges
     * @return
     */
    // S1: bfs
    // time = O(n), space = O(n)
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        HashSet<Integer>[] graph = new HashSet[n];
        for (int i = 0; i < n; i++) graph[i] = new HashSet<>();
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (graph[i].size() == 1) queue.offer(i);
            set.add(i);
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            res = new ArrayList<>();
            while (size-- > 0) {
                int cur = queue.poll();
                res.add(cur);
                set.remove(cur);
                for (int next : graph[cur]) {
                    graph[next].remove(cur);
                    if (graph[next].size() == 1) queue.offer(next);
                }
            }
        }
        res.addAll(set);
        return res;
    }

    // S2: Toplogical Sort
    // time = O(n), space = O(n)
    public List<Integer> findMinHeightTrees2(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        if (n == 1) return Arrays.asList(0);
        if (n == 2) return Arrays.asList(0, 1);

        List<Integer>[] graph = new List[n];
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] x : edges) {
            int a = x[0], b = x[1];
            graph[a].add(b);
            graph[b].add(a);
            indegree[a]++;
            indegree[b]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 1) queue.offer(i);
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                count++;
                for (int next : graph[cur]) {
                    indegree[next]--;
                    if (indegree[next] == 1) queue.offer(next);
                }
            }
            if (count == n - 1 || count == n - 2) break;
        }

        while (!queue.isEmpty()) res.add(queue.poll());
        return res;
    }
}
/**
 * 本题的意思是，想从一棵树的一群nodes里找出一个node作为根，使得从这个根节点出发，发散到周围的叶子节点的路径范围最短。
 * 可以想见，这个根节点必然得尽可能地位于“中央”。
 * 如何确定“中央”的位置呢？我们其实可以反其道而行之，从“边疆”出发往内地进军。
 * 从所有的叶子节点（入度为1、出度为0）同步出发，一步一步地往前走，那么它们的最终汇合点，必然就是最“中央”的地方。
 * 0 - 1 - 2 - 3 - 4 - 5 - 6
 *     |
 *     7
 * 从各个叶子节点出发向中间爬，爬到碰头了就是中心。
 * 如果不会相聚在一个点，任意选3或者4都可以。
 *
 * 很明显，这就是一棵树的层级遍历 (level order tranversal)。
 * 传统的树操作都是从root开始的（因为通常只给你一个root），必须从顶往底用队列的结构一层一层遍历。
 * 但这里给出了图的表述，这样我们就可以轻易地找出哪些是最底端的叶子节点，从叶子节点反推上去。
 * 基本的算法思想就是ＢＦＳ，具体的做法很像拓扑排序，可以参见269．Alien Dictionary.
 * 我们要借助一个Ｈａｓｈ表记录所有节点的度．
 * 每次我们将度为１（说明是叶子节点或边缘节点）加入队列．
 * 队列每弹出一个元素，我们就找这个元素的相邻节点，将它们的度都减一，一旦减至１（说明这个节点被砍成了边缘节点），就可以把这个节点加入队列．
 * 直到什么时候停止呢？直到所有已弹出的元素数目，加上已经加入队列的元素数目，恰好等于ｎ为止．
 * 这时候，队列的元素数目，要么为１，要么为２，这一个或两个元素就是答案．
 * 注意：为什么最后会有一个或两个元素可以作为答案。
 * 我也是看网上参考才知道的。不过这也非常好理解。
 * 如果最后还剩下三个连通的节点（因为这是一棵树，必然彼此连通），显然还有从两边“往中央进军”的余地，必然只有一个是中央；
 * 如果最后还剩下两个连通的节点，两边各进一步的话就僵持住了，显然可以是并列的“中央”
 */
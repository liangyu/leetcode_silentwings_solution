package LC301_600;
import java.util.*;
public class LC305_NumberofIslandsII {
    /**
     * You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water
     * and 1's represent land. Initially, all the cells of grid are water cells (i.e., all the cells are 0's).
     *
     * We may perform an add land operation which turns the water at position into a land. You are given an array
     * positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.
     *
     * Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into
     * a land.
     *
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may
     * assume all four edges of the grid are all surrounded by water.
     *
     * Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
     * Output: [1,1,2,3]
     *
     * Constraints:
     *
     * 1 <= m, n, positions.length <= 104
     * 1 <= m * n <= 104
     * positions[i].length == 2
     * 0 <= ri < m
     * 0 <= ci < n
     *
     * Follow up: Could you solve it in time complexity O(k log(mn)), where k == positions.length?
     * @param m
     * @param n
     * @param positions
     * @return
     */
    // time = O(m * n + l), space = O(m * n)  l: the number of operations
    // it takes O(m * n) to initialize UnionFind, and O(l) to process positions.
    // Note that Union operation takes essentially constant time
    // when UnionFind is implemented with both path compression and union by rank.
    private int[] parent;
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        parent = new int[m * n];
        for (int i = 0; i < m * n; i++) parent[i] = -1; // -1: sea O(m * n)
        int count = 0;

        for (int[] pos : positions) { // O(l)
            int x = pos[0], y = pos[1];
            int u = x * n + y; // land
            if (parent[u] != -1) { // already been unioned and visited -> keep the same without change
                res.add(res.get(res.size() - 1));
                continue;
            }
            parent[u] = u; // become land
            count++;

            // check neighbors
            for (int[] dir : directions) {
                int i = x + dir[0];
                int j = y + dir[1];
                int v = i * n + j;
                if (i < 0 || i >= m || j < 0 || j >= n) continue;
                if (parent[v] == -1) continue; // (i, j) is sea instead of land
                if (findParent(u) != findParent(v)) {
                    union(u, v);
                    count--;
                }
            }
            res.add(count);
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
 * 设置所有点的初始Father为-1，表示海洋。
 * 然后依次遍历每一块新陆地，最开始标记它的Root为自身，然后count++。
 * 依次考察这个新陆地相邻的四块：如果相邻的是陆地，并且新陆地和老陆地的Root不同，那么说明这是需要合并的两个集合，
 * 于是count--，并且将新陆地与旧陆地进行Union。最终实时输出count。
 * 有一个corner case是，positions里面可能会包含重复的同一块陆地。
 * 所以每遍历一块新陆地的时候，得先看看是否已经访问过了，已经访问过了就不要再重新标记Root，否则会出错。
 */

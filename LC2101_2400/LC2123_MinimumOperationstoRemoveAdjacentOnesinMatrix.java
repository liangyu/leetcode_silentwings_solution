package LC2101_2400;
import java.util.*;
public class LC2123_MinimumOperationstoRemoveAdjacentOnesinMatrix {
    /**
     * You are given a 0-indexed binary matrix grid. In one operation, you can flip any 1 in grid to be 0.
     *
     * A binary matrix is well-isolated if there is no 1 in the matrix that is 4-directionally connected (i.e.,
     * horizontal and vertical) to another 1.
     *
     * Return the minimum number of operations to make grid well-isolated.
     *
     * Input: grid = [[1,1,0],[0,1,1],[1,1,1]]
     * Output: 3
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * grid[i][j] is either 0 or 1.
     * @param grid
     * @return
     */
    List<Integer>[] next;
    int[] match;
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int minimumOperations(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        next = new List[m * n];
        for (int i = 0; i < m * n; i++) next[i] = new ArrayList<>();
        match = new int[m * n];
        Arrays.fill(match, -1);

        // construct next[]
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                for (int[] dir : directions) {
                    int x = i + dir[0];
                    int y = j + dir[1];
                    if (x < 0 || x >= m || y < 0 || y >= n) continue;
                    if (grid[x][y] != 1) continue;
                    next[i * n + j].add(x * n + y);
                }
            }
        }

        int t = m * n;
        boolean[] visited = new boolean[t];
        int count = 0;
        for (int i = 0; i < t; i++) {
            if (match[i] != -1) continue;
            Arrays.fill(visited, false);
            if (dfs(i, visited)) count++;
        }
        return count;
    }

    private boolean dfs(int i, boolean[] visited) {
        for (int j : next[i]) {
            if (visited[j]) continue;
            visited[j] = true;
            if (match[j] == -1 || dfs(match[j], visited)) {
                match[i] = j;
                match[j] = i;
                return true;
            }
        }
        return false;
    }

    // S2:
    private int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int[][] grid;
    private int m, n;
    private HashSet<Integer> vis;
    private int[] link;
    public int minimumOperations2(int[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;
        vis = new HashSet<>();
        link = new int[m * n];
        int cnt = 0;
        for (int i = 0; i < m;i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0 || grid[i][j] == 0) continue;
                if (dfs(i * n + j)) cnt++;
            }
        }
        return cnt;
    }

    private boolean dfs(int k) {
        int i = k / n, j = k % n;
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] == 0) continue;
            int z = x * n + y;
            if (!vis.contains(z)) {
                vis.add(z);
                if (link[z] == 0 || dfs(link[z])) {
                    link[z] = k;
                    vis.remove(z);
                    return true;
                }
                vis.remove(z);
            }
        }
        return false;
    }
}
/**
 * Hungarian 二分图
 * 无权最大二分图匹配，匈牙利算法
 * 有权的就是KM算法
 * ref: LC1820, 2123
 * 为什么可以转化成二分图匹配？
 * 300 x 300 => 无法暴力
 * 1 作为node, 相邻1之间有条边 => 特点：这图一定是二分图
 * 把所有结点恰好分为2部分，使得所有边都是跨接这两部分的。
 * 什么样的图可以分解为二分图呢？ => 不含有奇数个结点的环,所有边都是跨接2个集合的。
 * 在本题这样的2d matrix里面，如果要构成环的话，一定是偶数个结点，不可能出现奇数个！
 * 撸一圈的话，肯定是上下对称，左右对称的，一定是偶数。
 * 所以一定可以二分！！！
 * 要翻转之后没有2个1相连，则相当于把跨接的其中一个集合的1都变成0即可。
 * 翻转的集合B，点越少越好，而A集合越多越好。
 * 1. 如果有些点是孤立点，不和任何其他点相连，那这些点可以放在A，也可以放在B里，但B要越少越好，所以都扔A里面。
 * 2. B里的每个点都可能和A的若干点相连，A里的点也会指向B
 * 这个B里面的结点数目，就是最大二分图匹配。
 * 在这里面找出尽量多的边，使得这些边没有公共的顶点，就意味着在B集合里就对应不同的结点
 * 一条边对应着一个B点
 * 最大二分图匹配能找出多少个这样的匹配边，就意味着B里有多少个结点。
 * LC1820 就是最大二分图匹配
 * 建图 + 匈牙利算法
 */
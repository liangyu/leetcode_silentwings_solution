package LC901_1200;

public class LC959_RegionsCutBySlashes {
    /**
     * An n x n grid is composed of 1 x 1 squares where each 1 x 1 square consists of a '/', '\', or blank space ' '.
     * These characters divide the square into contiguous regions.
     *
     * Given the grid grid represented as a string array, return the number of regions.
     *
     * Note that backslash characters are escaped, so a '\' is represented as '\\'.
     *
     * Input: grid = [" /","/ "]
     * Output: 2
     *
     * Constraints:
     *
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 30
     * grid[i][j] is either '/', '\', or ' '.
     * @param grid
     * @return
     */
    // S1: dfs
    // time = O(n^2), space = O(n^2)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        int[][] grid2 = new int[3 * n][3 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == '/') {
                    grid2[i * 3][j * 3 + 2] = 1;
                    grid2[i * 3 + 1][j * 3 + 1] = 1;
                    grid2[i * 3 + 2][j * 3 + 0] = 1;
                } else if (grid[i].charAt(j) == '\\') {
                    grid2[i * 3][j * 3 + 0] = 1;
                    grid2[i * 3 + 1][j * 3 + 1] = 1;
                    grid2[i * 3 + 2][j * 3 + 2] = 1;
                }
            }
        }

        int count = 0;
        for (int i = 0; i < n * 3; i++) {
            for (int j = 0; j < n * 3; j++) {
                if (grid2[i][j] == 0) {
                    dfs(grid2, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int[][] grid, int x, int y) {
        int n = grid.length;
        if (x < 0 || x >= n || y < 0 || y >= n) return;
        if (grid[x][y] != 0) return;
        grid[x][y] = 2;

        for (int[] dir : directions) {
            int i = x + dir[0];
            int j = y + dir[1];
            dfs(grid, i, j);
        }
    }

    // S2: Union Find
    // time = O(n^2 * a(n)), space = O(n^2)   a: the Inverse-Ackermann function (if we were to use union-find by rank.)
    private int[] parent;
    public int regionsBySlashes2(String[] grid) {
        int n = grid.length;
        parent = new int[(n + 1) * (n + 1)]; // 每条边长度为n => 每条边有 n + 1 个点！
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                int idx = i * (n + 1) + j;
                parent[idx] = idx;
                if (i == 0 || i == n || j == 0 || j == n) {
                    parent[idx] = 0; // 外边框的点都属于group 0
                }
            }
        }

        int count = 1; // 什么都没有的话，只有本身一大块这一个封闭区域
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == ' ') continue;

                int a = -1, b = -1;
                if (grid[i].charAt(j) == '/') {
                    a = i * (n + 1) + j + 1;
                    b = (i + 1) * (n + 1) + j;
                } else if (grid[i].charAt(j) == '\\') {
                    a = i * (n + 1) + j;
                    b = (i + 1) * (n + 1) + j + 1;
                }

                if (findParent(a) == findParent(b)) count++;
                else union(a, b);
            }
        }
        return count;
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
 * S1: dfs：
 * 将每一个格子都再细化为3*3的像素，那么“\”和“/”就各自变成了一条斜对角线
 * 我们再看这个细化之后的3N*3N的矩阵，就会惊奇地发现，题目中定义的Regions此时都变成是像素联通的了。
 * 这就转换成了number of islands的题，用DFS,BFS或者Union Find都可以很轻松地解决。
 * Q:放大3倍，2倍行不行？
 * A:这里是不行的，
 * 因为我们在做DFS/BFS是从上下左右4个方向去做扩展遍历，当4个方向都是拦住(都是数字1)，就当成一个块，放大2倍时，图中的点会被分隔成块.
 *
 * S2: Union Find
 * 不看格子，只看点
 * 我们不考虑每个cell，而是考虑每个网点（即经纬线的相交点）。这样的网点有(N+1)*(N+1)个。
 * 最初始的时候我们将所有外围的点聚为一类，即满足i==0||i==N||j==0||j==N的网点聚在一起。其他内陆的网点都自成一类。
 * 什么时候会多出一个封闭区间呢？
 * 如果在2个属于同一个类的点之间拉出一条线，就会多出一个封闭区间。这个时候计数器就要加1.
 * 记得我们需要紧接着将a和b聚类在一起。
 * 如果a和b两点属于不同的类，那么意味着这个斜线不会生成一个新的封闭空间！
 * 我们考察完每个cell和所包含斜线的影响，最终返回计数器的值即可。
 * 注意，计数器的初始值应该是1.
 */

package LC1801_2100;

public class LC2088_CountFertilePyramidsinaLand {
    /**
     * A farmer has a rectangular grid of land with m rows and n columns that can be divided into unit cells. Each cell
     * is either fertile (represented by a 1) or barren (represented by a 0). All cells outside the grid are considered
     * barren.
     *
     * A pyramidal plot of land can be defined as a set of cells with the following criteria:
     *
     * The number of cells in the set has to be greater than 1 and all cells must be fertile.
     * The apex of a pyramid is the topmost cell of the pyramid. The height of a pyramid is the number of rows it
     * covers. Let (r, c) be the apex of the pyramid, and its height be h. Then, the plot comprises of cells (i, j)
     * where r <= i <= r + h - 1 and c - (i - r) <= j <= c + (i - r).
     * An inverse pyramidal plot of land can be defined as a set of cells with similar criteria:
     *
     * The number of cells in the set has to be greater than 1 and all cells must be fertile.
     * The apex of an inverse pyramid is the bottommost cell of the inverse pyramid. The height of an inverse pyramid is
     * the number of rows it covers. Let (r, c) be the apex of the pyramid, and its height be h. Then, the plot
     * comprises of cells (i, j) where r - h + 1 <= i <= r and c - (r - i) <= j <= c + (r - i).
     * Some examples of valid and invalid pyramidal (and inverse pyramidal) plots are shown below. Black cells indicate
     * fertile cells.
     *
     * Given a 0-indexed m x n binary matrix grid representing the farmland, return the total number of pyramidal and
     * inverse pyramidal plots that can be found in grid.
     *
     * Input: grid = [[0,1,1,0],[1,1,1,1]]
     * Output: 2
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 1000
     * 1 <= m * n <= 10^5
     * grid[i][j] is either 0 or 1.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int countPyramids(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] left = new int[m][n];
        int[][] right = new int[m][n];
        int[][] dp1 = new int[m][n];
        int[][] dp2 = new int[m][n];

        for (int i = 0; i < m; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) count++;
                else count = 0;
                left[i][j] = count;
            }
        }
        for (int i = 0; i < m; i++) {
            int count = 0;
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 1) count++;
                else count = 0;
                right[i][j] = count;
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                if (i == 0) {
                    if (grid[i][j] == 1) dp1[i][j] = 1;
                } else {
                    dp1[i][j] = Math.min(dp1[i - 1][j] + 1, Math.min(left[i][j], right[i][j]));
                }
                dp1[i][j] = Math.max(dp1[i][j], 1);
                res += Math.max(0, dp1[i][j] - 1);
            }
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                if (i == m - 1) {
                    if (grid[i][j] == 1) dp2[i][j] = 1;
                } else {
                    dp2[i][j] = Math.min(dp2[i + 1][j] + 1, Math.min(left[i][j], right[i][j]));
                }
                dp2[i][j] = Math.max(dp2[i][j], 1);
                res += Math.max(0, dp2[i][j] - 1);
            }
        }
        return res;
    }
}
/**
 * 一旦有3层金字塔，就代表一定有2层金字塔
 * 必须抓住金字塔的特征。我们通过数特征来统计金字塔的数目。
 * 要数数的话，一定要找特征值
 * 最显然的就是找塔尖 => 1层，2层，3层...金字塔塔尖(本题不包括1层金字塔)
 * 如果它作为某个金字塔的顶尖的话，可以代表哪些金字塔。
 * 显然，我们光看这个格子A本身是不充分，我们得看它往下一格B，查看B是否为一个长度为3的区间的中点；
 * 再查看B的下一格C，查看C是否为一个长度为5的区间的中点... 直至类似的规律不能再持续下去。
 * => 一点点往下移动 => time = O(mn*m) -> 10^8
 * 问题：站在顶上，不知道下层如何？
 * 那能不能反过来呢？=> 不是塔尖，而是底座中点
 * 如果是三层金字塔底层中点，也一定是2层或者1层金字塔底层中点 => 最大可以构造成多大的金字塔
 * => 只要知道最大构造k层，答案+k即可。
 * 自己作为底座的话，宽度最大是多少 => 提前处理左边和右边有多少个1
 * dp(A): the maximum base radius at A
 * dp(A) = Math.min(left(A), right(A), dp(B) + 1)
 * 半径不能为1 => res += dp(A) - 1
 * 数的是金字塔底座中点，特征值
 * 抓住特征来数数
 * ref: LC1277
 */

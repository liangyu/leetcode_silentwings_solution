package LC1201_1500;
public class LC1284_MinimumNumberofFlipstoConvertBinaryMatrixtoZeroMatrix {
    /**
     * Given a m x n binary matrix mat. In one step, you can choose one cell and flip it and all the four neighbors of
     * it if they exist (Flip is changing 1 to 0 and 0 to 1). A pair of cells are called neighbors if they share one edge.
     *
     * Return the minimum number of steps required to convert mat to a zero matrix or -1 if you cannot.
     *
     * A binary matrix is a matrix with all cells equal to 0 or 1 only.
     *
     * A zero matrix is a matrix with all cells equal to 0.
     *
     * Input: mat = [[0,0],[0,1]]
     * Output: 3
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 3
     * mat[i][j] is either 0 or 1.
     * @param mat
     * @return
     */
    // S1: bit mask
    // time = O(m * n * 2^(m * n)), space = O(m * n)
    int m, n;
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {0, 0}};
    public int minFlips(int[][] mat) {
        this.m = mat.length;
        this.n = mat[0].length;
        int res = Integer.MAX_VALUE;

        for (int state = 0; state < (1 << (m * n)); state++) {
            if (check(mat, state)) res = Math.min(res, Integer.bitCount(state));
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private boolean check(int[][] mat, int state) {
        int[][] copy = new int[m][n];
        for (int i = 0; i < m; i++) copy[i] = mat[i].clone();

        for (int i = 0; i < (m * n); i++) {
            if (((state >> i) & 1) == 1) {
                int x = i / n, y = i % n;
                for (int[] dir : directions) {
                    int xx = x + dir[0];
                    int yy = y + dir[1];
                    if (xx < 0 || xx >= m || yy < 0 || yy >= n) continue;
                    copy[xx][yy] = 1 - copy[xx][yy];
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (copy[i][j] != 0) return false;
            }
        }
        return true;
    }

    // S2: Gospers-Hack
    // time = O(m * n * 2^(m * n)), space = O(m * n)
    public int minFlips2(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int res = Integer.MAX_VALUE;

        if (check(mat, 0)) return 0; // k = 0 的case要单独考虑！！！
        for (int k = 1; k <= (m * n); k++) { // Gospers hack的k必须从1开始！！！k不能为0！k == 0 的case单独考虑！
            // Gospers-Hack -> interate all the m-bit state where there are k 1-bits
            int state = (1 << k) - 1;
            while (state < (1 << m * n)) {
                if (check(mat, state)) return k;
                int c = state & -state;
                int r = state + c;
                state = (((r ^ state) >> 2) / c) | r; // k 必须从1开始
            }
        }
        return -1;
    }
}
/**
 * 每个元素以它为主元素最多flip 1 次，2次就完全没有意义，因为这样就还原了。被动被翻转可以很多次。
 * 跟操作顺序无关，只跟次数有关。
 * 1 <= m, n <= 3  => 3 * 3  => 2^9 = 512,穷举所有可能
 * 每个位置主动翻不翻，被动翻不翻看上下左右翻不翻,1个元素也就check 5次，最多5次翻转
 * check下是不是都为0
 * 最多 = 2^9*5*9
 * 暴力题，所有策略中找出所有翻转次数最少的
 * 暴力枚举所有可能，9个位置，要么翻，要么不翻
 * state每一位代表每个位置，1010，1表示主不主动翻转，0代表不主动翻转！
 * 稍微再优化一点：不用把所有的state都跑完
 * 按照主动翻转次数从小到多来遍历这个state
 * 0000 0001 0010 0011 0100 0101
 * 如果按照bit 1单调的数目来遍历state的话，一旦发现check过了，就可以直接输出
 * 如何来实现呢？
 */
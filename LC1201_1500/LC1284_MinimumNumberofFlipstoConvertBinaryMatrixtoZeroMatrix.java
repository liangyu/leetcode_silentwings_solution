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
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {0, 0}};
    public int minFlips(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int res = Integer.MAX_VALUE;
        for (int state = 0; state < (1 << (m * n)); state++) {
            if (check(mat, state)) res = Math.min(res, Integer.bitCount(state));
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private boolean check(int[][] mat, int state) { // O(m * n)
        int m = mat.length, n = mat[0].length;
        int[][] temp = new int[m][n];
        for (int i = 0; i < m; i++) temp[i] = mat[i].clone();

        for (int b = 0; b < (m * n); b++) {
            int t = state % 2;
            state /= 2;
            if (t == 0) continue;
            int i = b / n, j = b % n;
            // 5个位置会flip
            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii < 0 || ii >= m || jj < 0 || jj >= n) continue;
                temp[ii][jj] = 1 - temp[ii][jj];
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (temp[i][j] != 0) return false;
            }
        }
        return true;
    }

    // S2: Gospers-Hack
    // time = O(m * n * 2^(m * n)), space = O(m * n)
    public int minFlips2(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int res = Integer.MAX_VALUE;

        if (check(mat, 0)) return 0;
        for (int k = 1; k <= (m * n); k++) {
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
 * 每个位置主动翻不翻，被动翻不翻看上下左右翻不翻
 * check下是不是都为0
 * 2^9*5*9
 * 暴力题，所有策略中找出所有翻转次数最少的
 * 暴力枚举所有可能，9个位置，要么翻，要么不翻
 * 稍微再优化一点：不用把所有的state都跑完
 * 按照主动翻转次数从小到多来遍历这个state
 * 0000 0001 0010 0011 0100 0101
 * 如果按照bit 1单调的数目来遍历state的话，一旦发现check过了，就可以直接输出
 * 如何来实现呢？
 */
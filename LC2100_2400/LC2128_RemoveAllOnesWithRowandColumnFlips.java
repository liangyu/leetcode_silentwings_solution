package LC2100_2400;
import java.util.*;
public class LC2128_RemoveAllOnesWithRowandColumnFlips {
    /**
     * You are given an m x n binary matrix grid.
     *
     * In one operation, you can choose any row or column and flip each value in that row or column (i.e., changing all
     * 0's to 1's, and all 1's to 0's).
     *
     * Return true if it is possible to remove all 1's from grid using any number of operations or false otherwise.
     *
     * Input: grid = [[0,1,0],[1,0,1],[0,1,0]]
     * Output: true
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
    // S1: XOR
    // time = O(m * n), space = O(1)
    public boolean removeOnes(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            int k = grid[i][0] ^ grid[0][0]; // if same, k = 0; if not, k = 1;
            for (int j = 0; j < n; j++) {
                if ((grid[i][j] ^ grid[0][j]) != k) return false;
            }
        }
        return true;
    }

    // S2: Flip
    // time = O(m * n), space = O(1)
    public boolean removeOnes2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 1) flip(grid, i, true);
        }

        for (int j = 0; j < n; j++) {
            if (grid[0][j] == 1) flip(grid, j, false);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) return false;
            }
        }
        return true;
    }

    private void flip(int[][] grid, int x, boolean isRow) {
        int m = grid.length, n = grid[0].length;
        if (isRow) {
            for (int j = 0; j < n; j++) {
                grid[x][j] = 1 - grid[x][j];
            }
        } else {
            for (int i = 0; i < m; i++) {
                grid[i][x] = 1 - grid[i][x];
            }
        }
    }
}
/**
 * 每行每列只能翻转0次或者最多1次，因为2次的话就回到原点，毫无意义。
 * 如果选择翻转某些行或者列，那么要能使最终结果为0，那就必须实现翻转后所有的行或者列都得相等，才能通过最后翻转这些有1的行或者列实现全0
 * 那么如果我们以第一行或者第一列作为参照，剩下的行或者列与它或者相等，或者互异，只有这样才能实现翻转后所有行或者列相等
 * 于是，我们如果以第一行为标准，剩下每一行与对应的第一行的元素在同一列上实现xor的话，所得到的的值都应该与该行第一个元素与0行第一个元素xor值相等
 * 即与第一行所有元素均相等，或者均互异，否则就不可能最终实现所有行或者列相等了
 * 以此可以来判断最终是否可以实现全0与否。
 */
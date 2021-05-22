package LC1801_2100;
import java.util.*;
public class LC1861_RotatingtheBox {
    /**
     * You are given an m x n matrix of characters box representing a side-view of a box. Each cell of the box is one
     * of the following:
     *
     * A stone '#'
     * A stationary obstacle '*'
     * Empty '.'
     * The box is rotated 90 degrees clockwise, causing some of the stones to fall due to gravity. Each stone falls down
     * until it lands on an obstacle, another stone, or the bottom of the box. Gravity does not affect the
     * obstacles' positions, and the inertia from the box's rotation does not affect the stones' horizontal positions.
     *
     * It is guaranteed that each stone in box rests on an obstacle, another stone, or the bottom of the box.
     *
     * Return an n x m matrix representing the box after the rotation described above.
     *
     * Input: box = [["#","#","*",".","*","."],
     *               ["#","#","#","*",".","."],
     *               ["#","#","#",".","#","."]]
     * Output: [[".","#","#"],
     *          [".","#","#"],
     *          ["#","#","*"],
     *          ["#","*","."],
     *          ["#",".","*"],
     *          ["#",".","."]]
     *
     * Constraints:
     *
     * m == box.length
     * n == box[i].length
     * 1 <= m, n <= 500
     * box[i][j] is either '#', '*', or '.'.
     * @param box
     * @return
     */
    // S1: 最优解！
    // time = O(m * n), space = O(1)
    public char[][] rotateTheBox(char[][] box) {
        int m = box.length, n = box[0].length;
        char[][] res = new char[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(res[i], '.');

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int count = 0;
                int k = j;
                while (k < n && box[i][k] != '*') {
                    if (box[i][k] == '#') count++; // record the number of stones above the obstacle
                    k++;
                } // k will be landing on the obstacle or the end of the row
                // (i, k) -> (k, m-1-i) rotate the row
                if (k != n) {  // if not reach the end of the row
                    res[k][m - 1 - i] = '*';
                }
                for (int s = 1; s <= count; s++) {
                    res[k - s][m - 1 - i] = '#';
                }
                j = k; // 调整下寻找下一个挡板的起点
            }
        }
        return res;
    }

    // S2 [Test Solution]
    // time = O(m * n), space = O(n)
    private boolean isOver = false;
    public char[][] rotateTheBox2(char[][] box) {
        // corner case
        if (box == null || box.length == 0 || box[0] == null || box[0].length == 0) return null;

        int m = box.length, n = box[0].length;
        for (char[] b : box) { // O(m)
            isOver = false;
            helper(b, n - 1); // O(n)
        }

        // rotate matrix
        char[][] res = new char[n][m];
        for (int i = 0; i < m; i++) { // O(m * n)
            for (int j = 0; j < n; j++) {
                res[j][m - 1 - i] = box[i][j];
            }
        }
        return res;
    }

    private void helper(char[] b, int end) {
        int[] ans = findObs(b, end); // O(n)
        int cur = ans[0];
        if (cur == -1) {
            isOver = true;
            return;
        }
        for (int i = ans[1]; i >= 0; i--) { // O9n)
            if (isOver) return;
            if (b[i] == '#' && i < cur) {
                b[i] = '.';
                b[cur--] = '#';
            } else if (b[i] == '*') {
                helper(b, i);
            }
        }
        isOver = true;
    }

    private int[] findObs(char[] b, int end) {
        for (int i = end; i >= 0; i--) { // O(n)
            if (b[i] == '.') {
                // check if there is a stone above it without any obs
                boolean hasObs = false;
                int obs = i - 1, stone = i - 1;
                for (int j = i - 1; j >= 0; j--) {
                    if (b[j] == '*') {
                        hasObs = true;
                        obs = j;
                        break;
                    } else if (b[j] == '#') {
                        stone = j;
                        break;
                    }
                }
                if (!hasObs) return new int[]{i, stone};
                else i = obs;
            }
        }
        return new int[]{-1, -1};
    }
}
/**
 * 挡板上面有多少个石头，在第二个挡板之前有多少个石头
 * => 对于原数组而言，逐行扫描，在同一行里以挡板为分界点，找上面有多少个石头，再恢复这一行的扫描，看第二个挡板之前有多少个石头
 */


package LC2101_2400;
import java.util.*;
public class LC2326_SpiralMatrixIV {
    /**
     * You are given two integers m and n, which represent the dimensions of a matrix.
     *
     * You are also given the head of a linked list of integers.
     *
     * Generate an m x n matrix that contains the integers in the linked list presented in spiral order (clockwise),
     * starting from the top-left of the matrix. If there are remaining empty spaces, fill them with -1.
     *
     * Return the generated matrix.
     *
     * Input: m = 3, n = 5, head = [3,0,2,6,8,1,7,9,4,2,5,5,0]
     * Output: [[3,0,2,6,8],[5,0,-1,-1,1],[5,2,4,9,7]]
     *
     * Input: m = 1, n = 4, head = [0,1,2]
     * Output: [[0,1,2,-1]]
     *
     * Constraints:
     *
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * The number of nodes in the list is in the range [1, m * n].
     * 0 <= Node.val <= 1000
     * @param m
     * @param n
     * @param head
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(res[i], -1);
        int x = 0, y = 0, d = 1;
        ListNode cur = head;
        for (int i = 0; i < m * n && cur != null; i++) {
            res[x][y] = cur.val;
            int a = x + directions[d][0];
            int b = y + directions[d][1];
            if (a < 0 || a >= m || b < 0 || b >= n || res[a][b] != -1) {
                d = (d + 1) % 4;
                a = x + directions[d][0];
                b = y + directions[d][1];
            }
            x = a;
            y = b;
            cur = cur.next;
        }
        return res;
    }
}
/**
 * 偏移量做法
 */
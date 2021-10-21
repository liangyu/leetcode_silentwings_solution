package LC601_900;

public class LC782_TransformtoChessboard {
    /**
     * You are given an n x n binary grid board. In each move, you can swap any two rows with each other, or any two
     * columns with each other.
     *
     * Return the minimum number of moves to transform the board into a chessboard board. If the task is impossible,
     * return -1.
     *
     * A chessboard board is a board where no 0's and no 1's are 4-directionally adjacent.
     *
     * Input: board = [[0,1,1,0],[0,1,1,0],[1,0,0,1],[1,0,0,1]]
     * Output: 2
     *
     * Constraints:
     *
     * n == board.length
     * n == board[i].length
     * 2 <= n <= 30
     * board[i][j] is either 0 or 1.
     * @param board
     * @return
     */
    public int movesToChessboard(int[][] board) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return 0;

        int n = board.length;
        int count1 = 1, count2 = 0;
        for (int j = 1; j < n; j++) {
            boolean same = (board[0][j] == board[0][0]);
            if (same) {
                count1++;
                for (int i = 0; i < n; i++) {
                    if (board[i][j] != board[i][0]) return -1;
                }
            } else {
                count2++;
                for (int i = 0; i < n; i++) {
                    if (board[i][j] == board[i][0]) return -1;
                }
            }
        }
        if (Math.abs(count1 - count2) > 1) return -1;

        count1 = 1;
        count2 = 0;
        for (int i = 1; i < n; i++) {
            boolean same = board[i][0] == board[0][0];
            if (same) {
                count1++;
                for (int j = 0; j < n; j++) {
                    if (board[i][j] != board[0][j]) return -1;
                }
            } else {
                count2++;
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == board[0][j]) return -1;
                }
            }
        }
        if (Math.abs(count1 - count2) > 1) return -1;

        int countDiff1 = 0, countDiff2 = 0;
        int res = 0;
        for (int j = 0; j < n; j++) {
            if (board[0][j] != j % 2) {
                countDiff1++;
            }
        }

        for (int i = 0; i < n; i++) {
            if (board[i][0] != i % 2) {
                countDiff2++;
            }
        }
        if (n % 2 == 0) {
            res += Math.min(countDiff1, n - countDiff1) / 2; // 要求次数最小, swap cols
            res += Math.min(countDiff2, n - countDiff2) / 2; // swap rows
        } else {
            if (countDiff1 % 2 == 1) countDiff1 = n - countDiff1;
            if (countDiff2 % 2 == 1) countDiff2 = n - countDiff2;
            res += countDiff1 / 2;
            res += countDiff2 / 2;
        }
        return res;
    }
}
/**
 * 任意交换2行，会有什么发现呢？
 * 4列完全相同，剩下四列也是完全相同；same for the rows => 最终才会产生那种效果
 * 任意交换2行，造成最重要的特点，对于所有列而言，总是有4个一样，另外4个长另一个样子
 * 一个线索 => 必须要保证4个要长一样，另外4个也要长一样
 * 希望它们是黑白相间的
 * 以第一列为一种模式，然后跟第一列去比较，要么跟第一列是一样的模式，要么是不一样的模式
 * if n is odd
 * 101
 * 110 -> 只miss match 1个是不可能的
 * 110010
 * 101010 => 2
 * 010101 => 4
 *
 * 11010
 * 10101 => 4
 * 01010 => 1
 *
 * 最大突破点：任意行跟列的交换不会影响行列的种类
 * 只要判断出是不是有4种一样的列，以及另外4种一样的列
 * 要行与列分别进行判断，保证每一行和列都是黑白交错的。
 * 这就是能形成棋盘的一个充分必要条件。
 * 只看第一行怎么重新排列，后面都会跟着
 * 要么都是黑白黑白，要么就是白黑白黑
 * 这里利用坐标奇偶性
 * 如果j是偶数，j % 2 == 0，强制这一格为0，不是的话就表示放错了
 * countDiff不管在什么情况下，必须是偶数
 * 因为mismatch只能发生在一对放错的0和1之间发生，所以奇数是不行的，奇数那种pattern是不可能实现的，只有偶数的情况才能实现。
 */

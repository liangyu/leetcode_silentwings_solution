package LC1801_2100;
import java.util.*;
public class LC2056_NumberofValidMoveCombinationsOnChessboard {
    /**
     * There is an 8 x 8 chessboard containing n pieces (rooks, queens, or bishops). You are given a string array pieces
     * of length n, where pieces[i] describes the type (rook, queen, or bishop) of the ith piece. In addition, you are
     * given a 2D integer array positions also of length n, where positions[i] = [ri, ci] indicates that the ith piece
     * is currently at the 1-based coordinate (ri, ci) on the chessboard.
     *
     * Each piece on the chessboard can be moved at most once according to the type of piece. In a move, you may choose
     * the direction the piece moves and the number of squares it travels without moving the piece outside the
     * chessboard. The pieces are allowed to move as follows:
     *
     * A rook can move horizontally or vertically from (r, c) to the direction of (r+1, c), (r-1, c), (r, c+1), or (r, c-1).
     * A queen can move horizontally, vertically, or diagonally from (r, c) to the direction of (r+1, c), (r-1, c),
     * (r, c+1), (r, c-1), (r+1, c+1), (r+1, c-1), (r-1, c+1), (r-1, c-1).
     * A bishop can move diagonally from (r, c) to the direction of (r+1, c+1), (r+1, c-1), (r-1, c+1), (r-1, c-1).
     * A move combination consists of all the moves performed on all the given pieces. Every second, each piece will
     * instantaneously travel one square towards their destination if they are not already at it. All pieces start
     * traveling at the 0th second. A move combination is invalid if, at a given time, two or more pieces occupy the
     * same square.
     *
     * Return the number of valid move combinations.
     * <p>
     * Notes:
     *
     * No two pieces will start in the same square.
     * It is possible that a piece is not moved in a move combination.
     * If two pieces are directly adjacent to each other, it is valid for them to move past each other and swap
     * positions in one second.
     *
     * Input: pieces = ["rook"], positions = [[1,1]]
     * Output: 15
     *
     * Constraints:
     *
     * n == pieces.length
     * n == positions.length
     * 1 <= n <= 4
     * pieces only contains the strings "rook", "queen", and "bishop".
     * There will be at most one queen on the chessboard.
     * 1 <= xi, yi <= 8
     * Each positions[i] is distinct.
     *
     * @param pieces
     * @param positions
     * @return
     */
    // time = O(n^3 * 3^n), space = O(2^n)
    private int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    HashSet<Long> res;
    public int countCombinations(String[] pieces, int[][] positions) {
        int n = pieces.length;
        res = new HashSet<>();
        for (int state = 0; state < (1 << (3 * n)); state++) { // iterate dir combinations  O(3^n)
            boolean flag = true;
            int[] dirs = new int[n];
            for (int i = 0; i < n; i++) { // O(n)
                int d = (state >> (i * 3)) % 8;
                if (pieces[i].equals("rook") && d > 3) {
                    flag = false;
                    break;
                }
                if (pieces[i].equals("bishop") && d < 4) {
                    flag = false;
                    break;
                }
                dirs[i] = d;
            }
            if (flag) {
                dfs(positions, dirs, (1 << n) - 1);
            }
        }
        return res.size() + 1; // 允许所有旗子都不动
    }

    private void dfs(int[][] positions, int[] dirs, int state) {
        int n = positions.length;
        // 只挑那些1变成0，而不挑那么0变成1的子集
        for (int subset = state; subset > 0; subset = (subset - 1) & state) { // subset不能为0， 否则会死循环，单独放在后面处理
            boolean flag = true;
            int[][] posNew = new int[n][2];
            for (int i = 0; i < n; i++) posNew[i] = positions[i].clone();
            for (int i = 0; i < n; i++) { // the i-th piece
                if (((subset >> i) & 1) == 1) { // 可以往前走
                    posNew[i][0] = positions[i][0] + dir[dirs[i]][0];
                    posNew[i][1] = positions[i][1] + dir[dirs[i]][1];
                    if (posNew[i][0] < 1 || posNew[i][0] > 8) {
                        flag = false;
                        break;
                    }
                    if (posNew[i][1] < 1 || posNew[i][1] > 8) {
                        flag = false;
                        break;
                    }
                }
            }
            if (!flag) continue;
            if (duplicate(posNew)) continue;

            long hash = getHash(posNew);
            if (res.contains(hash)) continue; // 已经走过了
            res.add(hash);
            dfs(posNew, dirs, subset);
        }
    }

    private boolean duplicate(int[][] pos) { // O(n^2)
        int n = pos.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (pos[i][0] == pos[j][0] && pos[i][1] == pos[j][1]) return true;
            }
        }
        return false;
    }

    private long getHash(int[][] pos) { // O(n)
        int n = pos.length;
        long res = 0;
        for (int i = 0; i < n; i++) {
            res = res * 10 + pos[i][0];
            res = res * 10 + pos[i][1];
        }
        return res;
    }
}
/**
 * 最多4个棋子
 * directions: 8*4*4*4 = 256
 * 盘面上所有棋子排列组合总共256种
 * 每个回合只能走一步，除非某些棋子决定不走，停下来，但一旦停下来就再也不能走了
 * 考虑各种方向组合，以及每个棋子前进步数的组合
 * 设置一个变量记录还在走的棋子 -> bitmask
 * 1111 => 1111
 *         1110 => 1110, 1010, 0110, ...
 *         1010
 *         ....
 *         2^4 - 1 = 15 (4个都不走就没意义了)
 * 分支如何确定？ => 看每个分支的子集
 * 各走一步，碰撞了，出界了 => invalid
 * 暴力，盘面非常小，棋子非常少，穷举是有可能的 8^4 * 256 ~ 10^6 可以接受的
 * 暴力的dfs
 * 穷举所有的状态变量 -> bitmask 3个比特位确定一个方向 xxx xxx xxx xxx  2^12 = 4096
 *
 * A向左走0步，B向右走1步 => (A, B + 1) -> 后续：A不变，B再走一步
 * A向下走0步，B向右走1步 => (A, B + 1) -> 后续：A不变，B再走一步，与上面一模一样，完全重复！！！
 * 出现重复，仅仅是针对上面这个case进行去重。
 * 没法在一个方向组合里去重，只能放在全局来去重
 * 拼接成一个长整型 => 代表一个盘面
 *
 * 关于block的问题：如果两个棋子在某位置相撞了，
 * 那么其中静止的那枚棋子后续状态是不会变化了，
 * 而要继续移动的那枚棋子，其后面位置的state都要基于相撞位置成立能走到，即为1的状态之上，所以通过continue打破了继续向下基于这个subset的dfs
 * 从而后面被block住的case也就不会在最后的总数之中出现了。
 */


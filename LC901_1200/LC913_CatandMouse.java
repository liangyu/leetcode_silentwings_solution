package LC901_1200;
import java.util.*;
public class LC913_CatandMouse {
    /**
     * A game on an undirected graph is played by two players, Mouse and Cat, who alternate turns.
     *
     * The graph is given as follows: graph[a] is a list of all nodes b such that ab is an edge of the graph.
     *
     * The mouse starts at node 1 and goes first, the cat starts at node 2 and goes second, and there is a hole at node 0.
     *
     * During each player's turn, they must travel along one edge of the graph that meets where they are.  For example,
     * if the Mouse is at node 1, it must travel to any node in graph[1].
     *
     * Additionally, it is not allowed for the Cat to travel to the Hole (node 0.)
     *
     * Then, the game can end in three ways:
     *
     * If ever the Cat occupies the same node as the Mouse, the Cat wins.
     * If ever the Mouse reaches the Hole, the Mouse wins.
     * If ever a position is repeated (i.e., the players are in the same position as a previous turn, and it is the
     * same player's turn to move), the game is a draw.
     * Given a graph, and assuming both players play optimally, return
     *
     * 1 if the mouse wins the game,
     * 2 if the cat wins the game, or
     * 0 if the game is a draw.
     *
     * Input: graph = [[2,5],[3],[0,4,5],[1,4,5],[2,3],[0,2,3]]
     * Output: 0
     *
     * Constraints:
     *
     * 3 <= graph.length <= 50
     * 1 <= graph[i].length < graph.length
     * 0 <= graph[i][j] < graph.length
     * graph[i][j] != i
     * graph[i] is unique.
     * The mouse and the cat can always move.
     * @param graph
     * @return
     */
    // time = O(n^3), space = O(n^2)
    public int catMouseGame(int[][] graph) {
        int n = graph.length;
        int[][][] color = new int[n][n][3]; // 0: unvisited, 1: mouse turn, 2: cat turn
        Queue<int[]> queue = new LinkedList<>();
        for (int turn = 1; turn <= 2; turn++) {
            for (int i = 1; i < n; i++) { // cat cannot be at 0
                color[i][i][turn] = 2; // cat win
                queue.offer(new int[]{i, i, turn});
            }
            for (int j = 0; j < n; j++) {
                color[0][j][turn] = 1;
                queue.offer(new int[]{0, j, turn});
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int m = cur[0], c = cur[1], t = cur[2];
            int status = color[m][c][t];

            for (int[] nextStatus : findNextStatus(graph, m, c, t)) { // 邻接状态
                int m2 = nextStatus[0], c2 = nextStatus[1], t2 =nextStatus[2];
                if (color[m2][c2][t2] != 0) continue;
                if (t2 == status) { // immediate win
                    color[m2][c2][t2] = status;
                    queue.offer(new int[]{m2, c2, t2});
                } else if (allEnemyWin(graph, color, m2, c2, t2)) { // eventually lose
                    color[m2][c2][t2] = (t2 == 1) ? 2 : 1;
                    queue.offer(new int[]{m2, c2, t2});
                }
            }
        }
        return color[1][2][1];
    }

    private List<int[]> findNextStatus(int[][] graph, int m, int c, int t) { // 后 -> 前
        List<int[]> res = new ArrayList<>();
        if (t == 1) {
            for (int c_next : graph[c]) {
                if (c_next != 0) res.add(new int[]{m, c_next, 2});
            }
        } else {
            for (int m_next : graph[m]) {
                res.add(new int[]{m_next, c, 1});
            }
        }
        return res;
    }

    private boolean allEnemyWin(int[][] graph, int[][][] color, int m2, int c2, int t2) {
        if (t2 == 1) {
            for (int m_next : graph[m2]) {
                if (color[m_next][c2][2] != 2) return false; // 如果下家不是猫赢 => 解困
            }
        } else if (t2 == 2) {
            for (int c_next : graph[c2]) {
                if (c_next != 0 && color[m2][c_next][1] != 1) return false;
            }
        }
        return true;
    }
}
/**
 * game theory
 * 99% 可以用dfs做
 * DFS(A)
 * DFS(B1) DFS(B2) DFS(B3) ... DFS(Bn)
 * 但这道题不行，因为这道题不但有赢有输，还有平局！！！
 * 对于A来说，什么时候判断平局呢？
 * 平局判断的条件？=> endpoint
 * 老鼠在哪里，猫在哪里，能确定平局吗？但输和赢是可以定义的。
 * status(mouse, cat, turn)
 * 平局是一个动态过程，在那里不停的循环重复出现，单点是无法确定平局状态的 => min-max失效，
 * 问题就在这里，平局的时候没有任何一个single point status来定义
 * 给我们一个全新的视角来做这个问题 => bottom-up
 * (m1,c1,t1) --> 1 => (m11,c11,t11) -> 2
 *                  => (m12,c12,t12) -> 1
 * (m2,c2,t2) --> 2
 * (m3,c3,t3) --> 2
 * (mi,ci,ti) 有些状态达不到 => draw
 * 把所有猫和老鼠的位置都穷举一遍
 * 有些情况没法确定输和赢，这就预示着它们是平局
 * m,c,t (t==1) status = cat win
 * m2,c2,t2 (t2==1) status = cat win
 * 如果这轮猫走投无路了，都是老鼠赢 => 只能加入队列中
 * (m',c',t') =>
 */

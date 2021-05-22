package LC901_1200;
import java.util.*;
public class LC1197_MinimumKnightMoves {
    /**
     * In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at square [0, 0].
     *
     * A knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal direction,
     * then one square in an orthogonal direction.
     *
     * Return the minimum number of steps needed to move the knight to the square [x, y].  It is guaranteed the answer
     * exists.
     *
     * Input: x = 2, y = 1
     * Output: 1
     *
     * Constraints:
     *
     * |x| + |y| <= 300
     * @param x
     * @param y
     * @return
     */
    // S1: BFS
    // time = O((max(Math.abs(x), Math.abs(y)))^2), space = O((max(Math.abs(x), Math.abs(y)))^2)
    private static final int[][] DIRECITONS = new int[][]{{-2, 1}, {2, 1}, {-1, 2}, {1, 2}, {-2, -1}, {2, -1}, {-1, -2}, {1, -2}};
    public int minKnightMoves(int x, int y) {
        // force to only consider the case in the 1st quadrant, the other 3 works exactly the same way
        x = Math.abs(x);
        y = Math.abs(y);

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        HashSet<String> visited = new HashSet<>();
        visited.add("0,0");

        int minLen = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int i = cur[0], j = cur[1];
                if (i == x && j == y) return minLen;
                for (int[] dir : DIRECITONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (!visited.contains(ii + "," + jj) && ii >= -1 && jj >= -1) {
                        queue.offer(new int[]{ii, jj});
                        visited.add(ii + "," + jj);
                    }
                }
            }
            minLen++;
        }
        return -1;
    }
}

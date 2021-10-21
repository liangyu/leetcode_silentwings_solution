package LC1501_1800;
import java.util.*;
public class LC1654_MinimumJumpstoReachHome {
    /**
     * A certain bug's home is on the x-axis at position x. Help them get there from position 0.
     *
     * The bug jumps according to the following rules:
     *
     * It can jump exactly a positions forward (to the right).
     * It can jump exactly b positions backward (to the left).
     * It cannot jump backward twice in a row.
     * It cannot jump to any forbidden positions.
     * The bug may jump forward beyond its home, but it cannot jump to positions numbered with negative integers.
     *
     * Given an array of integers forbidden, where forbidden[i] means that the bug cannot jump to the position
     * forbidden[i], and integers a, b, and x, return the minimum number of jumps needed for the bug to reach its home.
     * If there is no possible sequence of jumps that lands the bug on position x, return -1.
     *
     * Input: forbidden = [14,4,18,1,15], a = 3, b = 15, x = 9
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= forbidden.length <= 1000
     * 1 <= a, b, forbidden[i] <= 2000
     * 0 <= x <= 2000
     * All the elements in forbidden are distinct.
     * Position x is not forbidden.
     * @param forbidden
     * @param a
     * @param b
     * @param x
     * @return
     */
    // time = O(n), space = O(n)
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        if (x == 0) return 0;

        boolean[][] visited = new boolean[6001][2];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        int max_forbid = -1;
        for (int f : forbidden) max_forbid = Math.max(max_forbid, f);
        int limit = Math.max(x, max_forbid) + b; // 2000 + 2000 -> 4000

        for (int f : forbidden) {
            visited[f][0] = true;
            visited[f][1] = true;
        }

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int pos = cur[0], dir = cur[1]; // 0: right, 1: left
                if (pos <= limit && !visited[pos + a][0]) { // pos + a -> 4000 + 2000 = 6000
                    visited[pos + a][0] = true;
                    queue.offer(new int[]{pos + a, 0});
                    if (pos + a == x) return step + 1;
                }
                if (dir == 0) {
                    if (pos - b >= 0 && !visited[pos - b][1]) {
                        visited[pos - b][1] = true;
                        queue.offer(new int[]{pos - b, 1});
                        if (pos - b == x) return step + 1;
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
/**
 * minimum number -> bfs的应用,像波浪般推移
 * 到下一步有2个方案：向左或者向右
 * 分叉可以一直分下去
 * 一直往右走的分叉，似乎是没有任何boundary的
 * 如果我每次都选择往右走，可能会一直走下去，碰不到底，就有可能会TLE，越走越远
 * 那如何处理呢？
 * 我们假设整个路径是通过m次右移和n次左移实现从0到x的。我们可以知道这m次右移和n次左移
 * 0 -> x: m right + n left
 *         net gain = m * a - n * b
 * 我们任意打乱先后顺序的话（但这个乱序要避免碰到forbidden），并不影响最终到达x的结论。
 * 我们没有必要让人一路狂飙到右极限再一路狂飙回来。
 * 0 -> x = 5 => steps = 5
 * a = 3, b = 2
 * +3 -2 +3 -2 +3 -2 +3 -2 +3 -2 = 5
 * 无法预见到这么好的方案
 * 当我们走到9的时候，这个时候就可以判断出只要左移就行了，没必要右移
 * +3 +3 +3 -2
 * cur - x > b turn left
 * limit = x + b  => 只要左移
 * 这个limit有个bug，在于forbidden
 * limit = max(x, max_forbidden) + b)
 * if (lastTime is right) go left;
 * if (cur <= limit) go right;
 * 不能踩到forbidden,踩到就失效
 */
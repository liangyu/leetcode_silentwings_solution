package LC1201_1500;
import java.util.*;
public class LC1298_MaximumCandiesYouCanGetfromBoxes {
    /**
     * Given n boxes, each box is given in the format [status, candies, keys, containedBoxes] where:
     *
     * status[i]: an integer which is 1 if box[i] is open and 0 if box[i] is closed.
     * candies[i]: an integer representing the number of candies in box[i].
     * keys[i]: an array contains the indices of the boxes you can open with the key in box[i].
     * containedBoxes[i]: an array contains the indices of the boxes found in box[i].
     * You will start with some boxes given in initialBoxes array. You can take all the candies in any open box and you
     * can use the keys in it to open new boxes and you also can use the boxes you find in it.
     *
     * Return the maximum number of candies you can get following the rules above.
     *
     * Input: status = [1,0,1,0], candies = [7,5,4,100], keys = [[],[],[1],[]], containedBoxes = [[1,2],[3],[],[]],
     * initialBoxes = [0]
     * Output: 16
     *
     * Constraints:
     *
     * 1 <= status.length <= 1000
     * status.length == candies.length == keys.length == containedBoxes.length == n
     * status[i] is 0 or 1.
     * 1 <= candies[i] <= 1000
     * 0 <= keys[i].length <= status.length
     * 0 <= keys[i][j] < status.length
     * All values in keys[i] are unique.
     * 0 <= containedBoxes[i].length <= status.length
     * 0 <= containedBoxes[i][j] < status.length
     * All values in containedBoxes[i] are unique.
     * Each box is contained in one box at most.
     * 0 <= initialBoxes.length <= status.length
     * 0 <= initialBoxes[i] < status.length
     * @param status
     * @param candies
     * @param keys
     * @param containedBoxes
     * @param initialBoxes
     * @return
     */
    // time = O(n), space = O(n)
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        Queue<Integer> queue = new LinkedList<>();
        for (int b : initialBoxes) queue.offer(b);
        HashSet<Integer> set = new HashSet<>();
        int res = 0;
        boolean opened = true;

        while (!queue.isEmpty() && opened) {
            int size = queue.size();
            opened = false;
            while (size-- > 0) {
                int b = queue.poll();
                if (status[b] == 0 && !set.contains(b)) {
                    queue.offer(b); // 没有打开的盒子就放回队列，进入下一轮，看后面有没有钥匙可以打开它
                } else {
                    opened = true; // 标记这一轮有进展，有变化，防止死循环
                    res += candies[b];
                    for (int k : keys[b]) set.add(k);
                    for (int newb : containedBoxes[b]) {
                        queue.offer(newb);
                    }
                }
            }
        }
        return res;
    }
}
/**
 * 层级遍历，bfs
 * 打开盒子后可能得到新盒子，就这样一层一层
 * 题目保证了每一把钥匙在 keys 中不会出现超过一次，并且每一个盒子在 containedBoxes 中也不会出现超过一次，
 * 因此在广度优先搜索中最多会得到 N 把钥匙和 N 个盒子，总时间复杂度为 O(N)。
 */

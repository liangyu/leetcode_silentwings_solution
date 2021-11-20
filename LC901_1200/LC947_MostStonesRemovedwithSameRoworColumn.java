package LC901_1200;
import java.util.*;
public class LC947_MostStonesRemovedwithSameRoworColumn {
    /**
     * On a 2D plane, we place n stones at some integer coordinate points. Each coordinate point may have at most one stone.
     *
     * A stone can be removed if it shares either the same row or the same column as another stone that has not been removed.
     *
     * Given an array stones of length n where stones[i] = [xi, yi] represents the location of the ith stone, return
     * the largest possible number of stones that can be removed.
     *
     * Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= stones.length <= 1000
     * 0 <= xi, yi <= 10^4
     * No two stones are at the same coordinate point.
     * @param stones
     * @return
     */
    // time = O(nlogk), space = O(k)   n: num of stones; k: different (x,y) numbers of array stones
    HashMap<Integer, Integer> parent;
    HashMap<Integer, List<Integer>> mapX;
    HashMap<Integer, List<Integer>> mapY;
    int n = 10000;
    public int removeStones(int[][] stones) {
        parent = new HashMap<>();
        mapX = new HashMap<>();
        mapY = new HashMap<>();
        for (int[] x : stones) {
            int i = x[0], j = x[1];
            int id = i * n + j;
            parent.put(id, id);
            mapX.putIfAbsent(i, new ArrayList<>());
            mapY.putIfAbsent(j, new ArrayList<>());
            mapX.get(i).add(id);
            mapY.get(j).add(id);
        }

        for (int a : mapX.keySet()) {
            int id0 = mapX.get(a).get(0);
            for (int i = 1; i < mapX.get(a).size(); i++) {
                int id = mapX.get(a).get(i);
                if (findParent(id0) != findParent(id)) {
                    union(id0, id);
                }
            }
        }

        for (int a : mapY.keySet()) {
            int id0 = mapY.get(a).get(0);
            for (int i = 1; i < mapY.get(a).size(); i++) {
                int id = mapY.get(a).get(i);
                if (findParent(id0) != findParent(id)) {
                    union(id0, id);
                }
            }
        }

        HashSet<Integer> set = new HashSet<>(); // save each group's ancestor
        for (int[] x : stones) {
            int id = x[0] * n + x[1];
            int id0 = findParent(id);
            set.add(id0);
        }

        return stones.length - set.size();
    }

    private int findParent(int x) {
        if (x != parent.get(x)) parent.put(x, findParent(parent.get(x)));
        return parent.get(x);
    }

    private void union(int x, int y) {
        x = parent.get(x);
        y = parent.get(y);
        if (x < y) parent.put(y, x);
        else parent.put(x, y);
    }
}
/**
 * x x x x   xx
 *   x
 *   x
 *   x
 *   x
 *   x
 * 凡是同一行，就是一个group，同一个列也是一个group
 * 只要是同一个group,有k个元素，那一定能remove k - 1个元素
 * 每个group都拿到只剩1个，只有每个group只有最后一个拿不掉
 * total removed = all stones - group number
 * 不停通过两两之间union
 * follow-up: 这些stone移走的顺序是什么？
 *       z
 *       z
 *       z
 * xxxxx xx x xx
 *   y
 *   y
 *   y
 * 从角落里开始移除直到交叉路口停下来
 * 找那些入度为1的点先移除，最后只剩一行，从左到右删除到只剩1个即可
 */

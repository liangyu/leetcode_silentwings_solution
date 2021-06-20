package LC901_1200;
import java.util.*;
public class LC1182_ShortestDistancetoTargetColor {
    /**
     * You are given an array colors, in which there are three colors: 1, 2 and 3.
     *
     * You are also given some queries. Each query consists of two integers i and c, return the shortest distance
     * between the given index i and the target color c. If there is no solution return -1.
     *
     * Input: colors = [1,1,2,1,3,2,2,3,3], queries = [[1,3],[2,2],[6,1]]
     * Output: [3,0,3]
     *
     * Constraints:
     *
     * 1 <= colors.length <= 5*10^4
     * 1 <= colors[i] <= 3
     * 1 <= queries.length <= 5*10^4
     * queries[i].length == 2
     * 0 <= queries[i][0] < colors.length
     * 1 <= queries[i][1] <= 3
     * @param colors
     * @param queries
     * @return
     */
    // time = O(n + mlogn), space = O(n)   m: length of queries, n: length of colors
    public List<Integer> shortestDistanceColor(int[] colors, int[][] queries) {
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, TreeSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < colors.length; i++) {
            map.putIfAbsent(colors[i], new TreeSet<>());
            map.get(colors[i]).add(i);
        }

        for (int[] q : queries) {
            if (!map.containsKey(q[1])) {
                res.add(-1);
                continue;
            }
            Integer ck = map.get(q[1]).ceiling(q[0]);
            Integer fk = map.get(q[1]).floor(q[0]);
            if (ck != null && fk != null) res.add(Math.min(Math.abs(fk - q[0]), Math.abs(ck - q[0])));
            else if (ck == null && fk == null) res.add(-1);
            else res.add(ck == null ? Math.abs(fk - q[0]) : Math.abs(ck - q[0]));
        }
        return res;
    }
}

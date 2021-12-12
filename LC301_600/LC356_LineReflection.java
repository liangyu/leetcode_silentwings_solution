package LC301_600;
import java.util.*;
public class LC356_LineReflection {
    /**
     * Given n points on a 2D plane, find if there is such a line parallel to the y-axis that reflects the given points
     * symmetrically.
     *
     * In other words, answer whether or not if there exists a line that after reflecting all points over the given
     * line, the original points' set is the same as the reflected ones.
     *
     * Note that there can be repeated points.
     *
     * Input: points = [[1,1],[-1,1]]
     * Output: true
     *
     * Constraints:
     *
     * n == points.length
     * 1 <= n <= 10^4
     * -10^8 <= points[i][j] <= 10^8
     *
     * Follow up: Could you do better than O(n^2)?
     * @param points
     * @return
     */
    // S1: HashMap
    // time = O(nlogn), space = O(n)
    public boolean isReflected(int[][] points) {
        int n = points.length;
        if (n == 0) return true;

        HashMap<Integer, TreeSet<Integer>> map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) { // O(n)
            map.putIfAbsent(points[i][1], new TreeSet<>());
            map.get(points[i][1]).add(points[i][0]);
            set.add(points[i][0]);
        }

        double x0 = 0;
        for (int x : set) x0 += x;
        x0 /= set.size();

        for (int key : map.keySet()) {
            TreeSet<Integer> tset = map.get(key);
            int[] temp = new int[tset.size()];
            int idx = 0;
            for (int x : tset) temp[idx++] = x;
            int i = 0, j = temp.length - 1;
            while (i <= j) {
                if (temp[i] + temp[j] != x0 * 2) return false;
                i++;
                j--;
            }
        }
        return true;
    }

    // S2: HashSet (最优解!)
    // time = O(n), space = O(n)
    public boolean isReflected2(int[][] points) {
        int n = points.length, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            set.add(points[i][0] + "#" + points[i][1]);
            min = Math.min(min, points[i][0]);
            max = Math.max(max, points[i][0]);
        }

        int sum = min + max; // 找出如果对称轴存在时，对称两点的x值之和
        for (int[] x : points) {
            if (!set.contains(sum - x[0] + "#" + x[1])) return false;
        }
        return true;
    }
}

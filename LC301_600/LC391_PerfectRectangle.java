package LC301_600;
import java.util.*;
public class LC391_PerfectRectangle {
    /**
     * Given an array rectangles where rectangles[i] = [xi, yi, ai, bi] represents an axis-aligned rectangle. The
     * bottom-left point of the rectangle is (xi, yi) and the top-right point of it is (ai, bi).
     *
     * Return true if all the rectangles together form an exact cover of a rectangular region.
     *
     * Input: rectangles = [[1,1,3,3],[3,1,4,2],[3,2,4,4],[1,3,2,4],[2,3,3,4]]
     * Output: true
     *
     * Input: rectangles = [[1,1,2,3],[1,3,2,4],[3,1,4,2],[3,2,4,4]]
     * Output: false
     *
     * Input: rectangles = [[1,1,3,3],[3,1,4,2],[1,3,2,4],[2,2,4,4]]
     * Output: false
     *
     * Constraints:
     *
     * 1 <= rectangles.length <= 2 * 10^4
     * rectangles[i].length == 4
     * -10^5 <= xi, yi, ai, bi <= 10^5
     * @param rectangles
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isRectangleCover(int[][] rectangles) {
        HashMap<String, Integer> map = new HashMap<>();
        long sum = 0;
        for (int[] x : rectangles) {
            int a = x[0], b = x[1], c = x[2], d = x[3];
            String s1 = a + "#" + b;
            String s2 = a + "#" + d;
            String s3 = c + "#" + b;
            String s4 = c + "#" + d;
            map.put(s1, map.getOrDefault(s1, 0) + 1);
            map.put(s2, map.getOrDefault(s2, 0) + 1);
            map.put(s3, map.getOrDefault(s3, 0) + 1);
            map.put(s4, map.getOrDefault(s4, 0) + 1);
            sum += (long) (c - a) * (d - b);
        }

        List<int[]> res = new ArrayList<>();
        for (String key : map.keySet()) {
            int y = map.get(key);
            if (y == 1) {
                String[] strs = key.split("#");
                res.add(new int[]{Integer.valueOf(strs[0]), Integer.valueOf(strs[1])});
            } else if (y == 3) return false;
            else if (y > 4) return false;
        }
        if (res.size() != 4) return false;
        Collections.sort(res, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
        return sum == (long)(res.get(3)[0] - res.get(0)[0]) * (res.get(3)[1] - res.get(0)[1]);
    }
}
/**
 * 必要条件：
 * 1. 只出现1次的点有4个
 * 2. 3次： 0个
 * 3. 2次或4次：无限制
 * 4. 总面积相同
 *
 * 是否充分：
 */
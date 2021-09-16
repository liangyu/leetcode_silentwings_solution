package LC1801_2100;
import java.util.*;
public class LC2001_NumberofPairsofInterchangeableRectangles {
    /**
     * You are given n rectangles represented by a 0-indexed 2D integer array rectangles, where
     * rectangles[i] = [widthi, heighti] denotes the width and height of the ith rectangle.
     *
     * Two rectangles i and j (i < j) are considered interchangeable if they have the same width-to-height ratio.
     * More formally, two rectangles are interchangeable if widthi/heighti == widthj/heightj (using decimal division,
     * not integer division).
     *
     * Return the number of pairs of interchangeable rectangles in rectangles.
     *
     * Input: rectangles = [[4,8],[3,6],[10,20],[15,30]]
     * Output: 6
     *
     * Constraints:
     *
     * n == rectangles.length
     * 1 <= n <= 10^5
     * rectangles[i].length == 2
     * 1 <= widthi, heighti <= 10^5
     * @param rectangles
     * @return
     */
    // time = O(n), space = O(n)
    public long interchangeableRectangles(int[][] rectangles) {
        // corner case
        if (rectangles == null || rectangles.length == 0 || rectangles[0] == null || rectangles[0].length == 0) {
            return 0;
        }

        HashMap<Double, Integer> map = new HashMap<>();
        for (int [] r : rectangles) {
            double val = r[0] * 1.0 / r[1];
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        long res = 0;
        for (double key : map.keySet()) {
            if (map.get(key) > 1) {
                int count = map.get(key);
                res += (long) count * (count - 1) / 2;
            }
        }
        return res;
    }
}

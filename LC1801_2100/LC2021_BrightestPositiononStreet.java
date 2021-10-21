package LC1801_2100;
import java.util.*;
public class LC2021_BrightestPositiononStreet {
    /**
     * A perfectly straight street is represented by a number line. The street has street lamp(s) on it and is
     * represented by a 2D integer array lights. Each lights[i] = [positioni, rangei] indicates that there is a
     * street lamp at position positioni that lights up the area from [positioni - rangei, positioni + rangei] (inclusive).
     *
     * The brightness of a position p is defined as the number of street lamp that light up the position p.
     *
     * Given lights, return the brightest position on the street. If there are multiple brightest positions, return the
     * smallest one.
     *
     * Input: lights = [[-3,2],[1,2],[3,3]]
     * Output: -1
     *
     * Constraints:
     *
     * 1 <= lights.length <= 10^5
     * lights[i].length == 2
     * -10^8 <= positioni <= 10^8
     * 0 <= rangei <= 10^8
     * @param lights
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int brightestPosition(int[][] lights) {
        // corner case
        if (lights == null || lights.length == 0 || lights[0] == null || lights[0].length == 0) return 0;

        List<int[]> diff = new ArrayList<>();
        for (int[] x : lights) {
            diff.add(new int[]{x[0] - x[1], 1});
            diff.add(new int[]{x[0] + x[1], -1});
        }

        Collections.sort(diff, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);

        int max = -1, sum = 0, idx = -1;
        for (int[] x : diff) {
            sum += x[1];
            if (sum > max) {
                max = sum;
                idx = x[0];
            }
        }
        return idx;
    }
}

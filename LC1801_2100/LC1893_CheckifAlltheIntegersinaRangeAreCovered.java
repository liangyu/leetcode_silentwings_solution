package LC1801_2100;
import java.util.*;
public class LC1893_CheckifAlltheIntegersinaRangeAreCovered {
    /**
     * You are given a 2D integer array ranges and two integers left and right. Each ranges[i] = [starti, endi]
     * represents an inclusive interval between starti and endi.
     *
     * Return true if each integer in the inclusive range [left, right] is covered by at least one interval in ranges.
     * Return false otherwise.
     *
     * An integer x is covered by an interval ranges[i] = [starti, endi] if starti <= x <= endi.
     *
     * Input: ranges = [[1,2],[3,4],[5,6]], left = 2, right = 5
     * Output: true
     *
     * Constraints:
     *
     * 1 <= ranges.length <= 50
     * 1 <= starti <= endi <= 50
     * 1 <= left <= right <= 50
     * @param ranges
     * @param left
     * @param right
     * @return
     */
    // time = O(nlogn), space = O(1)
    public boolean isCovered(int[][] ranges, int left, int right) {
        // corner case
        if (ranges == null || ranges.length == 0 || ranges[0] == null || ranges[0].length == 0) return false;

        Arrays.sort(ranges, (o1, o2) -> o1[0] - o2[0]);
        for (int[] range : ranges) {
            if (left >= range[0] && left <= range[1]) left = range[1] + 1;
            if (left > right) return true;
        }
        return false;
    }
}

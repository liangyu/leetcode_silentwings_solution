package LC1801_2100;
import java.util.*;
public class LC1943_DescribethePainting {
    /**
     * There is a long and thin painting that can be represented by a number line. The painting was painted with
     * multiple overlapping segments where each segment was painted with a unique color. You are given a 2D integer
     * array segments, where segments[i] = [starti, endi, colori] represents the half-closed segment [starti, endi)
     * with colori as the color.
     *
     * The colors in the overlapping segments of the painting were mixed when it was painted. When two or more colors
     * mix, they form a new color that can be represented as a set of mixed colors.
     *
     * For example, if colors 2, 4, and 6 are mixed, then the resulting mixed color is {2,4,6}.
     * For the sake of simplicity, you should only output the sum of the elements in the set rather than the full set.
     *
     * You want to describe the painting with the minimum number of non-overlapping half-closed segments of these mixed
     * colors. These segments can be represented by the 2D array painting where painting[j] = [leftj, rightj, mixj] describes a half-closed segment [leftj, rightj) with the mixed color sum of mixj.
     *
     * For example, the painting created with segments = [[1,4,5],[1,7,7]] can be described by painting =
     * [[1,4,12],[4,7,7]] because:
     * [1,4) is colored {5,7} (with a sum of 12) from both the first and second segments.
     * [4,7) is colored {7} from only the second segment.
     * Return the 2D array painting describing the finished painting (excluding any parts that are not painted).
     * You may return the segments in any order.
     *
     * A half-closed segment [a, b) is the section of the number line between points a and b including point a and not
     * including point b.
     *
     * Input: segments = [[1,4,5],[4,7,7],[1,7,9]]
     * Output: [[1,4,14],[4,7,16]]
     *
     * Constraints:
     *
     * 1 <= segments.length <= 2 * 10^4
     * segments[i].length == 3
     * 1 <= starti < endi <= 10^5
     * 1 <= colori <= 10^9
     * Each colori is distinct.
     * @param segments
     * @return
     */
    // S1: Sort
    // time = O(nlogn), space = O(n)
    public List<List<Long>> splitPainting(int[][] segments) {
        List<List<Long>> res = new ArrayList<>();
        // corner case
        if (segments == null || segments.length == 0 || segments[0] == null || segments[0].length == 0) return res;

        List<int[]> eps = new ArrayList<>(); // [start/end, color, isEnd]
        for (int[] segment : segments) {
            eps.add(new int[]{segment[0], segment[2], 0});
            eps.add(new int[]{segment[1], segment[2], 1});
        }

        Collections.sort(eps, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[2] - o1[2]);

        int start = -1;
        long color = 0;
        for (int[] ep : eps) {
            if (ep[2] == 0) { // start
                if (start == -1) {
                    start = ep[0];
                    color += ep[1];
                } else {
                    if (start != ep[0]) res.add(Arrays.asList((long)start, (long)ep[0], color));
                    start = ep[0];
                    color += ep[1];
                }
            } else { // end
                if (res.size() == 0 || res.size() > 0 && res.get(res.size() - 1).get(1) != ep[0]) {
                    res.add(Arrays.asList((long)start, (long)ep[0], color));
                }
                color -= ep[1];
                start = color == 0 ? -1 : ep[0];
            }
        }
        return res;
    }

    // S2: TreeMap
    // time = O(nlogn), space = O(n)
    public List<List<Long>> splitPainting2(int[][] segments) {
        List<List<Long>> res = new ArrayList<>();
        // corner case
        if (segments == null || segments.length == 0 || segments[0] == null || segments[0].length == 0) return res;

        TreeMap<Integer, Long> map = new TreeMap<>();
        for (int[] segment : segments) {
            map.put(segment[0], map.getOrDefault(segment[0], 0L) + segment[2]);
            map.put(segment[1], map.getOrDefault(segment[1], 0L) - segment[2]);
        }

        long start = 0, color = 0;
        for (int key : map.keySet()) {
            if (color > 0) res.add(Arrays.asList(start, (long)key, color)); // only add to the res when color is > 0
            color += map.get(key);
            start = key;
        }
        return res;
    }

    // S3
    // time = O(nlogn), space = O(n)
    public List<List<Long>> splitPainting3(int[][] segments) {
        List<List<Long>> res = new ArrayList<>();
        TreeMap<Long, Long> pos2diff = new TreeMap<>();

        for (int[] seg : segments) {
            pos2diff.put((long)seg[0], pos2diff.getOrDefault((long)seg[0], 0L) + (long)seg[2]);
            pos2diff.put((long)seg[1], pos2diff.getOrDefault((long)seg[1], 0L) - (long)seg[2]);
        }

        long sum = 0;
        long start = -1, end = -1;
        for (long x : pos2diff.keySet()) {
            long pos = x, diff = pos2diff.get(x);
            if (start == -1) start = pos;
            else {
                end = pos;
                res.add(Arrays.asList(start, end, sum));
                start = end;
            }
            sum += diff;
            if (sum == 0) start = -1; // discard all 0 segments
        }
        return res;
    }
}
/**
 * each segment was painted with a "unique" color
 * 每一个起点和终点都会引起色彩的变化
 * sum == 0 => 色彩集合为0，不用记录
 * 暴力扫描线
 */

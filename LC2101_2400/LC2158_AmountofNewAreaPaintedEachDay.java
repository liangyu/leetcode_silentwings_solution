package LC2101_2400;
import java.util.*;
public class LC2158_AmountofNewAreaPaintedEachDay {
    /**
     * There is a long and thin painting that can be represented by a number line. You are given a 0-indexed 2D integer
     * array paint of length n, where paint[i] = [starti, endi]. This means that on the ith day you need to paint the
     * area between starti and endi.
     *
     * Painting the same area multiple times will create an uneven painting so you only want to paint each area of the
     * painting at most once.
     *
     * Return an integer array worklog of length n, where worklog[i] is the amount of new area that you painted on the
     * ith day.
     *
     * Input: paint = [[1,4],[4,7],[5,8]]
     * Output: [3,3,1]
     *
     * Constraints:
     *
     * 1 <= paint.length <= 10^5
     * paint[i].length == 2
     * 0 <= starti < endi <= 5 * 10^4
     * @param paint
     * @return
     */
    // S1: Sweep Line + TreeSet
    // time = O(nlogn), space = O(n)
    public int[] amountPainted(int[][] paint) {
        int n = paint.length, max = 0;
        for (int[] x : paint) max = Math.max(max, x[1]);
        List<int[]>[] diff = new List[max + 1]; // diff里存2个信息的pair，一个是idx，一个是标志起点还是终点！！！
        for (int i = 0; i < n; i++) { // O(n)
            int start = paint[i][0], end = paint[i][1];
            if (diff[start] == null) diff[start] = new ArrayList<>();
            if (diff[end] == null) diff[end] = new ArrayList<>();
            diff[start].add(new int[]{i, 1});
            diff[end].add(new int[]{i, -1});
        }

        TreeSet<Integer> set = new TreeSet<>();
        int[] res = new int[n];
        for (int i = 0; i <= max; i++) {  // O(max)
            if (diff[i] != null) {
                for (int[] x : diff[i]) {  // O(n)
                    if (x[1] == 1) set.add(x[0]); // 进入了interval
                    else set.remove(x[0]); // 出了interval
                }
            }
            // 如果当前有多个interval出现交叠，我们只要看最早的那个就是当前active的那个，只要通过set.first()即可获知！！！
            if (set.size() > 0) res[set.first()]++; // diff[i] == null的时候，状态不变，还在当前的interval，长度继续++即可。
        }
        return res;
    }
}

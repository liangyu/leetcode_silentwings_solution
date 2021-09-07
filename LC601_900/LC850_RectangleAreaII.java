package LC601_900;
import java.util.*;
public class LC850_RectangleAreaII {
    /**
     * We are given a list of (axis-aligned) rectangles. Each rectangle[i] = [xi1, yi1, xi2, yi2] , where (xi1, yi1)
     * are the coordinates of the bottom-left corner, and (xi2, yi2) are the coordinates of the top-right corner of the
     * ith rectangle.
     *
     * Find the total area covered by all rectangles in the plane. Since the answer may be too large, return it modulo
     * 10^9 + 7.
     *
     * Input: rectangles = [[0,0,2,2],[1,0,2,3],[1,0,3,1]]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= rectangles.length <= 200
     * rectanges[i].length = 4
     * 0 <= rectangles[i][j] <= 10^9
     * The total area covered by all rectangles will never exceed 263 - 1 and thus will fit in a 64-bit signed integer.
     * @param rectangles
     * @return
     */
    // S1：Coordinate Compression
    // time = O(n^3), space = O(n^2)
    public int rectangleArea(int[][] rectangles) {
        // corner case
        if (rectangles == null || rectangles.length == 0 || rectangles[0] == null || rectangles[0].length == 0) return 0;

        TreeSet<Integer> setX = new TreeSet<>();
        TreeSet<Integer> setY = new TreeSet<>();
        for (int[] r : rectangles) {
            setX.add(r[0]);
            setX.add(r[2]);
            setY.add(r[1]);
            setY.add(r[3]);
        }

        List<Integer> X = new ArrayList<>(setX);
        List<Integer> Y = new ArrayList<>(setY);

        int m = X.size(), n = Y.size();
        boolean[][]  points = new boolean[m][n];

        for (int[] r : rectangles) { // O(n^3)
            int x0 = lowerBound(X, r[0]);
            int x1 = lowerBound(X, r[2]);
            int y0 = lowerBound(Y, r[1]);
            int y1 = lowerBound(Y, r[3]);

            for (int i = x0; i < x1; i++) {
                for (int j = y0; j < y1; j++) {
                    points[i][j] = true;
                }
            }
        }

        long res = 0;
        long M = (long)(1e9 + 7);
        for (int i = 0; i < m - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (!points[i][j]) continue;
                res += (long)(X.get(i + 1) - X.get(i)) * (long)(Y.get(j + 1) - Y.get(j));
                res %= M;
            }
        }
        return (int) res;
    }

    private int lowerBound(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) left = mid + 1;
            else right = mid;
        }
        return list.get(left) >= target ? left : left + 1;
    }

    // S2: Sweep Line
    // time = O(n^2), space = O(n)
    public int rectangleArea2(int[][] rectangles) {
        // corner case
        if (rectangles == null || rectangles.length == 0 || rectangles[0] == null || rectangles[0].length == 0) return 0;

        TreeSet<Integer> setX = new TreeSet<>();
        TreeSet<Integer> setY = new TreeSet<>();
        for (int[] r : rectangles) {
            setX.add(r[0]);
            setX.add(r[2]);
            setY.add(r[1]);
            setY.add(r[3]);
        }

        List<Integer> row = new ArrayList<>(setX);
        List<Integer> col = new ArrayList<>(setY);

        HashMap<Integer, Integer> x2idx = new HashMap<>();
        HashMap<Integer, Integer> y2idx = new HashMap<>();
        for (int i = 0; i < row.size(); i++) x2idx.put(row.get(i), i);
        for (int i = 0; i < col.size(); i++) y2idx.put(col.get(i), i);

        int m = row.size(), n = col.size();
        long[][] sum = new long[m][n];
        long[][] diff = new long[m][n];

        for (int[] r : rectangles) {
            int x0 = r[0], y0 = r[1], x1 = r[2], y1 = r[3];
            diff[x2idx.get(x0)][y2idx.get(y0)]++;
            diff[x2idx.get(x0)][y2idx.get(y1)]--;
            diff[x2idx.get(x1)][y2idx.get(y0)]--;
            diff[x2idx.get(x1)][y2idx.get(y1)]++;
        }

        long res = 0;
        long M = (long)(1e9 + 7);
        sum[0][0] = diff[0][0];
        if (diff[0][0] > 0) {
            res += (long)(row.get(1) - row.get(0)) * (long)(col.get(1) - col.get(0)) % M;
        }

        for (int i = 0; i < m - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (i == 0 && j == 0) continue;
                long val1 = (i >= 1 ? sum[i - 1][j] : 0);
                long val2 = (j >= 1 ? sum[i][j - 1] : 0);
                long val3 = ((i >= 1 && j >= 1) ? sum[i - 1][j - 1] : 0);
                sum[i][j] = val1 + val2 - val3 + diff[i][j];
                if (sum[i][j] > 0) {
                    res = (res + (long)(row.get(i + 1) - row.get(i)) * (long)(col.get(j + 1) - col.get(j))) % M;
                }
            }
        }
        return (int) res;
    }
}

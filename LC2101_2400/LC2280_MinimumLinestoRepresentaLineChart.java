package LC2101_2400;
import java.util.*;
public class LC2280_MinimumLinestoRepresentaLineChart {
    /**
     * You are given a 2D integer array stockPrices where stockPrices[i] = [dayi, pricei] indicates the price of the
     * stock on day dayi is pricei. A line chart is created from the array by plotting the points on an XY plane with
     * the X-axis representing the day and the Y-axis representing the price and connecting adjacent points. One such
     * example is shown below:
     *
     * Return the minimum number of lines needed to represent the line chart.
     *
     * Input: stockPrices = [[1,7],[2,6],[3,5],[4,4],[5,4],[6,3],[7,2],[8,1]]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= stockPrices.length <= 10^5
     * stockPrices[i].length == 2
     * 1 <= dayi, pricei <= 10^9
     * All dayi are distinct.
     * @param stockPrices
     * @return
     */
    // S1
    // time = O(nlogn), space = O(1)
    public int minimumLines(int[][] stockPrices) {
        Arrays.sort(stockPrices, (o1, o2) -> o1[0] - o2[0]);

        int n = stockPrices.length;
        int count = Math.min(n - 1, 1);

        for (int i = 2; i < n; i++) {
            long dy1 = stockPrices[i][1] - stockPrices[i - 1][1];
            long dx1 = stockPrices[i][0] - stockPrices[i - 1][0];

            long dy2 = stockPrices[i - 1][1] - stockPrices[i - 2][1];
            long dx2 = stockPrices[i - 1][0] - stockPrices[i - 2][0];

            // dy1 / dx1 == dy2 / dx2
            if (dy1 * dx2 != dy2 * dx1) count++;
        }
        return count;
    }

    // S2: gcd
    // time = O(nlogn), space = O(1)
    public int minimumLines2(int[][] stockPrices) {
        Arrays.sort(stockPrices, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int n = stockPrices.length, count = 0;
        String last = "", cur = "";
        for (int i = 1; i < n; i++) {
            long x1 = (long) stockPrices[i - 1][0], y1 = (long) stockPrices[i - 1][1];
            long x2 = (long) stockPrices[i][0], y2 = (long) stockPrices[i][1];
            if (x1 == x2 && y1 == y2) continue;
            if (x1 == x2) cur = "0";
            else if (y1 == y2) cur = "INF";
            else {
                long dx = x2 - x1, dy = y2 - y1;
                long d = gcd(dx, dy);
                dx /= d;
                dy /= d;
                cur = dx + "/" + dy;
            }
            if (cur.equals(last)) continue;
            else {
                count++;
                last = cur;
            }
        }
        return count;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

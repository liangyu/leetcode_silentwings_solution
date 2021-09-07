package LC1801_2100;
import java.util.*;
public class LC1956_MinimumTimeForKVirusVariantstoSpread {
    /**
     * There are n unique virus variants in an infinite 2D grid. You are given a 2D array points, where
     * points[i] = [xi, yi] represents a virus originating at (xi, yi) on day 0. Note that it is possible for multiple
     * virus variants to originate at the same point.
     *
     * Every day, each cell infected with a virus variant will spread the virus to all neighboring points in the four
     * cardinal directions (i.e. up, down, left, and right). If a cell has multiple variants, all the variants will
     * spread without interfering with each other.
     *
     * Given an integer k, return the minimum integer number of days for any point to contain at least k of the unique
     * virus variants.
     *
     * Input: points = [[1,1],[6,1]], k = 2
     * Output: 3
     *
     * @param points
     * @param k
     * @return
     */
    // time = O(n^2 * nlogn), space = O(n)
    public int minDayskVariants(int[][] points, int k) {
        int n = points.length;
        Arrays.sort(points, (o1, o2) -> (o1[0] + o1[1]) - (o2[0] + o2[1]));

        long res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                List<Pair> diffs = new ArrayList<>();
                for (int t = i; t <= j; t++) {
                    diffs.add(new Pair(points[t][0] - points[t][1], t));
                }
                Collections.sort(diffs, (o1, o2) -> (int)o1.diff - (int)o2.diff);

                List<Integer> pivots = new ArrayList<>();
                for (int t = 0; t < diffs.size(); t++) {
                    if (diffs.get(t).val == i || diffs.get(t).val == j) {
                        pivots.add(t);
                    }
                }
                int pLeft = pivots.get(0), pRight = pivots.get(1);
                int r = 0;
                long minDiff = Integer.MAX_VALUE;
                for (int l = 0; l < diffs.size(); l++) {
                    if (l > pLeft) break;
                    while (r < diffs.size() && (r < pRight || r - l + 1 < k)) r++;
                    if (r < diffs.size()) {
                        minDiff = Math.min(minDiff, diffs.get(r).diff - diffs.get(l).diff);
                    }
                }
                long candidate1 = points[j][0] + points[j][1] - (points[i][0] + points[i][1]);
                long candidate2 = minDiff;
                res = Math.min(res, Math.max(candidate1, candidate2));
            }
        }
        return (int)(res + 1) / 2;
    }

    private class Pair {
        private long diff;
        private int val;
        public Pair(long diff, int val) {
            this.diff = diff;
            this.val = val;
        }
    }
}
/**
 * 引理1： 给定K个点，存在某个位置M，能够最小化M到所有点的曼哈顿距离的最大值D。
 * 结论：这个M一定位于这K个点中距离最远的两个点（记为AB）的连接路径的中点，即AM + MB = AB = L，并且D = (L+1)/2。
 * 这个结论可以用反证说明：如果M不在所述的位置，那么要么AM会变得比(L+1)/2更大，要么BM会变得比(L+1)/2更大，这样都会使得D更大。
 * 引理2： 给定K个点{xi,yi}，曼哈顿距离最远的两个点AB之间的距离L，可以计算如下：令 a = x + y, b = x - y.
 * 则 L = max(max{|ai-aj|}, max{|bi-bj|}) for all i,j
 * 本题是希望找到K个点集，使得它们的最大曼哈顿距离L满足 (L+1)<=K
 */

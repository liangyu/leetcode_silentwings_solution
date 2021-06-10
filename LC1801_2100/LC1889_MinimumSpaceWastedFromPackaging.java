package LC1801_2100;
import java.util.*;
public class LC1889_MinimumSpaceWastedFromPackaging {
    /**
     * You have n packages that you are trying to place in boxes, one package in each box. There are m suppliers that
     * each produce boxes of different sizes (with infinite supply). A package can be placed in a box if the size of
     * the package is less than or equal to the size of the box.
     *
     * The package sizes are given as an integer array packages, where packages[i] is the size of the ith package. The
     * suppliers are given as a 2D integer array boxes, where boxes[j] is an array of box sizes that the jth supplier
     * produces.
     *
     * You want to choose a single supplier and use boxes from them such that the total wasted space is minimized. For
     * each package in a box, we define the space wasted to be size of the box - size of the package. The total wasted
     * space is the sum of the space wasted in all the boxes.
     *
     * For example, if you have to fit packages with sizes [2,3,5] and the supplier offers boxes of sizes [4,8], you
     * can fit the packages of size-2 and size-3 into two boxes of size-4 and the package with size-5 into a box of
     * size-8. This would result in a waste of (4-2) + (4-3) + (8-5) = 6.
     * Return the minimum total wasted space by choosing the box supplier optimally, or -1 if it is impossible to fit
     * all the packages inside boxes. Since the answer may be large, return it modulo 10^9 + 7.
     *
     * Input: packages = [2,3,5], boxes = [[4,8],[2,8]]
     * Output: 6
     *
     * Constraints:
     *
     * n == packages.length
     * m == boxes.length
     * 1 <= n <= 10^5
     * 1 <= m <= 10^5
     * 1 <= packages[i] <= 10^5
     * 1 <= boxes[j].length <= 10^5
     * 1 <= boxes[j][k] <= 10^5
     * sum(boxes[j].length) <= 10^5
     * The elements in boxes[j] are distinct.
     * @param packages
     * @param boxes
     * @return
     */
    // time = O(nlogn + mklog(nk)), space = O(n)
    public int minWastedSpace(int[] packages, int[][] boxes) {
        // corner case
        if (packages == null || packages.length == 0 || boxes == null || boxes.length == 0) return 0;

        Arrays.sort(packages); // O(nlogn)
        int n = packages.length;

        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + packages[i - 1];

        long res = Long.MAX_VALUE, M = (long)(1e9 + 7);
        for (int[] box : boxes) { // O(m)
            Arrays.sort(box); // O(klogk)
            int m = box.length;
            if (packages[n - 1] > box[m - 1]) continue;
            long cur = 0;
            int prev = 0;
            for (int size : box) {  // O(k)
                if (size < packages[0]) continue;
                int next = upBound(packages, size); // O(logn)
                long sum = presum[next + 1] - presum[prev];
                long l = next - prev + 1;
                long add = l * size - sum;
                prev = next + 1;
                cur += add;
            }
            res = Math.min(res, cur);
        }
        return res == Long.MAX_VALUE ? -1 : (int)(res % M);
    }

    private int upBound(int[] packages, int target) {
        int left = 0, right = packages.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (packages[mid] > target) right = mid - 1;
            else left = mid + 1;
        }
        return right;
    }
}
/**
 * 双指针，固定一个y，看有多少个x能被收录其中
 * [x x x] [] x x x x
 *      j
 * y y y y
 * i
 * 固定一个box的指针，然后移动package的指针
 * => O(n + k) * m = O(nm + mk) ~ 10^10 -> TLE
 *
 * B.S. 找最后一个小于等于box i的 j => O(logn * k) * m = logn * km = TlogT (T ~ 10^5)
 */

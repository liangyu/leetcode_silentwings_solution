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
    // time = O(nlogn + m * (klogk + mlogn)), space = O(n)
    public int minWastedSpace(int[] packages, int[][] boxes) {
        int n = packages.length;
        Arrays.sort(packages); // O(nlogn)

        long M = (long)(1e9 + 7);
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + packages[i - 1]; // O(n)

        long res = Long.MAX_VALUE;
        for (int[] box : boxes) { // O(m)
            int m = box.length;
            Arrays.sort(box); // O(klogk)
            long waste = 0;
            int prev = -1;
            for (int i = 0; i < m; i++) { // O(m)
                int idx = upperBound(packages, box[i]); // find 1st package > box[i] // O(logn)
                if (idx == 0) continue;
                int j = idx - 1;
                waste += (long)(j - prev) * box[i] - (presum[j + 1] - presum[prev + 1]);
                prev = j;
                if (prev == n - 1) break;
            }
            if (prev != n - 1) continue;
            res = Math.min(res, waste);
        }
        return res == Long.MAX_VALUE ? -1 : (int)(res % M);
    }

    private int upperBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return nums[left] > target ? left : left + 1;
    }
}
/**
 * 双指针，固定一个y，看有多少个x能被收录其中
 * [x x x] [] x x x x
 *      j
 * y y y y
 * i
 * 固定一个box的指针，然后移动package的指针
 * => O(n + k) * m = O(nm + mk) ~ 10^10 + 10^5 -> TLE
 *
 * B.S. 找最后一个小于等于box i的 j => O(logn * k) * m = logn * km = TlogT (T ~ 10^5)
 */

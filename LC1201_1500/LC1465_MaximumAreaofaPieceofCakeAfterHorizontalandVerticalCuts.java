package LC1201_1500;
import java.util.*;
public class LC1465_MaximumAreaofaPieceofCakeAfterHorizontalandVerticalCuts {
    /**
     * Given a rectangular cake with height h and width w, and two arrays of integers horizontalCuts and verticalCuts
     * where horizontalCuts[i] is the distance from the top of the rectangular cake to the ith horizontal cut and
     * similarly, verticalCuts[j] is the distance from the left of the rectangular cake to the jth vertical cut.
     *
     * Return the maximum area of a piece of cake after you cut at each horizontal and vertical position provided in
     * the arrays horizontalCuts and verticalCuts. Since the answer can be a huge number, return this modulo 10^9 + 7.
     *
     * Input: h = 5, w = 4, horizontalCuts = [1,2,4], verticalCuts = [1,3]
     * Output: 4
     *
     * Constraints:
     *
     * 2 <= h, w <= 10^9
     * 1 <= horizontalCuts.length < min(h, 10^5)
     * 1 <= verticalCuts.length < min(w, 10^5)
     * 1 <= horizontalCuts[i] < h
     * 1 <= verticalCuts[i] < w
     * It is guaranteed that all elements in horizontalCuts are distinct.
     * It is guaranteed that all elements in verticalCuts are distinct.
     * @param h
     * @param w
     * @param horizontalCuts
     * @param verticalCuts
     * @return
     */
    // time = O(mlogm + nlogn), space = O(1)
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        long M = (long)(1e9 + 7);
        return (int)(findMax(horizontalCuts, h) * findMax(verticalCuts, w) % M);
    }

    private long findMax(int[] nums, int t) {
        Arrays.sort(nums);
        int s = 0, res = 0;
        for (int x : nums) {
            res = Math.max(res, x - s);
            s = x;
        }
        return Math.max(res, t - s);
    }
}
/**
 * 两个方向相互独立，各找一个最大值相乘即可。
 */
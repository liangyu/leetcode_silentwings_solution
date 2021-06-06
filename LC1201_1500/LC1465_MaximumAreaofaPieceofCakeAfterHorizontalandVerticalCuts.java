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
        // corner case
        if (horizontalCuts == null || verticalCuts == null) return 0;

        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);

        int m = horizontalCuts.length, n = verticalCuts.length;
        long mh = 0, mw = 0;
        int M = (int)1e9+7;
        for (int i = 0; i < m; i++) {
            mh = Math.max(mh, horizontalCuts[i] - (i == 0 ? 0 : horizontalCuts[i - 1]));
        }
        mh = Math.max(mh, h - horizontalCuts[m - 1]);

        for (int j = 0; j < n; j++) {
            mw = Math.max(mw, verticalCuts[j] - (j == 0 ? 0 : verticalCuts[j - 1]));
        }
        mw = Math.max(mw, w - verticalCuts[n - 1]);
        return (int)(mh * mw % M);
    }
}

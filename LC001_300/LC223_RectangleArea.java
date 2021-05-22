package LC001_300;
import java.util.*;
public class LC223_RectangleArea {
    /**
     * Given the coordinates of two rectilinear rectangles in a 2D plane, return the total area covered by the two
     * rectangles.
     *
     * The first rectangle is defined by its bottom-left corner (ax1, ay1) and its top-right corner (ax2, ay2).
     *
     * The second rectangle is defined by its bottom-left corner (bx1, by1) and its top-right corner (bx2, by2).
     *
     * Input: ax1 = -3, ay1 = 0, ax2 = 3, ay2 = 4, bx1 = 0, by1 = -1, bx2 = 9, by2 = 2
     * Output: 45
     *
     * Constraints:
     *
     * -10^4 <= ax1, ay1, ax2, ay2, bx1, by1, bx2, by2 <= 10^4
     * @param ax1
     * @param ay1
     * @param ax2
     * @param ay2
     * @param bx1
     * @param by1
     * @param bx2
     * @param by2
     * @return
     */
    // time = O(1), space = O(1)
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int areaA = (ax2 - ax1) * (ay2 - ay1);
        int areaB = (bx2 - bx1) * (by2 - by1);

        int left = Math.max(ax1, bx1);
        int right = Math.min(ax2, bx2);
        int top = Math.min(ay2, by2);
        int bottom = Math.max(ay1, by1);

        int overlap = 0;
        if (left < right && bottom < top) {
            overlap = (right - left) * (top - bottom);
        }
        return areaA + areaB - overlap;
    }
}

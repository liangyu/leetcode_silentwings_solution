package LC1501_1800;
import java.util.*;
public class LC1762_BuildingsWithanOceanView {
    /**
     * There are n buildings in a line. You are given an integer array heights of size n that represents the heights
     * of the buildings in the line.
     *
     * The ocean is to the right of the buildings. A building has an ocean view if the building can see the ocean
     * without obstructions. Formally, a building has an ocean view if all the buildings to its right have a smaller
     * height.
     *
     * Return a list of indices (0-indexed) of buildings that have an ocean view, sorted in increasing order.
     *
     * Input: heights = [4,2,3,1]
     * Output: [0,2,3]
     *
     * Constraints:
     *
     * 1 <= heights.length <= 10^5
     * 1 <= heights[i] <= 10^9
     * @param heights
     * @return
     */
    // time = O(n), space = O(n)
    public int[] findBuildings(int[] heights) {
        // corner case
        if (heights == null || heights.length == 0) return new int[0];

        int n = heights.length, max = 0;
        List<Integer> list = new ArrayList<>();

        for (int i = n - 1; i >= 0; i--) {
            if (heights[i] > max) {
                max = heights[i];
                list.add(i);
            }
        }
        int[] res = new int[list.size()];
        int idx = 0;
        for (int i = list.size() - 1; i >= 0; i--) res[idx++] = list.get(i);
        return res;
    }
}

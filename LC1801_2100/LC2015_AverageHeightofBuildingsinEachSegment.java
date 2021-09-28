package LC1801_2100;
import java.util.*;
public class LC2015_AverageHeightofBuildingsinEachSegment {
    /**
     * A perfectly straight street is represented by a number line. The street has building(s) on it and is represented
     * by a 2D integer array buildings, where buildings[i] = [starti, endi, heighti]. This means that there is a
     * building with heighti in the half-closed segment [starti, endi).
     *
     * You want to describe the heights of the buildings on the street with the minimum number of non-overlapping
     * segments. The street can be represented by the 2D integer array street where street[j] = [leftj, rightj, averagej]
     * describes a half-closed segment [leftj, rightj) of the road where the average heights of the buildings in the
     * segment is averagej.
     *
     * For example, if buildings = [[1,5,2],[3,10,4]], the street could be represented by street = [[1,3,2],[3,5,3],
     * [5,10,4]] because:
     * From 1 to 3, there is only the first building with an average height of 2 / 1 = 2.
     * From 3 to 5, both the first and the second building are there with an average height of (2+4) / 2 = 3.
     * From 5 to 10, there is only the second building with an average height of 4 / 1 = 4.
     * Given buildings, return the 2D integer array street as described above (excluding any areas of the street where
     * there are no buldings). You may return the array in any order.
     *
     * The average of n elements is the sum of the n elements divided (integer division) by n.
     *
     * A half-closed segment [a, b) is the section of the number line between points a and b including point a and not
     * including point b.
     *
     * Input: buildings = [[1,5,2],[3,10,4]]
     * Output: [[1,3,2],[3,5,3],[5,10,4]]
     *
     * Constraints:
     *
     * 1 <= buildings.length <= 10^5
     * buildings[i].length == 3
     * 0 <= starti < endi <= 10^8
     * 1 <= heighti <= 105
     * @param buildings
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[][] averageHeightOfBuildings(int[][] buildings) {
        List<int[]> diff = new ArrayList<>();
        for (int[] b : buildings) {
            diff.add(new int[]{b[0], 1, b[2]});
            diff.add(new int[]{b[1], -1, -b[2]});
        }

        Collections.sort(diff, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        List<int[]> res = new ArrayList<>();
        int start = -1, height = 0, count = 0;
        for (int[] x : diff) {
            if (start == -1) {
                start = x[0];
                height += x[2];
                count++;
            } else {
                if (start != x[0]) {
                    if (res.size() > 0 && res.get(res.size() - 1)[2] == height / count && res.get(res.size() - 1)[1] == start) {
                        start = res.get(res.size() - 1)[0];
                        res.remove(res.size() - 1);
                    }
                    res.add(new int[]{start, x[0], height / count});
                    start = x[0];
                }
                height += x[2];
                if (x[1] == 1) count++;
                else count--;
                if (height == 0) start = -1;
            }
        }
        int[][] ans = new int[res.size()][3];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }
}

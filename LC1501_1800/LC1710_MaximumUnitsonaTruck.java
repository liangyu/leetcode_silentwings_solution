package LC1501_1800;
import java.util.*;
public class LC1710_MaximumUnitsonaTruck {
    /**
     * You are assigned to put some amount of boxes onto one truck. You are given a 2D array boxTypes, where
     * boxTypes[i] = [numberOfBoxesi, numberOfUnitsPerBoxi]:
     *
     * numberOfBoxesi is the number of boxes of type i.
     * numberOfUnitsPerBoxi is the number of units in each box of the type i.
     * You are also given an integer truckSize, which is the maximum number of boxes that can be put on the truck.
     * You can choose any boxes to put on the truck as long as the number of boxes does not exceed truckSize.
     *
     * Return the maximum total number of units that can be put on the truck.
     *
     * Input: boxTypes = [[1,3],[2,2],[3,1]], truckSize = 4
     * Output: 8
     *
     * Constraints:
     *
     * 1 <= boxTypes.length <= 1000
     * 1 <= numberOfBoxesi, numberOfUnitsPerBoxi <= 1000
     * 1 <= truckSize <= 10^6
     * @param boxTypes
     * @param truckSize
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (o1, o2) -> o2[1] - o1[1]);
        int res = 0;
        for (int i = 0; i < boxTypes.length; i++) {
            res += boxTypes[i][1] * Math.min(boxTypes[i][0], truckSize);
            truckSize -= Math.min(boxTypes[i][0], truckSize);
            if (truckSize == 0) break;
        }
        return res;
    }
}

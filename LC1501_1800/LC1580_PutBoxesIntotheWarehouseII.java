package LC1501_1800;
import java.util.*;
public class LC1580_PutBoxesIntotheWarehouseII {
    /**
     * You are given two arrays of positive integers, boxes and warehouse, representing the heights of some boxes of
     * unit width and the heights of n rooms in a warehouse respectively. The warehouse's rooms are labeled from 0 to
     * n - 1 from left to right where warehouse[i] (0-indexed) is the height of the ith room.
     *
     * Boxes are put into the warehouse by the following rules:
     *
     * Boxes cannot be stacked.
     * You can rearrange the insertion order of the boxes.
     * Boxes can be pushed into the warehouse from either side (left or right)
     * If the height of some room in the warehouse is less than the height of a box, then that box and all other boxes
     * behind it will be stopped before that room.
     * Return the maximum number of boxes you can put into the warehouse.
     *
     * Input: boxes = [1,2,2,3,4], warehouse = [3,4,1,2]
     * Output: 4
     *
     * Constraints:
     *
     * n == warehouse.length
     * 1 <= boxes.length, warehouse.length <= 10^5
     * 1 <= boxes[i], warehouse[i] <= 10^9
     * @param boxes
     * @param warehouse
     * @return
     */
    // time = O(nlogn + m), space = O(1)
    public int maxBoxesInWarehouse(int[] boxes, int[] warehouse) {
        int n = warehouse.length;
        Arrays.sort(boxes);

        int count = 0;
        int i = 0, j = n - 1;
        for (int k = boxes.length - 1; k >= 0; k--) {
            int box = boxes[k];
            if (i > j) continue;
            if (box > Math.max(warehouse[i], warehouse[j])) continue;
            // move left: 右边太小放不进去，只能放左边；左右两边都比箱子大，但左边比右边小
            // if (warehouse[j] < box || (warehouse[i] >= box && warehouse[i] < warehouse[j])) i++;
            if (warehouse[j] < box) i++; // 由于排过序了，后面的箱子只会比它更小，因此无论选i还是j，后面的箱子都能放！
            else j--;
            count++;
        }
        return count;
    }
}

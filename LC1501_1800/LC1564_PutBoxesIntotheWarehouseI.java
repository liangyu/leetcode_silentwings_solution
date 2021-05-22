package LC1501_1800;
import java.util.*;
public class LC1564_PutBoxesIntotheWarehouseI {
    /**
     * You are given two arrays of positive integers, boxes and warehouse, representing the heights of some boxes of
     * unit width and the heights of n rooms in a warehouse respectively. The warehouse's rooms are labelled from 0 to
     * n - 1 from left to right where warehouse[i] (0-indexed) is the height of the ith room.
     *
     * Boxes are put into the warehouse by the following rules:
     *
     * Boxes cannot be stacked.
     * You can rearrange the insertion order of the boxes.
     * Boxes can only be pushed into the warehouse from left to right only.
     * If the height of some room in the warehouse is less than the height of a box, then that box and all other boxes
     * behind it will be stopped before that room.
     * Return the maximum number of boxes you can put into the warehouse.
     *
     * Input: boxes = [4,3,4,1], warehouse = [5,3,3,4,1]
     * Output: 3
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
    // S1: 最优解！！！
    // time = O(nlogn + m), space = O(1)
    public int maxBoxesInWarehouse(int[] boxes, int[] warehouse) {
        int i = boxes.length - 1, count = 0;

        Arrays.sort(boxes);

        for (int w : warehouse) {
            while (i >= 0 && boxes[i] > w) i--;
            if (i == -1) return count;
            count++;
            i--;
        }
        return count;
    }

    // S2
    // time = O(nlogn + m), space = O(1)
    public int maxBoxesInWarehouse2(int[] boxes, int[] warehouse) {
        for (int i = 1; i < warehouse.length; i++) { // O(m)
            warehouse[i] = Math.min(warehouse[i - 1], warehouse[i]);
        }
        Arrays.sort(boxes); // O(nlogn)

        int count = 0;
        for (int i = warehouse.length - 1; i >= 0; i--) { // O(m)
            if (count < boxes.length && boxes[count] <= warehouse[i]) count++;
        }
        return count;
    }
}

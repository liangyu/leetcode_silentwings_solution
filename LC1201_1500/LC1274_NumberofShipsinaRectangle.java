package LC1201_1500;

public class LC1274_NumberofShipsinaRectangle {
    /**
     * (This problem is an interactive problem.)
     *
     * Each ship is located at an integer point on the sea represented by a cartesian plane, and each integer point may
     * contain at most 1 ship.
     *
     * You have a function Sea.hasShips(topRight, bottomLeft) which takes two points as arguments and returns true If
     * there is at least one ship in the rectangle represented by the two points, including on the boundary.
     *
     * Given two points: the top right and bottom left corners of a rectangle, return the number of ships present in
     * that rectangle. It is guaranteed that there are at most 10 ships in that rectangle.
     *
     * Submissions making more than 400 calls to hasShips will be judged Wrong Answer. Also, any solutions that attempt
     * to circumvent the judge will result in disqualification.
     *
     * Input:
     * ships = [[1,1],[2,2],[3,3],[5,5]], topRight = [4,4], bottomLeft = [0,0]
     * Output: 3
     *
     * Constraints:
     *
     * On the input ships is only given to initialize the map internally. You must solve this problem "blindfolded".
     * In other words, you must find the answer using the given hasShips API, without knowing the ships position.
     * 0 <= bottomLeft[0] <= topRight[0] <= 1000
     * 0 <= bottomLeft[1] <= topRight[1] <= 1000
     * topRight != bottomLeft
     * @param sea
     * @param topRight
     * @param bottomLeft
     * @return
     */
    // time = O(n), space = O(n)
    public int countShips(Sea sea, int[] topRight, int[] bottomLeft) {
        int x1 = bottomLeft[0], x2 = topRight[0];
        int y1 = bottomLeft[1], y2 = topRight[1];
        int x3 = (x1 + x2) / 2, y3 = (y1 + y2) / 2;

        if (x1 == x2 && y1 == y2) return sea.hasShips(topRight, bottomLeft) ? 1 : 0;

        int a = 0, b = 0, c = 0, d = 0;
        if (x3 >= x1 && y3 >= y1 && sea.hasShips(new int[]{x3, y3}, new int[]{x1, y1})) {
            a = countShips(sea, new int[]{x3, y3}, new int[]{x1, y1});
        }
        if (x2 >= x3 + 1 && y3 >= y1 && sea.hasShips(new int[]{x2, y3}, new int[]{x3 + 1, y1})) {
            b = countShips(sea, new int[]{x2, y3}, new int[]{x3 + 1, y1});
        }
        if (x3 >= x1 && y2 >= y3 + 1 && sea.hasShips(new int[]{x3, y2}, new int[]{x1, y3 + 1})) {
            c = countShips(sea, new int[]{x3, y2}, new int[]{x1, y3 + 1});
        }
        if (x2 >= x3 + 1 && y2 >= y3 + 1 && sea.hasShips(new int[]{x2, y2}, new int[]{x3 + 1, y3 + 1})) {
            d = countShips(sea, new int[]{x2, y2}, new int[]{x3, y3 + 1});
        }
        return a + b + c + d;
    }
/**
 * // This is Sea's API interface.
 * // You should not implement it, or speculate about its implementation
 * class Sea {
 *     public boolean hasShips(int[] topRight, int[] bottomLeft);
 * }
 */
}
/**
 * 二分法 -> 平面4分
 * 很明显，我们将二维平面划均分为四个区域，然后给自递归处理，计算每个区域的countShips，然后累积起来。
 * 需要注意的几点：
 * 1. 划分区域的时候需要保证四块的边界不能重复。
 * 2. 提前用hasShip来预判是否有船在区域内，如果没有，可以直接返回零。
 * 3. 当左上角与右下角重合时，返回的结果与hasShip的结果一致。
 * 4. 在调用hasShip之前，需要保证右上角和左下角的相对位置关系是正确的：
 * 比如当右上角是[0,1]且左下角是[0,0]时，其实并不能分出四个区域，其实两个区域是“假的“，并不能调用 hasShip.
 * 注意：分的时候四个块不能有重合
 */

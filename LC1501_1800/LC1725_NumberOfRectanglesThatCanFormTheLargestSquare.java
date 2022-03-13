package LC1501_1800;

public class LC1725_NumberOfRectanglesThatCanFormTheLargestSquare {
    /**
     * You are given an array rectangles where rectangles[i] = [li, wi] represents the ith rectangle of length li and
     * width wi.
     *
     * You can cut the ith rectangle to form a square with a side length of k if both k <= li and k <= wi. For example,
     * if you have a rectangle [4,6], you can cut it to get a square with a side length of at most 4.
     *
     * Let maxLen be the side length of the largest square you can obtain from any of the given rectangles.
     *
     * Return the number of rectangles that can make a square with a side length of maxLen.
     *
     * Input: rectangles = [[5,8],[3,9],[5,12],[16,5]]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= rectangles.length <= 1000
     * rectangles[i].length == 2
     * 1 <= li, wi <= 10^9
     * li != wi
     * @param rectangles
     * @return
     */
    // time = O(n), space = O(1)
    public int countGoodRectangles(int[][] rectangles) {
        int n = rectangles.length, res = 0, count = 0;

        for (int i = 0; i < n; i++) {
            int side = Math.min(rectangles[i][0], rectangles[i][1]);
            if (side > res) {
                res = side;
                count = 1;
            } else if (side == res) count++;

        }
        return count;
    }
}

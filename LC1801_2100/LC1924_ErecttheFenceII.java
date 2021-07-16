package LC1801_2100;
import java.util.*;
public class LC1924_ErecttheFenceII {
    /**
     * You are given a 2D integer array trees where trees[i] = [xi, yi] represents the location of the ith tree in the
     * garden.
     *
     * You are asked to fence the entire garden using the minimum length of rope possible. The garden is well-fenced
     * only if all the trees are enclosed and the rope used forms a perfect circle. A tree is considered enclosed if it
     * is inside or on the border of the circle.
     *
     * More formally, you must form a circle using the rope with a center (x, y) and radius r where all trees lie
     * inside or on the circle and r is minimum.
     *
     * Return the center and radius of the circle as a length 3 array [x, y, r]. Answers within 10-5 of the actual
     * answer will be accepted.
     *
     * Input: trees = [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
     * Output: [2.00000,2.00000,2.00000]
     *
     * Constraints:
     *
     * 1 <= trees.length <= 3000
     * trees[i].length == 2
     * 0 <= xi, yi <= 3000
     * @param trees
     * @return
     */
    // time = O(n), space = O(n)
    public double[] outerTrees(int[][] trees) {
        // corner case
        if (trees == null || trees.length == 0) return new double[0];

        shuffle(trees);
        return helper(trees, 0, new LinkedList<>());
    }

    private double[] helper(int[][] trees, int k, LinkedList<int[]> list) {
        if (k == trees.length || list.size() == 3) return trivial(list);

        double[] circle = helper(trees, k + 1, list);

        if (isWithin(trees[k], circle)) return circle;
        list.addLast(trees[k]);
        circle = helper(trees, k + 1, list);
        list.pollLast();
        return circle;
    }

    private double[] trivial(LinkedList<int[]> list) {
        if (list.isEmpty()) return null;
        if (list.size() == 1) return new double[]{list.get(0)[0], list.get(0)[1], 0};
        if (list.size() == 2) return twoPointCircle(list.get(0), list.get(1));
        else {
            double[] circle = twoPointCircle(list.get(0), list.get(1));
            if (isWithin(list.get(2), circle)) return circle;
            circle = twoPointCircle(list.get(0), list.get(2));
            if (isWithin(list.get(1), circle)) return circle;
            circle = twoPointCircle(list.get(1), list.get(2));
            if (isWithin(list.get(0), circle)) return circle;
            return threePointCircle(list.get(0), list.get(1), list.get(2));
        }
    }

    private double[] twoPointCircle(int[] a, int[] b) {
        double x1 = a[0], y1 = a[1];
        double x2 = b[0], y2 = b[1];
        double xm = (x1 + x2) / 2, ym = (y1 + y2) / 2;
        return new double[]{xm, ym, Math.sqrt((x1 - xm) * (x1 - xm) + (y1 - ym) * (y1 - ym))};
    }

    private double[] threePointCircle(int[] a, int[] b, int[] c) {
        double x1 = a[0], y1 = a[1];
        double x2 = b[0], y2 = b[1];
        double x3 = c[0], y3 = c[1];

        double[] center = getCenter(x2 - x1, y2 - y1, x3 - x1, y3 - y1);
        double r = Math.sqrt(center[0] * center[0] + center[1] * center[1]);
        double xm = center[0] + x1;
        double ym = center[1] + y1;
        return new double[]{xm, ym, r};
    }

    private double[] getCenter(double x1, double y1, double x2, double y2) {
        double x = ((x1 * x1 + y1 * y1) * y2 - (x2 * x2 + y2 * y2) * y1) / (2.0 * (x1 * y2 - x2 * y1));
        double y = ((x1 * x1 + y1 * y1) * x2 - (x2 * x2 + y2 * y2) * x1) / (2.0 * (y1 * x2 - y2 * x1));
        return new double[]{x, y};
    }

    private boolean isWithin(int[] point, double[] circle) {
        if (circle == null) {
            return false;
        }
        double d2 = (point[0] - circle[0]) * (point[0] - circle[0]) + (point[1] - circle[1]) * (point[1] - circle[1]);
        return d2 < circle[2] * circle[2] + 0.0000001;
    }

    private void shuffle(int[][] arr) {
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            int j = random.nextInt(i + 1);
            if (j != i) {
                int[] temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
}

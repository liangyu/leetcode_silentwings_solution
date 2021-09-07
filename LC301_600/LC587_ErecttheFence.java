package LC301_600;
import java.util.*;
public class LC587_ErecttheFence {
    /**
     * You are given an array trees where trees[i] = [xi, yi] represents the location of a tree in the garden.
     *
     * You are asked to fence the entire garden using the minimum length of rope as it is expensive. The garden is well
     * fenced only if all the trees are enclosed.
     *
     * Return the coordinates of trees that are exactly located on the fence perimeter.
     *
     * Input: points = [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
     * Output: [[1,1],[2,0],[3,3],[2,4],[4,2]]
     *
     * Constraints:
     *
     * 1 <= points.length <= 3000
     * points[i].length == 2
     * 0 <= xi, yi <= 100
     * All the given points are unique.
     * @param trees
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[][] outerTrees(int[][] trees) {
        // corner case
        if (trees == null || trees.length == 0 || trees[0] == null || trees[0].length == 0) return new int[0][0];

        Arrays.sort(trees, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int n = trees.length;
        Stack<int[]> stack = new Stack<>();

        // lower hull
        for (int i = 0; i < n; i++) {
            while (stack.size() >= 2 && orientation(trees[i], stack.get(stack.size() - 2), stack.peek()) > 0) { // 不在逆时针方向上
                stack.pop();
            }
            stack.push(trees[i]); // 在逆时针方向上的话push当前点
        }
        stack.pop();

        // upper hull
        for (int i = n - 1; i >= 0; i--) {
            while (stack.size() >= 2 && orientation(trees[i], stack.get(stack.size() - 2), stack.peek()) > 0) {
                stack.pop();
            }
            stack.push(trees[i]);
        }

        HashSet<int[]> set = new HashSet<>(stack);
        int[][] res = new int[set.size()][2];
        int idx = 0;
        for (int[] point : set) res[idx++] = point;
        return res;
    }

    private int orientation(int[] p1, int[] p2, int[] p3) {
        return (p3[0] - p2[0]) * (p2[1] - p1[1]) - (p2[0] - p1[0]) * (p3[1] - p2[1]);
    }
}
/**
 * p: (x1, y1)  一开始的点
 * q: (x2, y2)  要考虑的点
 * r: (x3, y3)  参照点
 * pslope = y1 / x1;
 * qslope = y2 / x2;
 * y2 * x1 - y1 * x1 > 0 => qslope > pslope   q点在p点的逆时针方向上
 * 3个点：p, q, r
 * 如何判断在顺时针还是逆时针方向上呢？
 * 两个点变换成一个向量的问题进行处理
 * 把原来的pq两点变成向量
 * pq: (x2 - x1), (y2 - y1)
 * qr: (x3 - x2), (y3 - y2)
 * => cross product = (x2 - x1) * (y3 - y2) - (x3 - x2) * (y2 - y1) > 0 逆时针 来判断qr是在pr的左边还是右边
 *                                                                  < 0 顺时针
 */
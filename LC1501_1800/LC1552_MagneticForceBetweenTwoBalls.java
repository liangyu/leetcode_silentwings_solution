package LC1501_1800;
import java.util.*;
public class LC1552_MagneticForceBetweenTwoBalls {
    /**
     * In universe Earth C-137, Rick discovered a special form of magnetic force between two balls if they are put in
     * his new invented basket. Rick has n empty baskets, the ith basket is at position[i], Morty has m balls and needs
     * to distribute the balls into the baskets such that the minimum magnetic force between any two balls is maximum.
     *
     * Rick stated that magnetic force between two different balls at positions x and y is |x - y|.
     *
     * Given the integer array position and the integer m. Return the required force.
     *
     * Input: position = [1,2,3,4,7], m = 3
     * Output: 3
     *
     * Constraints:
     *
     * n == position.length
     * 2 <= n <= 10^5
     * 1 <= position[i] <= 10^9
     * All integers in position are distinct.
     * 2 <= m <= position.length
     * @param position
     * @param m
     * @return
     */
    // time = O(nlogn), space = O(logn)
    public int maxDistance(int[] position, int m) {
        // corner case
        if (position == null || position.length == 0) return 0;

        Arrays.sort(position); // O(nlogn)

        int n = position.length;
        int left = 1, right = position[n - 1] - position[0];
        while (left < right) { // O(logS)  S 为篮子位置的上限
            int mid = right - (right - left) / 2;
            if (isOK(position, mid, m)) left = mid;
            else right = mid - 1;
        }
        return left; // 一定有解！
    }

    private boolean isOK(int[] position, int dist, int m) {
        int n = position.length, count = 1;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && position[j] - position[i] < dist) j++;
            if (j == n) break;
            count++;
            i = j - 1;
            if (count == m) return true;
        }
        return false;
    }
}
/**
 * 最小的间隔一定是1，所以猜的比较小的话总是可以满足条件
 * 如果把答案猜的比较大，比如100，那就比较难以满足
 * 单调的过程 => 二分猜值
 * 如何判断是否满足呢？贪心！第一个球的位置肯定放最左边，第二个球至少隔开猜的距离值，中间都不能放，至少隔开所猜的值
 * 如此不停的摆放，找下一个available的pos，并且之间间隔 >= 所猜的答案，看能否放满
 * 但并不一定是最优解，所以要继续收敛
 */

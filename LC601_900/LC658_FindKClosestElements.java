package LC601_900;
import java.util.*;
public class LC658_FindKClosestElements {
    /**
     * Given a sorted integer array arr, two integers k and x, return the k closest integers to x in the array.
     * The result should also be sorted in ascending order.
     *
     * An integer a is closer to x than an integer b if:
     *
     * |a - x| < |b - x|, or
     * |a - x| == |b - x| and a < b
     *
     * Input: arr = [1,2,3,4,5], k = 4, x = 3
     * Output: [1,2,3,4]
     *
     * Constraints:
     *
     * 1 <= k <= arr.length
     * 1 <= arr.length <= 10^4
     * arr is sorted in ascending order.
     * -10^4 <= arr[i], x <= 10^4
     *
     * @param arr
     * @param k
     * @param x
     * @return
     */
    // S1: B.S.
    // time = O(nlogn), space = O(1)
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (arr == null || arr.length == 0) return res;

        int start = 0, end = arr.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] <= x) start = mid;
            else end = mid;
        }
        while (k > 0 && start >= 0 && end < arr.length) {
            if (Math.abs(arr[start] - x) <= Math.abs(arr[end] - x)) res.add(arr[start--]);
            else res.add(arr[end++]);
            k--;
        }

        if (start < 0 && end < arr.length) {
            while (k-- > 0) res.add(arr[end++]);
        } else if (end >= arr.length && start >= 0) {
            while (k-- > 0) res.add(arr[start--]);
        }
        Collections.sort(res);
        return res;
    }

    // S2: k + 1 size window (最优解！！！）
    // time = O(logn), space = O(1)
    public List<Integer> findClosestElements2(int[] arr, int k, int x) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (arr == null || arr.length == 0 || k <= 0) return res;

        int n = arr.length;
        int left = 0, right = n - k;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1; // 左端点太远，向右收缩
            } else right = mid; // 此时mid是个有效解
        }
        for (int i = left; i < left + k; i++) {
            res.add(arr[i]);
        }
        return res;
    }
}

/**
 *  S2: K + 1 size window
 *
 *      |__________|     k + 1个元素的window，一定有一个不合格的，在头部或者尾部，离x远的不合格
 *           x
 *      ==> 真正k-size的window的右端点不会超过此时k + 1 size的window的右端点，
 *      而左端点也不会越过此时k + 1 size window的左端点
 *      这样就可以砍掉一半的空间，最后肯定有个收敛值，肯定有解
 * => 选window的起始点
 * 搜索的位置是k-closest subarray的左端点在哪
 * 注意：left可行解的范围在[0, n - k]的范围内
 * 为什么初始范围是0~arr.size()-k，既然取得是k+1大小的窗口，难道不会越界吗？
 * 越界的情况只有可能是mid==arr.size()-k，即初始范围的右端点。但是实际上如果因为mid是向小取整，
 * 上面的情况只会发生在left==right==arr.size()-k的时候，这时循环条件不满足，已经退出了。
 */
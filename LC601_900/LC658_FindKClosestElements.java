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
        if (arr == null || arr.length == 0) return res;

        int start = 0, end = arr.length - k; // start, end指的都是左端点的两端取值范围
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (x - arr[mid] > arr[mid + k] - x) { // 左半部分比右半部分多，所以要右移start = mid
                start = mid;
            } else { // 右半部分比左半部分多，所以要左移，end = mid。注意相等的情况，依然是左移，因为同样距离，取小值更优先！
                end = mid;
            }
        }

        // post-processing，跳出loop后再次比较左右两端点来判断哪个作为最后k-size window的左端点
        if (start + k < arr.length && x - arr[start] > arr[start + k] - x) { // 注意start + k 不能越界！！！
            start = end; // 左半部分大，那么明显窗口要右移，选择end来作为左端点；反之选择start作为左端点。
        }

        // 输出从start开始的k个元素一定是正确解
        for (int i = start; i < start + k; i++) {
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
 */
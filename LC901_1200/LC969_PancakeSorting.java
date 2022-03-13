package LC901_1200;
import java.util.*;
public class LC969_PancakeSorting {
    /**
     * Given an array of integers arr, sort the array by performing a series of pancake flips.
     *
     * In one pancake flip we do the following steps:
     *
     * Choose an integer k where 1 <= k <= arr.length.
     * Reverse the sub-array arr[0...k-1] (0-indexed).
     * For example, if arr = [3,2,1,4] and we performed a pancake flip choosing k = 3, we reverse the sub-array [3,2,1],
     * so arr = [1,2,3,4] after the pancake flip at k = 3.
     *
     * Return an array of the k-values corresponding to a sequence of pancake flips that sort arr. Any valid answer
     * that sorts the array within 10 * arr.length flips will be judged as correct.
     *
     * Input: arr = [3,2,4,1]
     * Output: [4,2,4,3]
     * Starting state: arr = [3, 2, 4, 1]
     * After 1st flip (k = 4): arr = [1, 4, 2, 3]
     * After 2nd flip (k = 2): arr = [4, 1, 2, 3]
     * After 3rd flip (k = 4): arr = [3, 2, 1, 4]
     * After 4th flip (k = 3): arr = [1, 2, 3, 4]
     *
     * Constraints:
     *
     * 1 <= arr.length <= 100
     * 1 <= arr[i] <= arr.length
     * All integers in arr are unique (i.e. arr is a permutation of the integers from 1 to arr.length).
     * @param arr
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public List<Integer> pancakeSort(int[] arr) {
        List<Integer> res = new ArrayList<>();
        int n = arr.length, k = n;

        while (k > 0) { // O(n)
            int idx = 0;
            for (int i = 0; i < n; i++) { // O(n)
                if (arr[i] == k && i != k - 1) {
                    idx = i == 0 ? k - 1 : i; // 将最大元素置换到头部后，来个底朝天， i = k - 1,否则就是[0:i]做个flip
                    break;
                }
            }
            flip(arr, idx); // O(n)
            res.add(idx + 1);
            if (arr[k - 1] == k) k--;
        }
        return res;
    }

    private void flip(int[] arr, int i) {
        int j = 0;
        while (j < i) {
            int t = arr[j];
            arr[j++] = arr[i];
            arr[i--] = t;
        }
    }
}

package LC1201_1500;
import java.util.*;
public class LC1340_JumpGameV {
    /**
     * Given an array of integers arr and an integer d. In one step you can jump from index i to index:
     *
     * i + x where: i + x < arr.length and 0 < x <= d.
     * i - x where: i - x >= 0 and 0 < x <= d.
     * In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] for all indices k
     * between i and j (More formally min(i, j) < k < max(i, j)).
     *
     * You can choose any index of the array and start jumping. Return the maximum number of indices you can visit.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     * Input: arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= arr.length <= 1000
     * 1 <= arr[i] <= 10^5
     * 1 <= d <= arr.length
     * @param arr
     * @param d
     * @return
     */
    // time = O(n^2), space = O(n)
    public int maxJumps(int[] arr, int d) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length, res = 0;
        int[] mem = new int[n];
        for (int i = 0; i < n; i++) {
            dfs(arr, d, i, mem);
            res = Math.max(res, mem[i]);
        }
        return res;
    }

    private int dfs(int[] arr, int d, int i, int[] mem) {
        if (mem[i] != 0) return mem[i]; // 一旦访问过，mem[i]至少是1，不会是0

        int res = 1;
        for (int k = 1; k <= d; k++) {
            if (i + k >= arr.length) break;
            if (arr[i + k] >= arr[i]) break;
            res = Math.max(res, dfs(arr, d, i + k, mem) + 1);
        }
        for (int k = 1; k <= d; k++) {
            if (i - k < 0) break;
            if (arr[i - k] >= arr[i]) break;
            res = Math.max(res, dfs(arr, d, i - k, mem) + 1);
        }
        mem[i] = res;
        return res;
    }
}
/**
 * 人只能往低处走 -> 最容易判断的就是低处的bar = 1， 然后就是倒数第二低的bar
 * 先解决矮的，再解决高的 -> dp
 * => 不需要从最矮的出发 => 先解决小问题，再处理大问题 => recursion + memo => O(n) 每个柱子只解决1次
 */
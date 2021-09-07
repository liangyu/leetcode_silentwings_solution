package LC1201_1500;
import java.util.*;
public class LC1477_FindTwoNonoverlappingSubarraysEachWithTargetSum {
    /**
     * Given an array of integers arr and an integer target.
     *
     * You have to find two non-overlapping sub-arrays of arr each with a sum equal target. There can be multiple
     * answers so you have to find an answer where the sum of the lengths of the two sub-arrays is minimum.
     *
     * Return the minimum sum of the lengths of the two required sub-arrays, or return -1 if you cannot find such two
     * sub-arrays.
     *
     * Input: arr = [3,2,2,4,3], target = 3
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i] <= 1000
     * 1 <= target <= 10^8
     * @param arr
     * @param target
     * @return
     */
    // S1: Two Pointers
    // time = O(n), space = O(n)
    public int minSumOfLengths(int[] arr, int target) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length;
        int i = 0, sum = 0, res = Integer.MAX_VALUE;
        int[] f = new int[n];
        Arrays.fill(f, Integer.MAX_VALUE / 2);

        for (int j = 0; j < n; j++) {
            sum += arr[j];
            while (sum > target) sum -= arr[i++];
            if (sum == target) {
                f[j] = j - i + 1;
                if (i > 0) res = Math.min(res, f[i - 1] + f[j]);
            }
            // update f[j]
            if (j > 0) f[j] = Math.min(f[j], f[j - 1]);
        }
        return res > n ? -1 : res;
    }

    // S2: Prefix + HashMap
    // time = O(n), space = O(n)
    public int minSumOfLengths2(int[] arr, int target) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += arr[i];
            map.put(sum, i);
        }

        sum = 0;
        int minLen = Integer.MAX_VALUE, res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            if (map.containsKey((sum - target))) {
                minLen = Math.min(minLen, i - map.get(sum - target));
            }
            if (map.containsKey(sum + target) && minLen != Integer.MAX_VALUE) {
                res = Math.min(res, minLen + map.get(sum + target) - i);
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
/**
 * ans=min(ans,f(i−1)+j−i+1)
 * 表示当前以j结尾的满足条件的区间长度与i-1之前的最小的区间长度之和，这样就能满足两个窗口不重叠且长度之和最小。
 */
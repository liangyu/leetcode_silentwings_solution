package LC301_600;
import java.util.*;
public class LC321_CreateMaximumNumber {
    /**
     * You are given two integer arrays nums1 and nums2 of lengths m and n respectively. nums1 and nums2 represent the
     * digits of two numbers. You are also given an integer k.
     *
     * Create the maximum number of length k <= m + n from digits of the two numbers. The relative order of the digits
     * from the same array must be preserved.
     *
     * Return an array of the k digits representing the answer.
     *
     * Input: nums1 = [3,4,6,5], nums2 = [9,1,2,5,8,3], k = 5
     * Output: [9,8,6,5,3]
     *
     * Constraints:
     *
     * m == nums1.length
     * n == nums2.length
     * 1 <= m, n <= 500
     * 0 <= nums1[i], nums2[i] <= 9
     * 1 <= k <= m + n
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    // S1: Greedy
    // time = O(m * (m^2 + n^2)), space = O(m + n)
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] res = new int[k];

        for (int i = 0; i <= Math.min(m, k); i++) { // O(m)
            if (m < i || n < k - i) continue;
            int[] arr1 = maxArray(nums1, i); // O(m^2)
            int[] arr2 = maxArray(nums2, k - i); // O(n^2)
            int[] temp = merge(arr1, arr2, k); // O(m + n)
            if (greater(temp, 0, res, 0)) res = temp; // O(max(m, n))
        }
        return res;
    }

    private int[] maxArray(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[k];
        int j = 0; // j: stack size
        for (int i = 0; i < n; i++) { // O(n)
            // need to pop elements from res by following:
            // 1. stack is not empty -> j > 0
            // 2. top element of stack is smaller than the current element
            // 3. there are still enough elements left to fill -> n - i > k - j (remain n - i elements to fill k - j space)
            while (j > 0 && res[j - 1] < nums[i] && n - i > k - j) j--; // O(n)
            if (j < k) res[j++] = nums[i]; // if still has enough space to fill, fill it after escaping the while loop above
        }
        return res;
    }

    private int[] merge(int[] nums1, int[] nums2, int k) {
        int[] res = new int[k];
        int i = 0, j = 0, idx = 0;
        while (idx < k) { // O(m + n)
            res[idx++] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }
        return res;
    }

    private boolean greater(int[] nums1, int i, int[] nums2, int j) {
        int m = nums1.length, n = nums2.length;
        while (i < m && j < n && nums1[i] == nums2[j]) {
            i++;
            j++;
        }

        if (i == m) return false;
        if (j == n) return i != m;
        if (i < m && nums1[i] > nums2[j]) return true;
        return false;
    }

    // S1.2
    // time = O(k * (m + n)), space = O(m + n)
    public int[] maxNumber2(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= k; i++) { // O(k)
            if (i > m || k - i > n) continue;
            List<Integer> p1 = findMax(nums1, i); // O(m)
            List<Integer> p2 = findMax(nums2, k - i); // O(n)
            List<Integer> temp = merge(p1, p2); // O(m + n)
            if (!max(res, 0, temp, 0)) res = new ArrayList<>(temp);
        }
        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }

    private List<Integer> findMax(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();

        int n = nums.length, drop = n - k;
        for (int i = 0; i < n; i++) {
            while (res.size() > 0 && drop > 0 && nums[i] > res.get(res.size() - 1)) {
                res.remove(res.size() - 1);
                drop--;
            }
            res.add(nums[i]);
        }
        while (drop-- > 0) res.remove(res.size() - 1);
        return res;
    }

    private List<Integer> merge(List<Integer> p1, List<Integer> p2) {
        int m = p1.size(), n = p2.size();
        List<Integer> res = new ArrayList<>();

        int i = 0, j = 0;
        while (i < m && j < n) {
            if (p1.get(i) > p2.get(j)) {
                res.add(p1.get(i++));
            } else if (p1.get(i) < p2.get(j)) {
                res.add(p2.get(j++));
            } else {
                if (max(p1, i, p2, j)) res.add(p1.get(i++));
                else res.add(p2.get(j++));
            }
        }
        while (i < m) res.add(p1.get(i++));
        while (j < n) res.add(p2.get(j++));
        return res;
    }

    private boolean max(List<Integer> p1, int i, List<Integer> p2, int j) {
        int m = p1.size(), n = p2.size();
        while (i < m && j < n) {
            if (p1.get(i) == p2.get(j)) {
                i++;
                j++;
            } else if (p1.get(i) > p2.get(j)) return true;
            else return false;
        }
        if (i < m) return true;
        return false;
    }

    // S2: dp (TLE!!!)
    // time = O(m * n * k), space = O(m * n * k)
    public int[] maxNumber3(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        String[][][] dp = new String[m + 1][n + 1][k + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int l = 0; l <= k; l++) {
                    dp[i][j][l] = "";
                }
            }
        }
        int[] arr1 = new int[m + 1], arr2 = new int[n + 1];
        for (int i = 0; i < m; i++) arr1[i + 1] = nums1[i];
        for (int i = 0; i < n; i++) arr2[i + 1] = nums2[i];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int l = 1; l <= Math.min(i + j, k); l++) {
                    dp[i][j][l] = "";
                    if (i >= 1) {
                        String a = dp[i][j][l], b = dp[i - 1][j][l - 1] + arr1[i];
                        dp[i][j][l] = a.compareTo(b) >= 0 ? a : b;
                    }
                    if (j >= 1) {
                        String a = dp[i][j][l], b = dp[i][j - 1][l - 1] + arr2[j];
                        dp[i][j][l] = a.compareTo(b) >= 0 ? a : b;
                    }
                    if (i >= 1) {
                        String a = dp[i][j][l], b = dp[i - 1][j][l];
                        dp[i][j][l] = a.compareTo(b) >= 0 ? a : b;
                    }
                    if (j >= 1) {
                        String a = dp[i][j][l], b = dp[i][j - 1][l];
                        dp[i][j][l] = a.compareTo(b) >= 0 ? a : b;
                    }
                }
            }
        }
        int[] res = new int[dp[m][n][k].length()];
        for (int i = 0; i < dp[m][n][k].length(); i++) {
            res[i] = dp[m][n][k].charAt(i) - '0';
        }
        return res;
    }
}
/**
 * ref: LC402 相似
 * 双序列dp -> 状态转移比较套路
 * dp[i][j][k]: the maximum number of length k <= m + n from digits of nums[0:i] and nums2[0:j]
 * dp[m][n][K] => dp[i'][j'][k']
 * nums1: xxxxx i
 * nums2: xxxxxxxx j
 * dp[i-1][j][k-1] + nums1[i]
 * dp[i][j-1][k-1] + nums2[j]
 * dp[i-1][j-1][k]
 *
 * dp[0][j][k] =>
 *      dp[0][j-1][k-1] + nums2[j]
 *      dp[0][j-1][k]
 * dp[i][0][k] =>
 *
 * S2:
 * 6 5
 * 8 3
 * 总共就取k个数字
 * 维持一个递减的序列
 * 1：从nums1里取i个元素组成最大数组，从nums2里取k-i个元素组成最大数组。
 * 2：合并之前结果，得到一个长度为k的最大数组。
 * 3：对于不同长度分配的情况，比较每次得到的长度为k的最大数组，返回最大的一个。
 */
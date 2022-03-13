package LC2101_2400;
import java.util.*;
public class LC2167_MinimumTimetoRemoveAllCarsContainingIllegalGoods {
    /**
     * You are given a 0-indexed binary string s which represents a sequence of train cars. s[i] = '0' denotes that the
     * ith car does not contain illegal goods and s[i] = '1' denotes that the ith car does contain illegal goods.
     *
     * As the train conductor, you would like to get rid of all the cars containing illegal goods. You can do any of the
     * following three operations any number of times:
     *
     * Remove a train car from the left end (i.e., remove s[0]) which takes 1 unit of time.
     * Remove a train car from the right end (i.e., remove s[s.length - 1]) which takes 1 unit of time.
     * Remove a train car from anywhere in the sequence which takes 2 units of time.
     * Return the minimum time to remove all the cars containing illegal goods.
     *
     * Note that an empty sequence of cars is considered to have no cars containing illegal goods.
     *
     * Input: s = "1100101"
     * Output: 5
     *
     * Input: s = "0010"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 2 * 10^5
     * s[i] is either '0' or '1'.
     * @param s
     * @return
     */
    // S1: dp
    // time = O(n), space = O(n)
    public int minimumTime(String s) {
        int n = s.length();
        int[] pre = new int[n];
        pre[0] = s.charAt(0) == '1' ? 1 : 0;
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + (s.charAt(i) == '1' ? 1 : 0);
        }

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = 2 * pre[i] - i;
        }

        int preMax = 1, res = n;
        for (int j = 0; j < n; j++) {
            res = Math.min(res, arr[j] - preMax + n);
            preMax = Math.max(preMax, arr[j]);
        }
        return res;
    }

    // S2: two pass
    // time = O(n), space = O(n)
    public int minimumTime2(String s) {
        int n = s.length();
        int[] left = new int[n];
        left[0] = s.charAt(0) == '1' ? 1 : 0;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == '0') left[i] = left[i - 1];
            else left[i] = Math.min(i + 1, left[i - 1] + 2);
        }

        int[] right = new int[n];
        right[n - 1] = s.charAt(n - 1) == '1' ? 1 : 0;
        for (int i = n - 2; i >= 0; i--) {
            if (s.charAt(i) == '0') right[i] = right[i + 1];
            else right[i] = Math.min(n - i, right[i + 1] + 2);
        }

        int res = Math.min(left[n - 1], right[0]); // corner case: 从左到右扫到底，从右到左扫到底
        int preMax = 1;
        for (int j = 0; j < n - 1; j++) {
            res = Math.min(res, left[j] + right[j + 1]); // 一定会有分界点，如果没有分界点呢？
        }
        return res;
    }

    // S3: one pass (optimal solution)
    // time = O(n), space = O(1)
    public int minimumTime3(String s) {
        int n = s.length(), left = 0, res = n;
        for (int i = 0; i < n; i++) {
            left = Math.min(left + (s.charAt(i) - '0') * 2, i + 1); // 求出i左边2种选项的最小值
            res = Math.min(res, left + n - 1 - i); // i的右边cost = n - 1 - i
        }
        return res;
    }
}
/**
 * [x x x] (i x j) [x x x]
 * i + 2*(prefix[j] - prefix[i-1]) + (n-j-1)
 * 2*prefix[j]-j - 2*prefix[i-1] + (i-1) + n
 * arr[k] = 2*prefix[k]-k;
 * min: arr[j] - arr[i-1] + n  where i < j
 * x x i x x x j
 * arr[-1] = 2 * prefix[-1] + 1 = 1  -> preMax = 1
 * 可能不用规则3
 * S2: 拆成2部分：法则1+法则3 | 法则2+法则3
 * dp1[i]: the minimum cost to remove all 1s in [0:i] using rule 1 & 3
 * dp2[i]: the minimum cost to remove all 1s in [i:n-1] using rule 2 & 3
 * dp1[i]:
 * 1. if s[i] == 0, dp1[i] = dp1[i-1]
 * 2. if s[i] == 1, dp1[i] = min(i + 1, 2 + dp1[i-1])
 * dp2[i]:
 * 1. if s[i] == 0, dp2[i] = dp2[i+1]
 * 2. if s[i] == 1, dp2[i] = min(n - i, 2 + dp1[i+1])
 * min{dp1[i] + dp2[i+1]}
 */

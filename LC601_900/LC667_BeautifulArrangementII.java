package LC601_900;
import java.util.*;
public class LC667_BeautifulArrangementII {
    /**
     * Given two integers n and k, you need to construct a list which contains n different positive integers ranging
     * from 1 to n and obeys the following requirement:
     * Suppose this list is [a1, a2, a3, ... , an], then the list [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|]
     * has exactly k distinct integers.
     *
     * If there are multiple answers, print any of them.
     *
     * Input: n = 3, k = 1
     * Output: [1, 2, 3]
     *
     * Input: n = 3, k = 2
     * Output: [1, 3, 2]
     *
     * The n and k are in the range 1 <= k < n <= 104.
     *
     * @param n
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int[] constructArray(int n, int k) {
        // corner case
        if (n <= k) return null; // 题目给定限制条件 n > k

        int[] res = new int[n];
        // 1, n, 2, n - 1, 3, ...
        int i = 0, left = 1, right = n;
        while (i < k) {
            res[i++] = left++;
            if (i < k) res[i++] = right--;
        }
        if (i % 2 == 0) { // 已经找到 k - 1个distinct之后，下面的只要保持前后两个数差值为1作为第k个distinct差值即可！！！
            while (i < res.length) res[i++] = right--;
            // 如果当前是奇数位，原来之前摆放的都是left的数字，这时要放right，因为前一位是right
        } else {
            while (i < res.length) res[i++] = left++;
            // 同样，如果当前是偶数位，由于上一位放的是left，那么这时后面都要放下一个left，使得差值为1
        }
        return res;
    }
}

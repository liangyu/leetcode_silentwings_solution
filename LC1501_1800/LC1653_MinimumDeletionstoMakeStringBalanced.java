package LC1501_1800;

public class LC1653_MinimumDeletionstoMakeStringBalanced {
    /**
     * You are given a string s consisting only of characters 'a' and 'b'.
     *
     * You can delete any number of characters in s to make s balanced. s is balanced if there is no pair of indices
     * (i,j) such that i < j and s[i] = 'b' and s[j]= 'a'.
     *
     * Return the minimum number of deletions needed to make s balanced.
     *
     * Input: s = "aababbab"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is 'a' or 'b'.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int minimumDeletions(String s) {
        int n = s.length();
        int[] pre = new int[n];
        pre[0] = s.charAt(0) == 'b' ? 1 : 0;
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + (s.charAt(i) == 'b' ? 1 : 0);
        }
        int[] suf = new int[n];
        suf[n - 1] = s.charAt(n - 1) == 'a' ? 1 : 0;
        for (int i = n - 2; i >= 0; i--) {
            suf[i] = suf[i + 1] + (s.charAt(i) == 'a' ? 1 : 0);
        }

        int res = Math.min(pre[n - 1], suf[0]); // choose the min value between all changed to 'a' or all changed to 'b'
        for (int i = 0; i < n - 1; i++) {
            res = Math.min(res, pre[i] + suf[i + 1]);
        }
        return res;
    }
}
/**
 * TwoPass ref: LC1769
 * x x x x | x x x x
 *       i  i+1
 * A[i] = ...
 * B[i] = ...
 * res[i] = f(A[i] + B[i])
 * res = min(res[i])
 *
 * aaaa....bbb
 * a和b的分界点在哪？
 * 2个corner case: all become 'a' or 'b'
 */
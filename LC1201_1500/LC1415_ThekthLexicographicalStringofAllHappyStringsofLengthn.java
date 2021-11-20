package LC1201_1500;
import java.util.*;
public class LC1415_ThekthLexicographicalStringofAllHappyStringsofLengthn {
    /**
     * A happy string is a string that:
     *
     * consists only of letters of the set ['a', 'b', 'c'].
     * s[i] != s[i + 1] for all values of i from 1 to s.length - 1 (string is 1-indexed).
     * For example, strings "abc", "ac", "b" and "abcbabcbcb" are all happy strings and strings "aa", "baa" and "ababbc"
     * are not happy strings.
     *
     * Given two integers n and k, consider a list of all happy strings of length n sorted in lexicographical order.
     *
     * Return the kth string of this list or return an empty string if there are less than k happy strings of length n.
     *
     * Input: n = 1, k = 3
     * Output: "c"
     *
     * Constraints:
     *
     * 1 <= n <= 10
     * 1 <= k <= 100
     * @param n
     * @param k
     * @return
     */
    // S1: DFS
    // time = O(n), space = O(n)
    HashSet<String> set;
    private String res = "";
    public String getHappyString(int n, int k) {
        set = new HashSet<>();
        dfs(new StringBuilder(), 0, n, k);
        return res;
    }

    private void dfs(StringBuilder sb, int cur, int n, int k) {
        // base case
        if (cur == n) {
            String s = sb.toString();
            set.add(s);
            if (set.size() == k) res = s;
            return;
        }

        // pruning
        if (res != "") return; // immediately return the result

        for (char c = 'a'; c <= 'c'; c++) {
            if (cur != 0 && c == sb.charAt(sb.length() - 1)) continue;
            sb.append(c);
            dfs(sb, cur + 1, n, k);
            sb.setLength(sb.length() - 1);
        }
    }

    // S2: Math
    // time = O(n), space = O(n)
    public String getHappyString2(int n, int k) {
        StringBuilder sb = new StringBuilder();
        int total = (int)(Math.pow(2, n - 1) * 3);
        if (k > total) return "";

        dfs(sb, n, k - 1); // convert k to 0-index
        return sb.toString();
    }

    private void dfs(StringBuilder sb, int n, int k) {
        if (n == 0) return;

        int t = k / (int)Math.pow(2, n - 1); // t只能是0或者1，0 -> a, 1 -> b
        char ch = (char)('a' + t);

        if (sb.length() > 0 && ch >= sb.charAt(sb.length() - 1)) ch++; // t = 0 -> a++ to b; else b++ to c
        sb.append(ch);
        dfs(sb, n - 1, k - t * (int)Math.pow(2, n - 1)); // when t = 0, do nothing for a to b; else t = 1 for b to c
    }
}
/**
 * recursion
 * 当我们尝试填写长度为n的字符串的首字母时，无论首字母是什么，之后的n-1位都有pow(2,n-1)种填写方法。
 * 所以我们用k/pow(2,n-1)就可以确定此时的首字母ch应该是字母表的第几个。注意这里的k应该用0-index更为方便。
 * 比如k=0，那么ch应该就是'a'，如果k=1，那么ch应该就是'b'.
 * 但是我们还需要考虑到之前一位的制约。
 * 如果发现计算得到的ch比上一位字母要大，那么意味着当前字母基数应该加1。
 * 因为此位我们不能尝试和前面一样的字母，所以会少pow(2,n-1)的可能性。
 */

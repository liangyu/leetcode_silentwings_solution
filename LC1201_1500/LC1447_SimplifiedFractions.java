package LC1201_1500;
import java.util.*;
public class LC1447_SimplifiedFractions {
    /**
     * Given an integer n, return a list of all simplified fractions between 0 and 1 (exclusive) such that the
     * denominator is less-than-or-equal-to n. You can return the answer in any order.
     *
     * Example 1:
     *
     * Input: n = 2
     * Output: ["1/2"]
     * Explanation: "1/2" is the only unique fraction with a denominator less-than-or-equal-to 2.
     * Example 2:
     *
     * Input: n = 3
     * Output: ["1/2","1/3","2/3"]
     * Example 3:
     *
     * Input: n = 4
     * Output: ["1/2","1/3","1/4","2/3","3/4"]
     * Explanation: "2/4" is not a simplified fraction because it can be simplified to "1/2".
     *
     *
     * Constraints:
     *
     * 1 <= n <= 100
     * @param n
     * @return
     */
    // time = O(n^2 * logn), space = O(1)
    public List<String> simplifiedFractions(int n) {
        List<String> res = new ArrayList<>();

        HashSet<String> set = new HashSet<>();
        for (int i = n; i > 0; i--) {
            for (int j = i - 1; j > 0; j--) {
                StringBuilder sb = new StringBuilder();
                int a = i / gcd(i, j);
                int b = j / gcd(i, j);
                sb.append(b).append('/').append(a);
                set.add(sb.toString());
            }
        }
        res.addAll(set);
        return res;
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}

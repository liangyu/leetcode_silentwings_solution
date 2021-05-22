package LC001_300;
import java.util.*;
public class LC254_FactorCombinations {
    /**
     * Numbers can be regarded as the product of their factors.
     *
     * For example, 8 = 2 x 2 x 2 = 2 x 4.
     * Given an integer n, return all possible combinations of its factors. You may return the answer in any order.
     *
     * Note that the factors should be in the range [2, n - 1].
     *
     * Input: n = 32
     * Output: [[2,16],[4,8],[2,2,8],[2,4,4],[2,2,2,4],[2,2,2,2,2]]
     *
     * Constraints:
     *
     * 1 <= n <= 10^8
     * @param n
     * @return
     */
    // time = O(2^n), space = O(n)
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList<>();
        helper(n, 2, new ArrayList<>(), res, (int)Math.sqrt(n));
        return res;
    }

    private void helper(int n, int start, List<Integer> path, List<List<Integer>> res, int root) {
        // base case
        if (n == 1) {
            if (path.size() > 1) {
                res.add(new ArrayList<>(path));
                return;
            }
        }

        for (int i = start; i <= n; i++) { // O(n)
            if (i > root) i = n; // in this case, when i == n, path.size() = 1, so it won't be added into the res
            if (n % i == 0) {
                path.add(i);
                helper(n / i, i, path, res, (int)Math.sqrt(n / i));
                path.remove(path.size() - 1);
            }
        }
    }
}
/**
 * Actually, factors of an integer n (except for 1 and n) are always between 1 and sqrt(n), so you do not have to check
 * those numbers between sqrt(n) and n. However, in your method, we need to check n, so I added a check, when i is
 * greater than sqrt(n), i will jump directly to n. This little change improved a lot.
 */
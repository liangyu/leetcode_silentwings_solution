package LC001_300;
import java.util.*;
public class LC60_PermutationSequence {
    /**
     * The set [1, 2, 3, ..., n] contains a total of n! unique permutations.
     *
     * By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
     *
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * Given n and k, return the kth permutation sequence.
     *
     * Input: n = 3, k = 3
     * Output: "213"
     *
     * Constraints:
     *
     * 1 <= n <= 9
     * 1 <= k <= n!
     * @param n
     * @param k
     * @return
     */
    // time = O(n^2), space = O(n)
    public String getPermutation(int n, int k) {
        List<Integer> digits = new ArrayList<>();
        for (int i = 1; i <= n; i++) digits.add(i);

        k--; // make it 0-index
        String res = "";

        while (n > 0) {
            int a = k / fact(n - 1);
            res += (digits.get(a)); // 确定最高位

            k -= a * fact(n - 1); // 一位一位确定，循环n次,一直循环到n = 1 -> k = 0
            n--;

            digits.remove(a); // 确定一位就移除一位，在剩下的数字当中去选择 -> delete: O(n)
        }
        return res;
    }

    private int fact(int k) {
        int res = 1;
        for (int i = 1; i <= k; i++) {
            res *= i;
        }
        return res;
    }
}

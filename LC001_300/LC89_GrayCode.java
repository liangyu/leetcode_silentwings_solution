package LC001_300;
import java.util.*;
public class LC89_GrayCode {
    /**
     * The gray code is a binary numeral system where two successive values differ in only one bit.
     *
     * Given an integer n representing the total number of bits in the code, return any sequence of gray code.
     *
     * A gray code sequence must begin with 0.
     *
     * Input: n = 2
     * Output: [0,1,3,2]
     *
     * Constraints:
     *
     * 1 <= n <= 16
     * @param n
     * @return
     */
    // time = O(2^n), space = O(1)
    public List<Integer> grayCode(int n) {
        List<Integer> res = new ArrayList<>();
        res.add(0);
        // corner case
        if (n == 0) return res;

        for (int i = 0; i < n; i++) {
            int len = res.size();
            for (int j = len - 1; j >= 0; j--) {
                res.add(res.get(j) | (1 << i));
            }
        }
        return res;
    }
}
/**
 * 镜像法: 逆序排列一下
 * 0      00       000
 * 1      01       001
 *        11       011
 *        10       010
 *                 110
 *                 111
 *                 101
 *                 100
 */

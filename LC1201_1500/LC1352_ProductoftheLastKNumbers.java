package LC1201_1500;
import java.util.*;
public class LC1352_ProductoftheLastKNumbers {
    /**
     * Design an algorithm that accepts a stream of integers and retrieves the product of the last k integers of the
     * stream.
     *
     * Implement the ProductOfNumbers class:
     *
     * ProductOfNumbers() Initializes the object with an empty stream.
     * void add(int num) Appends the integer num to the stream.
     * int getProduct(int k) Returns the product of the last k numbers in the current list. You can assume that always
     * the current list has at least k numbers.
     * The test cases are generated so that, at any time, the product of any contiguous sequence of numbers will fit
     * into a single 32-bit integer without overflowing.
     *
     * Input
     * ["ProductOfNumbers","add","add","add","add","add","getProduct","getProduct","getProduct","add","getProduct"]
     * [[],[3],[0],[2],[5],[4],[2],[3],[4],[8],[2]]
     *
     * Output
     * [null,null,null,null,null,null,20,40,0,null,32]
     *
     * Constraints:
     *
     * 0 <= num <= 100
     * 1 <= k <= 4 * 10^4
     * At most 4 * 104 calls will be made to add and getProduct.
     * The product of the stream at any point in time will fit in a 32-bit integer.
     */
    List<Integer> pre;
    int n, lastZero;
    public LC1352_ProductoftheLastKNumbers() {
        pre = new ArrayList<>();
        pre.add(1);
        n = 0;
        lastZero = 0;
    }

    // time = O(1), space = O(n)
    public void add(int num) {
        n++;
        if (num == 0) {
            pre.add(1);
            lastZero = n;
        } else pre.add(pre.get(pre.size() - 1) * num);
    }

    // time = O(1), space = O(n)
    public int getProduct(int k) {
        // [n -k + 1, n]
        if (lastZero <= n - k) return pre.get(n) / pre.get(n - k);
        return 0;
    }
}
/**
 * prefix
 * pre[i]: the product of nums[1]*nums[2]*...*nums[i]
 * prod[i:j] = pre[j] / pre[i-1]
 * 1 2 [3 2 4] = pre[4] / pre[1] = 48 / 2 = 24
 * 1 [2 3 4] = pre[4] / pre[0] = 48 / 1 = 48
 * nums[i] = 0
 * pre[i] = 0, pre[i+1] = 0, ...
 * 如何处理0？
 * x x x [x 0 3 2 4]
 * n = 8, k = 5, res = 0   只要范围里混入一个0， 答案始终为0  => lastZero, res = 0;
 * x x x x 0 [3 2 4]
 *         1  3 6 24
 * n = 8, k = 3, res =
 */
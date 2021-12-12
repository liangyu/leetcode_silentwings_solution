package LC1201_1500;
import java.util.*;
public class LC1442_CountTripletsThatCanFormTwoArraysofEqualXOR {
    /**
     * Given an array of integers arr.
     *
     * We want to select three indices i, j and k where (0 <= i < j <= k < arr.length).
     *
     * Let's define a and b as follows:
     *
     * a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1]
     * b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k]
     * Note that ^ denotes the bitwise-xor operation.
     *
     * Return the number of triplets (i, j and k) Where a == b.
     *
     * Input: arr = [2,3,1,6,7]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= arr.length <= 300
     * 1 <= arr[i] <= 10^8
     * @param arr
     * @return
     */
    // S1: HashMap
    // time = O(n^2), space = O(n)
    public int countTriplets(int[] arr) {
        int n = arr.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>(); // pre_xor_sum_val -> {idx}
        map.putIfAbsent(0, new ArrayList<>());
        map.get(0).add(-1);

        int xorsum = 0, res = 0;
        for (int k = 0; k < n; k++) {
            xorsum ^= arr[k];
            for (int i : map.getOrDefault(xorsum ^ 0, new ArrayList<>())) {
                // xorsum [i+1:k] = 0
                // j: i + 2 -> k
                res += k - i - 1;
            }
            map.putIfAbsent(xorsum, new ArrayList<>());
            map.get(xorsum).add(k);
        }
        return res;
    }

    // S2
    // time = O(n^2), space = O(1)
    public int countTriplets2(int[] arr) {
        int n = arr.length, res = 0;
        for (int i = 0; i < n; i++) {
            int val = arr[i];
            for (int k = i + 1; k < n; k++) {
                val ^= arr[k];
                if (val == 0) res += k - i;
            }
        }
        return res;
    }
}
/**
 * x x [i x x] [j x k] x x x
 * x x [i x x j x k] x x x
 * A^A = 0
 * x^x^x == x^x
 * 只要找到i和k，中间的j可以在[i+1,k]
 * 如果确定i和j，拆分的个数 = k - i
 * 只要找到二元对，就能找到k-i种三元对的方法
 * 找子区间xor = 0
 * XOR_sum
 * sum
 * x x [x x x x] x x x
 *            i
 * 转化为前缀和的形式
 * xor_sum[i:k] = pre_xor_sum[k] ^ pre_xor[i-1]
 * pre_xor[i-1] = pre_xor_sum[k] ^ 0
 * sum[i:k] = pre_sum[k] - pre_sum[i-1]
 * xor_sum[i:k] = pre_xor_sum[k] ^ pre_xor_sum[i-1] (if i == 0 -> -1)
 */

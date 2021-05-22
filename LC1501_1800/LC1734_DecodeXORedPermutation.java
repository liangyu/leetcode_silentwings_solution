package LC1501_1800;
import java.util.*;
public class LC1734_DecodeXORedPermutation {
    /**
     * There is an integer array perm that is a permutation of the first n positive integers, where n is always odd.
     *
     * It was encoded into another integer array encoded of length n - 1, such that encoded[i] = perm[i] XOR perm[i + 1].
     * For example, if perm = [1,3,2], then encoded = [2,1].
     *
     * Given the encoded array, return the original array perm. It is guaranteed that the answer exists and is unique.
     *
     * Input: encoded = [3,1]
     * Output: [1,2,3]
     *
     * Input: encoded = [6,5,4,6]
     * Output: [2,4,1,5,3]
     *
     * Constraints:
     *
     * 3 <= n < 10^5
     * n is odd.
     * encoded.length == n - 1
     *
     * @param encoded
     * @return
     */
    // time = O(n), space = O(1)
    public int[] decode(int[] encoded) {
        int n = encoded.length + 1;
        int sum = 0;
        for (int i = 1; i <= n; i++) sum ^= i; // permutation of the first n positive integers!!!

        // 隔着跳
        int noheadSum = 0;
        for (int i = 1; i < encoded.length; i += 2) {
            noheadSum ^= encoded[i];
        }

        int[] res = new int[n];
        res[0] = sum ^ noheadSum; // n is always odd, so we can pair the rest except for the 1st one
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] ^ encoded[i - 1];
        }
        return res;
    }
}

/**
 * a ^ a = 0  -> 异或的题目一般肯定会用到这个性质
 *
 * XOR(encoded) = nums[0] ^ nums[n - 1]  -> 不是特别好用
 *
 * n is odd -> 除掉一个，就可以两两配对，总的异或值就知道了，因为题目第一句话就给出了这个要求的output array是一个[1, n]左闭右闭区间内
 * 的正整数的排列组合，也就是说肯定是长度为n，并且里面为数字都是1...n的所有unique数字排列组合。那么整个array的XOR值就可以直接从1异或到n，
 * 因为异或操作符合交换律！而两两配对的也可以通过encoded的隔位取值而获得，剩下的那个孤立的头元素就迎刃而解了。
 */

package LC1801_2100;
import java.util.*;
public class LC1850_MinimumAdjacentSwapstoReachtheKthSmallestNumber {
    /**
     * You are given a string num, representing a large integer, and an integer k.
     *
     * We call some integer wonderful if it is a permutation of the digits in num and is greater in value than num.
     * There can be many wonderful integers. However, we only care about the smallest-valued ones.
     *
     * Return the minimum number of adjacent digit swaps that needs to be applied to num to reach the kth smallest
     * wonderful integer.
     *
     * The tests are generated in such a way that kth smallest wonderful integer exists.
     *
     * Input: num = "5489355142", k = 4
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= num.length <= 1000
     * 1 <= k <= 1000
     * num only consists of digits.
     * @param num
     * @param k
     * @return
     */
    // time = O(n^2), space = O(n)
    public int getMinSwaps(String num, int k) {
        // corner case
        if (num == null || num.length() == 0) return 0;

        char[] chars = num.toCharArray();
        char[] arr = num.toCharArray();
        for (int i = 0; i < k; i++) {
            nextPermutation(chars); // O(n)
        }

        int res = 0;
        for (int i = 0; i < arr.length; i++) { // O(n)
            int count = 0;
            for (int j = 0; j < chars.length; j++) { // O(n)
                if (chars[j] == '#') continue;
                if (chars[j] == arr[i]) {
                    chars[j] = '#';
                    break;
                }
                count++;
            }
            res += count;
        }
        return res;
    }

    private void nextPermutation(char[] chars) {
        int i = chars.length - 1;
        while (i >= 1 && chars[i] <= chars[i - 1]) i--;
        if (i == 0) {
            reverse(chars, 0);
            return;
        }

        // case 2
        i--;
        int j = chars.length - 1;
        while (chars[j] <= chars[i] && j > i) j--;
        swap(chars, i, j);
        reverse(chars, i + 1);
        return;
    }

    private void swap(char[] chars, int i , int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    private void reverse(char[] chars, int start) {
        int i = start, j = chars.length - 1;
        while (i < j) {
            swap(chars, i++, j--);
        }
    }
}
/**
 * O(kn) -> next permutation
 * old -> new => adjacent swap
 * 本质是求逆序对
 * 原题：35241 -> 12345  最少需要多少次swap
 * greedy  1 挪到最前面 -> 做4次swap
 * 2要挪到3和5的前面 -> 挪2次
 * 3不需要被主动swap，只需要被动swap
 * 4的前面只要跟5 swap -> 1次
 * 5的前面没有比它大的，自然就位了
 * => 4 + 2 + 1 = 7 次
 * 本质上就是求多少个逆序对
 * {i, j} i < j && nums[i] > nums[j]
 * 做法：固定j，然后看前面有几个nums[i] > nums[j]，一加就可以了
 *
 * 这里是要把一个字符串变成另一个字符串
 * bdac -> cadb
 * 4321    1234
 * 做成一个简单的映射就可以了，所以第二部分就是求逆序对的过程 => LC493
 * 有相同的数字 => 相同的元素不需要做swap
 * 定义逆序对的时候要be careful
 * O(nlogn) -> O(n^2)
 * old: abcd
 * new: bcda
 * abcd -> a#cd -> a##d -> a### -> ####
 * + 1     + 1    + 1      + 0     冒泡
 */

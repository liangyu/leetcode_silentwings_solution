package LC901_1200;
import java.util.*;
public class LC907_SumofSubarrayMinimums {
    /**
     * Given an array of integers arr, find the sum of min(b), where b ranges over every (contiguous) subarray of arr.
     * Since the answer may be large, return the answer modulo 10^9 + 7.
     *
     * Input: arr = [3,1,2,4]
     * Output: 17
     *
     * Constraints:
     *
     * 1 <= arr.length <= 3 * 10^4
     * 1 <= arr[i] <= 3 * 10^4
     * @param arr
     * @return
     */
    // time = O(n), space = O(n)
    public int sumSubarrayMins(int[] arr) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length;
        int[] nextSmaller = new int[n];
        Arrays.fill(nextSmaller, n); // 人为设定一个上界index,挡住向左边不断寻找的smaller
        int[] prevSmaller = new int[n];
        Arrays.fill(prevSmaller, -1); // 人为设定一个下界index，挡住向右边不断寻找的smaller or equal

        Stack<Integer> stack = new Stack<>(); // stack里存的是index
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                nextSmaller[stack.pop()] = i;
            }
            stack.push(i);
        }
        // stack里剩下元素 -> 没有nextSmaller，默认就是n
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) { // smaller or equal
                prevSmaller[stack.pop()] = i;
            }
            stack.push(i);
        }

        long res = 0;
        long M = (long)(1e9 + 7);
        for (int i = 0; i < n; i++) {
            int a = prevSmaller[i];
            int b = nextSmaller[i];
            long num = arr[i] * (i - a) % M * (b - i) % M;
            res = (res + num) % M;
        }
        return (int)res;
    }
}
/**
 * refer: LC1856
 * 所有subArray的最小值都加起来
 * subArray的个数 = C(n, 2) + n = n * (n - 1) / 2 + n = n * (n + 1) / 2
 * 在n个数里取2个数，一个作起点，一个作终点， 不重合的话是C(n, 2)，加上重合变成一个点，总共n个
 * 穷举法 => O(n^2)
 * x 0 [3 2 4 5] 1 x x x
 *    2 * 3 = 6
 *    ret += 2 * 6
 *        += 4 * ?
 *        += 5 * ?
 * 以2为中心，向左向右推，但依然是O(n^2)的解法，但仔细研究下发现，向左拓展到第一个比2小的元素，其实找的第一个小于2的元素
 * 向右找第一个比2小的元素 => prev smaller element next smaller element 单调栈 -> One pass O(n)
 * next smaller element
 * 1 3 5 7  2   => 3, 5, 7 的next smaller element 就是2，踢掉 => 1 2
 * 3 5 7 不是任何元素的next smaller element，栈里永远放的是递增的，一旦遇到一个递减的，这个就是next smaller
 * prev smaller element => 从后往前扫，维护一个单调递增的栈
 *
 * 细节：如果有重复元素怎么办？
 * 2 [8 5 6 7 5 6] 2
 *      ^     ^
 * 处理重复元素怎么办，会算多次 => 做个约定：
 * nums[i]: next smaller element
 *          previous smaller or equal element => 不同的index不会对应同一个subArray
 *          1 3 5 7 5 -> 1 3 5 5 -> 1 3 5
 *
 * 总结：思路转变，不需要找到所有的subArray再找最小值 -> 想的是如果某个元素作为最小值，它的subArray有哪些？有多少个？
 * 取决于向左能推到哪里，向右能推到哪里，这两个边界位置的组合就是subArray的个数 -> 基本解法就是找边界, prevSmaller和nextSmaller
 * 用单调栈的经典用法！！！ O(n)时间找到 prevGreater / nextGreater element，基本上也都是相同的想法，维护一个单调栈，万一出现不单调的元素，
 * 就退栈，没有退栈的元素继续留在里面。
 * 最大难点：遇到重复元素怎么办？这时候就要约定，若干最小值，取哪一个是最小值。左边只能停到比它小或者相等的为止，右边还可以继续往后。
 * 为了避免重复计算，我们需要做额外规定以做区分。比如认为如果有若干个相同的数，则最左边的那个才是最小值。
 * 这样的话，类似[3,4,4,3,4,4,3]这样的subarray，只会在考察第一个3的时候被计入，而在考察其他的3的时候不会被计入。
 * 特别注意：如果一个数没有next smaller element，那么意味着它的左边界是可以到n；
 * 如果一个数没有prev smaller/equal element，那么意味着它的左边界是可以到-1.
 * [
 */

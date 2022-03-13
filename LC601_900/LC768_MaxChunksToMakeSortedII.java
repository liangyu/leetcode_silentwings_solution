package LC601_900;
import java.util.*;
public class LC768_MaxChunksToMakeSortedII {
    /**
     * You are given an integer array arr.
     *
     * We split arr into some number of chunks (i.e., partitions), and individually sort each chunk. After concatenating
     * them, the result should equal the sorted array.
     *
     * Return the largest number of chunks we can make to sort the array.
     *
     * Input: arr = [5,4,3,2,1]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= arr.length <= 2000
     * 0 <= arr[i] <= 10^8
     * @param arr
     * @return
     */
    // S1: prefix sum
    // time = O(nlogn), space = O(n)
    public int maxChunksToSorted(int[] arr) {
        int[] exp = arr.clone();
        Arrays.sort(exp);

        int n = arr.length, count = 0;
        long sum1 = 0, sum2 = 0;
        for (int i = 0; i < n; i++) {
            sum1 += arr[i];
            sum2 += exp[i];
            if (sum1 == sum2) {
                count++;
                sum1 = 0;
                sum2 = 0;
            }
        }
        return count;
    }

    // S2: Monotonic Stack
    // time = O(n), space = O(n)
    public int maxChunksToSorted2(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int curMax = 0;
        for (int x : arr) {
            if (stack.isEmpty() || stack.peek() <= x) {
                stack.push(x);
                curMax = x;
            } else {
                while (!stack.isEmpty() && stack.peek() > x) stack.pop();
                stack.push(curMax);
            }
        }
        return stack.size();
    }

    // S3: leftMax + rightMin
    // time = O(n), space = O(n)
    public int maxChunksToSorted3(int[] arr) {
        int n = arr.length;
        int[] leftMax = new int[n];
        leftMax[0] = arr[0];
        for (int i = 1; i < n; i++) leftMax[i] = Math.max(leftMax[i - 1], arr[i]);

        int count = 0, rightMin = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            if (rightMin >= leftMax[i]) count++;
            rightMin = Math.min(rightMin, arr[i]);
        }
        return count;
    }
}
/**
 * [2,1],[3],[4],[4]
 * [1,2],[3],[4],[4]
 * lack:  1 -> null
 * extra: 2 -> null
 * 维护2个set
 * 缺啥就在多的集合里找一找，多了就去缺的里面找一找
 * time = O(nlogn)
 *
 * [2,1],[3],[4],[4]
 * [1,2],[3],[4],[4]
 *   3
 *   3
 * 为什么sum相等，就认为元素肯定相等？ => sorted
 * 看它的Prefix Sum
 * 2-presum = 3
 * 原来的数组里面，唯一能确定一个2-sum = 3
 * 递增原理
 *
 * S2:
 * ...3, [7,8,4,6,5],9....
 * ...3,[7,8,4]  -> 维护一个单调递增的栈
 * ...3,[7,8,4]，6 -> 6没有8大，6必须要并到前面chunk里面去，6和8去比
 * curMax = 8
 * 与curMax比，6 < 8，说明在6之前已经出现一个比6大的元素，所以6肯定自己不能成为一个chunk，必须和前面并在一块
 * 提示我们因为我们要看最大值，只有最大值是有用的，4，5，6没有必要保留 =>
 * ...3,[7,8] 9
 * 9 > curMax 大， 9加入到栈内
 * 每个chunk只派一个代表放入栈内 => 放最大的8，把7干掉 ->
 * 4进入，7和8去掉，然后再把8加回来
 * 凡是比8小的都属于同一个chunk
 * ...3
 * ...3,7
 * ...3,7,8
 * ...3,7,8,4 => ...3,8  （4把7和8弹走，但是保留8加回来）4也不要，只需要用最大值8来代表这个chunk即可。
 * ...3,8,6 => ...3,8 （6把8弹走，但是保留8加回来）
 * ...3,8,5 => ...3,8 （5把8弹走，但是保留8加回来）
 * ...3,8,9
 *
 * S3:
 * 核心思路：后面的分块的最小值要大于等于前面的分块的最大值
 * 先遍历一遍，记录到当前数为止的最大值head
 * 然后从后往前遍历，如果遍历过的arr数组里的最小值比前一个分块的最大值head要大，块的数量 + 1
 * 遍历时要维护一下遍历过的arr里的最小值
 */
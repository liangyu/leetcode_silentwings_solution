package LC601_900;
import java.util.*;
public class LC739_DailyTemperatures {
    /**
     * Given an array of integers temperatures represents the daily temperatures, return an array answer such that
     * answer[i] is the number of days you have to wait after the ith day to get a warmer temperature.
     * If there is no future day for which this is possible, keep answer[i] == 0 instead.
     *
     * Input: temperatures = [73,74,75,71,69,72,76,73]
     * Output: [1,1,4,2,1,1,0,0]
     *
     * Constraints:
     *
     * 1 <= temperatures.length <= 10^5
     * 30 <= temperatures[i] <= 100
     * @param temperatures
     * @return
     */
    // S1: stack
    // time = O(n), space = O(n)
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                res[stack.peek()] = i - stack.pop();
            }
            stack.push(i);
        }
        return res;
    }

    // S2: greedy
    // time = O(n), space = O(1)
    public int[] dailyTemperatures2(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];

        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;
            // 注意：必须判断res[j] != 0 否则while会陷入一个死循环
            // res[j]就指向大于temperatures[j]的下一个元素的index，所以j += res[j]不会越界
            // 目的就是为了寻找在i右边大于i的元素，既然j比i要小，那么可以直接跳到刚好比j大的元素上继续向右找
            // 如果跳出while loop依然没有得到比i大的j，那么必然res[i] = 0
            while (res[j] != 0 && temperatures[j] <= temperatures[i]) j += res[j];
            if (temperatures[j] > temperatures[i]) res[i] = j - i;
        }
        return res;
    }
}
/**
 * 此题可以非常类似于 maximum histgram，维护一个递减序列的栈，并且存放这的是index而不是数值。
 * 此题可以从后往前遍历数组。此题转化为：对于任意的nums[i]，查找早于它处理的最近一个大于它的数的位置。
 * 构造一个栈，维护一个递减数列：这样如果进入一个新数nums[i]小于栈顶元素，那么它的最近的大于nums[i]的数就是这个栈顶元素。
 * 如果这个新数nums[i]大于栈顶元素，就不断退栈直至遇到比它大的为止，那么当前这个栈顶元素也就是最近的一个比nums[i]大的数。
 * 因为存储的是index，所以很容易得到两个数在位置上的距离之差
 * S2: greedy
 * 此题可以从后往前考虑．对于results[i]，我们考察其右边的那个元素，即results[j],where j=i+1.
 * 当temp[j]<=temp[i],我们将指针跳转j+=results[j]，这样就可以加快搜索的效率．
 * 这样直到找到一个temp[j]>temp[i]即为找到结果；或者直到发现results[j]==0时说明再也找不下去了．
 */
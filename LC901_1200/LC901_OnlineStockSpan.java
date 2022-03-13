package LC901_1200;
import java.util.*;
public class LC901_OnlineStockSpan {
    /**
     * Design an algorithm that collects daily price quotes for some stock and returns the span of that stock's price
     * for the current day.
     *
     * The span of the stock's price today is defined as the maximum number of consecutive days (starting from today and
     * going backward) for which the stock price was less than or equal to today's price.
     *
     * For example, if the price of a stock over the next 7 days were [100,80,60,70,60,75,85], then the stock spans
     * would be [1,1,1,2,1,4,6].
     * Implement the StockSpanner class:
     *
     * StockSpanner() Initializes the object of the class.
     * int next(int price) Returns the span of the stock's price given that today's price is price.
     *
     * Input
     * ["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
     * [[], [100], [80], [60], [70], [60], [75], [85]]
     * Output
     * [null, 1, 1, 1, 2, 1, 4, 6]
     *
     * Constraints:
     *
     * 1 <= price <= 10^5
     * At most 10^4 calls will be made to next.
     */
    // S1: one stack
    Stack<Integer> stack;
    List<Integer> list;
    int i;
    public LC901_OnlineStockSpan() {
        stack = new Stack<>();
        list = new ArrayList<>();
        i = 0;
    }
    // time = O(n), space = O(n)
    public int next(int price) {
        int res = 0;
        list.add(price);

        while (!stack.isEmpty() && list.get(stack.peek()) <= list.get(i)) stack.pop(); // 维护一个严格单调递减栈
        // 注意：退栈退到空，意味着此前所有栈内元素都比当前的元素小，所以答案一共是i+1个元素;否则就是 i-stack.peek()个元素。
        res = stack.isEmpty() ? i - (-1) : i - stack.peek();
        stack.push(i);
        i++; // don't forget to make i++
        return res;
    }
}
/**
 * 单调递增栈
 * 5 4 [3 2 1] 3 x x
 * 往前推推推 -> 单调栈
 * 具体怎么选？无非就单调增/单调减
 * 假设手头元素都是单调的，看看答案是什么,必须要保证能有退栈机制，剩下保持单调增，比如下面3跨不过5
 * 1 2 3 4 5 3 2 4 (X)
 * 5 4 [3 2 1] 3 4
 */

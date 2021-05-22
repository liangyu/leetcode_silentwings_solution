package LC601_900;
import java.util.*;
public class LC895_MaximumFrequencyStack {
    /**
     *
     Implement FreqStack, a class which simulates the operation of a stack-like data structure.

     FreqStack has two functions:

     push(int x), which pushes an integer x onto the stack.
     pop(), which removes and returns the most frequent element in the stack.
     If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.

     Input:
     ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
     [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
     Output: [null,null,null,null,null,null,null,5,7,5,4]

     Note:

     Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
     It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
     The total number of FreqStack.push calls will not exceed 10000 in a single test case.
     The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
     The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.
     */
    // time = O(1), space = O(n)
    HashMap<Integer, Integer> freq = new HashMap<>();
    HashMap<Integer, Stack<Integer>> group;
    int maxfreq;
    public LC895_MaximumFrequencyStack() {
        freq = new HashMap<>();
        group = new HashMap<>();
        maxfreq = 0;
    }

    public void push(int x) {
        freq.put(x, freq.getOrDefault(x, 0) + 1);
        if (freq.get(x) > maxfreq) maxfreq = freq.get(x);
        group.putIfAbsent(freq.get(x), new Stack<>());
        group.get(freq.get(x)).push(x);
    }

    public int pop() {
        int top = group.get(maxfreq).pop();
        freq.put(top, freq.get(top) - 1);
        // 只要maxfreq对应的stack为空，那么maxfreq一定就-1，因为个数是1个1个减的
        //  [[5],[7],[5],[7],[4],[5]] -> 555 77 4 -> 55 77 4 -> 5 77 4 -> 5 7 4
        if (group.get(maxfreq).size() == 0) maxfreq--;
        return top;
    }
}

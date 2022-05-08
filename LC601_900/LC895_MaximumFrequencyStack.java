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
    HashMap<Integer, List<Integer>> map;
    HashMap<Integer, Integer> count;
    int maxFreq = 0;
    public LC895_MaximumFrequencyStack() {
        map = new HashMap<>();
        count = new HashMap<>();
    }

    public void push(int val) {
        int freq = count.getOrDefault(val, 0);
        map.putIfAbsent(freq + 1, new ArrayList<>());
        map.get(freq + 1).add(val);
        count.put(val, count.getOrDefault(val, 0) + 1);
        maxFreq = Math.max(maxFreq, freq + 1);
    }

    public int pop() {
        int n = map.get(maxFreq).size();
        int x = map.get(maxFreq).get(n - 1);
        map.get(maxFreq).remove(n - 1);
        if (map.get(maxFreq).size() == 0) maxFreq--;
        count.put(x, count.get(x) - 1);
        return x;
    }
}

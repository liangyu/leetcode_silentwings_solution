package LC301_600;
import practice.NestedInteger;

import java.util.*;
public class LC385_MiniParser {
    /**
     * Given a string s represents the serialization of a nested list, implement a parser to deserialize it and return
     * the deserialized NestedInteger.
     *
     * Each element is either an integer or a list whose elements may also be integers or other lists.
     *
     * Input: s = "324"
     * Output: 324
     *
     * Input: s = "[123,[456,[789]]]"
     * Output: [123,[456,[789]]]
     *
     * Constraints:
     *
     * 1 <= s.length <= 5 * 10^4
     * s consists of digits, square brackets "[]", negative sign '-', and commas ','.
     * s is the serialization of valid NestedInteger.
     * All the values in the input are in the range [-10^6, 10^6].
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public NestedInteger deserialize(String s) {
        if (s.charAt(0) != '[') return new NestedInteger(Integer.parseInt(s));

        Stack<NestedInteger> stack = new Stack<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '[') stack.push(new NestedInteger());
            else {
                int j = i;
                while (i < n && s.charAt(i) != ',' && s.charAt(i) != ']') i++;
                if (i > j) {
                    int num = Integer.parseInt(s.substring(j, i));
                    stack.peek().add(new NestedInteger(num));
                }

                if (s.charAt(i) == ']' && stack.size() > 1) {
                    NestedInteger t = stack.pop();
                    stack.peek().add(t);
                }
            }
        }
        return stack.peek();
    }
}
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
/**
 * 本质判断有无中括号
 */
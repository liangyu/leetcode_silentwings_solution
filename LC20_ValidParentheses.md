# LC20 Valid Parentheses
标签（空格分隔）： LeetCode Java Stack

---
    /**
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
     * determine if the input string is valid.
     *
     * An input string is valid if:
     *
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     *
     * Input: s = "([)]"
     * Output: false
     *
     * Input: s = "{[]}"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 104
     * s consists of parentheses only '()[]{}'.
     *
     * @param s
     * @return
     */
     
【难点误区】

出错的地方主要有2个：

1. if 左右括号无法一一配对的话，一定要记得用else return false!!!
2. 最后出loop，不是一定return true或者false，而是要check stack是否为空！

【解题思路】

括号匹配问题，常规解法 -> stack，左括号入栈，右括号看匹配，如果匹配则弹栈，否则就不是valid。


```java
// time = O(n), space = O(n)
public boolean isValid(String s) {
    // corner case
    if (s == null || s.length() == 0) return false;

    Stack<Character> stack = new Stack<>();

    for (char c : s.toCharArray()) {
        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.peek();
            if (c == ')' && top == '(' || c == ']' && top == '[' || c == '}' && top == '{') {
                stack.pop();
            } else return false; // 别忘了不符合上述条件，也要立即return false!!!
        }
    }
    return stack.isEmpty(); // 注意最后要check stack是否为空！！！
}
```


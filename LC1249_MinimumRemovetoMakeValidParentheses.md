# LC1249 Minimum Remove to Make Valid Parentheses
标签（空格分隔）： LeetCode Java Stack

---
    /**
     * Given a string s of '(' , ')' and lowercase English characters.
     *
     * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting
     * parentheses string is valid and return any valid string.
     *
     * Formally, a parentheses string is valid if and only if:
     *
     * It is the empty string, contains only lowercase characters, or
     * It can be written as AB (A concatenated with B), where A and B are valid strings, or
     * It can be written as (A), where A is a valid string.
     *
     * Input: s = "lee(t(c)o)de)"
     * Output: "lee(t(c)o)de"
     *
     * Input: s = "a)b(c)d"
     * Output: "ab(c)d"
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is one of  '(' , ')' and lowercase English letters.
     *
     * @param s
     * @return
     */

【难点误区】

本题的思路很容易想到用stack，难点在于除去左右括号在stack上的处理，如何在遍历的过程中去记录下要被删除的元素index。在这里的关键一点就是使用一个HashSet去存取，凡是无法匹配到的左括号和右括号的index都被丢入，由于index是unique，同时HashSet可以提供O(1)的查询，所以就会比较方便的在第二次loop时查询并遗弃。

【解题思路】

本题依然延续传统思路，遇到括号匹配问题，则通用的做法就是使用stack去处理。基本做法也是如此，遇到左括号则压栈，右括号来了后看当前stack里有无对应的左括号，有的话就是合法匹配，直接弹栈左括号，没有的话，那么这个右括号肯定就是多余要被删除的，那么可以将当前index存入被删除的数据结构里。由于后面考虑到要O(1)时间查询当前访问元素是否要被删除，所以我们可以使用一个HashSet来存放要被删除的所有元素的index，因为肯定是unique的。遍历完之后，如果栈里还剩下左括号，那么也是多余要被删掉的，同样也倒入到HashSet之中。最后再从头遍历一遍，把所有不在HashSet里的元素加入StringBuilder即可。


```java
// time = O(n), space = O(n）
public String minRemoveToMakeValid(String s) {
    // corner case
    if (s == null || s.length() == 0) return "";

    Stack<Integer> stack = new Stack<>(); // 存放左括号来和右括号去配对check，一旦出现则互相抵消，剩下的就是要被删除的
    HashSet<Integer> set = new HashSet<>(); // 存放所有要被删除的元素index

    for (int i = 0; i < s.length();i++) { // O(n)
        char c = s.charAt(i);
        if (c == '(') stack.push(i); // 遇到左括号依然按照传统套路去压栈
        else if (c == ')') {
            if (stack.isEmpty()) set.add(i); // 无对应左括号，则右括号要放入要被删除元素的set里
            else stack.pop(); // 遇到右括号如果有对应的左括号，则是合法成对，右括号可无视，左括号出栈
        }
    }

    // stack里剩下的左括号都是没有对应右括号匹配的，因此也要加入到被删除的set里
    while (!stack.isEmpty()) set.add(stack.pop());
    StringBuilder sb = new StringBuilder();
    for (int i = 0 ; i < s.length(); i++) { // O(n)
        // 除去set里都是要被删除的元素，其他可以无脑加入StringBuilder出答案
        if (!set.contains(i)) sb.append(s.charAt(i));
    }
    return sb.toString();
}
```


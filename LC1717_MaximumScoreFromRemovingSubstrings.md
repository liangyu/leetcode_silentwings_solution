# L1717 Maximum Score From Removing Substrings
标签（空格分隔）： LeetCode Java Greedy Stack

---

    /**
     * You are given a string s and two integers x and y. You can perform two types of operations any number of times.
     *
     * Remove substring "ab" and gain x points.
     * For example, when removing "ab" from "cabxbae" it becomes "cxbae".
     * Remove substring "ba" and gain y points.
     * For example, when removing "ba" from "cabxbae" it becomes "cabxe".
     * Return the maximum points you can gain after applying the above operations on s.
     *
     * Input: s = "cdbcbbaaabab", x = 4, y = 5
     * Output: 19
     *
     * Input: s = "aabbaaxybbaabb", x = 5, y = 4
     * Output: 20
     *
     * Constraints:
     *
     * 1 <= s.length <= 105
     * 1 <= x, y <= 104
     * s consists of lowercase English letters.
     *
     * @param s
     * @param x
     * @param y
     * @return
     */

【难点误区】

本题最大的难点在于想到使用贪心策略，即永远都是最先优先删除x, y中所代表的较大的那个combo组合，即如果x > y，那么先尽量删除ab组合，否则就是优先删除ba组合，这样的话无论是用StringBuilder还是用stack去做，都是two pass。

S1: StringBuilder的做法，要特别注意pass 1结束后记得一定要更新删除较大combo后的string，而不能使用原来的input string。

S2: stack的做法，则需要使用2个stack，在第一个pass做完后，第二个pass则是从第一个stack的栈顶pop出发开始逐个反向check是否存在较小的combo组合，具体做法同pass 1一致，就是先从栈顶pop出一个元素(second)，然后去check当前目标栈的栈顶(对于pass 1来说，目标栈是stack1, 对于pass 2来说是stack2)元素，即first，然后通过check"first+second"组成的combo是否符合当前要求的max or min组合来决定是否可以删除。删除的做法就是直接目标栈顶pop出first，而second由于是暂时存在当前一个临时变量中，可以直接无视，因为该变量在loop遍历到下一个元素时即会被覆盖，也就意味着不会存入目标栈，等同于完成了删除操作。

【解题思路】

x ababbabaa y ababab x -> 删到最后肯定只有要么都是a，要么都是b

aaaaaaaaa / bbbbbbbb
完全取决于a的个数与b的个数之差 -> m个a，n个b -> m - n -> 删除k次是一定的 -> 优先删除值较大的那个组合 -> bbbaaaaa -> aa

朴素的贪心思想

2 pass -> 第一遍删ab，第二遍删ba -> 简单粗暴一些

```java     
// S1: StringBuilder
// time = O(n), space = o(n)
public int maximumGain(String s, int x, int y) {
    // corner case
    if (s == null || s.length() == 0) return 0;

    int res = 0;

    if (x < y) { // 从左到右优先删ba -> 从右向左优先删ab == reverse后还是从左到右删ab,但是记得x与y互换保证x > y
        s = reverse(s);
        return maximumGain(s, y, x);
    }

    // Step 1: delete all "ab"
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
        sb.append(c);
        // check当前StringBuilder最后2位是否为"ab"
        while (sb.length() >= 2 && sb.substring(sb.length() - 2).equals("ab")) {
            sb.setLength(sb.length() - 2);
            res += x;
        }
    }

    s = sb.toString();
    // Step 2: delete all "ba"
    sb = new StringBuilder();
    for (char c : s.toCharArray()) {
        sb.append(c);
        // check当前StringBuilder最后2位是否为"ab"
        while (sb.length() >= 2 && sb.substring(sb.length() - 2).equals("ba")) {
            sb.setLength(sb.length() - 2);
            res += y;
        }
    }
    return res;
}

private String reverse(String s) {
    StringBuilder sb = new StringBuilder(s);
    return sb.reverse().toString();
}
```

```java
// S2: Two Stacks (prefer!!!)
// time = O(n), space = O(n)
public int maximumGain2(String s, int x, int y) {
    // corner case
    if (s == null || s.length() == 0) return 0;

    Stack<Character> stack1 = new Stack<>();
    Stack<Character> stack2 = new Stack<>();
    int res = 0;
    int max = Math.max(x, y), min = Math.min(x, y);
    char first = x > y ? 'a' : 'b', second = x > y ? 'b' : 'a'; // if x > y -> "ab"; y > x -> "ba"
	
	// pass 1: delete max combo
    for (char c : s.toCharArray()) {
        if (!stack1.isEmpty() && stack1.peek() == first && c == second) {
            stack1.pop();
            res += max;
        } else stack1.push(c);
    }
	
	// pass 2: delete min combo
    while (!stack1.isEmpty()) {
        char c = stack1.pop();
        if (!stack2.isEmpty() && stack2.peek() == first && c == second) {
            stack2.pop();
            res += min;
        } else stack2.push(c);
    }
    return res;
}
```

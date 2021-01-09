# LC76 Minimum Window Substring
标签（空格分隔）： LeetCode HashMap TwoPointers SlidingWindow

---
    /**
     * Given two strings s and t, return the minimum window in s which will contain all the characters in t. If there
     * is no such window in s that covers all characters in t, return the empty string "".
     *
     * Note that If there is such a window, it is guaranteed that there will always be only one unique minimum window
     * in s.
     *
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC"
     *
     * Constraints:
     *
     * 1 <= s.length, t.length <= 105
     * s and t consist of English letters.
     *
     * @param s
     * @param t
     * @return
     */


【难点误区】

本题的难点在于：

1. 如何使用双指针去定义一个sliding window，整个window如何滑动，以及在符合题目要求找到最小window的前提下不断扩张右边缘 i，同时因为最后结果要输出具体的window，所以必须要保留minimum window substring的起点，以及最后得出的长度就可以拿到最小的窗口具体是什么样的。
2. 不管当前字符是否出现在target string t 当中，但是依然可以被统计在dict[ ] 之中，只不过最终记录出的是一个经过计算后变成负数的无效答案，因此统计target string里的每个字符是否都被cover住的有效长度total也就不需要做任何改变。
3. 当出现符合条件的string出现后，就可以逐步缩小左边缘，导致一部分先前访问涉及到的char会重新离开统计的window范畴，回到原先的dict[ ]里，一旦数值恢复后依然 > 0，则表示该回到dict[ ]里的字符应该在 t 中出现过，这个时候total++，否则对于 t 中不存在的字符，即使还原在dict[ ]里，最后的值也不会 > 1，而只能是 == 0。

想清楚这些，在下面的解题中就可以顺利的使用双指针的做法将该问题得到解决，最后记得要check min，如果依然等于初始值而在过去的运行中没有发现符合条件的window存在，则返回空string，否则就是返回知道了起点start和终点start + min - 1的一个substring。




【解题思路】

根据从LC3 Longest Substring Without Repeating Characters获得对于使用two pointers来解决一类sliding window的解法，我们同样可以在这里使用HashMap或者int[128]来处理这个问题。由于要输出最终的string而不是最小长度，我们必须记录下出现最小长度时的起点start，这样的话最后走完全程track完毕之后，可以通过起点start + min来获得minimum string的终点。因此，这里必须要再用一个指针 j 来配合指针 i 来一起表示sliding window的左右端点。

首先将target string里的字符全部统计存入int[128]之中，作为一本字典dict来check接下来在string s中窗口内字符的有效性。然后从左往右依次访问s里的每个字符，并在对应的int[ ]里进行 - 1操作。这个时候，我们发现对于int[ ] 来讲，计算是并没有出现在之前的string t 里，但是同样做--操作导致变成负数的话，也不影响结果，只不过通过if来判断，如果 < 0的话就不要进行total--，即不计入有效考虑的范围之内。这样一路下去扩张window右边缘 i，直到所有有效的t里的字符都被涉及统计到了，这个时候就代表当前window里应该包含有t里所有的字符。于是计算当前window长度 i - j + 1，其中 i 是右端点，j 是左端点，这个时候如果比之前统计出的最小值min还要小，则需要更新：

1. min = i - j + 1
2. start = j

然后继续移动左端点 j，然后每移出窗口一格，该格对应的字符就可以从dict[ ]里加回去，total也同步++直至total != 0，即在窗口左端点移动的时候，窗口中的字符又出现不满足所有 t 中字符出现在窗口范围内的条件，然后才可以跳出total == 0下的loop，继续扩张右端点 i直至走完全程的string s。

最后返回输出最终结果的min之前，首先要记得判断min是否有过更新，有的话就可以放心的return 一个知道起点和长度的substring，否则的话则表示没有找到这样一个window，于是返回空string。




```java     
// S1: int[128] + Sliding Window
// time = O(n), space = O(1)
public String minWindow(String s, String t) {
    // corner case
    if (s == null || s.length() == 0 || t == null || t.length() == 0) return "";

    int[] dict = new int[128]; // ASCII
    // 记录target string进入dict
    for (int i = 0; i < t.length(); i++) dict[t.charAt(i)]++; 

    int start = 0, total = t.length();
    int min = Integer.MAX_VALUE;
    int j = 0;

    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        // 注意这里无论如何，dict[c]都需要--而变化，哪怕if条件不满足导致total不变！！！
        if (dict[c]-- > 0) total--; 
        while (total == 0) {
            if (i - j + 1 < min) {
                min = i - j + 1;
                start = j;
            }
            // 访问过的char由于左端窗口收缩，又都在收缩的同时在dict里加了回来
            if (++dict[s.charAt(j++)] > 0) total++;
        }
    }
    return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);
}

```

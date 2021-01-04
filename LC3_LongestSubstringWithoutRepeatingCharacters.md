# LC3 Longest Substring Without Repeating Characters

标签（空格分隔）： LeetCode HashMap TwoPointers SlidingWindow

---
    /**
     * Given a string s, find the length of the longest substring without repeating characters.
     *
     * Input: s = "abcabcbb"
     * Output: 3
     *
     * Constraints:
     *
     * 0 <= s.length <= 5 * 104
     * s consists of English letters, digits, symbols and spaces.
     *
     * @param s
     * @return
     */

【难点误区】

这道题想到用brute-force两个for loop去做并不难，难点主要在于下面2个：

1. 想到通过利用HashMap去mainain一个sliding window，边走边记录，同时在窗口进来一个重复char时，更新左边缘start，直到重新满足条件再继续向右扩张，这就是传统sliding window的基本思路。
2. 一定要注意更新左端点的条件是当start < map.get(c)，即当前重复元素在之前窗口中记录的位置之前，否则如果start已经在这之后的话，那这个重复元素根本不在当前的sliding window中，自然也就不需要更新start了，否则会导致start后退，窗口逆向扩大，导致出错！所以在左端点移动前，一定要记得判断左端点start的位置与上一个当前元素位置之间的关系！！！


【解题思路】

这道题是sliding window的一个典型应用。凡是涉及到sliding window，一般有两种做法，一种是比较general的方法，采用HashMap，而另一种则是如果题目只是涉及char或者string的话，则可以考虑用一个int[ ]来做。关于int[ ]，可以分为下面三种去根据题目具体选用：

* int[26] for Letters 'a' - 'z' or 'A' - 'Z'
* int[128] for ASCII
* int[256] for Extended ASCII

本题在这里根据contraints，由于s只包括英文字母，数字，符号以及空格，可以在这里只选择int[128]来表示ASCII，如果还涉及到其他语言文字里的字符，则需要用Extended ASCII，即使用int[256]来表示。

无论使用哪种方法，sliding window的院里都是固定住window一头的start，然后不断扩张右边缘，当出现违背窗口继续向右扩张的条件时，开始收缩左边缘，即变动start直到重新符合条件的window出现，这时再继续移动右边缘扩张即可。在这里，我们首先设置窗口左边缘start = 0，然后不断移动右边缘，主要通过 i 来实现。在这里符合继续扩张的条件是窗口内的字符都要互不相同，也就是说通过查重操作来判定窗口内的字符没有重复。要查重，就有HashMap和array两种形式，分别对应于下面的S1和S2。其中S1通过HashMap来查重，一旦出现重复，需要实现2步：

1. 如果start出现在最近重复字符在map里记录的位置之前，则更新左边缘start至出现重复字符的最近位置处 + 1
2. 更新重复元素在map里的index为当前的 i

这样的话保证从现在更新后的start与当前 i 之间的所有元素又都是distinct的了。这里尤其要注意，当且仅当start在当前重复元素在map里上次记录的最近index之前，才需要更新start，否则start早已在这之后，更新后反而start出现了向前移动的“倒退”，显然是有问题的。

这样每次移动一步，并进行对应操作之后，同时同步更新最大窗口长度max的值，当窗口最后移动到底的时候，即可根据一路上同步track的max得出最大长度。

* 时间复杂度：O(n)，从左到右每个元素最多进出window一次，所以是O(n)
* 空间复杂度：HashMap解法是O(n)，int[128]是O(1)，都是各自开辟辅助空间的消耗



```java     
// S1: HashMap + Sliding Window
// time = O(n), space = O(n)
public int lengthOfLongestSubstring(String s) {
    // corner case
    if (s == null || s.length() == 0) return 0;

    HashMap<Character, Integer> map = new HashMap<>();
    int max = 0, start = 0;

    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (map.containsKey(c)) {
            if (map.get(c) >= start) start = map.get(c) + 1;
        }
        map.put(c, i);
        max = Math.max(max, i - start + 1);
    }
    return max;
}
```
    
```java  
// S2: int[128] 最优解！！！
// time = O(n), space = O(1)
public int lengthOfLongestSubstring(String s) {
    // corner case
    if (s == null || s.length() == 0) return 0;

    int[] index = new int[128]; // ASCII
    Arrays.fill(index, -1); // O(1), 初始化为一个invalid的值
    int max = 0, start = 0;

    for (int i = 0; i < s.length(); i++) { // O(n)
        char c = s.charAt(i);
        if (index[c] != -1) { // 如果invalid，表明之前没遇到和记录过该char
            if (index[c] >= start) start = index[c] + 1;
        }
        index[c] = i;
        max = Math.max(max, i - start + 1);
    }
    return max;
}
```

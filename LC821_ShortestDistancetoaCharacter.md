# LC821 Shortest Distance to a Character

标签（空格分隔）： LeetCode Java TreeSet

---
    /**
     * Given a string s and a character c that occurs in s, return an array of integers answer where
     * answer.length == s.length and answer[i] is the shortest distance from s[i] to the character c in s.
     *
     * Input: s = "loveleetcode", c = "e"
     * Output: [3,2,1,0,1,0,0,1,2,2,1,0]
     *
     * Input: s = "aaab", c = "b"
     * Output: [3,2,1,0]
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^4
     * s[i] and c are lowercase English letters.
     * c occurs at least once in s.
     *
     * @param s
     * @param c
     * @return
     */

【解题思路】


When going left to right, we'll remember the index prev of the last character C we've seen. Then the answer is i - prev.

When going right to left, we'll remember the index prev of the last character C we've seen. Then the answer is prev - i.

We take the minimum of these two answers to create our final answer.


```java     
// S1: Min Array
// time = O(n), space = O(1)
public int[] shortestToChar(String s, char c) {
    // corner case
    if (s == null || s.length() == 0) return new int[0];

    int[] res = new int[s.length()];
    int prev = Integer.MIN_VALUE / 2; // 这里不能取Integer.MIN_VALUE,否则下面i - prev会出界！！！

    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == c) prev = i;
        res[i] = i - prev;
    }

    prev = Integer.MAX_VALUE / 2; // 这里prev = Integer.MAX_VALUE也可以，因为prev - i是一定不会出界的！
    for (int i = s.length() - 1; i >= 0; i--) {
        if (s.charAt(i) == c) prev = i;
        res[i] = Math.min(res[i], prev - i);
    }
    return res;
}
```
```java
// S2: TreeSet
// time = O(nlogn), space = O(n)
public int[] shortestToChar2(String s, char c) {
    // corner case
    if (s == null || s.length() == 0) return new int[0];

    TreeSet<Integer> treeSet = new TreeSet<>();
    for (int i = 0; i < s.length(); i++) { // O(n)
        if (s.charAt(i) == c) treeSet.add(i); // O(logn)
    }

    int[] res = new int[s.length()];
    for (int i = 0; i < s.length(); i++) {
        Integer fk = treeSet.floor(i);
        Integer ck = treeSet.ceiling(i);
        if (fk == null) res[i] = ck - i;
        else if (ck == null) res[i] = i - fk;
        else res[i]= Math.min(i - fk, ck - i);
    }
    return res;
}
```

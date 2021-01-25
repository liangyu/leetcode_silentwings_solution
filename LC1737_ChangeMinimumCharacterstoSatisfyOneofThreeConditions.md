# LC1737 Change Minimum Characters to Satisfy One of Three Conditions

标签（空格分隔）： LeetCode Java Greedy

---
    /**
     * You are given two strings a and b that consist of lowercase letters. In one operation, you can change any
     * character in a or b to any lowercase letter.
     *
     * Your goal is to satisfy one of the following three conditions:
     *
     * Every letter in a is strictly less than every letter in b in the alphabet.
     * Every letter in b is strictly less than every letter in a in the alphabet.
     * Both a and b consist of only one distinct letter.
     *
     * Return the minimum number of operations needed to achieve your goal.
     *
     * Input: a = "aba", b = "caa"
     * Output: 2
     *
     * Input: a = "dabadd", b = "cda"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= a.length, b.length <= 105
     * a and b consist only of lowercase letters.
     *
     * @param a
     * @param b
     * @return
     */

【难点误区】

主要难点在于以下2点：

1. 想不到通过暴力枚举26个小写字母来一个个试每一个字母作为选定代表，找出比它大，比它小以及和它不同的字符数目，并从中找出最小值的方法来处理3种不同condition的例子。
2. 先给两个string做词频统计，可以大大优化时间复杂度
3. 最大的隐藏坑：针对condition 1和condition 2，如果两个string中有一个为"a"的话，你是搞不出一个所有字符比"a"都小的string的。也就是说，我们在暴力枚举26个字母来作为天选之子时，"a"是不work的，也就是说遍历的i智能从1开始，而不能从0开始。而condition 3由于是挑选distinct的字符(注意题目这里的distinct意思是要让a和b里最终的字符都是相同的同一个，而不是只要a里都相同，b里都相同，而a, b里的字符可以不同的意思，这里就是要让a和b里的字符都全部相同，也就是最后只有同一个字符的意思)，所以在这里是可以最终都变作"a"的。

【解题思路】

```
/**
 * 暴力枚举分界线 -> 效率也不低，总共才26个字符
 * 大方向：枚举，26遍，扫一遍就可以知道borderline，扫一遍就行了
 * 26 * 10^5
 * 提前做一下词频统计，a有几个，b有几个，做一下预处理
 */
```


```java     
// time = O(m + n), space = O(1)
public int minCharacters(String a, String b) {
    int[] countA = new int[26];
    int[] countB = new int[26];

    for (char c : a.toCharArray()) {
        countA[c - 'a']++;
    }
    for (char c : b.toCharArray()) {
        countB[c - 'a']++;
    }

    int res = Integer.MAX_VALUE;
    for (int i = 0; i < 26; i++) { // O(1)
        // 大坑：对于condition 1和2来说，这里的threshold i 不能是a，也就是不能为0，你无法构造一个比a更小的字符串！！！
        // 所以只能从b开始，也就是i只能从1开始
        if (i > 0) {
            // condition 1: a < b
            int change = 0;
            for (int j = i; j < 26; j++) {
                change += countA[j];
            }
            for (int j = 0; j < i; j++) {
                change += countB[j];
            }
            res = Math.min(res, change);

            // condition 2: b < a
            change = 0;
            for (int j = i; j < 26; j++) {
                change += countB[j];
            }
            for (int j = 0; j < i; j++) {
                change += countA[j];
            }
            res = Math.min(res, change);
        }

        // condition 3: a and b distinct
        int change = 0;
        for (int j = 0; j < 26; j++) { // O(1)
            if (j != i) {
                change += countA[j];
                change += countB[j];
            }
        }
        res = Math.min(res, change);
    }
    return res;
}
```

# LC1641 Count Sorted Vowel Strings
标签（空格分隔）： LeetCode Java DP Math

---
    /**
     * Given an integer n, return the number of strings of length n that consist only of vowels (a, e, i, o, u)
     * and are lexicographically sorted.
     *
     * A string s is lexicographically sorted if for all valid i, s[i] is the same as or comes before s[i+1]
     * in the alphabet.
     *
     * Input: n = 1
     * Output: 5
     * Explanation: The 5 sorted strings that consist of vowels only are ["a","e","i","o","u"].
     *
     * Input: n = 2
     * Output: 15
     * Explanation: The 15 sorted strings that consist of vowels only are
     * ["aa","ae","ai","ao","au","ee","ei","eo","eu","ii","io","iu","oo","ou","uu"].
     * Note that "ea" is not a valid string since 'e' comes after 'a' in the alphabet.
     *
     * Input: n = 33
     * Output: 66045
     *
     * Constraints:
     * 1 <= n <= 50
     * @param n
     * @return


【难点误区】

本题两种做法都很有难度：DP和数学解，要求掌握DP的方法。

DP的基本思想就是2维，第一个维度决定index (0 ~ n)，第二维度决定在当前index上放aeiou的哪一个。根据dp的基本思路，看最后一步，dp[i][k]代表在index = i的位置上放置了aeiou里的第k个(k = 0 ~ 4)，那么返回看最后一步，它是由上一步dp[i - 1][j]过来的，其中的限制条件就是 j <= k。于是状态转移方程：dp[i][k] = sum(dp[i - 1][j]) (j <= k)，即把所有上一步的可能性都列举并加和，就能cover住所有从上一步来到这最后一步的情况。

由于涉及到i - 1，所以要初始化 i == 0的情况，即在第一个位置上放置aeiou的情况，每种都是唯一的，即只能放1个a，1个e，1个i，1个o，1个u。最后把index = n - 1上所有的可能性都枚举加和起来就是最终的结果。

数学解基本就是应用所谓的“插板法”，总共在4个位置插板，包括首尾位置，这样就能把n长度的string分成5段，从前往后分别代表aeiou的情况，即看每一段有几个a，几个e，几个i，几个o和几个u。但是这里面存在一个大的问题，因为只有首尾可以插板导致首尾2个区间为0，中间的eio没办法弄成区间为0的状况，所以在长度为n的string有n + 1个位置可以插板的基础上，再增加3种可以cover到eio区间取值为0的特殊情况，即overlap 1&2, 2&3以及3&4，也就是插板1和2重合，2和3重合，3和4重合，这样的话就可以造成区间2，3，4位0，从而让eio也可以为0，cover住所有可能的情况了。最后的结果就是从 n + 4 个插板的位置里选4个位置进行插板，即 res = C(n + 4, 4)，可以直接数学求解，也可以写一个helper函数来计算combo，带入参数n + 4和4即可得到。

注意，其中combo的helper函数比较常用，可以作为模板记下，类似于最大公约数的模板代码gcd。


【解题思路】

```
/**
 * S1: DP
 * X X X X i-1 i
 * dp[i][k]: the number of strings of which the i-th element is k
 * dp[i][k] = sum(dp[i - 1][j]) (j <= k)    i = 1 ~ n, k = 0 ~ 4
 *
 * S2: Math
 * 插板法：n balls; n + 1 spaces; 4 boards
 *        _o_o | o_o | o_o_o | o_o | o_
 *          2     2      3      2    1  ==> aaeeiiioou
 *        _o_o | o_o | o_o_o | o_o_o| _ |
 *          2     2      3       3    0   ==> aaeeiiiooo (u不用)
 *      C(n + 1, 4)
 *
 *        |o_o_o_o|o_o_o|o_o_o|
 *      0     4     3     3    0  ==> 问题：2，3，4永远不可能为0，但是事实上是可以为0的，所以是有问题的
 *       ==> 解决：n + 1 spaces, 3 special options：overlap 1 & 2，2 & 3， 3 & 4
 *       ==> n + 4 candidates ==> pick 4 ==> C(n + 4, 4)
 *     _o_o_o_o_o_o_o_o_o_o_    2 5 7 overlap 1 & 2
 *     _o_o || o_o_o | o_o | o_o_o_
 *       2  0    3      2      3     ==> aaiiioouuu
 *     _o_o_o_o_o_o_o_o_o_o_    0 overlap 1 & 2 overlap 2 & 3 overlap 3 & 4
 *     |||| o_o_o_o_o_o_o_o_o_o_
 *     0 0 0 0 10  ==> uuuuuuuuuu -> 符合条件的string
 *
 *     ==> res = C(n + 4, 4) = (n + 4) * (n + 3) * (n + 2) * (n + 1) / 24
 */
```

```java
// S1: DP (prefer!!!)
// time = O(n), space = O(n)
public int countVowelStrings(int n) {
    int[][] dp = new int[n][5];

    // init: 构造第一个字符，无论是a e i o u 哪种情况，都是只有一种构造方法
    for (int k = 0; k < 5; k++) dp[0][k] = 1; // 对于第一个字符是'a' -> 1种构造方法，其他也都一样

    for (int i = 1; i < n; i++) {
        for (int k = 0; k < 5; k++) {
            for (int j = 0; j <= k; j++) {
                dp[i][k] += dp[i - 1][j];
            }
        }
    }

    int res = 0;
    for (int k = 0; k < 5; k++) res += dp[n - 1][k];
    return res;
}
```

```java
// S2: Math (最优解!)
// time = O(1), space = O(1)
public int countVowelStrings2(int n) {
    return combo(n + 4, 4);
}

private int combo(int n, int k) { // O(k)
    long res = 1;
    for (int i = 0; i < k; i++) {
        res *= n - i;
        res /= i + 1;
    }
    return (int) res;
}     
```

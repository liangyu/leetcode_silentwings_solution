# LC5 Longest Palindromic Substring
标签（空格分隔）： LeetCode Java DP TwoPointers

---
    /**
     * Given a string s, return the longest palindromic substring in s.
     *
     * Input: s = "babad"
     * Output: "bab"
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s consist of only digits and English letters (lower-case and/or upper-case)
     *
     * @param s
     * @return
     */

【难点误区】

本题的难点在于想到这2种做法，S1 DP做法难在如何定义出一个dp[i][j]，并且如何根据最后一步以及上一步来判断当前dp[i][j]的状态，而S2 中心扩散法的难点在于想到从中心点出发，2种情况，长度为奇数和偶数，都可以分别用left，right两指针向两头扩散来找出最长的string。

【解题思路】

本题有2种经典作法，都应该要掌握。

S1是DP解法，定义一个boolean[ ][ ]，2个维度分别代表string的起点和终点，boolean[i][j]就代表以i为起点，j为终点的string是否是palindrome。这种方法的一个关键之处就在于如何去根据i和j去判断当前是一个palindrome。这就要用到DP的一个重要思想，最后一步以及从这个最后一步向前看上一步的状态。关于palindrome的判断，我们可以记住以下模板：

        X X X X X X X X X X          X X   or    X O X
        i ->          <-  j          i j         i   j

1.  s.charAt(i) == s.charAt(j) && j - i <= 2
2. s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]

=> dp[i][j] = s.charAt(i) == s.charAt(j) && ((j - i <= 2) || dp[i + 1][j - 1])

如果dp[i][j] == true，那么就看 j - i + 1是否会大于max，如果大于max的话，则更新max以及result string。

S2是中心扩散法，也是最优解。具体做法就是从string的每一个位置出发作为最终palindrome的中心点，向左右两边扩散。这里就有2种情况，一种就是最终的palindrome的长度是偶数，那么这个中心就有2个，left和right, 由于从0出发，所以left + 1 = right。如果长度是奇数位，那么中心就是一个，此时方便起见，我们依然沿用left, right2个中心点指针，只不过此时left == right。这样就能cover住两种情况，然后分别讨论找到所有可能性当中最大长度的string。

判断扩散的基本原则: 

1. left >= 0 && right < len，即左右中心指针不能出界
2. s.charAt(left) == s.charAt(right)，即左右中心指针指向的字符必须相等。

满足上面两个条件，即可继续向两头扩散，分别就是left--, right++。

```java
// S1: DP
// time = O(n^2), space = O(n^2)
public String longestPalindrome(String s) {
    // corner case
    if (s == null || s.length() == 0) return "";

    int len = s.length();
    int max = 0;
    String res = "";
    boolean[][] dp = new boolean[len][len];

    for (int j = 0; j < len; j++) {
        for (int i = 0; i <= j; i++) {
            if (s.charAt(i) == s.charAt(j) && ((j - i <= 2) || dp[i + 1][j - 1])) {
                dp[i][j] = true;
            }
            if (dp[i][j]) {
                if (j - i + 1 > max) {
                    max = j - i + 1;
                    res = s.substring(i, j + 1);
                }
            }
        }
    }
    return res;
}
```
```java
// S2: 中心扩散法 最优解！！！
// time = O(n^2), space = O(1)
public String longestPalindrome(String s) {
    // corner case
    if (s == null || s.length() == 0) return "";

    String res = "";
    for (int i = 0; i < s.length(); i++) { // O(n)
        res = helper(s, res, i, i);
        res = helper(s, res, i , i + 1);
    }
    return res;
}

private String helper(String s, String res, int left, int right) {
    int len = s.length();
    while (left >= 0 && right < len && s.charAt(left) == s.charAt(right)) { // O(n)
        left--;
        right++;
    }
    // 出loop时，left与right分别多-1和多+1，所以起点是left + 1，终点是right - 1
    String cur = s.substring(left + 1, right); 
    if (cur.length() > res.length()) res = cur; // 找到更长的则更新res
    return res;
}
```


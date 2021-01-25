# LC680 Valid Palindrome II
标签（空格分隔）： LeetCode Java TwoPointers

---
    /**
     * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
     *
     * Input: "aba"
     * Output: True
     *
     * Note:
     * The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
     *
     * @param s
     * @return
     */

【难点误区】

遇到双指针所指字符不同时，要分两叉考虑，既可以移动左指针也可以移动右指针，一通百通，有一个isPalin()为true即为true。

【解题思路】

延续LC125 Valid Palindrome的思路，依然使用双指针来解决，只不过这题不再有其他字符，只有小写字母a ~ z。但在本题中，由于可以彼此有一个字符不同，所以当两指针相向而行，发现两者所指字符不等时，就会出现分两叉的情况，要么左指针向内移动1位而右指针不变，要么右指针向内移动1位而左指针不变，然后继续check isPalin即可。

```java
// time = O(n), space = O(1)
public boolean validPalindrome(String s) {
    // corner case
    if (s == null || s.length() == 0) return true;

    int i = 0, j = s.length() - 1;
    while (i < j) { // O(n)
        char c1 = s.charAt(i), c2 = s.charAt(j);
        if (c1 == c2) {
            i++;
            j--;
        } else {
            return isPalin(s, i + 1, j) || isPalin(s, i, j - 1); // 分两叉分别讨论
        }
    }
    return true;
}

private boolean isPalin(String s, int i, int j) { // O(n)
    while (i < j) {
        if (s.charAt(i++) != s.charAt(j--)) { // 双指针相向而行
            return false;
        }
    }
    return true;
}
```


# LC125 Valid Palindrome
标签（空格分隔）： LeetCode Java TwoPointers

---
    /**
     * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
     *
     * Note: For the purpose of this problem, we define empty string as valid palindrome.
     *
     * Input: "A man, a plan, a canal: Panama"
     * Output: true
     *
     * Input: "race a car"
     * Output: false
     *
     * Constraints:
     *
     * s consists only of printable ASCII characters.
     *
     * @param s
     * @return
     */

【难点误区】

在移动双指针相向而行的时候，一定要确保i < j，即左右指针不能相等或者越过。相等时由于左右指针指向同一个字符，因此直接越过出loop，返回true。

【解题思路】

首尾双指针相向移动，逐个check。要能够check，首先得保证是有效字符，alphanumeric代表有效字符是大小写和0 ~ 9的数字，直接写个helper函数验证即可。由于大小写不区分，即'A' == 'a'，所以可以直接一上来先全部转化成大写或者小写，即用toLowerCase()或者toUpperCase()。这里特别注意，在移动到有效字符的过程中，一定要保证i < j，即双指针不能相等或者越过。

```java
// time = O(n), space = O(1)
public boolean isPalindrome(String s) {
    // corner case
    if (s == null || s.length() == 0) return true;

    s = s.toLowerCase();
    int i = 0, j = s.length() - 1;

    while (i < j) {
        while (i < j && !isValid(s.charAt(i))) i++; // 注意i < j不能相等或者越过
        while (i < j && !isValid(s.charAt(j))) j--;
        if (s.charAt(i) != s.charAt(j)) return false;
        i++;
        j--;
    }
    return true;
}

private boolean isValid(char c) {
    if (c >= 'a' && c <= 'z' || c >= '0' && c <= '9') return true;
    return false;
}
```


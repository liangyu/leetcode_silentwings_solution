# LC415 Add Strings
标签（空格分隔）： LeetCode Java TwoPointers

---
    /**
     * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.
     *
     * Note:
     *
     * The length of both num1 and num2 is < 5100.
     * Both num1 and num2 contains only digits 0-9.
     * Both num1 and num2 does not contain any leading zero.
     * You must not use any built-in BigInteger library or convert the inputs to integer directly.
     *
     * @param num1
     * @param num2
     * @return
     */

【难点误区】

出错的点在于由于是加和到carry上，所以一定要记得先append carry % 10的结果，再更新carry /= 10，否则如果倒过来，carry已经变更，后面append carry % 10很明显值就变了，所以先后处理顺序一定要注意！！！

【解题思路】

双指针分别指向num1和num2，每一位数字都直接增加到carry上，并且逐位append到一个StringBuilder上，直到两者都全部走完加和完毕。最后出loop一定要记得check carry是否为0。如果不为0，还要再加一位carry，最后把整个StringBuilder反转即可。


```java
// time = O(max(m, n)), space = O(max(m, n))
public String addStrings(String num1, String num2) {
    // corner case
    if (num1 == null || num1.length() == 0) return num2;
    if (num2 == null || num2.length() == 0) return num1;

    int i = num1.length() - 1, j = num2.length() - 1;
    int carry = 0;
    StringBuilder sb = new StringBuilder();

    while (i >= 0 || j >= 0) {
        if (i >= 0) {
            carry += num1.charAt(i--) - '0';
        }
        if (j >= 0) {
            carry += num2.charAt(j--) - '0';
        }
        sb.append(carry % 10); // 先append,再更新carry!!!
        carry /= 10;
    }
    if (carry > 0) sb.append(carry);
    return sb.reverse().toString();
}
```


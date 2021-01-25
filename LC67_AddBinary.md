# LC67 Add Binary

标签（空格分隔）： LeetCode Java Math

---
    /**
     * Given two binary strings a and b, return their sum as a binary string.
     *
     * Input: a = "11", b = "1"
     * Output: "100"
     *
     * Input: a = "1010", b = "1011"
     * Output: "10101"
     *
     * Constraints:
     *
     * 1 <= a.length, b.length <= 10^4
     * a and b consist only of '0' or '1' characters.
     * Each string does not contain leading zeros except for the zero itself.
     *
     * @param a
     * @param b
     * @return
     */
     
【难点误区】

处理好进位和取余，最后出loop一定要记得check carry，以及最后需要reverse sb。

【解题思路】

本题解题思路类似LC2 Add Two Numbers，从尾部开始累加到carry上，遇到进位就取余和做除法。最后出loop的时候一定要记得check carry，如果不为0，要把最后的carry也要加上。由于是从尾部向头部累加，所以最后结果需要做reverse。


```java     
// time = O(max(m, n)), space = O(max(m, n))
public String addBinary(String a, String b) {
    // corner case
    if (a == null || a.length() == 0 || b == null || b.length() == 0) {
        return "";
    }

    int i = a.length() - 1, j = b.length() - 1;
    int carry = 0;
    StringBuilder sb = new StringBuilder();
    while (i >= 0 || j >= 0) {
        if (i >= 0) {
            carry += a.charAt(i--) - '0';
        }
        if (j >= 0) {
            carry += b.charAt(j--) - '0';
        }
        sb.append(carry % 2);
        carry = carry / 2;
    }
    if (carry > 0) sb.append(carry);
    return sb.reverse().toString();
}
```

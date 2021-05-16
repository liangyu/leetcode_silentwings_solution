# LC1342 Number of Steps to Reduce a Number to Zero

标签（空格分隔）： LeetCode Java 

---

【题目】

    /**
     * Given a non-negative integer num, return the number of steps to reduce it to zero. If the current number is even,
     * you have to divide it by 2, otherwise, you have to subtract 1 from it.
     *
     * Input: num = 14
     * Output: 6
     *
     * Constraints:
     *
     * 0 <= num <= 10^6
     * 
     * @param num
     * @return
     */

【思路】

简单题，常规解法即最优，另一种做法是用bit。

【解答】

```java     
// time = O(logn), space = O(1)
public int numberOfSteps (int num) {
    int count = 0;
    while (num > 0) {
        if (num % 2 == 0) num /= 2;
        else num--;
        count++;
    }
    return count;
}
```

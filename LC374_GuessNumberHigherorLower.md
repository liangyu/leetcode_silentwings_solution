# LC374 Guess Number Higher or Lower
标签（空格分隔）： LeetCode Java BinarySearch

---
    /**
     * I pick a number from 1 to n. You have to guess which number I picked.
     *
     * Every time you guess wrong, I will tell you whether the number I picked is higher or lower than your guess.
     *
     * You call a pre-defined API int guess(int num), which returns 3 possible results:
     *
     * -1: The number I picked is lower than your guess (i.e. pick < num).
     * 1: The number I picked is higher than your guess (i.e. pick > num).
     * 0: The number I picked is equal to your guess (i.e. pick == num).
     * Return the number that I picked.
     *
     * Input: n = 10, pick = 6
     * Output: 6
     *
     * @param n
     * @return
     */


【难点误区】

B.S.简单模板题，注意post-processing别忘了做！

【解题思路】

B.S.直接套二分法模板即可。


```java
// time = O(logn), space = O(1)
public int guessNumber(int n) {
    int start = 1, end = n;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (guess(mid) == 0) return mid;
        if (guess(mid) == -1) end = mid;
        else start = mid;
    }
    if (guess(start) == 0) return start;
    if (guess(end) == 0) return end;
    throw new RuntimeException("No valid answer!");
}

/**
 * Forward declaration of guess API.
 * @param  num   your guess
 * @return 	     -1 if num is lower than the guess number
 *			      1 if num is higher than the guess number
 *               otherwise return 0
 * int guess(int num);
 */   
```

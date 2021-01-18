	# LC278 First Bad Version
	标签（空格分隔）： LeetCode BinarySearch
	
	---

    /**
     * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one,
     * which causes all the following ones to be bad.
     *
     * You are given an API bool isBadVersion(version) which returns whether version is bad.
     *
     * Input: n = 5, bad = 4
     * Output: 4
     *
     * Constraints:
     * 1 <= bad <= n <= 231 - 1
     *
     * @param n
     * @return
     */


【难点误区】

模板题，没啥好说的。


【解题思路】

直接套用B.S.二分法模板即可。


```java     
/* The isBadVersion API is defined in the parent class VersionControl.
  boolean isBadVersion(int version); */
// time = O(logn), space = o(1)
public int firstBadVersion(int n) {
    int start = 1, end = n;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (isBadVersion(mid)) end = mid;
        else start = mid;
    }
    if (isBadVersion(start)) return start;
    return end;
}
```

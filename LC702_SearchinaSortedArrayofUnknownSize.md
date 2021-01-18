# LC702 Search in a Sorted Array of Unknown Size
标签（空格分隔）： LeetCode Java BinarySearch

---

    /**
     * Given an integer array sorted in ascending order, write a function to search target in nums.
     * If target exists, then return its index, otherwise return -1. However, the array size is unknown to you.
     * You may only access the array using an ArrayReader interface,
     * where ArrayReader.get(k) returns the element of the array at index k (0-indexed).
     *
     * You may assume all integers in the array are less than 10000, and if you access the array out of bounds,
     * ArrayReader.get will return 2147483647.
     *
     * Input: array = [-1,0,3,5,9,12], target = 9
     * Output: 4
     *
     * Constraints:
     *
     * You may assume that all elements in the array are unique.
     * The value of each element in the array will be in the range [-9999, 9999].
     * The length of the array will be in the range [1, 10^4].
     *
     * @param reader
     * @param target
     * @return
     */

【难点误区】

1. 反向应用B.S.，start = 0, end = 1，先探路找到右边界，然后就可以正向使用B.S.模板即可。
2. 这题要特别注意出界的情况，每次用get API来check值之前，一定要注意先check是否出界！！！


【解题思路】

先反向使用B.S.探出右边界end，然后就可以使用B.S.二分模板正向使用B.S.来解决。


```java     
// time = O(logn), space = O(1)  n: an index of target value.
public int search(ArrayReader reader, int target) {
    int start = 0, end = 1;
    while (reader.get(end) != 2147483647 && reader.get(end) < target) {
        start = end;
        end *= 2;
    }

    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (reader.get(mid) != 2147483647 && reader.get(mid) == target) return mid;
        if (reader.get(mid) != 2147483647 && reader.get(mid) < target) start = mid;
        else end = mid;
    }
    if (reader.get(start) == target) return start;
    if (reader.get(end) == target) return end;
    return -1;
}

/**
 * // This is ArrayReader's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface ArrayReader {
 *     public int get(int index) {}
 * }
 */
```


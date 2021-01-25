# LC1679 Max Number of K-Sum Pairs
标签（空格分隔）： LeetCode Java HashTable

---
    /**
     * You are given an integer array nums and an integer k.
     *
     * In one operation, you can pick two numbers from the array whose sum equals k and remove them from the array.
     *
     * Return the maximum number of operations you can perform on the array.
     *
     * Input: nums = [1,2,3,4], k = 5
     * Output: 2
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= 10^9
     *
     * @param nums
     * @param k
     * @return
     */


【难点误区】

本题最大难点是联想到类似LC 1 Two Sum的做法，即使用HashMap来从前面存入map里的值寻找到与当前数值所匹配的那个。如果不匹配，则将当前值存入HashMap之中，这里要注意的是一定要加上else，因为和之前那种情况是互斥的，千万不能直接写而导致无论如何当前访问的值会加入到HashMap里造成错误！

【解题思路】

通过使用HashMap，采用类似LC 1 Two Sum的方法来寻找满足条件的pair，如果找到，则去掉这一对，即当前遍历到的元素不进入map，同时原来存于map中的值个数 - 1。由于我们用的是containsKey去check的，所以一旦个数减为0，就要把key的entry删掉，这样就不用再去check里面元素个数是否为0。在找到的时候同时count + 1，最后output为count即可。


```java
// time = O(n), space = O(n)
public int maxOperations(int[] nums, int k) {
    // corner case
    if (nums == null || nums.length == 0) return 0;

    HashMap<Integer, Integer> map = new HashMap<>();
    int count = 0;

    for (int n : nums) {
        int diff = k - n;
        if (map.containsKey(diff)) {
            map.put(diff, map.get(diff) - 1);
            count++;
            if (map.get(diff) == 0) { // delete depleted element to eliminate the key
                map.remove(diff);
            }
        } else { // if not able to remove, then add it to the hashmap
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
    }
    return count;
}     
```

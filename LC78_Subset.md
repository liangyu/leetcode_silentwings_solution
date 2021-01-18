# LC78 Subset
标签（空格分隔）： LeetCode Java Backtracking DFS

---

    /**
     * Given an integer array nums, return all possible subsets (the power set).
     *
     * The solution set must not contain duplicate subsets.
     *
     * Input: nums = [1,2,3]
     * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     * All the numbers of nums are unique.
     *
     * @param nums
     * @return
     */


【难点误区】

本题是DFS模板题，基本思想是边找边存，尤其要注意一上来为“空”的时候也要存入，可以作为模板记下！！！

关键的要点就是for loop起点从当前idx开始向后遍历，进入下一层时是i + 1 而不是idx + 1，在每个loop阶段，固定住起点idx，而对于每一个向后遍历的 i 进入下一层。



【解题思路】

```
/**
 * 时间复杂度： O(n * 2^n)
 * 
 *                     { }
 *                    / |  \
 *                 {1} {2} {3}     --> O(n)
 *                /  \
 *           2取/不取  3取/不取      --> O(2^(n - 1))
 *      {1, 2, 3}, {1, 3}, {1, 2}, {1}  ==> O(2^n)
 *      but we also need to do deep copy for each possible subset and add it to the res list,
 *      and in worst case the cost of deep copy is O(n)
 *      ==> time = O(n * 2^n)
 *
 * 空间复杂度：O(n) 递归时的栈空间消耗，也就是压栈的深度，如果考虑output的space，那么space = O(n * 2^n)
 */
```

```java     
// time = O(n * 2^n), space = O(n)
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    // corner case
    if (nums == null || nums.length == 0) return res;

    dfs(nums, 0, new ArrayList<>(), res);
    return res;
}

private void dfs(int[] nums, int idx, List<Integer> list, List<List<Integer>> res) {
    res.add(new ArrayList<>(list)); // O(n)

    for (int i = idx; i < nums.length; i++) { // O(n)
        list.add(nums[i]);
        dfs(nums, i + 1, list, res);
        list.remove(list.size() - 1);
    }
}
```

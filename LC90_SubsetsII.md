# LC90 Subsets II
标签（空格分隔）： LeetCode Java Backtracking DFS

---

    /**
     * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).
     *
     * Note: The solution set must not contain duplicate subsets.
     *
     * Input: [1,2,2]
     * Output:
     * [
     *   [2],
     *   [1],
     *   [1,2,2],
     *   [2,2],
     *   [1,2],
     *   []
     * ]
     *
     * @param nums
     * @return
     */


【难点误区】

1. 查重： 选择第一个作为“代表”，从前往后把接下来重复的都跳过，实现方式就是通过 i != idx && nums[i] == nums[i - 1]来查重。
2. 一定要记得，**对于任何出现duplicate的数组或者string题，如果要用到前后相邻来查重的话，一定要保证其是sorted!!!!非常重要！！！千万不能忘记！！！**本题没有说是sorted array，所以在进入DFS之前，一定要手动调用sort使其所有duplicate的元素统统都相邻。


【解题思路】

沿用LC78 Subset I的模板，唯一的区别是以上谈的2点：**必须先sort**；相邻位置查重，选择第一个出现的为代表而保留，后面的统统跳过。

```java     
// time = O(n * 2^n), space = O(n)
public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    // corner case
    if (nums == null || nums.length == 0) return res;

    // step 1: sort the array!!!
    Arrays.sort(nums); // O(nlogn)

    // step 2: DFS
    dfs(nums, 0, new ArrayList<>(), res);
    return res;
}

private void dfs(int[] nums, int idx, List<Integer> list, List<List<Integer>> res) {
    res.add(new ArrayList<>(list));

    for (int i = idx; i < nums.length; i++) {
        // deduplication
        if (i != idx && nums[i] == nums[i - 1]) continue;
        list.add(nums[i]);
        dfs(nums, i + 1, list, res);
        list.remove(list.size() - 1);
    }
}
```

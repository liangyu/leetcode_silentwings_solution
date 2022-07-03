package LC001_300;
import java.util.*;
public class LC46_Permutations {
    /**
     * Given an array nums of distinct integers, return all the possible permutations.
     * You can return the answer in any order.
     *
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * All the integers of nums are unique.
     *
     * @param nums
     * @return
     */
    // S1: swap
    // time = O(n * n!), space = O(n)
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        dfs(nums, 0, res);
        return res;
    }

    private void dfs(int[] nums, int idx, List<List<Integer>> res) {
        // base case
        if (idx == nums.length) {
            List<Integer> list = new ArrayList<>();
            for (int n : nums) list.add(n); // O(n)
            res.add(list);
            return;
        }

        for (int i = idx; i < nums.length; i++) {
            swap(nums, idx, i);
            dfs(nums, idx + 1, res);
            swap(nums, idx, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // S2: dfs
    // time = O(n * n!), space = O(n)
    class Solution {
        List<List<Integer>> res;
        List<Integer> path;
        boolean[] visited;
        int n;
        public List<List<Integer>> permute(int[] nums) {
            n = nums.length;
            res = new ArrayList<>();
            path = new ArrayList<>();
            visited = new boolean[n];

            dfs(nums, 0);
            return res;
        }

        private void dfs(int[] nums, int idx) {
            if (idx == n) {
                res.add(new ArrayList<>(path));
                return;
            }

            for (int i = 0; i < n; i++) {
                if (visited[i]) continue;
                visited[i] = true;
                path.add(nums[i]);
                dfs(nums, idx + 1);
                path.remove(path.size() - 1);
                visited[i] = false;
            }
        }
    }
}

/**
 * reference : http://www.1point3acres.com/bbs/thread-117602-1-1.html
 *
 * 每次函数F(N)中递归调用了N次函数F(N-1)，所以总共有T(N)=NT(N-1)=N*(N-1)*T(N-2)T(1)T(0)=N!*T(0)这么多次函数调用，
 * 又因为当每次输入的元素个数N=0时，需要将一个解压入解集。这是一个O(N)操作，也就是T(0)=N，所以复杂度是O(N!*N)。
 * 用解集数来估计得到的结果也是一样的：N个数的全排列是N!种，故共有N!个解，每个解需要O(N)时间构造，所以总时间是O(N!*N)。
 */


package LC1501_1800;
import java.util.*;
public class LC1713_MinimumOperationstoMakeaSubsequence {
    /**
     * You are given an array target that consists of distinct integers and another integer array arr that can have
     * duplicates.
     *
     * In one operation, you can insert any integer at any position in arr. For example, if arr = [1,4,1,2], you can
     * add 3 in the middle and make it [1,4,3,1,2]. Note that you can insert the integer at the very beginning or end
     * of the array.
     *
     * Return the minimum number of operations needed to make target a subsequence of arr.
     *
     * A subsequence of an array is a new array generated from the original array by deleting some elements
     * (possibly none) without changing the remaining elements' relative order. For example, [2,7,4] is a subsequence
     * of [4,2,3,7,2,1,4] (the underlined elements), while [2,4,2] is not.
     *
     * Input: target = [5,1,3], arr = [9,4,2,3,4]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= target.length, arr.length <= 10^5
     * 1 <= target[i], arr[i] <= 10^9
     * target contains no duplicates.
     * @param target
     * @param arr
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int minOperations(int[] target, int[] arr) {
        // 做个映射 -> 题目给出的条件：元素都是unique的，映射起来不会出现问题
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < target.length; i++) map.put(target[i], i);

        List<Integer> q = new ArrayList<>();
        for (int x : arr) {
            if (!map.containsKey(x)) continue; // target didn't have x
            q.add(map.get(x)); // index -> buffer
        }

        List<Integer> p = new ArrayList<>();
        for (int x : q) {
            int idx = upperBound(p, x);
            if (idx == p.size()) p.add(x);
            else p.set(idx, x);
        }
        return target.length - p.size();
    }

    // find the 1st pos >= target
    private int upperBound(List<Integer> buffer, int target) {
        // corner case
        if (buffer.size() == 0) return 0;
        int left = 0, right = buffer.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (buffer.get(mid) < target) left = mid + 1;
            else right = mid;
        }
        return buffer.get(left) >= target ? left : left + 1;
    }
}
/**
 * taeget = [x x a x b c]
 * arr = [x a b x c x]
 * longest common subsequence
 * 双序列dp => O(m*n)
 * dp[i][j]: A[0:i] and B[0:i]
 * if (A[i] == B[j])
 *      dp[i][j] = dp[i-1][j-1]+1
 * else
 *      dp[i][j] = max{dp[i-1][j], dp[i][j-1]}
 * LCS
 * distinct integers
 * for i: 0~m
 *      binary_search(j)
 *
 * [a,b,c,d,e ...]
 * [x f a b e c d]   LIS
 * 递增子序列
 * LCS -> LIS 根据distinct integers 转化成LIS，可以把它搞成递增序列
 * 找第一个大于等于它的最小值
 * 此题的算法是，将target里面的所有元素顺次映射成1,2,3...，
 * 然后将target在arr里面的元素也都替换成对应的1,2,3...其他没有在target里出现的数字都忽略。
 * 于是我们可以计算target和arr的LIS。最终的答案就是target的长度减去LIS的长度。
 */
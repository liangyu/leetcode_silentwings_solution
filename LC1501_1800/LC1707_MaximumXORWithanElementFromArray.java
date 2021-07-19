package LC1501_1800;
import java.util.*;
public class LC1707_MaximumXORWithanElementFromArray {
    /**
     * You are given an array nums consisting of non-negative integers. You are also given a queries array, where
     * queries[i] = [xi, mi].
     *
     * The answer to the ith query is the maximum bitwise XOR value of xi and any element of nums that does not exceed
     * mi. In other words, the answer is max(nums[j] XOR xi) for all j such that nums[j] <= mi. If all elements in
     * nums are larger than mi, then the answer is -1.
     *
     * Return an integer array answer where answer.length == queries.length and answer[i] is the answer to the ith query.
     *
     * Input: nums = [0,1,2,3,4], queries = [[3,1],[1,3],[5,6]]
     * Output: [3,3,7]
     *
     * Constraints:
     *
     * 1 <= nums.length, queries.length <= 10^5
     * queries[i].length == 2
     * 0 <= nums[j], xi, mi <= 10^9
     * @param nums
     * @param queries
     * @return
     */
    // time = O(mlogm + nlogn + m * n), space = O(m)  m: length of queries, n: length of nums
    public int[] maximizeXor(int[] nums, int[][] queries) {
        int[] res = new int[queries.length];
        // corner case
        if (nums == null || nums.length == 0) return res;

        int[][] que = new int[queries.length][3];
        for (int i = 0; i < queries.length; i++) {
            que[i][0] = queries[i][0];
            que[i][1] = queries[i][1];
            que[i][2] = i;
        }

        Arrays.sort(nums);
        Arrays.sort(que, (o1, o2) -> o1[1] - o2[1]); // sort according to mi

        TrieNode root = new TrieNode();
        int i = 0;
        for (int[] q : que) {
            while (i < nums.length && nums[i] <= q[1]) {
                TrieNode node = root;
                for (int k = 31; k >= 0; k--) {
                    if (node.next[(nums[i] >> k) & 1] == null) {
                        node.next[(nums[i] >> k) & 1] = new TrieNode();
                    }
                    node = node.next[(nums[i] >> k) & 1];
                }
                i++;
            }
            if (i == 0) {
                res[q[2]] = -1;
                continue;
            }

            TrieNode node = root;
            int ans = 0;
            for (int k = 31; k >= 0; k--) {
                if (node.next[1 - ((q[0] >> k) & 1)] != null) {
                    node = node.next[1 - ((q[0] >> k) & 1)];
                    ans = ans * 2 + 1;
                } else {
                    node = node.next[(q[0] >> k) & 1];
                    ans = ans * 2 + 0;
                }
            }
            res[q[2]] = ans;
        }
        return res;
    }

    private class TrieNode {
        private TrieNode[] next;
        public TrieNode() {
            this.next = new TrieNode[2];
        }
    }
}
/**
 * LC421的原型
 * 先挑m比较小的query，这样能够让你选的数就比较少
 * 1 -> 0 / 1
 * 暴力：每个元素遍历一遍 => O(10^10) => O(n^2)
 * 反着想：xor要最大
 * x = 10011011
 * y = 01100100  (绝配) => 找一个准绝配，与y最接近，尽量找最高位要是0 =>
 * 接下来看第二位要是1 => 第三位 = 1，如果都是0，那打平，再看第4位... => 前几位xor出来的结果最大！
 * 尽快过滤出形式上跟y接近的树
 * 所有元素都写成32位的2进制数 => 构建一个字典树
 * 0 - 向左拐； 1 - 向右拐 => 共享前缀，理论上32层 -> O(32n) = O(n) 对n个sorted queries而言
 * LC1697 把query排序
 */

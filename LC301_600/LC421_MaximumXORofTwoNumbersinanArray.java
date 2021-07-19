package LC301_600;

public class LC421_MaximumXORofTwoNumbersinanArray {
    /**
     * Given an integer array nums, return the maximum result of nums[i] XOR nums[j], where 0 <= i <= j < n.
     *
     * Input: nums = [3,10,5,25,2,8]
     * Output: 28
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^5
     * 0 <= nums[i] <= 2^31 - 1
     * @param nums
     * @return
     */
    public int findMaximumXOR(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        TrieNode root = new TrieNode();

        // built tree
        for (int num : nums) { // 没有就创建新的结点，有的话就继续一路往下走
            TrieNode node = root;
            for (int k = 31; k >= 0; k--) {
                if (node.next[(num >> k) & 1] == null) {
                    node.next[(num >> k) & 1] = new TrieNode();
                }
                node = node.next[(num >> k) & 1];
            }
        }

        // 固定一个数，找与它最般配的一个数
        int res = 0;
        for (int num : nums) {
            TrieNode node = root;
            int ans = 0;
            for (int k = 31; k >= 0; k--) {
                if (node.next[1 - (num >> k) & 1] != null) {
                    node = node.next[1 - (num >> k) & 1];
                    ans = ans * 2 + 1;
                } else {
                    node = node.next[(num >> k) & 1];
                    ans = ans * 2 + 0;
                }
            }
            res = Math.max(res, ans);
        }
        return res;
    }

    private class TrieNode {
        TrieNode[] next;
        public TrieNode() {
            next = new TrieNode[2];
        }
    }
}
/**
 * 先找一个
 * 101011001 -> 32bit
 * 1xxxxxxx
 * xxxxxxxx
 * 1xxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * 1xxxxxxx
 * 技巧：每个数的长度只有32位
 * 贪心的想法
 * 1010100110 绝配
 * xor后最高位为1
 * ideally挑出来最高位是1
 * 然后继续看正数第二位异或出来要是1，继续筛选
 * 如果不凑巧都没有，那就继续向下看
 * 1101？？？？？？ -> 前4位最大，没有其他竞争者了
 * 不断淘汰的过程
 * 每走一轮就会砍掉一个分支
 * 再走一轮，又砍掉一些分支
 * 这是一个tree -> trie
 * 所有的candidate都构建成二叉树 -> 省空间
 * 叶子节点所有深度都是一样的
 * 搜索起来很方便
 * => time = O(32*n)
 * 遍历这棵树，最多走32层
 *
 */

package LC1801_2100;

public class LC1803_CountPairsWithXORinaRange {
    /**
     * Given a (0-indexed) integer array nums and two integers low and high, return the number of nice pairs.
     *
     * A nice pair is a pair (i, j) where 0 <= i < j < nums.length and low <= (nums[i] XOR nums[j]) <= high.
     *
     * Input: nums = [1,4,2,7], low = 2, high = 6
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * 1 <= nums[i] <= 2 * 10^4
     * 1 <= low <= high <= 2 * 10^4
     * @param nums
     * @param low
     * @param high
     * @return
     */
    // time = O(n), space = O(n)
    public int countPairs(int[] nums, int low, int high) {
        return countPairsSmallerThan(nums, high + 1) - countPairsSmallerThan(nums, low);
    }

    private int countPairsSmallerThan(int[] nums, int th) {
        TrieNode root = new TrieNode();
        int count = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            count += countNumsSmallerThan(root, nums[i], th);
            insert(root, nums[i]); // 永远只找i前面的数，避免double count，count完之后再把nums[i]加入trie
        }
        return count;
    }

    private int countNumsSmallerThan(TrieNode root, int num, int th) {
        TrieNode node = root;
        int count = 0;
        // 考虑第i层，默认前i-1层我们所取的元素与num的前i-1位亦或之后恰好等于th的前i-1位，都打平
        for (int i = 31; i >= 0; i--) {
            int c = (th >> i) & 1; // c: threshold的第i位
            int b = (num >> i) & 1; // b: num的第i位
            int a = (b ^ c); // a ^ b = c

            if (a == 1 && c == 1) {
                if (node.next[0] != null ) count += node.next[0].count; // go to branch '0'，得保证下面有0节点
                if (node.next[1] != null) node = node.next[1]; // go to branch '1' -> 继续打平手
                else break; // 下面没有任何元素，找不到任何东西，可以直接break了
            } else if (a == 0 && c == 1) {
                if (node.next[1] != null ) count += node.next[1].count; // go to branch '1'
                if (node.next[0] != null ) node = node.next[0]; // go to branch '0', keep recursion to go down the trie
                else break;
            } else if (a == 1 && c == 0) {
                if (node.next[1] != null) node = node.next[1];
                else break;
                // note: if pick the branch '0', then a ^ b = 1 > c, so completely discard this path
            } else if (a == 0 && c == 0) {
                if (node.next[0] != null) node = node.next[0]; // go to branch '0'
                else break;
                // if go to branch '1', 1 & b = 1 > c -> discard completely
            }
        }
        return count;
    }

    private void insert(TrieNode root, int num) {
        TrieNode node = root;
        for (int i = 31; i >= 0; i--) {
            if (((num >> i) & 1) == 1) {
                if (node.next[1] == null) node.next[1] = new TrieNode();
                node = node.next[1];
            } else {
                if (node.next[0] == null) node.next[0] = new TrieNode();
                node = node.next[0];
            }
            node.count++;
        }
    }

    private class TrieNode {
        private TrieNode[] next;
        private int count; // 表示这个结点下面有多少个叶子节点,建树时更新
        public TrieNode() {
            this.next = new TrieNode[2];
            this.count = 0;
        }
    }
}
/**
 * 2 * 10^4 => O(n^2) 做不了
 * array + xor => trie
 * ref: LC421
 * 确定一个i去有意识地寻找j的话，字典树非常高效，因为最多只要遍历32层即可, O(32)
 * 这就是用字典树的优势
 * i = 111
 * j = 000 (ideally) 有意识的去找，dfs遍历trie
 * 套路：既要比什么大，又要比什么小 =>
 * 转化成一种问题：count pairs smaller than H + 1 (单边问题) - count pairs smaller than L
 * ref: LC992
 * countNumsSmallerThan => 找一个临界元素，与num亦或后等于th
 * a = 0, c = 1 -> countAll   node[0].count
 * a = 1, c = 1 -> recursive(node[1])
 * a = 0, c = 0 -> recursive(node[0])
 * a = 1, c = 0 -> discard (node[1])
 * 因为c和th都是给定的，所以只要考虑a的2种情况即可。
 */
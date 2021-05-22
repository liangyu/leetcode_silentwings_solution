package LC301_600;
import java.util.*;
public class LC526_BeautifulArrangement {
    /**
     * Suppose you have n integers from 1 to n. We define a beautiful arrangement as an array that is constructed by
     * these n numbers successfully if one of the following is true for the ith position (1 <= i <= n) in this array:
     *
     * The number at the ith position is divisible by i.
     * i is divisible by the number at the ith position.
     *
     * Given an integer n, return the number of the beautiful arrangements that you can construct.
     *
     * Input: n = 2
     * Output: 2
     * Explanation:
     * The first beautiful arrangement is [1, 2]:
     * Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).
     * Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).
     * The second beautiful arrangement is [2, 1]:
     * Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).
     * Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
     *
     * Constraints:
     *
     * 1 <= n <= 15
     *
     *
     * @param n
     * @return
     */
    // S1: DFS
    // time = O(n!), space = O(n)
    // recursion depth at most is O(n) ==> space = O(n)
    private int res = 0;
    public int countArrangement(int n) {
        boolean[] visited = new boolean[n + 1]; // valid index starts from 1 to n, 0 is not used here
        dfs(n, visited, 1);
        return res;
    }

    private void dfs(int n, boolean[] visited, int pos) {
        // base case - success
        if (pos > n) {   // pos can be at idx = n, so it must go over n to claim it is successful
            res++;
            return;
        }

        for (int i = 1; i <= n; i++) { // valid index starts from 1 to n
            if (!visited[i] && (pos % i == 0 || i % pos == 0)) {
                visited[i] = true;
                dfs(n, visited, pos + 1); // go deep in this branch
                visited[i] = false;
            }
        }
    }
}

/**
 * 【难点误区】
 *
 * 一上来看不出是分叉搜索的套路，完全不知道如何handle解决这种数字permutation的排布问题。然而一旦看出是DFS backtracking的套路的话，唯一要
 * 注意的就是有效区间是在1 ~ n 而不是 0 ~ n - 1，这样开boolean[ ]的时候就会注意到多开一格到 n + 1而不是n，同时base case跳出recursion的
 * 条件也是在 pos > n 而不是在 pos == n.
 *
 * 【解题思路】
 *
 * 1 ~ n个数字，放在1 ~ n个位置上，要实现 (num % idx == 0) || (idx % num == 0)，那么可以想到是一个分叉搜索问题 => 2种情况，分两叉分别
 * go deep走下去看后面的数字能否放在其余对应的位置上直到所有数字都能满足条件，一直到idx > n即 idx 从1 ~ n都走过了一遍且满足上述条件，这样的话
 * 就得到了一个有效解 => res++. 同时在分叉过程中，已经被占用的index显然不能继续被其他数字所再次占用，所以必须开辟一个boolean[ ]来查重，因为
 * idx 是从1 ~ n，所以我们必须开n + 1个空间，其中0是无效的idx，虽然开了但不使用，这样就能保证1 ~ n都是可以有效被使用来查重。
 *
 * DFS三段论，input -> n, 查重 -> boolean[ ] visited, 当前搜到哪 -> pos，可以放到 1 ~ n之间任何依旧available的位置上，所以必须用
 * for loop 来进行搜索，一旦满足上述两个条件且没有被visited占用的话，就可以标记visited为true，然后go deep进入下一个数字继续下一层搜索，结束
 * 后记得backtracking后的setback，把visited标为false即可，出口的base case即为 pos > n，表示所有数字从1 ~ n都已经访问完毕并且放在满足
 * 条件的位置上了，这个时候有效解个数res + 1，同时return。
 *
 * 时间复杂度：worst case是1 ~ n所有可能的permutation都要check一遍 => O(n!)
 * 空间复杂度：boolean[ ] 开销是O(n)，同时每个数字的压栈深度最多也是O(n) => O(n)
 */

package LC1201_1500;
import java.util.*;
public class LC1361_ValidateBinaryTreeNodes {
    /**
     * You have n binary tree nodes numbered from 0 to n - 1 where node i has two children leftChild[i] and
     * rightChild[i], return true if and only if all the given nodes form exactly one valid binary tree.
     *
     * If node i has no left child then leftChild[i] will equal -1, similarly for the right child.
     *
     * Note that the nodes have no values and that we only use the node numbers in this problem.
     *
     * Input: n = 4, leftChild = [1,-1,3,-1], rightChild = [2,-1,-1,-1]
     * Output: true
     *
     * Constraints:
     *
     * n == leftChild.length == rightChild.length
     * 1 <= n <= 10^4
     * -1 <= leftChild[i], rightChild[i] <= n - 1
     * @param n
     * @param leftChild
     * @param rightChild
     * @return
     */
    // time = O(n), space = O(n)
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int[][] graph = new int[n][2];
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new int[]{leftChild[i], rightChild[i]};
            if (leftChild[i] != -1) indegree[leftChild[i]]++;
            if (rightChild[i] != -1) indegree[rightChild[i]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                if (queue.size() == 0) queue.offer(i);
                else return false;
            } else if (indegree[i] > 1) return false;
        }
        if (queue.size() == 0) return false;

        int count = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            count++;
            for (int next : graph[cur]) {
                if (next == -1) continue;
                indegree[next]--;
                if (indegree[next] == 0) queue.offer(next);
            }
        }
        return count == n;
    }
}

package LC1201_1500;
import java.util.*;
public class LC1306_JumpGameIII {
    /**
     * Given an array of non-negative integers arr, you are initially positioned at start index of the array. When you
     * are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach to any index with value 0.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     * Input: arr = [4,2,3,0,3,1,2], start = 5
     * Output: true
     *
     * Constraints:
     *
     * 1 <= arr.length <= 5 * 10^4
     * 0 <= arr[i] < arr.length
     * 0 <= start < arr.length
     * @param arr
     * @param start
     * @return
     */
    // time = O(n), space = O(n)
    public boolean canReach(int[] arr, int start) {
        // corner case
        if (arr == null || arr.length == 0) return false;

        int n = arr.length;
        boolean[] visited = new boolean[n];
        return dfs(arr, start, visited);
    }

    private boolean dfs(int[] arr, int start, boolean[] visited) {
        if (start < 0 || start >= arr.length) return false;
        if (arr[start] == 0) return true;
        if (visited[start]) return false;

        visited[start] = true;
        if (dfs(arr, start - arr[start], visited)) return true;
        if (dfs(arr, start + arr[start], visited)) return true;
        return false;
    }
}
/**
 * x - x - x - x - x - 1
 *     |___________|
 * 存在环 -> 访问过的不再访问
 * 基本的搜索，DFS或者BFS均可。在DFS的过程中，任何已经访问过的节点都不需要再访问。
 * 因为它们要么是已经证实是通往dead end；要么是当前的探索路径中已经访问过的节点，再访问的话就成环会变死循环
 */

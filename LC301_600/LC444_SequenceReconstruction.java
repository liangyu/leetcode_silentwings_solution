package LC301_600;
import java.util.*;
public class LC444_SequenceReconstruction {
    /**
     * Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs. The org
     * sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 104. Reconstruction means building a shortest
     * common supersequence of the sequences in seqs (i.e., a shortest sequence so that all sequences in seqs are
     * subsequences of it). Determine whether there is only one sequence that can be reconstructed from seqs and it is
     * the org sequence.
     *
     * Input: org = [1,2,3], seqs = [[1,2],[1,3]]
     * Output: false
     *
     * Constraints:
     *
     * 1 <= n <= 10^4
     * org is a permutation of {1,2,...,n}.
     * 1 <= segs[i].length <= 10^5
     * seqs[i][j] fits in a 32-bit signed integer.
     *
     *
     * UPDATE (2017/1/8):
     * The seqs parameter had been changed to a list of list of strings (instead of a 2d array of strings).
     * Please reload the code definition to get the latest changes.
     * @param org
     * @param seqs
     * @return
     */
    // time = O(V + E), space = O(n)
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        // corner case
        if (seqs == null || seqs.size() == 0 || seqs.get(0) == null || seqs.get(0).size() == 0) return false;

        int n = org.length;
        List<Integer>[] graph = new List[n + 1];
        for (int i = 0; i <= n; i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[n + 1];
        for (List<Integer> s : seqs) {
            if (s.size() == 1) {
                if (s.get(0) < 1 || s.get(0) > n) return false;
            }
            for (int i = 0; i < s.size() - 1; i++) {
                int a = s.get(i), b = s.get(i + 1);
                if (a < 1 || a > n || b < 1 || b > n) return false;
                graph[a].add(b);
                indegree[b]++;
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }

        int count = 0;
        while (!queue.isEmpty()) {
            if (queue.size() > 1) return false;
            int cur = queue.poll();
            if (cur != org[count]) return false;
            count++;

            for (int next : graph[cur]) {
                indegree[next]--;
                if (indegree[next] == 0) queue.offer(next);
            }
        }
        if (count != n) return false;
        return true;
    }
}
/**
 * 本题巧妙地应用了拓扑排序的原理。我们根据seqs可以得到每个节点的入度和（有向的）邻接节点。
 * 我们每个回合里，统计当前入度为零的节点，这样的节点必须只有一个。
 * 如果有若干个，那么他们彼此之间的先后顺序必然是不确定的。
 * 此外，这唯一的入度为零的节点（整个图的起点），也必须是org里当前的首元素。如果不满足这两个条件，我们就返回false。
 * 在满足这两个条件之后，我们就弹出org的首元素，考察它所邻接的节点及其更新后的入度（它们的入度要减一），继续寻找其中入度为零的节点。
 * 不断重复直至遍历完所有的拓扑关系。
 * 当遍历完所有的拓扑关系后，我们要求也恰好遍历完org里面所有的节点。否则返回false。
 * 此外，seqs里面所有的节点必须是在[1,n]的范围里。
 */

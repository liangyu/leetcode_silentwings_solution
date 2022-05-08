package LC301_600;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class LC433_MinimumGeneticMutation {
    /**
     * A gene string can be represented by an 8-character long string, with choices from 'A', 'C', 'G', and 'T'.
     *
     * Suppose we need to investigate a mutation from a gene string start to a gene string end where one mutation is
     * defined as one single character changed in the gene string.
     *
     * For example, "AACCGGTT" --> "AACCGGTA" is one mutation.
     * There is also a gene bank bank that records all the valid gene mutations. A gene must be in bank to make it a
     * valid gene string.
     *
     * Given the two gene strings start and end and the gene bank bank, return the minimum number of mutations needed
     * to mutate from start to end. If there is no such a mutation, return -1.
     *
     * Note that the starting point is assumed to be valid, so it might not be included in the bank.
     *
     * Input: start = "AACCGGTT", end = "AACCGGTA", bank = ["AACCGGTA"]
     * Output: 1
     *
     * Constraints:
     *
     * start.length == 8
     * end.length == 8
     * 0 <= bank.length <= 10
     * bank[i].length == 8
     * start, end, and bank[i] consist of only the characters ['A', 'C', 'G', 'T'].
     * @param start
     * @param end
     * @param bank
     * @return
     */
    // time = O(C * n * m), space = O(n * m)
    public int minMutation(String start, String end, String[] bank) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        HashSet<String> set = new HashSet<>();
        set.add(start);

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String cur = queue.poll();
                if (cur.equals(end)) return step;

                for (String next : bank) {
                    if (helper(cur, next) && set.add(next)) queue.offer(next);
                }
            }
            step++;
        }
        return -1;
    }

    private boolean helper(String s, String t) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            if (s.charAt(i) != t.charAt(i)) count++;
        }
        return count == 1;
    }
}

package LC601_900;
import java.util.*;
public class LC838_PushDominoes {
    /**
     * There are n dominoes in a line, and we place each domino vertically upright. In the beginning, we simultaneously
     * push some of the dominoes either to the left or to the right.
     *
     * After each second, each domino that is falling to the left pushes the adjacent domino on the left. Similarly, the
     * dominoes falling to the right push their adjacent dominoes standing on the right.
     *
     * When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.
     *
     * For the purposes of this question, we will consider that a falling domino expends no additional force to a
     * falling or already fallen domino.
     *
     * You are given a string dominoes representing the initial state where:
     *
     * dominoes[i] = 'L', if the ith domino has been pushed to the left,
     * dominoes[i] = 'R', if the ith domino has been pushed to the right, and
     * dominoes[i] = '.', if the ith domino has not been pushed.
     * Return a string representing the final state.
     *
     * Input: dominoes = "RR.L"
     * Output: "RR.L"
     *
     * Input: dominoes = ".L.R...LR..L.."
     * Output: "LL.RR.LLRRLL.."
     *
     * Constraints:
     *
     * n == dominoes.length
     * 1 <= n <= 10^5
     * dominoes[i] is either 'L', 'R', or '.'.
     * @param dominoes
     * @return
     */
    // S1: BFS
    // time = O(n), space = O(n)
    public String pushDominoes(String dominoes) {
        // corner case
        if (dominoes == null || dominoes.length() == 0) return "";

        int n = dominoes.length();
        char[] chars = dominoes.toCharArray();
        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (chars[i] != '.') {
                queue.offer(i);
                set.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            char[] temp = chars.clone();
            while (size-- > 0) {
                int cur = queue.poll();
                set.remove(cur);
                if (chars[cur] == 'L') {
                    if (cur > 0 && chars[cur - 1] == '.') {
                        if (!set.contains(cur - 1)) {
                            temp[cur - 1] = 'L';
                            set.add(cur - 1);
                        } else {
                            temp[cur - 1] = chars[cur - 1];
                            set.remove(cur - 1);
                        }
                    }
                } else {
                    if (cur < n - 1 && chars[cur + 1] == '.') {
                        if (!set.contains(cur + 1)) {
                            temp[cur + 1] = 'R';
                            set.add(cur + 1);
                        } else {
                            temp[cur + 1] = chars[cur + 1];
                            set.remove(cur + 1);
                        }
                    }
                }
            }
            for (int idx : set) queue.offer(idx);
            chars = temp;
        }
        return String.valueOf(chars);
    }
}

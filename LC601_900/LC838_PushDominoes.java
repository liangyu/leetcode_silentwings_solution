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

    // S1.2: BFS
    // time = O(n), space = O(n)
    public String pushDominoes2(String dominoes) {
        // corner case
        if (dominoes == null || dominoes.length() == 0) return "";

        int n = dominoes.length();
        char[] chars = dominoes.toCharArray();
        Queue<int[]> queue = new LinkedList<>();
        int[] res = new int[n];
        Arrays.fill(res, -2); // 0,1,2都已经表示三种状态了，这里要表示原始未动，得用第4个数-2
        for (int i = 0; i < n; i++) {
            if (chars[i] == 'L') {
                queue.offer(new int[]{i, -1});
                res[i] = -1;
            }
            else if (chars[i] == 'R') {
                queue.offer(new int[]{i, 1});
                res[i] = 1;
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            HashMap<Integer, Integer> map = new HashMap<>(); // 每一层单独记录新出发的骨牌状态
            while (size-- > 0) {
                int[] cur = queue.poll();
                int pos = cur[0], dir = cur[1];
                if (dir == 1 && pos + 1 < n && res[pos + 1] == -2) {
                    map.put(pos + 1, map.getOrDefault(pos + 1, 0) + 1);
                } else if (dir == -1 && pos - 1 >= 0 && res[pos - 1] == -2) {
                    map.put(pos - 1, map.getOrDefault(pos - 1, 0) - 1);
                }
            }
            for (int key : map.keySet()) {
                queue.offer(new int[]{key, map.get(key)});
                res[key] = map.get(key);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (res[i] == -1) sb.append('L');
            else if (res[i] == 1) sb.append('R');
            else sb.append('.');
        }
        return sb.toString();
    }

    // S3: one scan
    // time = O(n), space = O(n)
    public String pushDominoes3(String dominoes) {
        // corner case
        if (dominoes == null || dominoes.length() == 0) return "";

        int n = dominoes.length(), i = 0;
        char[] chars = dominoes.toCharArray();
        while (i < n) {
            if (chars[i] == '.') i++;
            else if (chars[i] == 'L') {
                int j = i - 1;
                while (j >= 0 && chars[j] == '.') chars[j--] = 'L';
                i++;
            } else if (chars[i] == 'R') {
                int k = i + 1;
                while (k < n && chars[k] == '.') k++;
                if (k < n && chars[k] == 'L') {
                    int left = i + 1, right = k - 1;
                    while (left < right) {
                        chars[left++] = 'R';
                        chars[right--] = 'L';
                    }
                    i = k + 1;
                } else {
                    while (i < k) chars[i++] = 'R';
                }
            }
        }
        return String.valueOf(chars);
    }
}
/**
 * 根据题意，我们按照回合的顺序进行BFS模拟。队列的初始，放入所有被推导的骨牌。
 * 在一个回合里，我们弹出所有在上一回合被推倒的骨牌。
 * 如果是个刚被往左推倒的骨牌，会在此回合影响它左边的骨牌（如果未被推倒的话），使其有左倾的趋势；
 * 同理，对于刚被往右推倒的骨牌，会在此回合影响它右边的骨牌（如果未被推倒的话），使其有右倾的趋势。
 * 注意，有些骨牌可能会在此回合里既被左倾也被右倾，那么它就最终的状态是站立。
 * 这一回合结束时，我们要将所有在本回合被推倒的骨牌加入队列中。
 * 最终所有骨牌会在BFS的过程中被加入队列确认状态。
 */

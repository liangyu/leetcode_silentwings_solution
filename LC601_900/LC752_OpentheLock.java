package LC601_900;
import java.util.*;
public class LC752_OpentheLock {
    /**
     * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', '3', '4', '5',
     * '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0'
     * to be '9'. Each move consists of turning one wheel one slot.
     *
     * The lock initially starts at '0000', a string representing the state of the 4 wheels.
     *
     * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the wheels of the
     * lock will stop turning and you will be unable to open it.
     *
     * Given a target representing the value of the wheels that will unlock the lock, return the minimum total number
     * of turns required to open the lock, or -1 if it is impossible.
     *
     * Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= deadends.length <= 500
     * deadends[i].length == 4
     * target.length == 4
     * target will not be in the list deadends.
     * target and deadends[i] consist of digits only.
     * @param deadends
     * @param target
     * @return
     */
    // time = O(n^2 * a^n + k) = O(16 + 10^4 + k) = O(k), space = O(a^n + k) = O(10^4 + k) = O(k)
    // a表示数字的个数 = 10，n表示状态的位数 = 4，k表示数组deadends的大小
    public int openLock(String[] deadends, String target) {
        // corner case
        if (target == null || target.length() == 0) return -1;

        HashSet<String> deads = new HashSet<>();
        for (String d : deadends) deads.add(d);
        if (deads.contains("0000")) return -1;

        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        HashSet<String> visited = new HashSet<>();
        visited.add("0000");

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String cur = queue.poll();
                if (cur.equals(target)) return step;
                List<String> nexts = convert(cur);
                for (String next : nexts) {
                    if (visited.contains(next) || deads.contains(next)) continue;
                    queue.offer(next);
                    visited.add(next);
                }
            }
            step++;
        }
        return -1;
    }

    private List<String> convert(String cur) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = -1; j <= 1; j += 2) {
                char[] temp = cur.toCharArray();
                int num = (temp[i] - '0' + j + 10) % 10;
                temp[i] = (char)(num + '0');
                res.add(String.valueOf(temp));
            }
        }
        return res;
    }
}

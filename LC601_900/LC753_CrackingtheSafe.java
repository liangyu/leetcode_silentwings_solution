package LC601_900;
import java.util.*;
public class LC753_CrackingtheSafe {
    /**
     * There is a safe protected by a password. The password is a sequence of n digits where each digit can be in the
     * range [0, k - 1].
     *
     * The safe has a peculiar way of checking the password. When you enter in a sequence, it checks the most recent n
     * digits that were entered each time you type a digit.
     *
     * For example, the correct password is "345" and you enter in "012345":
     * After typing 0, the most recent 3 digits is "0", which is incorrect.
     * After typing 1, the most recent 3 digits is "01", which is incorrect.
     * After typing 2, the most recent 3 digits is "012", which is incorrect.
     * After typing 3, the most recent 3 digits is "123", which is incorrect.
     * After typing 4, the most recent 3 digits is "234", which is incorrect.
     * After typing 5, the most recent 3 digits is "345", which is correct and the safe unlocks.
     * Return any string of minimum length that will unlock the safe at some point of entering it.
     *
     * Input: n = 1, k = 2
     * Output: "10"
     *
     * Constraints:
     *
     * 1 <= n <= 4
     * 1 <= k <= 10
     * 1 <= k^n <= 4096
     * @param n
     * @param k
     * @return
     */
    // S1: De Bruijin
    // time = O(k^n), space = O(k^n)
    public String crackSafe(int n, int k) {
        HashMap<String, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n - 1; i++) sb.append(0); // add n - 1 '0'
        // how to set the nth digit -> use n - 1 digits as key, the nth digit as value
        // each time only check the former n - 1 digits
        // the last digit didn't appear before
        for (int i = 0; i < (int) Math.pow(k, n); i++) {
            String key = sb.substring(sb.length() - (n - 1)); // get the last n - 1 digits
            map.put(key, (map.getOrDefault(key, 0) + 1) % k);
            sb.append(map.get(key));
        }
        return sb.toString();
    }

    // S2: dfs
    // time = O(k^(n + 1)), space = O(n * k^n)
    public String crackSafe2(int n, int k) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(0);
        HashSet<String> visited = new HashSet<>();
        visited.add(sb.toString());
        int amount = (int) Math.pow(k, n);
        dfs(sb, visited, amount, n, k);
        return sb.toString(); // or return sb.reverse().toString(); to match the test case result
    }

    private boolean dfs(StringBuilder sb, HashSet<String> visited, int amount, int n, int k) {
        // base case
        if (visited.size() == amount) return true; // all possibilities have been visited

        String sub = sb.substring(sb.length() - (n - 1));
        for (char c = '0'; c < k + '0'; c++) { // O(k)
            String s = sub + c;
            if (!visited.contains(s)) {
                visited.add(s);
                sb.append(c);
                if (dfs(sb, visited, amount, n, k)) return true; // O(k^n)
                sb.setLength(sb.length() - 1);
                visited.remove(s);
            }
        }
        return false;
    }
}
/**
 * n^k种
 * 每次只看最后n位
 * De Bruijin序列
 * n^k + (n - 1)
 * 我们希望遍历所有状态，每走一条边，后面就要+1位，走到一个新的状态，给一个新的密码组合
 * 把所有结点遍历一遍，经历的边越少越好
 * 肯定能找到一条路径，经历每个结点1次
 * 可以证明哈密尔顿路径是存在的
 * 也可以看成是一个欧拉环
 * 一次不拉的走过每条边一次
 * 每条边当做一个密码组合
 * 用最少的路径来遍历每条边，如果每条边只遍历1次，就是个欧拉回路
 * 哈密尔顿路径并不是唯一的
 * 怎么高效找出来呢？
 * [xxxxxxx] [x]
 */

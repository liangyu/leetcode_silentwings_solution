package LC001_300;
import java.util.*;
public class LC93_RestoreIPAddresses {
    /**
     * Given a string s containing only digits, return all possible valid IP addresses that can be obtained from s.
     * You can return them in any order.
     *
     * A valid IP address consists of exactly four integers, each integer is between 0 and 255, separated by single
     * dots and cannot have leading zeros. For example, "0.1.2.201" and "192.168.1.1" are valid IP addresses
     * and "0.011.255.245", "192.168.1.312" and "192.168@1.1" are invalid IP addresses.
     *
     * Input: s = "25525511135"
     * Output: ["255.255.11.135","255.255.111.35"]
     *
     * Constraints:
     *
     * 0 <= s.length <= 3000
     * s consists of digits only.
     * @param s
     * @return
     */
    // time = O(1), space = O(1)
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0) return res;

        dfs(s, 0, 0, new StringBuilder(), res);
        return res;
    }

    private void dfs(String s, int idx, int num, StringBuilder path, List<String> res) {
        // base case - success
        if (idx == s.length() && num == 4) {
            path.setLength(path.length() - 1);
            res.add(path.toString());
            return;
        }

        // base case - fail
        if (idx == s.length() || num == 4) return;

        int len = path.length(), val = 0;
        for (int i = idx; i < idx + 3; i++) {
            if (i >= s.length()) break;
            val = val * 10 + s.charAt(i) - '0';
            if (val >= 0 && val <= 255) {
                path.append(val + ".");
                dfs(s, i + 1, num + 1, path, res);
                path.setLength(len);
            }
            if (val == 0) break;
        }
    }
}

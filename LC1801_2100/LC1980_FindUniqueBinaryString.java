package LC1801_2100;
import java.util.*;
public class LC1980_FindUniqueBinaryString {
    /**
     * Given an array of strings nums containing n unique binary strings each of length n, return a binary string of
     * length n that does not appear in nums. If there are multiple answers, you may return any of them.
     *
     * Input: nums = ["01","10"]
     * Output: "11"
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 16
     * nums[i].length == n
     * nums[i] is either '0' or '1'.
     * @param nums
     * @return
     */
    // S1: DFS
    // time = O(2^n), space = O(n)
    private String res = "";
    public String findDifferentBinaryString(String[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return "";

        HashSet<String> set = new HashSet<>();
        for (String num : nums) set.add(num);

        if (dfs(0, nums.length, new StringBuilder(), set)) return res;
        return "";
    }

    private boolean dfs(int idx, int n, StringBuilder path, HashSet<String> set) {
        // base case
        if (idx == n) {
            String s = path.toString();
            if (!set.contains(s)) {
                res = s;
                return true;
            }
            return false;
        }

        // pick '0'
        path.append('0');
        if (dfs(idx + 1, n, path, set)) return true;
        path.setLength(path.length() - 1);

        // pick '1'
        path.append('1');
        if (dfs(idx + 1, n, path, set)) return true;
        path.setLength(path.length() - 1);

        return false;
    }

    // S2: Cantor's Diagonalization
    // time = O(n), space = O(n)
    public String findDifferentBinaryString2(String[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(1 - (nums[i].charAt(i) - '0'));
        }
        return sb.toString();
    }
}
/**
 * The trick to do this question is somewhat similar to Cantor's Diagonalization.
 * Since we are given that number of bits in the number is equal to number of elements.
 * What we can do is we create a binary string which differs from first binary at 1st position,
 * second binary at 2nd position, third binary at 3rd position,...and so on.
 * This will make sure that the binary we have made is unique (as it differs from all others at atleast one position).
 */
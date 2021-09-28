package LC1201_1500;
import java.util.*;
public class LC1239_MaximumLengthofaConcatenatedStringwithUniqueCharacters {
    /**
     * Given an array of strings arr. String s is a concatenation of a sub-sequence of arr which have unique characters.
     *
     * Return the maximum possible length of s.
     *
     * Input: arr = ["un","iq","ue"]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= arr.length <= 16
     * 1 <= arr[i].length <= 26
     * arr[i] contains only lower case English letters.
     * @param arr
     * @return
     */
    // S1: dfs
    // time = O(2^n), space = O(n)
    private int max = 0;
    public int maxLength(List<String> arr) {
        // corner case
        if (arr == null || arr.size() == 0) return 0;

        dfs(arr, 0, new StringBuilder());
        return max;
    }

    private void dfs(List<String> arr, int idx, StringBuilder path) {
        // base case
        if (idx == arr.size()) return;

        int len = path.length();
        for (int i = idx; i < arr.size(); i++) {
            String s = arr.get(i);
            path.append(s);
            if (!hasDup(path)) {
                max = Math.max(max, path.length());
                dfs(arr, i + 1, path);
            }
            path.setLength(len);
        }
    }

    private boolean hasDup(StringBuilder path) {
        HashSet<Character> set = new HashSet<>();
        for (char c : path.toString().toCharArray()) {
            if (!set.add(c)) return true;
        }
        return false;
    }

    // S2: bit mask
    // time = O(2^n), space = O(n)
    private int res = 0;
    public int maxLength2(List<String> arr) {
        // corner case
        if (arr == null || arr.size() == 0) return 0;

        int n = arr.size();

        // transform each unique string to bit codes
        List<Long> codes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            long code = 0;
            boolean flag = true;
            for (char c : arr.get(i).toCharArray()) {
                if (((code >> (c - 'a')) & 1) == 1) {
                    flag = false;
                    break;
                }
                code += (1 << (c - 'a'));
            }
            if (flag) codes.add(code);
        }

        long bits = 0;
        for (int i = 0; i < codes.size(); i++) {
            dfs(codes, i, codes.get(i));
        }
        return res;
    }

    private void dfs(List<Long> codes, int i, long bits) {
        res = Math.max(res, Long.bitCount(bits));
        for (int j = i + 1; j < codes.size(); j++) {
            if ((bits & codes.get(j)) == 0) { // check if two strings have duplicate chars
                dfs(codes, j, bits + codes.get(j));
            }
        }
    }
}
/**
 * 本题就是一个基本的DFS爆搜。注意不要误认为是LIS。
 * 一个long long变量有32个bit，我们可以用来记录dfs的过程中已经收集了哪些字符，记做变量bits。
 * 当前DFS过程中的bits与某个新字符串对应的bits做“AND”之后，如果不为零时，说明二者有重复的字符，搜索可以终止。
 * 最终的答案就是搜索过程中bits出现的最多的1的各种
 */
package LC1201_1500;
import java.util.*;
public class LC1234_ReplacetheSubstringforBalancedString {
    /**
     * You are given a string containing only 4 kinds of characters 'Q', 'W', 'E' and 'R'.
     *
     * A string is said to be balanced if each of its characters appears n/4 times where n is the length of the string.
     *
     * Return the minimum length of the substring that can be replaced with any other string of the same length to
     * make the original string s balanced.
     *
     * Return 0 if the string is already balanced.
     *
     * Input: s = "QWER"
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s.length is a multiple of 4
     * s contains only 'Q', 'W', 'E' and 'R'.
     * @param s
     * @return
     */
    // S1: B.S.
    // time = O(nlogn), space = O(n)
    public int balancedString(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        int n = s.length();
        boolean flag = true;
        for (char key : map.keySet()) {
            if (map.get(key) != n / 4) flag = false;
        }
        if (flag) return 0;

        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isOK(s, mid, map)) right = mid;
            else left = mid + 1;
        }
        return left; // 一定有解，全部魔改 n/4
    }

    private boolean isOK(String s, int k, HashMap<Character, Integer> map) {
        HashMap<Character, Integer> sum = new HashMap<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            sum.put(s.charAt(i), sum.getOrDefault(s.charAt(i), 0) + 1);
            if (i >= k) sum.put(s.charAt(i - k), sum.get(s.charAt(i - k)) - 1);

            boolean flag = true;
            for (char key : map.keySet()) {
                int diff = !sum.containsKey(key) ? map.get(key) : map.get(key) - sum.get(key);
                if (diff > n / 4) {
                    flag = false;
                    break;
                }
            }
            if (flag) return true;
        }
        return false;
    }

    // S1: Two Pointers
    // time = O(n), space = O(n)
    public int balancedString2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        int n = s.length();
        boolean flag = true;
        for (char key : map.keySet()) {
            if (map.get(key) != n / 4) flag = false;
        }
        if (flag) return 0;

        int j = 0, res = Integer.MAX_VALUE;
        HashMap<Character, Integer> sum = new HashMap<>();
        for (int i = 0; i < n; i++) {
            while (j < n && !checkOK(sum, map, s)) {
                sum.put(s.charAt(j), sum.getOrDefault(s.charAt(j), 0) + 1);
                j++;
            }
            if (checkOK(sum, map, s)) res = Math.min(res, j - i);
            sum.put(s.charAt(i), sum.get(s.charAt(i)) - 1);
        }
        return res;
    }

    private boolean checkOK(HashMap<Character, Integer> sum, HashMap<Character, Integer> map, String s) {
        int n = s.length();
        for (char key : map.keySet()) {
            int diff = !sum.containsKey(key) ? map.get(key) : map.get(key) - sum.get(key);
            if (diff > n / 4) return false;
        }
        return true;
    }
}
/**
 * 区间型：确定左右边界
 * 已知给一个区间，能不能通过改动满足条件呢？
 * 任意改，操作空间非常大。
 * 区间外面哪个字母太多了
 * 考察在区间外的字母，它出现的次数如果< n/4，就没问题
 * w x [w x x x] x x
 * 区间内可以魔改，外面改不了
 * 唯一的问题是，如果外面的字母多了，区间内就做不了了
 * 这个东西宁缺毋滥
 * 如果每个词频都 <= n / 4 就OK
 * 如果超过了，那没办法，就不行了
 * 第一个想法是用二分搜值 => 探索这个窗口的大小
 * 0 ~ n   O(nlogn) n ~ 10^5 可行  搜区间的长度和位置
 * 双指针
 * x [x x x x] x x x
 * 确定一个边界找另一个边界
 * 外面字母越多越容易坏事
 * 右边界找到可以了，这时就得到固定该左边界的最小窗口
 * 这时候需要遍历下一个左指针的位置
 * 这时候需要考虑一个问题：右指针是否需要从头来？不需要！
 * x [x x x x] x x x
 * x x [x x] x x x x -> 右边界是不可能往回走的 => O(n)
 * 根本：判定条件！！！
 */

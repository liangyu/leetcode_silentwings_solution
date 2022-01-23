package LC301_600;
import java.util.*;
public class LC358_RearrangeStringkDistanceApart {
    /**
     * Given a string s and an integer k, rearrange s such that the same characters are at least distance k from each
     * other. If it is not possible to rearrange the string, return an empty string "".
     *
     * Input: s = "aabbcc", k = 3
     * Output: "abcabc"
     *
     * Constraints:
     *
     * 1 <= s.length <= 3 * 10^5
     * s consists of only lowercase English letters.
     * 0 <= k <= s.length
     * @param s
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(n)
    public String rearrangeString(String s, int k) {
        if (k == 0) return s;
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1]);

        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) pq.offer(new int[]{count[i], i});
        }

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            if (pq.size() < k && pq.peek()[0] > 1) return ""; // 取不完了！
            int n = Math.min(pq.size(), k);
            List<int[]> temp = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                sb.append((char)(pq.peek()[1] + 'a'));
                temp.add(pq.poll());
            }

            for (int[] x : temp) {
                if (x[0] > 1) {
                    x[0]--;
                    pq.offer(x);
                }
            }
        }
        return sb.toString();
    }
}
/**
 * ref: LC767 (代码可以套用，when k = 2), LC1054
 * abc cba
 * 优先取频次比较多的字母
 * aaaaaaaaa
 * bbbbbbb
 * ccccc
 * dddd
 * eee
 * f
 * 挑词频最高的k个字母，用pq
 */
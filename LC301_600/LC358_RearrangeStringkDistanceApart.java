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
        // corner case
        if (k == 0) return s;
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<Pair>((o1, o2) -> o1.freq != o2.freq ? o2.freq - o1.freq : o1.ch - o2.ch);
        for (char key : map.keySet()) pq.offer(new Pair(map.get(key), key));

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            if (pq.size() < k && pq.peek().freq > 1) return "";
            int n = Math.min(k, pq.size());
            List<Pair> temp = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                sb.append(pq.peek().ch);
                temp.add(pq.poll());
            }

            for (Pair x : temp) {
                if (x.freq > 1) {
                    x.freq--;
                    pq.offer(x);
                }
            }
        }
        return sb.toString();
    }

    private class Pair {
        private int freq;
        private char ch;
        public Pair(int freq, char ch) {
            this.freq = freq;
            this.ch = ch;
        }
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
 */
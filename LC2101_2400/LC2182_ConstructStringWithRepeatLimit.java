package LC2101_2400;
import java.util.*;
public class LC2182_ConstructStringWithRepeatLimit {
    /**
     * You are given a string s and an integer repeatLimit. Construct a new string repeatLimitedString using the
     * characters of s such that no letter appears more than repeatLimit times in a row. You do not have to use all
     * characters from s.
     *
     * Return the lexicographically largest repeatLimitedString possible.
     *
     * A string a is lexicographically larger than a string b if in the first position where a and b differ, string a
     * has a letter that appears later in the alphabet than the corresponding letter in b. If the first
     * min(a.length, b.length) characters do not differ, then the longer string is the lexicographically larger one.
     *
     * Input: s = "cczazcc", repeatLimit = 3
     * Output: "zzcccac"
     *
     * Constraints:
     *
     * 1 <= repeatLimit <= s.length <= 10^5
     * s consists of lowercase English letters.
     * @param s
     * @param repeatLimit
     * @return
     */
    // S1: PQ
    // time = O(nlogn), space = O(n)
    public String repeatLimitedString(String s, int repeatLimit) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        PriorityQueue<int[]> pq = new PriorityQueue<>(((o1, o2) -> o2[0] - o1[0])); // {idx, freq}
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) pq.offer(new int[]{i, freq[i]});
        }

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int[] x = pq.poll();
            char c1 = (char)(x[0] + 'a');
            int count1 = x[1];
            int limit = repeatLimit;
            // 如果这一轮的最大频率字符与上一轮的次大频率字符相同，那么这里该字符最多只能放limit-1个！
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == c1) limit--;
            int k = Math.min(limit, count1);
            for (int i = 0; i < k; i++) sb.append(c1);
            count1 -= k;
            if (pq.size() == 0) break;

            int[] y = pq.poll();
            char c2 = (char)(y[0] + 'a');
            int count2 = y[1];
            sb.append(c2);
            count2--;

            if (count1 > 0) pq.offer(new int[]{x[0], count1});
            if (count2 > 0) pq.offer(new int[]{y[0], count2});
        }
        return sb.toString();
    }
}

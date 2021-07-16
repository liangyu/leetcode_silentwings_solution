package LC601_900;
import java.util.*;
public class LC767_ReorganizeString {
    /**
     * Given a string s, rearrange the characters of s so that any two adjacent characters are not the same.
     *
     * Return any possible rearrangement of s or return "" if not possible.
     *
     * Input: s = "aab"
     * Output: "aba"
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // S1: PQ
    // time = O(nlogn), space = O(n)
    public String reorganizeString(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int n = s.length();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);

        PriorityQueue<Pair> pq = new PriorityQueue<>((o1, o2) -> o2.freq - o1.freq);
        StringBuilder sb = new StringBuilder();

        for (char key : map.keySet()) pq.offer(new Pair(map.get(key), key));

        while (!pq.isEmpty()) {
            int len = pq.size();
            if (len >= 2) {
                List<Pair> temp = new ArrayList<>();
                sb.append(pq.peek().c);
                temp.add(pq.poll());

                sb.append(pq.peek().c);
                temp.add(pq.poll());

                for (Pair p : temp) {
                    if (p.freq > 1) {
                        p.freq--;
                        pq.offer(p);
                    }
                }
            } else if (len == 1) {
                if (pq.peek().freq > 1) return "";
                sb.append(pq.poll().c);
            }
        }
        return sb.toString();
    }

    private class Pair {
        private int freq;
        private char c;
        public Pair(int freq, char c) {
            this.freq = freq;
            this.c = c;
        }
    }

    // S2: count
    // time = O(n), space = O(n)
    public String reorganizeString2(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int n = s.length();
        int[] hash = new int[26];
        for (int i = 0; i < n; i++) hash[s.charAt(i) - 'a']++;

        int max = 0, letter = 0;
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] > max) {
                max = hash[i];
                letter = i;
            }
        }
        if (max > (n + 1) / 2) return "";

        char[] res = new char[n];
        int idx = 0;
        while (hash[letter] > 0) {
            res[idx] = (char)(letter + 'a');
            idx += 2;
            hash[letter]--;
        }

        for (int i = 0; i < hash.length; i++) {
            while (hash[i] > 0) {
                if (idx >= res.length) idx = 1;
                res[idx] = (char)(i + 'a');
                idx += 2;
                hash[i]--;
            }
        }
        return String.valueOf(res);
    }
}
/**
 * 用PQ的贪心法比较好 -> 哪个频次多就用哪个
 * aaaaaaaa
 * bbbbbb
 * cccc
 * ddd
 * [ab][ab][ab][ac][cd]
 * 每次都取频次最多的2个
 * S2: no sort
 * count letter appearance and store in hash[i]
 * find the letter with largest occurence.
 * put the letter into even index numbe (0, 2, 4 ...) char array
 * put the rest into the array
 */

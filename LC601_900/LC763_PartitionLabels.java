package LC601_900;
import java.util.*;
public class LC763_PartitionLabels {
    /**
     * You are given a string s. We want to partition the string into as many parts as possible so that each letter
     * appears in at most one part.
     *
     * Note that the partition is done so that after concatenating all the parts in order, the resultant string should
     * be s.
     *
     * Return a list of integers representing the size of these parts.
     *
     * Input: s = "ababcbacadefegdehijhklij"
     * Output: [9,7,8]
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        int[] last = new int[26];
        int n = s.length();
        for (int i = 0; i < n; i++) last[s.charAt(i) - 'a'] = i;

        int j = 0, start = 0;
        for (int i = 0; i < n; i++) {
            j = Math.max(j, last[s.charAt(i) - 'a']);
            if (i == j) {
                res.add(i - start + 1);
                start = i + 1;
            }
        }
        return res;
    }

    // S2
    // time = O(n), space = O(n)
    public List<Integer> partitionLabels2(String s) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0) return res;

        int[][] freq = new int[26][2];
        for (int[] f : freq) Arrays.fill(f, -2);
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (freq[c - 'a'][0] == -2) freq[c - 'a'] = new int[]{i, i};
            else freq[c - 'a'][1] = i;
        }

        int start = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (freq[c - 'a'][0] < 0) continue;
            if (check(s, freq, start, freq[c - 'a'][1])) {
                res.add(freq[c - 'a'][1] - start + 1);
                start = freq[c - 'a'][1] + 1;
                i = start - 1;
            }
        }
        return res;
    }

    private boolean check(String s, int[][] freq, int a, int b) {
        for (int i = a; i <= b; i++) {
            char c = s.charAt(i);
            if (freq[c - 'a'][0] >= a && freq[c - 'a'][1] <= b) continue;
            return false;
        }
        return true;
    }
}

package LC001_300;
import java.util.*;
public class LC187_RepeatedDNASequences {
    /**
     * The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'.
     *
     * For example, "ACGAATTCCG" is a DNA sequence.
     * When studying DNA, it is useful to identify repeated sequences within the DNA.
     *
     * Given a string s that represents a DNA sequence, return all the 10-letter-long sequences (substrings) that occur
     * more than once in a DNA molecule. You may return the answer in any order.
     *
     * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
     * Output: ["AAAAACCCCC","CCCCCAAAAA"]
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is either 'A', 'C', 'G', or 'T'.
     * @param s
     * @return
     */
    // S1: sliding window
    // time = O(n), space = O(n)
    public List<String> findRepeatedDnaSequences(String s) {
        // corner case
        if (s == null || s.length() == 0) return new ArrayList<>();

        HashSet<String> seen = new HashSet<>();
        HashSet<String> repeated = new HashSet<>();

        for (int i = 0; i < s.length() - 9; i++) {
            String sub = s.substring(i, i + 10);
            if (!seen.add(sub)) repeated.add(sub);
        }
        return new ArrayList<>(repeated);
    }

    // S2： bit mask
    // time = O(n), space = O(n)
    public List<String> findRepeatedDnaSequences2(String s) {
        List<String> res = new ArrayList<>();
        // corner case
        if (s == null || s.length() == 0) return res;

        int n = s.length();
        int key = 0;

        HashMap<Integer, Boolean> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int val = convert(s.charAt(i));
            key = ((key << 2) & 0xfffff) | val;
            if (i < 9) continue;
            Boolean shouldAdd = map.get(key);
            if (shouldAdd == null) map.put(key, false);
            else if (!shouldAdd) {
                res.add(s.substring(i - 9, i + 1));
                map.put(key, true);
            }
        }
        return res;
    }

    private int convert(char c) {
        switch(c) {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            case 'T': return 3;
            default: return -1;
        }
    }
}

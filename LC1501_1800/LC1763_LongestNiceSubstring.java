package LC1501_1800;
import java.util.*;
public class LC1763_LongestNiceSubstring {
    /**
     * A string s is nice if, for every letter of the alphabet that s contains, it appears both in uppercase and
     * lowercase. For example, "abABB" is nice because 'A' and 'a' appear, and 'B' and 'b' appear. However, "abA" is
     * not because 'b' appears, but 'B' does not.
     *
     * Given a string s, return the longest substring of s that is nice. If there are multiple, return the substring of
     * the earliest occurrence. If there are none, return an empty string.
     *
     * Input: s = "YazaAay"
     * Output: "aAa"
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists of uppercase and lowercase English letters.
     *
     * @param s
     * @return
     */
    // S1: DFS
    // time = O(n^2), space = O(n)
    public String longestNiceSubstring(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        HashSet<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) set.add(c);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (set.contains(Character.toLowerCase(c)) && set.contains(Character.toUpperCase(c))) continue;
            String sub1 = longestNiceSubstring(s.substring(0, i)), sub2 = longestNiceSubstring(s.substring(i + 1));
            return sub1.length() >= sub2.length() ? sub1 : sub2;
        }
        return s;
    }

    // S2: Two Pointers
    // time = O(26n) = O(n), space = O(1)
    public String longestNiceSubstring2(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int maxLen = -1, start = -1;
        for (int m = 1; m <= 26; m++) {
            int[] res = helper(s.toCharArray(), m); // [len, start]
            if (res[0] > maxLen) {
                maxLen = res[0];
                start = res[1];
            } else {
                if (maxLen == res[0] && res[1] < start) start = res[1]; // earliest occurrence
            }
        }
        return maxLen != -1 ? s.substring(start, start + maxLen) : "";
    }

    private int[] helper(char[] s, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        HashMap<Character, Integer> copy = new HashMap<>();

        int n = s.length, j = 0;
        int start = -1, len = -1;
        for (int i = 0; i < n; i++) {
            while (j < n && (map.size() < k || map.size() == k && map.containsKey(Character.toLowerCase(s[j])))) {
                // 26 -> all transform to lowercase, at the same time keep an original copy
                map.put(Character.toLowerCase(s[j]), map.getOrDefault(Character.toLowerCase(s[j]), 0) + 1);
                copy.put(s[j], copy.getOrDefault(s[j], 0) + 1);
                j++;
            }
            if (map.size() < k) break; // not sufficient
            // check if the window is valid
            boolean flag = true;
            for (char key : map.keySet()) {
                if (!copy.containsKey(key) || !copy.containsKey(Character.toUpperCase(key))) { // check if the val is 0
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (j - i > len) {
                    len = j - i;
                    start = i;
                }
            }
            // shrink i at the left end
            map.put(Character.toLowerCase(s[i]), map.get(Character.toLowerCase(s[i])) - 1);
            if (map.get(Character.toLowerCase(s[i])) == 0) map.remove(Character.toLowerCase(s[i]));
            copy.put(s[i], copy.get(s[i]) - 1);
            if (copy.get(s[i]) == 0) copy.remove(s[i]);
        }
        return new int[]{len ,start};
    }

    // S3: bitmask
    // time = O(n^2), space = O(1)
    public String longestNiceSubstring3(String s) {
        int n = s.length(), maxLen = 0;
        String res = "";
        for (int i = 0; i < n; i++) {
            int a = 0, b = 0;
            for (int j = i; j < n; j++) {
                char c = s.charAt(j);
                if (Character.isLowerCase(c)) {
                    a |= 1 << (c - 'a');
                } else b |= 1 << (c - 'A');
                if (a == b && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;
    }

    // S4: divide & conquer (最优解！！！)
    // time = O(n), space = O(1)
    private int maxPos, maxLen;
    public String longestNiceSubstring4(String s) {
        this.maxPos = 0;
        this.maxLen = 0;
        dfs(s, 0, s.length() - 1);
        return s.substring(maxPos, maxPos + maxLen);
    }

    private void dfs(String s, int start, int end) {
        if (start >= end) return;

        int a = 0, b = 0;
        for (int i = start; i <= end; i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) a |= 1 << (c - 'a');
            if (Character.isUpperCase(c)) b |= 1 << (c - 'A');
        }
        if (a == b) {
            if (end - start + 1 > maxLen) {
                maxPos = start;
                maxLen = end - start + 1;
            }
            return;
        }

        // not successful
        int valid = a & b; // get the common pos mask
        int j = start;
        while (j <= end) { // explore all possible nice strings and dfs
            int i = j;
            while (j <= end && (valid & (1 << Character.toLowerCase(s.charAt(j)) - 'a')) != 0) j++;
            dfs(s, i, j - 1);
            j++;
        }
    }
}

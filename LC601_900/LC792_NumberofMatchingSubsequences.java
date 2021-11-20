package LC601_900;
import java.util.*;
public class LC792_NumberofMatchingSubsequences {
    /**
     * Given a string s and an array of strings words, return the number of words[i] that is a subsequence of s.
     *
     * A subsequence of a string is a new string generated from the original string with some characters (can be none)
     * deleted without changing the relative order of the remaining characters.
     *
     * For example, "ace" is a subsequence of "abcde".
     *
     * Input: s = "abcde", words = ["a","bb","acd","ace"]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= s.length <= 5 * 10^4
     * 1 <= words.length <= 5000
     * 1 <= words[i].length <= 50
     * s and words[i] consist of only lowercase English letters.
     * @param s
     * @param words
     * @return
     */
    // S1
    // time = O(n + m * k), space = O(m) n: length of s, m: length of words, k: average length of word
    public int numMatchingSubseq(String s, String[] words) {
        // corner case
        if (s == null || s.length() == 0 || words == null || words.length == 0) return 0;

        HashMap<Character, Deque<String>> map = new HashMap<>();
        for (String word : words) { // O(m)
            map.putIfAbsent(word.charAt(0), new LinkedList<>());
            map.get(word.charAt(0)).offerLast(word);
        }

        int count = 0;
        for (char c : s.toCharArray()) { // O(n)
            if (map.containsKey(c)) {
                Deque<String> deque = map.get(c);
                int size = deque.size();
                for (int i = 0; i < size; i++) { // 所有对于在loop里进行增删操作的都要提前把size/length cache出来去loop!!!
                    String str = deque.removeFirst();
                    if (str.length() == 1) count++;
                    else {
                        map.putIfAbsent(str.charAt(1), new LinkedList<>());
                        map.get(str.charAt(1)).offerLast(str.substring(1));
                    }
                }
            }
        }
        return count;
    }

    // S2: TreeSet
    // time = O(n * logm * k), space = O(m)  n: length of s, m: length of words, k: average length of word
    public int numMatchingSubseq2(String s, String[] words) {
        TreeSet<Integer>[] pos = new TreeSet[26];
        for (int i = 0; i < 26; i++) pos[i] = new TreeSet<>();
        for (int i = 0; i < s.length(); i++) {
            pos[s.charAt(i) - 'a'].add(i);
        }

        int count = 0;
        for (String word : words) {
            if (word.length() > s.length()) continue;
            if (check(word, pos)) count++;
        }
        return count;
    }

    private boolean check(String word, TreeSet<Integer>[] pos) {
        int i = 0;
        for (char ch : word.toCharArray()) {
            Integer idx = pos[ch - 'a'].ceiling(i);
            if (idx == null) return false;
            i = idx + 1;
        }
        return true;
    }

    // S3: State Machine
    // time = O(26m + n * k), space = O(26m) n: length of s, m: length of words, k: average length of word
    public int numMatchingSubseq3(String s, String[] words) {
        // corner case
        if (s == null || s.length() == 0 || words == null || words.length == 0) return 0;

        int m = s.length();
        s = "#" + s; // s[1:m]

        int[][] next = new int[m + 1][26];
        Arrays.fill(next[m], -1); // init,站在最尾端向右看，什么字符都没有，即-1

        for (int i = m; i >= 1; i--) { // O(m)
            for (int k = 0; k < 26; k++) next[i - 1][k] = next[i][k];
            next[i - 1][s.charAt(i) - 'a'] = i;
        }

        int res = 0;
        for (String word : words) { // O(n)
            int i = 0;
            boolean flag = true;
            for (char ch : word.toCharArray()) { // O(k)
                i = next[i][ch - 'a'];
                if (i == -1) {
                    flag = false;
                    break;
                }
            }
            if (flag) res++;
        }
        return res;
    }
}
/**
 * 状态机 state machine
 * 最常见做法 subsequence 双指针
 * s = [axxxx]a{x}
 * word = a {bc}  找剩下字符串里最靠前的
 *           ^
 * O((m+n)*k) = m*k
 * 找的就是s里面的第一个a
 * 挨个扫一遍才能找到s里的第一个a => bs
 * pos[a] = {3,5,7}
 * pos[b] = {2,4,8}
 * pos[c] = {.2,3,6...}
 * word = a b c  -> 找a后面的b
 * O(n*logm*k)
 *    0 1 2 3 4 5 6 7 8
 * s =  x x a b a x a c
 * S3: next[0][a] = 3   从 idx = 0 的位置向右看，第一个出现a的位置 = 3
 *     next[3][b] = 4
 *     next[4][c] = 8   -> read directly from the table    跳转非常快 -> 读n次
 *     next[8][d] = -1  没有，找不到d
 * 2个维度，m * 26 矩阵
 *  预处理，从后往前
 *  next[10][a] = -1
 *  next[10][b] = -1
 *  next[10][c] = -1
 *  next[10][d] = -1
 *
 *  站在9往右看，
 *  next[9][a] = -1
 *  next[9][b] = 10
 *  next[9][c] = -1
 *  next[9][d] = -1
 *
 *  理论上时间复杂度 = O(26m + n * k)
 *  状态机：从一个状态直接跳转到下一个状态
 */

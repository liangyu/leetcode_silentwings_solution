package LC301_600;
import java.util.*;
public class LC336_PalindromePairs {
    /**
     * Given a list of unique words, return all the pairs of the distinct indices (i, j) in the given list, so that the
     * concatenation of the two words words[i] + words[j] is a palindrome.
     *
     * Input: words = ["abcd","dcba","lls","s","sssll"]
     * Output: [[0,1],[1,0],[3,2],[2,4]]
     *
     * Constraints:
     *
     * 1 <= words.length <= 5000
     * 0 <= words[i].length <= 300
     * words[i] consists of lower-case English letters.
     * @param words
     * @return
     */
    // time = O(nlogn + n * k^2), space = O(n * k)
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (words == null || words.length == 0) return res;

        int n = words.length;
        HashSet<String> set = new HashSet<>();
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(words[i], i);
        }
        Arrays.sort(words, (o1, o2) -> o1.length() - o2.length()); // 通过排序，后面扫一个加一个的方法来去重！
        for (int j = 0; j < n; j++) {
            String s = words[j];
            for (int k = 0; k <= s.length(); k++) {
                String s1 = s.substring(0, k);
                String s2 = s.substring(k);
                if (isPalin(s1)) {
                    String t = s2;
                    t = reverse(t);
                    if (!t.equals(s) && set.contains(t)) res.add(Arrays.asList(map.get(t), map.get(s)));
                }
                if (isPalin(s2)) {
                    String t = s1;
                    t = reverse(t);
                    if (!t.equals(s) && set.contains(t)) res.add(Arrays.asList(map.get(s), map.get(t)));
                }
            }
            set.add(words[j]);
        }
        return res;
    }

    private boolean isPalin(String s) {
        int i = 0, j = s.length() - 1;
        while (i <= j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    private String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) sb.append(s.charAt(i));
        return sb.toString();
    }
}
/**
 * O(n^2)
 * {i, j} 快速精准的去找，效率更高
 * 如果i在j之前
 * xxxx yyy yyyyy
 * 通过一个set，根据当前第j个串，看它xxxx在不在这个set里面 => 要遍历长度次
 * O(n * l)
 */

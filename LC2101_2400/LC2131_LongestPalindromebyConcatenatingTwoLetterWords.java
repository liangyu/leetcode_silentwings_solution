package LC2101_2400;
import java.util.*;
public class LC2131_LongestPalindromebyConcatenatingTwoLetterWords {
    /**
     * You are given an array of strings words. Each element of words consists of two lowercase English letters.
     *
     * Create the longest possible palindrome by selecting some elements from words and concatenating them in any order.
     * Each element can be selected at most once.
     *
     * Return the length of the longest palindrome that you can create. If it is impossible to create any palindrome,
     * return 0.
     *
     * A palindrome is a string that reads the same forward and backward.
     *
     * Input: words = ["lc","cl","gg"]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= words.length <= 10^5
     * words[i].length == 2
     * words[i] consists of lowercase English letters.
     * @param words
     * @return
     */
    // S1: HashMap
    // time = O(n), space = O(n)
    public int longestPalindrome(String[] words) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) map.put(word, map.getOrDefault(word, 0) + 1);

        int res = 0;
        boolean flag = false;
        for (String word : words) {
            if (!map.containsKey(word)) continue;
            if (word.charAt(0) == word.charAt(1)) { // xx
                int count = map.get(word);
                res += count / 2 * 2 * 2;
                if (count % 2 == 1) flag = true; // 有落单的xx,标记下，因为只能使用1次放在正中间！
                map.remove(word);
            } else { // ab and ba
                String rev = reverse(word);
                if (map.containsKey(rev)) {
                    int cnt1 = map.get(word), cnt2 = map.get(rev);
                    res += Math.min(cnt1, cnt2) * 2 * 2;
                    map.remove(word);
                    map.remove(rev);
                }
            }
        }
        return res + (flag ? 2 : 0);
    }

    private String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        return sb.reverse().toString();
    }

    // S2:HashMap
    // time = O(n), space = O(n)
    public int longestPalindrome2(String[] words) {
        HashMap<String, Integer> count1 = new HashMap<>(); // ab
        HashMap<String, Integer> count2 = new HashMap<>(); // ba, key统一做ab
        HashMap<String, Integer> count3 = new HashMap<>(); // xx

        for (String str : words) {
            String str2 = reverse(str);
            if (str.equals(str2)) count3.put(str, count3.getOrDefault(str, 0) + 1);
            else {
                String key = str.compareTo(str2) < 0 ? str : str2;
                if (key.equals(str)) count1.put(key, count1.getOrDefault(key, 0) + 1);
                else count2.put(key, count1.getOrDefault(key, 0) + 1);
            }
        }

        int res = 0;
        for (String key : count1.keySet()) {
            int a = count1.get(key), b = count2.get(key);
            int k = Math.min(a, b);
            res += k * 2 * 2;
        }

        boolean flag = false;
        for (int val : count3.values()) {
            int k = val / 2;
            res += k * 2 * 2;
            if (val % 2 == 1) flag = true;
        }

        return res + (flag ? 2 : 0);
    }
}
/**
 * 设计回文串，左右对称
 * ab：count1
 * ba: count2
 * k = min(count1, count2)
 * res += k*2*2 (第1个2表示每个字符串含2个字符，第2个2表示左边放一个，右边放一个)
 * 另一个途径：
 * xx: count3
 * 首选一对对左右两边放置
 * k = count3/2
 * res += k*2*2
 * 最后一个case:落单的case xx
 * 自个放正中间，不需要与别人配对
 * res += 2
 * (()) -> 不是回文串
 * ())( -> 这个才是回文串
 */
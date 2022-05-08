package LC2101_2400;
import java.util.*;
public class LC2213_LongestSubstringofOneRepeatingCharacter {
    /**
     * You are given a 0-indexed string s. You are also given a 0-indexed string queryCharacters of length k and a
     * 0-indexed array of integer indices queryIndices of length k, both of which are used to describe k queries.
     *
     * The ith query updates the character in s at index queryIndices[i] to the character queryCharacters[i].
     *
     * Return an array lengths of length k where lengths[i] is the length of the longest substring of s consisting of
     * only one repeating character after the ith query is performed.
     *
     * Input: s = "babacc", queryCharacters = "bcb", queryIndices = [1,3,3]
     * Output: [3,3,4]
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of lowercase English letters.
     * k == queryCharacters.length == queryIndices.length
     * 1 <= k <= 10^5
     * queryCharacters consists of lowercase English letters.
     * 0 <= queryIndices[i] < s.length
     * @param s
     * @param queryCharacters
     * @param queryIndices
     * @return
     */
    TreeMap<Integer, Integer> map; // key: interval head, val: interval tail
    TreeMap<Integer, Integer> multiSet; // lengths of all intervals sorted in map
    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        map = new TreeMap<>();
        multiSet = new TreeMap<>();
        char[] chars = s.toCharArray();
        int n = chars.length;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && chars[j] == chars[i]) j++;
            map.put(i, j - 1);
            multiSet.put(j - i, multiSet.getOrDefault(j - i, 0) + 1);
            i = j - 1;
        }

        int m = queryIndices.length;
        int[] res = new int[m];
        for (int k = 0; k < queryIndices.length; k++) {
            int idx = queryIndices[k];
            if (chars[idx] != queryCharacters.charAt(k)) {
                insert(idx);
                chars[idx] = queryCharacters.charAt(k);
                mergeRight(idx, chars);
                mergeLeft(idx, chars);
            }
            res[k] = multiSet.lastKey();
        }
        return res;
    }

    private void insert(int idx) {
        Integer iter = map.floorKey(idx); // will not be null
        int a = iter, b = map.get(iter);
        if (a == b && b == idx) return;
        removeInterval(a);
        if (a == idx) {
            addInterval(a, a);
            addInterval(a + 1, b);
        } else if (b == idx) {
            addInterval(b, b);
            addInterval(a, b - 1);
        } else {
            addInterval(a, idx - 1);
            addInterval(idx, idx);
            addInterval(idx + 1, b);
        }
    }

    private void mergeRight(int idx, char[] chars) {
        if (idx == chars.length - 1) return;
        if (chars[idx] != chars[idx + 1]) return;

        int a = idx + 1, b = map.get(a);
        removeInterval(idx);
        removeInterval(a);
        addInterval(idx, b);
    }

    private void mergeLeft(int idx, char[] chars) {
        if (idx == 0) return;
        if (chars[idx] != chars[idx - 1]) return;

        Integer iter = map.lowerKey(idx);
        int a = iter, b = map.get(a);
        int c = map.get(idx);

        removeInterval(idx);
        removeInterval(a);
        addInterval(a, c);
    }

    private void addInterval(int a, int b) {
        map.put(a, b);
        multiSet.put(b - a + 1, multiSet.getOrDefault(b - a + 1, 0) + 1);
    }

    private void removeInterval(int a) {
        int b = map.get(a);
        map.remove(a);
        multiSet.put(b - a + 1, multiSet.get(b - a + 1) - 1);
        if (multiSet.get(b - a + 1) == 0) multiSet.remove(b - a + 1);
    }
}
/**
 * 一连串字符的拼接
 * 区间之间紧密连接
 * 把字母改了之后可能区间变多了
 * 也有可能改的字母在当中，会可能增加2个区间
 * 也有可能减少区间，造成区间合并
 * 改一个字母对区间的影响，可能会增加，也可能会减少
 * 分3步走：不直接考虑它到底会增加，减少还是不变
 * 先暂时把它增加起来，也许后续还是会有合并
 * 1. insert
 * 2. mergeRight
 * 3. mergeLeft
 * 肯定能找到一个区间包含idx:  a <= idx <= b
 * 3种情况：
 * 1. 改变idx在头部: [a,a], [a+1,b]; [a,b-1],[b,b]; [a,idx-1],[idx,idx],[idx+1,b]
 * 所有操作都是删区间，增区间
 * 给我一个idx，立马把idx找出来
 * 用TreeMap: key 区间头，val 区间尾  -> 最后一个<= idx 的位置作为区间头
 */

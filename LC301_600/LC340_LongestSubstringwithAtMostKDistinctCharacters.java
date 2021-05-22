package LC301_600;
import java.util.*;
public class LC340_LongestSubstringwithAtMostKDistinctCharacters {
    /**
     * Given a string s and an integer k, return the length of the longest substring of s that contains at most k
     * distinct characters.
     *
     * Input: s = "eceba", k = 2
     * Output: 3
     *
     * Input: s = "aa", k = 1
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 5 * 104
     * 0 <= k <= 50
     *
     * @param s
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k <= 0) return 0;

        int start = 0, max = 0, curK = 0;
        int[] counter = new int[256]; // new一个int[], 考虑extended ASCII

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (counter[c]++ == 0) curK++; // 注意不管curK是否需要更新，都必须更新counter[c]
            while (curK > k) {
                if (--counter[s.charAt(start++)] == 0) curK--;
                // 收缩左边缘的同时counter也要同步减少，直到distinct char的数目减少到k为止
            }
            max = Math.max(max, i - start + 1); // check当前有效window长度 = i - start + 1与max的大小关系，是否需要更新
        }
        return max;
    }
}

/**
 * 这题只能通过new一个int[256]来进行counter统计，不能用HashSet，原因在于在一个window里可能会存在duplicate char,只要保证distinct的chars
 * 最多为K个即可，所以必须要统计到每个char在该window里的具体个数，而不能直接去重。只有当超过K个时，收缩左边界，同时看当前window的curK是否需要
 * 变化减小，直至变为k为止，统计长度与max进行比较更新，所以要满足以上条件，必须用一个int[]。
 */

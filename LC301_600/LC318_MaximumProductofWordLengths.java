package LC301_600;
import java.util.*;
public class LC318_MaximumProductofWordLengths {
    /**
     * Given a string array words, return the maximum value of length(word[i]) * length(word[j]) where the two words do
     * not share common letters. If no such two words exist, return 0.
     *
     * Input: words = ["abcw","baz","foo","bar","xtfn","abcdef"]
     * Output: 16
     *
     * Constraints:
     *
     * 2 <= words.length <= 1000
     * 1 <= words[i].length <= 1000
     * words[i] consists only of lowercase English letters.
     * @param words
     * @return
     */
    // time = O(n^2), space = O(n)
    public int maxProduct(String[] words) {
        int n = words.length, res = 0;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            int state = 0;
            for (char c : words[i].toCharArray()) {
                state |= 1 << (c - 'a');
            }
            arr[i] = state;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((arr[i] & arr[j]) == 0) {
                    res = Math.max(res, words[i].length() * words[j].length());
                }
            }
        }
        return res;
    }
}
/**
 * 预处理一遍words，利用o(n)的时间将其字母信息存储下来，当穷举两两组合时所需要的判定时间减少
 * 因为小写字母只有26个，可以用一个32位的int每一个bit来代表一个字母出现与否。这样只用一个int就能编码一个word所包含的字母信息了。
 * 两个word是否含有有相同的字母，则让两个对应code进行与操作，如果结果不是0，说明有一个bit不是零，代表了这个字母在两个word里都出现过。
 */

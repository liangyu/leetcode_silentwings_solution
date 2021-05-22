package LC001_300;
import java.util.*;
public class LC38_CountandSay {
    /**
     * The count-and-say sequence is a sequence of digit strings defined by the recursive formula:
     *
     * countAndSay(1) = "1"
     * countAndSay(n) is the way you would "say" the digit string from countAndSay(n-1), which is then converted into a
     * different digit string.
     * To determine how you "say" a digit string, split it into the minimal number of groups so that each group is a
     * contiguous section all of the same character. Then for each group, say the number of characters, then say the
     * character. To convert the saying into a digit string, replace the counts with a number and concatenate every
     * saying.
     *
     * Input: n = 4
     * Output: "1211"
     *
     * Constraints:
     *
     * 1 <= n <= 30
     * @param n
     * @return
     */
    // time = O(2^n), space = O(2^(n - 1)) for space of sb
    public String countAndSay(int n) {
        int i = 1;
        String res = "1";
        while (i < n) {
            int count = 0;
            char c = res.charAt(0);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j <= res.length(); j++) { // 注意这里是<=，最后==的时候要把最后的结果append进入sb，否则最后一组会遗漏
                if (j != res.length() && res.charAt(j) == c) count++; // 注意j最后会出界的情况！
                else {
                    sb.append(count);
                    sb.append(c);
                    if (j != res.length()) { // 只要j还没出界，出现下一个不重复的数时就要重新统计count，以及对应的新digit
                        count = 1;
                        c = res.charAt(j);
                    }
                }
            }
            res = sb.toString();
            i++;
        }
        return res;
    }
}

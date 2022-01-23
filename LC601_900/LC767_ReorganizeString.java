package LC601_900;
import java.util.*;
public class LC767_ReorganizeString {
    /**
     * Given a string s, rearrange the characters of s so that any two adjacent characters are not the same.
     *
     * Return any possible rearrangement of s or return "" if not possible.
     *
     * Input: s = "aab"
     * Output: "aba"
     *
     * Constraints:
     *
     * 1 <= s.length <= 500
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // S1: 跳着构造 (最优解!)
    // time = O(n), space = O(n)
    public String reorganizeString(String s) {
        int n = s.length(), max = 0, maxIdx = 0;
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
            if (max < count[c - 'a']) {
                max = count[c - 'a'];
                maxIdx = c - 'a';
            }
        }
        if (max > (n + 1) / 2) return "";

        char[] chars = new char[n];
        int idx = 0;
        while (count[maxIdx] > 0) { // 优先首先安排放入频率最高的元素，剩下的元素插入顺序无关紧要
            chars[idx] = (char)('a' + maxIdx);
            idx += 2;
            count[maxIdx]--;
        }

        for (int i = 0; i < 26; i++) {
            while (count[i] > 0) {
                if (idx >= n) idx = 1;
                chars[idx] = (char)(i + 'a');
                idx += 2;
                count[i]--;
            }
        }
        return String.valueOf(chars);
    }

    // S2: 顺着构造(PQ)
    // time = O(nlogn), space = O(n)
    public String reorganizeString2(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1]);

        for (int i = 0; i < 26; i++) {
            if (count[i] == 0) continue;
            pq.offer(new int[]{count[i], i});
        }

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int k = Math.min(2, pq.size()); // k可能是一个1
            List<int[]> temp = new ArrayList<>(); // 暂存p
            for (int i = 0; i < k; i++) {
                int[] cur = pq.poll();
                int freq = cur[0];
                char c = (char)(cur[1] + 'a');
                sb.append(c);
                freq--;
                // 不能马上放回pq,否则会出现问题，如果有个char频次特别多，马山放回去又会被拿出来，所以要最后再塞回去
                if (freq > 0) temp.add(new int[]{freq, cur[1]});
            }
            if (k == 1 && temp.size() > 0) return ""; // a并没有取完！构造失败！{1 : a} ok  {2 : a} x
            for (int[] x : temp) pq.offer(x);
        }
        return sb.toString();
    }

    // S3: 跳着构造
    // time = O(nlogn), space = O(n)
    public String reorganizeString3(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;

        List<int[]> arr = new ArrayList<>(); // {freq, char}
        for (char c : s.toCharArray()) {
            if (count[c - 'a'] == 0) continue;
            arr.add(new int[]{count[c - 'a'], c - 'a'});
        }

        // 注意：写lambda expression时要考虑freq相等的情况如何去排序呢？a ： 5， b : 5 不能交错排序！！！
        Collections.sort(arr, (o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1]); // freq高的在前面

        // 逐个先在odd位填充，再在even位填充
        int n = s.length(), i = 0; // res里填充哪个位置
        char[] res = new char[n];
        for (int[] x : arr) {
            int cnt = x[0];
            char c = (char)('a' + x[1]);
            res[i] = c;
            if (i >= 1 && res[i] == res[i - 1]) return ""; // 相邻填充两元素如果相等，表示无解！！！
            i += 2;
            if (i >= n) i = 1;
        }
        return String.valueOf(res);
    }
}
/**
 * 用PQ的贪心法比较好 -> 哪个频次多就用哪个
 * aaaaaaaa
 * bbbbbb
 * cccc
 * ddd
 * [ab][ab][ab][ac][cd]
 * 每次都取频次最多的2个
 * S2: no sort
 * count letter appearance and store in hash[i]
 * find the letter with largest occurence.
 * put the letter into even index numbe (0, 2, 4 ...) char array
 * put the rest into the array
 *
 * 把所有的字符按照词频重新排序，优先处理词频比较高的字符
 * 主动让它们彼此规避，不让它们相邻 => 往上面填充 a_a_a_a_a_a_
 * 如果发现到头了，就从头填充
 * 除非什么情况下失败？=> 如果a特别多，超过半数，这样尽管努力把a分开，但是总会有a彼此相接触
 * 最高频字符 > 总个数的一半，那就无解
 * 贪心思想 => 排序 + 先奇数位再偶数位
 *
 * use PQ -> 按照词频排序，弹出 + 放入
 * 顺序构造: aaaabbbcc
 * 首先能用词频高的
 * 目的是让剩下的字符词频尽量接近
 * 如果不优先选择词频高的，会更早消耗掉词频低的
 * => 尽量消耗词频比较高的
 * 一次要构造2个不同字符
 * aaaabbbcc
 * res = ababacacb
 * ref: LC1054 一模一样， LC1953
 * 2种做法: 跨着构造，顺序构造！！！
 *
 * 为什么要优先排列频次最高的？否则遇到下面的例子就会出错：
 * 输入：
 * "vvvlo"
 * 输出：
 * "lvovv"
 * 预期结果：
 * "vlvov"
 */

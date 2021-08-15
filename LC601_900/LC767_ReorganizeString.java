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
    // S1: PQ
    // time = O(nlogn), space = O(n)
    public String reorganizeString(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        // 统计词频
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>((o1, o2) -> o1.freq != o2.freq ? o2.freq - o1.freq : o1.c - o2.c);

        for (char key : map.keySet()) {
            pq.offer(new Pair(map.get(key), key));
        }

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            // 1次挑出2个字符
            int k = Math.min(2, pq.size()); // k可能是一个1
            List<Pair> temp = new ArrayList<>(); // 暂存p
            for (int i = 0; i < k; i++) {
                Pair p = pq.poll();
                sb.append(p.c);
                p.freq--;
                // 不能马上放回pq,否则会出现问题，如果有个char频次特别多，马山放回去又会被拿出来，所以要最后再塞回去
                if (p.freq > 0) temp.add(p);
            }
            if (k == 1 && temp.size() > 0) return ""; // {1 : a} ok  {2 : a} x
            for (Pair p : temp) pq.offer(p);
        }
        return sb.toString();
    }

    private class Pair {
        private int freq;
        private char c;
        public Pair(int freq, char c) {
            this.freq = freq;
            this.c = c;
        }
    }

    // S2: count
    // time = O(n), space = O(n)
    public String reorganizeString2(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int n = s.length();
        int[] hash = new int[26];
        for (int i = 0; i < n; i++) hash[s.charAt(i) - 'a']++;

        int max = 0, letter = 0;
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] > max) {
                max = hash[i];
                letter = i;
            }
        }
        if (max > (n + 1) / 2) return "";

        char[] res = new char[n];
        int idx = 0;
        while (hash[letter] > 0) {
            res[idx] = (char)(letter + 'a');
            idx += 2;
            hash[letter]--;
        }

        for (int i = 0; i < hash.length; i++) {
            while (hash[i] > 0) {
                if (idx >= res.length) idx = 1;
                res[idx] = (char)(i + 'a');
                idx += 2;
                hash[i]--;
            }
        }
        return String.valueOf(res);
    }

    // S3: 跨着构造
    // time = O(nlogn), space = O(n)
    public String reorganizeString3(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        // 统计词频
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        // 搞成pair来根据词频排序
        List<Pair> arr = new ArrayList<>();
        for (char ch : s.toCharArray()) {
            arr.add(new Pair(map.get(ch), ch));
        }

        // 注意：写lambda expression时要考虑freq相等的情况如何去排序呢？a ： 5， b : 5 不能交错排序！！！
        Collections.sort(arr, (o1, o2) -> o1.freq != o2.freq ? o2.freq - o1.freq : o1.c - o2.c);

        // 逐个现在odd位填充，再在even位填充
        int n = s.length();
        char[] res = new char[n];
        int i = 0; // res里填充哪个位置
        for (Pair p : arr) {
            // 填充一个隔一个
            res[i] = p.c;
            if (i > 0 && res[i] == res[i - 1]) return ""; // 相邻填充两元素如果相等，表示无解！！！
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
 */

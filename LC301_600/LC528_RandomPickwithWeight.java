package LC301_600;
import java.util.*;
public class LC528_RandomPickwithWeight {
    /**
     * You are given an array of positive integers w where w[i] describes the weight of ith index (0-indexed).
     *
     * We need to call the function pickIndex() which randomly returns an integer in the range [0, w.length - 1].
     * pickIndex() should return the integer proportional to its weight in the w array. For example, for w = [1, 3],
     * the probability of picking the index 0 is 1 / (1 + 3) = 0.25 (i.e 25%) while the probability of picking the
     * index 1 is 3 / (1 + 3) = 0.75 (i.e 75%).
     *
     * More formally, the probability of picking index i is w[i] / sum(w).
     *
     * Input
     * ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
     * [[[1,3]],[],[],[],[],[]]
     * Output
     * [null,1,1,1,1,0]
     *
     * Constraints:
     *
     * 1 <= w.length <= 10000
     * 1 <= w[i] <= 10^5
     * pickIndex will be called at most 10000 times.
     *
     * @param w
     */
    // S1: BS
    // time = O(n), space = O(n)
    List<Integer> list;
    Random random;
    int sum = 0;
    public LC528_RandomPickwithWeight(int[] w) {
        list = new ArrayList<>();
        random = new Random();
        for (int x : w) {
            sum += x;
            list.add(sum);
        }
    }

    // time = O(logn), space = O(1)
    public int pickIndex() {
        int r = random.nextInt(sum) + 1; // 注意：list里放的sum，值域是[1:sum] (1 <= w[i] <= 10^5),而不是从0开始，所以要+1！！！
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < r) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    // S2: TreeMap
    TreeMap<Integer, Integer> map;
    Random rand;
    int total = 0;
    // time = O(nlogn), space = O(n)
    public Solution(int[] w) {
        map = new TreeMap<>();
        rand = new Random();
        for (int i = 0; i < w.length; i++) {
            total += w[i];
            map.put(total, i);
        }
    }
    // time = O(logn), space = O(n)
    public int pickIndex2() {
        int r = rand.nextInt(total) + 1;
        return map.get(map.ceilingKey(r));
    }
}

/**
 *  0 1 2 3 4 5 6
 *  1 2 3 2 1 4 2
 *
 *  0 1 1 2 2 2 3 3 4 5 5 5 5 6 6 -> 等概率在里面取 (均匀采样)
 *  这种方法的大问题就是如果数字很大就会爆！！！
 *  rand() % K ~ U[0, K - 1]
 *
 *  概率密度分布 -> 转化成累积概率分布
 *  <= 0 的概率 = 1
 *  <= 1 的概率 = 1 + 2 = 3
 *  <= 2 的概率 = 1 + 2 + 3 = 6
 *  <= 3       = 8
 *  <= 4       = 9
 *  <= 5       = 13
 *  <= 6       = 15
 *
 *  cumulative probability distribution
 *  15份权重里均匀分布采样，这时加入采样采到1个7，7在6和8中间，这里就是在2与3中间
 *    1    2       3         2      1          4           2
 *   [0] [1,2] [3, 4, 5]  [6, 7]   [8] [9, 10, 11, 12] [13, 14]
 *
 *   rand() % K ~ U[0, K - 1]  => if 10, 输出5，占4份地盘
 *
 *   每次都拿区间的尾代替这个区间 => 0 2 5 7 8 12 14 => 找第一个 >= r 的位置即可
 */

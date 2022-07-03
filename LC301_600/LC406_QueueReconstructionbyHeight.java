package LC301_600;
import java.util.*;
public class LC406_QueueReconstructionbyHeight {
    /**
     * You are given an array of people, people, which are the attributes of some people in a queue (not necessarily in
     * order). Each people[i] = [hi, ki] represents the ith person of height hi with exactly ki other people in front
     * who have a height greater than or equal to hi.
     *
     * Reconstruct and return the queue that is represented by the input array people. The returned queue should be
     * formatted as an array queue, where queue[j] = [hj, kj] is the attributes of the jth person in the queue
     * (queue[0] is the person at the front of the queue).
     *
     * Input: people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
     * Output: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
     *
     * Constraints:
     *
     * 1 <= people.length <= 2000
     * 0 <= hi <= 10^6
     * 0 <= ki < people.length
     * It is guaranteed that the queue can be reconstructed.
     * @param people
     * @return
     */
    // S1
    // time = O(nlogn), space = O(n)
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1]);

        int n = people.length;
        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (res.size() == 0) res.add(people[i]);
            else {
                int pos = people[i][1];
                res.add(pos, people[i]);
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    // S2: BIT
    // time = O(nlogn), space = O(n)
    int n;
    int[] tr;
    public int[][] reconstructQueue2(int[][] people) {
        n = people.length;
        tr = new int[n + 1];

        Arrays.sort(people, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);

        int[][] res = new int[n][2];
        for (int[] p : people) {
            int h = p[0], k = p[1];
            int l = 1, r = n;
            while (l < r) {
                int mid = l + (r - l) / 2;
                if (mid - query(mid) >= k + 1) r = mid;
                else l = mid + 1;
            }
            res[l - 1] = p;
            add(l, 1);
        }
        return res;
    }

    private void add(int x, int v) {
        for (int i = x; i <= n; i += i & -i) tr[i] += v;
    }

    private int query(int x) {
        int res = 0;
        for (int i = x; i > 0; i -= i & -i) res += tr[i];
        return res;
    }
}
/**
 * 此题我们来这样考虑，比如对于[h,k]来说，如果只考虑那些身高比他高、位置比他靠前的人群，他是排第k+1个。
 * 那如果我们把所有身高比他高的人都已经拉了出来排成一排，我们再将这个人塞进第k+1位，也是不违和的。
 * 因为剩下那些比他矮的人，无论放在哪里，都不会再影响到k这个数值。
 * 于是，贪心的方法很简单。我们按身高依次处理。
 * 当处理某人时，所有比他高的都已经处理完了，然后将该人放在第k+1个位置即可。
 * 他的插入不会对之前那些“高人”的排名产生任何的影响。依次类推处理完所有的人。
 * 这里，当有两个人的身高相同怎么办呢？先处理k小的，他优先插入，优先得到更靠前的位置。
 *
 * 1. 排序： 身高 h inc  k dec
 * 2. 对(h,k) 从前往后找到第1个空位，使得当前空位前面有k个空位
 * 二分 mid
 * 树状数组 O(logn)^2
 */
package LC901_1200;
import java.util.*;
public class LC1054_DistantBarcodes {
    /**
     * In a warehouse, there is a row of barcodes, where the ith barcode is barcodes[i].
     *
     * Rearrange the barcodes so that no two adjacent barcodes are equal. You may return any answer, and it is
     * guaranteed an answer exists.
     *
     * Input: barcodes = [1,1,1,2,2,2]
     * Output: [2,1,2,1,2,1]
     *
     * Constraints:
     *
     * 1 <= barcodes.length <= 10000
     * 1 <= barcodes[i] <= 10000
     * @param barcodes
     * @return
     */
    // S1: 跳着构造(最优解!)
    // time = O(n), space = O(n)
    public int[] rearrangeBarcodes(int[] barcodes) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 0, maxNum = 0, n = barcodes.length;
        for (int x : barcodes) {
            map.put(x, map.getOrDefault(x, 0) + 1);
            if (max < map.get(x)) {
                max = map.get(x);
                maxNum = x;
            }
        }

        int[] res = new int[n];
        int idx = 0;
        while (map.get(maxNum) > 0) {
            res[idx] = maxNum;
            idx += 2;
            map.put(maxNum, map.get(maxNum) - 1);
        }

        for (int x : map.keySet()) {
            if (map.get(x) == 0) continue;
            while (map.get(x) > 0) {
                if (idx >= n) idx = 1;
                res[idx] = x;
                idx += 2;
                map.put(x, map.get(x) - 1);
            }
        }
        return res;
    }

    // S2: 顺着构造
    // time = O(nlogn), space = O(n)
    public int[] rearrangeBarcodes2(int[] barcodes) {
        // corner case
        if (barcodes == null || barcodes.length == 0) return new int[0];

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : barcodes) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1]);

        for (int key : map.keySet()) {
            pq.offer(new int[]{map.get(key), key}); // {freq, num}
        }

        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            if (pq.size() == 1 && pq.peek()[0] > 1) return new int[0]; // failed case
            // choose 2 different kinds of digits
            int len = pq.size();
            List<int[]> temp = new ArrayList<>();
            for (int i = 0; i < Math.min(2, len); i++) {
                res.add(pq.peek()[1]);
                if (pq.peek()[0] > 1) temp.add(pq.peek());
                pq.poll();
            }
            for (int[] x : temp) {
                x[0]--;
                pq.offer(x);
            }
        }
        int[] ans = new int[res.size()];
        int i = 0;
        for (int x : res) ans[i++] = x;
        return ans;
    }

    // S3: 跳着构造
    // time = O(nlogn), space = O(n)
    public int[] rearrangeBarcodes3(int[] barcodes) {
        // corner case
        if (barcodes == null || barcodes.length == 0) return new int[0];

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : barcodes) map.put(x, map.getOrDefault(x, 0) + 1);

        List<int[]> p = new ArrayList<>();
        for (int x : barcodes) p.add(new int[]{map.get(x), x});

        Collections.sort(p, (o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1]);

        int[] res = new int[barcodes.length];
        int i = 0;
        for (int[] x : p) {
            res[i] = x[1];
            i += 2;
            if (i >= barcodes.length) i = 1;
        }
        return res;
    }


}
/**
 * ref: LC767 一模一样，LC358特例
 * 用一个PQ来存储出现的频次，每次取2个不同种类的数字
 * 优先取频次比较多的
 * 希望留下的diversity越多越好
 * 1 1 1 1
 * 2 2
 * 3 3
 * 套路解法：可以延伸到两个数字之间任意k个
 * S2: 排序 + 交错排列
 * 333333322221111
 * 323232313131
 */

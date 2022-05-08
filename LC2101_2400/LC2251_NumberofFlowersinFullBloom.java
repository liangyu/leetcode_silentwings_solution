package LC2101_2400;
import java.util.*;
public class LC2251_NumberofFlowersinFullBloom {
    /**
     * You are given a 0-indexed 2D integer array flowers, where flowers[i] = [starti, endi] means the ith flower will
     * be in full bloom from starti to endi (inclusive). You are also given a 0-indexed integer array persons of size n,
     * where persons[i] is the time that the ith person will arrive to see the flowers.
     *
     * Return an integer array answer of size n, where answer[i] is the number of flowers that are in full bloom when
     * the ith person arrives.
     *
     * Input: flowers = [[1,6],[3,7],[9,12],[4,13]], persons = [2,3,7,11]
     * Output: [1,2,2,2]
     *
     * Input: flowers = [[1,10],[3,3]], persons = [3,3,2]
     * Output: [2,2,1]
     *
     * Constraints:
     *
     * 1 <= flowers.length <= 5 * 10^4
     * flowers[i].length == 2
     * 1 <= starti <= endi <= 10^9
     * 1 <= persons.length <= 5 * 10^4
     * 1 <= persons[i] <= 10^9
     * @param flowers
     * @param persons
     * @return
     */
    // S1: diff array
    // time = O((m + n) * logm), space = O(m)
    public int[] fullBloomFlowers(int[][] flowers, int[] persons) {
        Arrays.sort(flowers, ((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]));

        TreeMap<Integer, Integer> diff = new TreeMap<>();
        for (int[] x : flowers) {
            int a = x[0], b = x[1] + 1;
            diff.put(a, diff.getOrDefault(a, 0) + 1);
            diff.put(b, diff.getOrDefault(b, 0) - 1);
        }

        int sum = 0;
        TreeMap<Integer, Integer> presum = new TreeMap<>();
        for (int key : diff.keySet()) {
            sum += diff.get(key);
            presum.put(key, sum);
        }

        int n = persons.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            Integer fk = presum.floorKey(persons[i]);
            res[i] = fk == null ? 0 : presum.get(fk);
        }
        return res;
    }

    // S2: BIT
    // time = O((m + n) * logm), space = O(m)
    public int[] fullBloomFlowers2(int[][] flowers, int[] persons) {
        int n = persons.length;
        BIT bit = new BIT();

        for (int[] x : flowers) {
            int a = x[0], b = x[1] + 1;
            bit.update(a + 1, 1);
            bit.update(b + 1, -1);
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = bit.query(persons[i] + 1);
        }
        return res;
    }

    private class BIT {
        HashMap<Integer, Integer> bitree;
        public BIT() {
            this.bitree = new HashMap<>();
        }

        private void update(int x, int delta) {
            for (int i = x; i <= (int) 1e9 + 1; i += i & (-i)) {
                bitree.put(i, bitree.getOrDefault(i, 0) + delta);
            }
        }

        private int query(int x) {
            int res = 0;
            for (int i = x; i > 0; i -= i & (-i)) {
                res += bitree.getOrDefault(i, 0);
            }
            return res;
        }
    }

    // S3: diff array
    // time = O(mlogm + nlogn), space = O(m + n)
    public int[] fullBloomFlowers3(int[][] flowers, int[] persons) {
        int m = flowers.length, n = persons.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int a = flowers[i][0], b = flowers[i][1] + 1;
            map.put(a, map.getOrDefault(a, 0) + 1);
            map.put(b, map.getOrDefault(b, 0) - 1);
        }

        List<int[]> diff = new ArrayList<>();
        for (int key : map.keySet()) diff.add(new int[]{key, map.get(key)}); // {time, flowers}
        Collections.sort(diff, (o1, o2) -> o1[0] - o2[0]);

        List<int[]> p = new ArrayList<>();
        for (int i = 0; i < n; i++) p.add(new int[]{persons[i], i});
        Collections.sort(p, (o1, o2) -> o1[0] - o2[0]); // {time, id}

        int j = 0, sum = 0;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            while (j < diff.size() && diff.get(j)[0] <= p.get(i)[0]) sum += diff.get(j++)[1];
            res[p.get(i)[1]] = sum;
        }
        return res;
    }
}
/**
 * 扫描线，差分数组
 * start, end
 * diff[start] = 1
 * diff[end + 1] = -1
 */

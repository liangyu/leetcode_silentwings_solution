package LC2100_2400;
import java.util.*;
public class LC2121_IntervalsBetweenIdenticalElements {
    /**
     * You are given a 0-indexed array of n integers arr.
     *
     * The interval between two elements in arr is defined as the absolute difference between their indices. More
     * formally, the interval between arr[i] and arr[j] is |i - j|.
     *
     * Return an array intervals of length n where intervals[i] is the sum of intervals between arr[i] and each element
     * in arr with the same value as arr[i].
     *
     * Note: |x| is the absolute value of x.
     *
     * Input: arr = [2,1,3,1,2,3,3]
     * Output: [4,2,7,2,4,4,5]
     *
     * Constraints:
     *
     * n == arr.length
     * 1 <= n <= 10^5
     * 1 <= arr[i] <= 10^5
     * @param arr
     * @return
     */
    // S1: presum
    // time = O(n), space = O(n)
    public long[] getDistances(int[] arr) {
        int n = arr.length;
        long[] res = new long[n];
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i);
        }

        for (int key : map.keySet()) {
            List<Integer> list = map.get(key);
            long sum = 0;
            int k = list.size();

            long[] presum = new long[k];
            presum[0] = list.get(0);
            for (int i = 1; i < k; i++) presum[i] = presum[i - 1] + list.get(i);
            for (int i = 0; i < k; i++) {
                long val1 = (long) list.get(i) * (i + 1) - presum[i];
                long val2 = presum[k - 1] - presum[i] - (long) list.get(i) * (k - 1 - i);
                res[list.get(i)] = val1 + val2;
            }
        }
        return res;
    }

    // S2: 结论转移 one pass
    // time = O(n), space = O(n)
    public long[] getDistances2(int[] arr) {
        int n = arr.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i);
        }

        HashMap<Integer, List<Long>> temp = new HashMap<>();
        for (int x : map.keySet()) {
            List<Integer> pos = map.get(x);
            int k = pos.size();
            long sum = 0;
            for (int i = 0; i < k; i++) sum += pos.get(i) - pos.get(0);
            temp.putIfAbsent(x, new LinkedList<>());
            temp.get(x).add(sum); // save res[0]

            for (int i = 1; i < k; i++) {
                sum += (pos.get(i) - pos.get(i - 1)) * i;
                sum -= (pos.get(i) - pos.get(i - 1)) * (k - i);
                temp.get(x).add(sum);
            }
        }

        long[] res = new long[n];
        for (int i = 0; i < n; i++) {
            res[i] = temp.get(arr[i]).get(0);
            temp.get(arr[i]).remove(0);
        }
        return res;
    }
}
/**
 * ref: LC1685
 * 2: [0,4]
 * 1: [1,3]
 * 3: [2,5,6]
 *     7 4 5
 * 看每个小数组里与每个元素差的绝对值之和
 * 结论转移：one pass
 * x x x x i-1, i, x x x x x
 * res[i-1] => res[i]
 * res[i-1] = sum |nums[i-1] - nums[k]| k=0,1,2,...i-1
 *           +sum |nums[i-1] - nums[k]| k=i,i+1,...n-1
 * res[i] = sum |nums[i] - nums[k]| k=0,1,2,...i-1
 *           +sum |nums[i] - nums[k]| k=i,i+1,...n-1
 * res[i]-res[i-1] = (nums[i]-nums[i-1])*i - (nums[i]-nums[i-1])*(n-i)
 */
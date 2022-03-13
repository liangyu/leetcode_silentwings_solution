package LC1201_1500;
import java.util.*;
public class LC1414_FindtheMinimumNumberofFibonacciNumbersWhoseSumIsK {
    /**
     * Given an integer k, return the minimum number of Fibonacci numbers whose sum is equal to k. The same Fibonacci
     * number can be used multiple times.
     *
     * The Fibonacci numbers are defined as:
     *
     * F1 = 1
     * F2 = 1
     * Fn = Fn-1 + Fn-2 for n > 2.
     * It is guaranteed that for the given constraints we can always find such Fibonacci numbers that sum up to k.
     *
     * Input: k = 7
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= k <= 10^9
     * @param k
     * @return
     */
    // S1:
    // time = O(logk), space = O(logk)
    public int findMinFibonacciNumbers(int k) {
        List<Integer> list = new ArrayList<>();
        list.add(1);

        int a = 1, b = 1;
        while (a + b <= k) {
            int c = a + b;
            list.add(c);
            a = b;
            b = c;
        }

        int res = 0, n = list.size();
        for (int i = n - 1; i >= 0; i--) {
            if (k >= list.get(i)) {
                k -= list.get(i);
                res++;
            }
        }
        return res;
    }

    // S2: B.S.
    // time = O(logk), space = O(logk)
    public int findMinFibonacciNumbers2(int k) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(1L);
        while (list.get(list.size() - 1) < k) {
            int n = list.size();
            list.add(list.get(n - 1) + list.get(n - 2));
        }

        int count = 0;
        while (k > 0) {
            int idx = lowerBound(list, k);
            count++;
            k -= list.get(idx);
        }
        return count;
    }

    private int lowerBound(List<Long> nums, int t) {
        int left = 0, right = nums.size() - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums.get(mid) <= t) left = mid;
            else right = mid - 1;
        }
        return nums.get(left) <= t ? left : left - 1;
    }
}

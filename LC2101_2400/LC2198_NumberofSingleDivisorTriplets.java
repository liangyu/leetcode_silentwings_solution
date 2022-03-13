package LC2101_2400;
import java.util.*;
public class LC2198_NumberofSingleDivisorTriplets {
    /**
     * You are given a 0-indexed array of positive integers nums. A triplet of three distinct indices (i, j, k) is
     * called a single divisor triplet of nums if nums[i] + nums[j] + nums[k] is divisible by exactly one of
     * nums[i], nums[j], or nums[k].
     *
     * Return the number of single divisor triplets of nums.
     *
     * Input: nums = [4,6,7,3,2]
     * Output: 12
     *
     * Constraints:
     *
     * 3 <= nums.length <= 10^5
     * 1 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public long singleDivisorTriplet(int[] nums) {
        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);

        List<int[]> combos = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            if (map.containsKey(i)) {
                for (int j = 1; j <= 100; j++) {
                    if (map.containsKey(j)) {
                        if (i == j && map.get(i) < 2) continue;
                        for (int k = 1; k <= 100; k++) {
                            if (map.containsKey(k)) {
                                if (i == j && j == k && map.get(i) < 3) continue;
                                if (j == k && map.get(j) < 2) continue;
                                if (k == i && map.get(k) < 2) continue;
                                if (helper(i, j, k)) combos.add(new int[]{i, j, k});
                            }
                        }
                    }
                }
            }
        }

        long res = 0;
        for (int[] x : combos) {
            int a = map.get(x[0]);
            int b = map.get(x[1]);
            int c = map.get(x[2]);
            if (x[0] == x[1] && x[1] == x[2]) res += (long) a * (a - 1) * (a - 2);
            else if (x[0] == x[1]) res += (long) a * (a - 1) * c;
            else if (x[1] == x[2]) res += (long) b * (b - 1) * a;
            else if (x[2] == x[0]) res += (long) c * (c - 1) * b;
            else res += (long) a * b * c;
        }
        return res;
    }

    private boolean helper(int a, int b, int c) {
        int sum = a + b + c;
        if (sum % a == 0 && sum % b != 0 && sum % c != 0) return true;
        if (sum % b == 0 && sum % a != 0 && sum % c != 0) return true;
        if (sum % c == 0 && sum % a != 0 && sum % b != 0) return true;
        return false;
    }
}

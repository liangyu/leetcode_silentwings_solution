package LC2101_2400;
import java.util.*;
public class LC2122_RecovertheOriginalArray {
    /**
     * Alice had a 0-indexed array arr consisting of n positive integers. She chose an arbitrary positive integer k and
     * created two new 0-indexed integer arrays lower and higher in the following manner:
     *
     * lower[i] = arr[i] - k, for every index i where 0 <= i < n
     * higher[i] = arr[i] + k, for every index i where 0 <= i < n
     * Unfortunately, Alice lost all three arrays. However, she remembers the integers that were present in the arrays
     * lower and higher, but not the array each integer belonged to. Help Alice and recover the original array.
     *
     * Given an array nums consisting of 2n integers, where exactly n of the integers were present in lower and the
     * remaining in higher, return the original array arr. In case the answer is not unique, return any valid array.
     *
     * Note: The test cases are generated such that there exists at least one valid array arr.
     *
     * Input: nums = [2,10,6,4,8,12]
     * Output: [3,7,11]
     *
     * Constraints:
     *
     * 2 * n == nums.length
     * 1 <= n <= 1000
     * 1 <= nums[i] <= 10^9
     * The test cases are generated such that there exists at least one valid array arr.
     * @param nums
     * @return
     */
    // S1
    // time = O(nlogn + klogk + n * k), space = O(n + k)
    public int[] recoverArray(int[] nums) {
        Arrays.sort(nums); // O(nlogn)
        int n = nums.length;
        if (n == 2) {
            int k = (nums[1] - nums[0]) / 2;
            return new int[]{nums[0] + k};
        }

        HashSet<Integer> set = new HashSet<>();
        int start = nums[0]; // nums[0] = arr[0] - k
        for (int i = 1; i < n - 1; i++) { // O(n)
            int diff = nums[i] - start;
            if (diff == 0 || diff % 2 != 0) continue;
            set.add(diff / 2);
        }

        start = nums[n - 1]; // nums[n - 1] = arr[n - 1] + k
        List<Integer> kList = new ArrayList<>();
        for (int i = 1; i < n - 1; i++) {  // O(n)
            int diff = start - nums[i];
            if (diff == 0 || diff % 2 != 0) continue;
            if (!set.contains(diff / 2)) continue;
            kList.add(diff / 2);
        }

        Collections.sort(kList); // O(klogk)

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1); // O(n)

        int[] res = new int[n / 2];
        for (int k : kList) {  // O(k)
            HashMap<Integer, Integer> copy = new HashMap<>(map);
            int idx = 0;
            res = new int[n / 2];
            for (int i = 0; i < n; i++) {  // O(n)
                if (!copy.containsKey(nums[i])) continue; // already visited
                int num = nums[i] + k;
                res[idx++] = num;
                if (!copy.containsKey(num + k)) break; // this k doesn't work, try next k

                copy.put(nums[i], copy.get(nums[i]) - 1);
                if (copy.get(nums[i]) == 0) copy.remove(nums[i]);
                copy.put(num + k, copy.get(num + k) - 1);
                if (copy.get(num + k) == 0) copy.remove(num + k);
            }
            if (copy.size() == 0) break; // find one solution
        }
        return res;
    }

    // S2: Two pointers
    public int[] recoverArray2(int[] nums) {
        int n = nums.length / 2;
        Arrays.sort(nums);

        for (int i = 1; i <= n; i++) { // 至少有n - 1个数比min(arr) + k要大，所以只要考虑1~n
            if (nums[i] == nums[0]) continue;
            if ((nums[i] - nums[0]) % 2 == 1) continue;
            int k = (nums[i] - nums[0]) / 2;

            int left = 0, right = 0;
            int[] res = new int[n];
            boolean[] visited = new boolean[2 * n];
            boolean flag = true;
            for (int t = 0; t < n; t++) {
                while (left < 2 * n && visited[left]) left++;
                if (left == 2 * n) {
                    flag = false;
                    break;
                }
                visited[left] = true;
                while (right < 2 * n && (visited[right] || nums[right] != nums[left] + 2 * k)) right++;
                if (right < 2 * n) {
                    res[t] = nums[left] + k;
                    visited[right] = true;
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) return res;
        }
        return new int[0];
    }
}
/**
 * construct problem
 * ref: LC2007
 * nums里最小值和最大值可以确定
 * min(nums) = min(arr) - k
 *             min(arr) + k -> 并不能直接确定，只能做一个遍历 O(n) -> k -> O(n^2)
 * max(nums) = max(arr) + k
 * z y y y y
 * o x x x x o x x x x
 * nums[left] = z - k
 */


